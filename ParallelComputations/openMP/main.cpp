#include "checker.h"
#include <chrono>
#include <iostream>
#include <stdlib.h>
#include <omp.h>

unsigned int first, last, n;
void parseArguments(int argc, char* argv[]){
	first = 1;
	n = 1;
	last = 1000000;
	for (int i = 0; i < argc; i++){
		if (strcmp(argv[i], "-first") == 0) {
			first = atoi(argv[i+1]);
		}
		if (strcmp(argv[i], "-last") == 0) {
			last = atoi(argv[i+1]);
		}
		if (strcmp(argv[i], "-n") == 0) {
			n = atoi(argv[i+1]);
		}
	}
}

int main(int argc, char* argv[]) {
	parseArguments(argc, argv);
	omp_set_num_threads(n);
	
	CheckerFactory checkerFactory;
	Checker* checkers[n];
	for (int i = 0; i < n; i++) 
		checkers[i] = checkerFactory.createChecker();

	unsigned int result = 0;
	bool found = false;

	auto start = std::chrono::high_resolution_clock::now();

	#pragma omp parallel for
	for (unsigned int curr = first; curr < last; curr++){
		if (!found) {
			if (checkers[omp_get_thread_num()]->check(curr)){
				std::cout << curr << "\n";
				result = curr;
				found = true;
			}
		}
	}

	auto stop = std::chrono::high_resolution_clock::now();
	auto duration = std::chrono::duration_cast<std::chrono::microseconds>(stop - start);
	std::cout << "Result is: " << result << "\n";
	std::cout << "Execution time: " << duration.count() << " microseconds\n";
}