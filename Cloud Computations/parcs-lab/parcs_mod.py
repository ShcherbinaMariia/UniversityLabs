from Pyro4 import expose
from collections import namedtuple
import numpy as np

Constraints = namedtuple('Constraints', 'begin end step')
MLE_Value = namedtuple('MLE_Value', 'likelihood A B')

NX = 10
NY = 10

def values(A):
    return (A.end - A.begin)//A.step + 1

def calculate_likelihood(A, B):
        return 1.0/(1 + np.exp(A**2 - 5*B - 4*A*B - 10*A + B**2))

def check_conditions(A, B):
    return (A-B)*np.cos(B) < np.sin(A) * np.exp(A+B)

def decode_MLE_Value(t):
    return MLE_Value(t[0], t[1], t[2])

def decode_constrains(t):
    return Constraints(t[0], t[1], t[2])

def get_linspace(a):
    return np.linspace(a.begin, a.end, (a.end - a.begin)//a.step + 1)

def decode_cell(cell, cell_A_step, cell_B_step, stepA, stepB):
    a_start = cell / NX
    b_start = cell % NX

    return Constraints(a_start, a_start + cell_A_step, stepA), Constraints(b_start, b_start + cell_B_step, stepB)

def process_cell(A, B):
        current_maxx = MLE_Value(-1, 0, 0)
        print(A)
        A = get_linspace(decode_constrains(A))
        print(A)
        B = get_linspace(decode_constrains(B))
        for a in A:
            for b in B:
                if not check_conditions(a, b):
                    continue
                l = calculate_likelihood(a, b)
                if l > current_maxx[0]:
                    current_maxx = MLE_Value(l, a, b)
        return current_maxx


class Solver:
    def __init__(self, workers=None, input_file_name=None, output_file_name=None):
        self.input_file_name = input_file_name
        self.output_file_name = output_file_name
        self.workers = workers
        print("Inited")

    def solve(self):
        print("Job Started")
        n_workers = len(self.workers)
        print("Workers %d" % n_workers)
        A, B = self.read_input()

        area = values(A) * values(B)
        area_per_worker = area//n_workers
        delta_B_per_worker = area_per_worker//values(A)

        cell_A_step = (A.end - A.begin) // NX
        cell_B_step = (B.end - B.begin) // NY

        cell_indices = np.arange(0, NX*NY)
        worker_indices = np.random.randint(0, len(self.workers), (NX * NY))

        # map
        mapped = []
        for i in xrange(0, len(self.workers)):
            mapped.append(self.mymap(cell_indices[worker_indices==i], cell_A_step, cell_B_step, A.step, B.step))

        print 'Map finished: ', mapped

        # reduce
        reduced = self.myreduce(mapped)
        print("Reduce finished: " + str(reduced))

        # output
        self.write_output(reduced)

        print("Job Finished")

    @staticmethod
    @expose
    def mymap(cells, cell_A_step, cell_B_step, stepA, stepB):
        maxx = None
        for cell in cells:
            A, B = decode_cell(cell, cell_A_step, cell_B_step, stepA, stepB)
            current_maxx = process_cell(A, B)
            if maxx is None or current_maxx > maxx:
                maxx = current_maxx
        return maxx

    @staticmethod
    @expose
    def myreduce(mle_values):
        maxx = decode_MLE_Value(mle_values[0])

        for raw_mle_value in mle_values:
            mle_value = decode_MLE_Value(raw_mle_value)
            if mle_value.likelihood > maxx.likelihood:
                maxx = mle_value
        return maxx

    def read_and_parse_constraint(self, f):
        begin, end, step = map(int, f.readline().split())
        return Constraints(begin, end, step)

    def read_input(self):
        f = open(self.input_file_name, 'r')
        A = self.read_and_parse_constraint(f)
        B = self.read_and_parse_constraint(f)
        f.close()

        return A, B

    def write_output(self, result):
        f = open(self.output_file_name, 'w')
        result = decode_MLE_Value(result)
        f.write("The maximum likelihood value is {} using A={} and B={}".format(result.likelihood, result.A, result.B))
        f.write('\n')
        f.close()
