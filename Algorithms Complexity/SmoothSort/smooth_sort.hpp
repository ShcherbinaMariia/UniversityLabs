#pragma once

#include<vector>
#include<string>

extern std::vector<int> L;

struct Heap {
	int root;
	int order;

public:
	Heap(int root, int order);

	static void heapify(std::vector<std::string>& data, int root, int order);
	static int getLeft(int root, int order);
	static int getRight(int root, int order);
};

class HeapSequence {
	std::vector<Heap*> heaps;
	void restoreSequence(std::vector<std::string>& data);
	void appendElement(std::vector<std::string>& data, int index);

public:
	void insertElement(std::vector<std::string>& data, int index);
	std::string popMax(std::vector<std::string>& data);
};

class SmoothSort {
public:
	SmoothSort(int maxn);
	void sort(std::vector<std::string>& data);
};