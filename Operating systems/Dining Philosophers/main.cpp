#include <iostream>
#include <thread>
#include "philosopher.hpp"

void addPhilosopher(Philosopher* philosopher, int t) {
	philosopher->live(std::chrono::milliseconds(t));
}

int main() {
	int n, t;
	std::cout << "Enter number of philosophers: ";
	std::cin >> n;

	std::cout << "Enter execution time: ";
	std::cin >> t;

	Fork* forks[n];
	for (int i = 0; i < n; i++){
		forks[i] = new Fork(i);
	}

	std::thread threads[n];
	Philosopher* philosophers[n];
	for (int i = 0; i < n; i++){
		philosophers[i] = new Philosopher(forks[i], forks[(i + 1) % n]);
		threads[i] = std::thread(addPhilosopher, philosophers[i], t);
	}

	for (int i = 0; i < n; i++){
		threads[i].join();
	}

	for (int i = 0; i < n; i++){
		std::cout << "Philosopher " << i << " ate for " << 
					 philosophers[i]->getEatingTime() << " ms \n";
	}
	return 0;
}