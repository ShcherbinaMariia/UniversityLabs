#include "smooth_sort.hpp"
#include<vector>
#include<iostream>

std::vector<int> L;

std::string maxString(std::string a, std::string b) {
	return a > b ? a : b;
}

void initLeonardoNumbers(int max_sum) {
	long long sum = 2;
	L.push_back(1);
	L.push_back(1);
	while (sum < max_sum) {
		int next = L[L.size() - 1] + L[L.size() - 2] + 1;
		L.push_back(next);
		sum += next;
	}
}

Heap::Heap(int root, int order): root(root), order(order) {}

int Heap::getRight(int root, int order) {
	return root - 1;
}

int Heap::getLeft(int root, int order) {
	return root - L[order - 2] - 1;
}

void Heap::heapify(std::vector<std::string>& data, int root, int order) {
	if (order < 2) {
		return;
	}

	int right = Heap::getRight(root, order);
	int left = Heap::getLeft(root, order);

	std::string maxx = maxString(maxString(data[right], data[left]), data[root]);

	if (maxx == data[root]) {
		return;
	}

	if (maxx == data[right]) {
		std::swap(data[right], data[root]);
		heapify(data, right, order - 2);
		return;
	}

	// maxx == data[left]
	std::swap(data[left], data[root]);
	heapify(data, left, order - 1);
}

void HeapSequence::appendElement(std::vector<std::string>& data, int index) {
	int n = heaps.size() - 1;

	if (n >= 1 && heaps[n]->order + 1 == heaps[n - 1]->order) {
		int newOrder = heaps[n]->order + 2;

		heaps.pop_back();
		heaps.pop_back();

		heaps.push_back(new Heap(index, newOrder));
		return;
	}

	if (n >= 0 && heaps[n]->order == 1) {
		heaps.push_back(new Heap(index, 0));
		return;
	}

	heaps.push_back(new Heap(index, 1));
}

void HeapSequence::restoreSequence(std::vector<std::string>& data) {
	int current = heaps.size() - 1;

	while (current > 0) {
		int root = heaps[current]->root;
		int order = heaps[current]->order;

		int prev = heaps[current - 1]->root;

		std::string maxx = maxString(data[root], data[prev]);

		if (heaps[current]->order > 1) {
			int left = Heap::getLeft(root, order);
			int right = Heap::getRight(root, order);
			maxx = maxString(maxx, maxString(data[left], data[right]));
		}

		if (maxx != data[prev]) {
			break;
		}

		std::swap(data[prev], data[root]);
		current--;
	}

	Heap::heapify(data, heaps[current]->root, heaps[current]->order);
}

void HeapSequence::insertElement(std::vector<std::string>& data, int index) {
	this->appendElement(data, index);
	this->restoreSequence(data);
}

std::string HeapSequence::popMax(std::vector<std::string>& data) {
	int current = heaps.size() - 1;
	int root = heaps[current]->root;
	std::string result = data[root];

	if (heaps[current]->order < 2) {
		heaps.pop_back();
		return result;
	}

	int order = heaps[current]->order;
	int left = Heap::getLeft(root, order);
	int right = Heap::getRight(root, order);

	heaps.pop_back();

	heaps.push_back(new Heap(left, order - 1));
	this->restoreSequence(data);

	heaps.push_back(new Heap(right, order - 2));
	this->restoreSequence(data);

	return result;
}

SmoothSort::SmoothSort(int maxn) {
	initLeonardoNumbers(maxn);
}

void SmoothSort::sort(std::vector<std::string>& data) {
	HeapSequence heaps;

	for (int i = 0; i < data.size(); i++) {
		heaps.insertElement(data, i);
	}

	for (int i = 0; i < data.size(); i++) {
		heaps.popMax(data);
	}
}

