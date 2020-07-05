#include "main.hpp"
using namespace std;
using namespace chrono;

double getTime(void (*f1)(), void (*f2)()){
	auto start1 = steady_clock::now();
	f1();
	auto end1 = steady_clock::now();
	auto elapsed1 = duration_cast<milliseconds>(end1 - start1);
	auto start2 = steady_clock::now();
	f2();
	auto end2 = steady_clock::now();
	auto elapsed2 = duration_cast<milliseconds>(end2 - start2);
	double totalTime = (elapsed1 - elapsed2).count();
	return 1000.0 * N * numberOfOperationsPerCycle / totalTime;
}