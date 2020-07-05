#pragma once
#include <deque>
#include <string>

class LongHex{
	const static char kAlphabet[16];
	std::deque<int> digits;

	void deleteLeadingZeroes();

public:
	LongHex();
	LongHex(std::string s);
	LongHex(unsigned int* s, int n);
	bool operator < (LongHex const &other) const;

	std::string toString();
};