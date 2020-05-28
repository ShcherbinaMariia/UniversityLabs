#include "checker.h"
#include <chrono>
#include <iostream>
#include <mpi.h>

int kCommunicationInterval = 1000;

int num_processes, rank;
int first, last;

void parseArguments(int argc, char* argv[]){
	first = 1;
	last = 1000000;
	for (int i = 0; i < argc; i++){
		if (strcmp(argv[i], "-first") == 0) {
			first = atoi(argv[i+1]);
		}
		if (strcmp(argv[i], "-last") == 0) {
			last = atoi(argv[i+1]);
		}
	}
}

void setLocalRange(){
	int amount = (last - first) / num_processes + 1;
	first = rank * amount;
	last = first + amount;
}

int main(int argc, char* argv[]) {
	MPI_Init(&argc, &argv);
	parseArguments(argc, argv);

	MPI_Comm_size(MPI_COMM_WORLD, &num_processes);
  	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
  	
  	setLocalRange();
	
	CheckerFactory checkerFactory;
	auto start = std::chrono::high_resolution_clock::now();

	Checker* checker = checkerFactory.createChecker();
	int foundLocal = 0;

	for (unsigned int curr = first; curr <= last; curr++){
		if (!foundLocal && checker->check(curr)){
			foundLocal = 1;
			std::cout << "Result is: " << curr << "\n";
		}

		if ((curr - first) % kCommunicationInterval == 0){
			int foundGlobal = 0;
			MPI_Allreduce(&foundLocal, &foundGlobal, 1, MPI_INT, MPI_MAX, MPI_COMM_WORLD);
			if (foundGlobal){
				break;
			}
		}
	}

	auto stop = std::chrono::high_resolution_clock::now();
	auto duration = std::chrono::duration_cast<std::chrono::microseconds>(stop - start);
	if (rank == 0) {
		std::cout << "Execution time: " << duration.count() << " microseconds\n"; 
	}
	MPI_Finalize();
}