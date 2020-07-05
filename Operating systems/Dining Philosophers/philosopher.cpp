#include "fork.hpp"
#include "philosopher.hpp"
#include <thread>
#include<iostream>

void Philosopher::live(std::chrono::milliseconds lifetime){
	auto start = std::chrono::system_clock::now();
	while (true) {
		eat();
		think();
		auto curr = std::chrono::system_clock::now();
		auto elapsedTime = std::chrono::duration_cast<std::chrono::milliseconds>
                             (curr-start);
        if (elapsedTime >= lifetime) 
			return;
	}
}

void Philosopher::think(){
	std::this_thread::sleep_for(Philosopher::quantum);
}

void Philosopher::eat(){
	int leftOrder = _leftFork->getOrder();
	int rightOrder = _rightFork->getOrder();

	if (leftOrder < rightOrder){
		_leftFork->pick();
		_rightFork->pick();
	} else {
		_rightFork->pick();
		_leftFork->pick();
	}
	std::this_thread::sleep_for(Philosopher::quantum);

	if (leftOrder < rightOrder){
		_rightFork->put();
		_leftFork->put();
	} else {
		_leftFork->put();
		_rightFork->put();
	}

	this->eatingTimes++;
}

Philosopher::Philosopher(Fork* leftFork, Fork* rightFork)
{
	this->_leftFork = leftFork;
	this->_rightFork = rightFork;
}

long long Philosopher::getEatingTime(){
	return (this->eatingTimes * Philosopher::quantum).count();
}