#include<iostream>
#include<vector>
#include<fstream>
#include<string>
#include<stdlib.h>
#include<set>
#include"smooth_sort.hpp"

const int CHUNK_SIZE = 1000000;

std::string getChunkFilename(int index) {
	return "data/" + std::to_string(index) + ".txt";
}

void readChunk(std::ifstream& inputFile, int& unprocessed, std::vector<std::string>& data) {
	int currentChunkSize = std::min(CHUNK_SIZE, unprocessed);
	data.resize(currentChunkSize);

	for (int i = 0; i < currentChunkSize; i++) {
		inputFile >> data[i];
	}

	unprocessed -= currentChunkSize;
}

void writeChunk(int index, std::vector<std::string>& data) {
	std::ofstream outputFile(getChunkFilename(index));

	for (int i = 0; i < data.size(); i++) {
		outputFile << data[i] << " ";
	}

	outputFile.close();
}

void insertNext(std::ifstream& file, int index, std::set<std::pair<std::string, int>>& smallest) {
	std::string x;
	if (file >> x) {
		smallest.insert(std::make_pair(x, index));
	}
}

void writeOutput(std::ofstream& outputFile, std::vector<std::string>& data) {
	for (int i = 0; i < data.size(); i++) {
		outputFile << data[i] << " ";
	}
}

void mergeChunks(int n) {
	std::ifstream files[n];
	std::set<std::pair<std::string, int>> smallest;
	std::vector<std::string> data;

	std::ofstream outputFile("data/output.txt");

	for (int i = 0; i < n; i++) {
		files[i] = std::ifstream(getChunkFilename(i));
		insertNext(files[i], i, smallest);
	}

	while (smallest.size() > 0) {
		std::pair<std::string, int> next = *smallest.begin();
		smallest.erase(smallest.begin());

		data.push_back(next.first);

		if (data.size() % CHUNK_SIZE == 0) {
			writeOutput(outputFile, data);
			data.clear();
		}

		insertNext(files[next.second], next.second, smallest);
	}

	writeOutput(outputFile, data);
	data.clear();

	for (int i = 0; i < n; i++) {
		files[i].close();
	}
}

void executeSort() {
	std::ifstream inputFile("input.txt");

	int n;
	inputFile >> n;

	int unprocessed = n, nChunks = 0;
	std::vector<std::string> data;
	SmoothSort sorter(n);

	while (unprocessed > 0) {
		readChunk(inputFile, unprocessed, data);
		sorter.sort(data);
		writeChunk(nChunks, data);
		data.clear();
		nChunks++;
	}

	inputFile.close();

	mergeChunks(nChunks);
}


double getTime(void (*f)()){
	auto start =  std::chrono::steady_clock::now();
	f();
	auto end =  std::chrono::steady_clock::now();

	auto elapsed =  std::chrono::duration_cast< std::chrono::milliseconds>(end - start);
	double totalTime = elapsed.count();
	
	return totalTime;
}

int main() {
	std::ios_base::sync_with_stdio(false);

	int time = getTime(executeSort);
	std::cout << time << " ";

	return 0;
}