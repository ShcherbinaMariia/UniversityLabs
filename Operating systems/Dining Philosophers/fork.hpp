#pragma once
#include <mutex>

class Fork{
	std::mutex _lock;
	int _order;

public:
	Fork(int order);
	int getOrder();
	void pick();
	void put();
};