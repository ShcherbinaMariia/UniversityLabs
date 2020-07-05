#pragma once
#include "fork.hpp"

class Philosopher{
	static auto constexpr quantum = std::chrono::milliseconds(5);
	Fork* _leftFork;
	Fork* _rightFork;
	int eatingTimes = 0;
public:
	Philosopher(Fork* leftFork, Fork* rightFork);

	void live(std::chrono::milliseconds lifetime);
	void eat();	
	void think();
	long long getEatingTime();
};