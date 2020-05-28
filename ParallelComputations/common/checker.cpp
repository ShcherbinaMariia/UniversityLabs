#include "checker.h"
#include <fstream>
#include <iostream>

CheckerFactory::CheckerFactory() {
	std::ifstream input_file;
	input_file.open("../common/input.txt");
	std::string goal, suffix;

	input_file >> goal;
	input_file >> suffix;

	goal_ = LongHex(goal);
	suffix_ = suffix;
}

Checker* CheckerFactory::createChecker() {
	return new Checker(this->goal_, this->suffix_);
}

Checker::Checker(LongHex goal, std::string suffix){
	this->goal_ = goal;
	this->hasher = SHA256();

	int prefix_size = sizeof(unsigned int);
	this->key_size = prefix_size + suffix.size();
	this->key = new byte[this->key_size];

	for (int i = 0; i < suffix.size(); i++){
		this->key[prefix_size + i] = suffix[i];
	}
}

bool Checker::check(unsigned int prefix){
	int i = 0;
	for (int shift = 28; shift >= 0; shift -= 4){
		this->key[i] = ((prefix >> shift) & 0xF);
		i++;
	}
	return hasher.hash(this->key, this->key_size) < this->goal_; 
}