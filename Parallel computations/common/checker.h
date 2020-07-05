#pragma once
#include "SHA256/sha256.h"

class Checker {
	LongHex goal_;
	SHA256 hasher;
	byte* key;
	unsigned int key_size;

public: 
	Checker(LongHex goal_, std::string suffix_);
	bool check(unsigned int prefix);
};

class CheckerFactory {
	LongHex goal_;
	std::string suffix_;

public:
	CheckerFactory();
	Checker* createChecker();
};