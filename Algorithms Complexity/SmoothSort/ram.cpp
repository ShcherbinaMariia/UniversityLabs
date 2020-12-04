#include "smooth_sort.hpp"
#include <iostream>
#include <vector>
#include <fstream>

void readData(std::vector<std::string>& data, int& n){
	std::ifstream inputFile("input.txt");

	inputFile >> n;
	data.resize(n);

	for (int i = 0; i < n; i++) {
		inputFile >> data[i];
	}

	inputFile.close();
}

double getTime(void (*f)()){
	auto start =  std::chrono::steady_clock::now();
	f();
	auto end =  std::chrono::steady_clock::now();

	auto elapsed =  std::chrono::duration_cast< std::chrono::milliseconds>(end - start);
	double totalTime = elapsed.count();
	
	return totalTime;
}

void writeData(std::vector<std::string>& data){
	std::ofstream outputFile("output.txt");

	outputFile << data.size() << "\n";

	for (int i = 0; i < data.size(); i++) {
		outputFile << data[i] << " ";
	}

	outputFile.close();
}


void sort() {
	std::vector<std::string> data;
	int n;
	readData(data, n);
	SmoothSort sorter(n);
	sorter.sort(data);
	writeData(data);
}

int main() {
	std::ios_base::sync_with_stdio(false);

	int time = getTime(sort);
	std::cout << time << " ";

	return 0;
}