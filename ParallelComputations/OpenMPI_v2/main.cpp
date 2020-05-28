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

int main(int argc, char* argv[]) {
	MPI_Init(&argc, &argv);
	parseArguments(argc, argv);

	MPI_Comm_size(MPI_COMM_WORLD, &num_processes);
  	MPI_Comm_rank(MPI_COMM_WORLD, &rank);

  	int* buff = new int[2];
  	int amountPerProcess = (last - first) / num_processes + 1;

  	if (rank == 0){
  		// calculation of local ranges for each process
  		first = 1;
  		last = amountPerProcess;

  		int firstI, lastI;
  		for (int i = 1; i < num_processes; i++){
  			firstI = i * amountPerProcess;
  			lastI = firstI + amountPerProcess;
  			buff[0] = firstI;
  			buff[1] = lastI;
  			MPI_Send(buff, 2, MPI_INT, i, 0, MPI_COMM_WORLD);
  		}
  	} else {
  		MPI_Recv(buff, 2, MPI_INT, 0, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
  		first = buff[0];
  		last = buff[1];
  	}
 	
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