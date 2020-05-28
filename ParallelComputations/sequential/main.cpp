#include "checker.h"
#include <chrono>
#include <iostream>
#include <stdlib.h>

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

	CheckerFactory checkerFactory;
	Checker* checker = checkerFactory.createChecker();

	unsigned int result = 0;
	unsigned int chunkSize = (last - first) / n + 1;
	bool found = false;

	auto start = std::chrono::high_resolution_clock::now();

	for (unsigned int curr = 0; curr <= chunkSize; curr++){
		for (int i = 0; i < n; i++){
			if (checker->check(first + chunkSize*i + curr)){
				result = first + chunkSize*i + curr;
				found = true;
				break;
			}
		}
		if (found) break;
	}

	auto stop = std::chrono::high_resolution_clock::now();
	auto duration = std::chrono::duration_cast<std::chrono::microseconds>(stop - start); 
	std::cout << "Result is: " << result << "\n";
	std::cout << "Execution time: " << duration.count() << " microseconds\n";
}