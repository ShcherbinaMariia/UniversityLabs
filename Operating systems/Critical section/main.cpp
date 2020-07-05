#include <iostream>
#include <chrono>
#include <thread>
#include "peterson_mutex.hpp" 
using namespace std;

int increments_per_thread = 100000;
int shared_variable;
PetersonMutex shared_variable_mutex;

void increment(int threadNumber){
	for (int i = 0; i < increments_per_thread; i++){
		shared_variable = shared_variable + 1;
	}
}

void syncronizedIncrement(int threadNumber){
	for (int i = 0; i < increments_per_thread; i++){
		shared_variable_mutex.lock(threadNumber);
		shared_variable = shared_variable + 1;
		shared_variable_mutex.unlock(threadNumber);
	}
}

void execute(void (*f)(int)) {
	shared_variable = 0;
	auto start = chrono::high_resolution_clock::now();

	thread t0 = thread(f, 0);
	thread t1 = thread(f, 1);

	t0.join();
	t1.join();

	auto stop = chrono::high_resolution_clock::now();
	auto duration = chrono::duration_cast<chrono::microseconds>(stop - start); 
	cout << "Resulting value: " << shared_variable << "\n";
	cout << "Execution time: " << duration.count() << " microseconds\n"; 
}

int main() {
	cout << "Without syncronization\n";
	execute(increment);

	cout << "Using syncronization\n";
	execute(syncronizedIncrement);
	return 0;
}