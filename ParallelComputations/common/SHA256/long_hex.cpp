#include "long_hex.h"

const char LongHex::kAlphabet[16] = {'0','1','2','3','4','5','6','7',
                                     '8','9','a','b','c','d','e','f'};

void LongHex::deleteLeadingZeroes() {
	while (this->digits.size() != 1 && this->digits.front() == 0){
		this->digits.pop_front();
	}
}

LongHex::LongHex(){
	digits.push_back(0);
}

LongHex::LongHex(std::string s){
	for (int i = 0; i < s.size(); i++){
		for (int j = 0; j < 16; j++){
			if (kAlphabet[j] == s[i]){
				digits.push_back(j);
				break;
			}
	    }
	}
	deleteLeadingZeroes();
}

LongHex::LongHex(unsigned int* s, int n){
	for (int i = 0; i < n; i++){
		for (int shift = 28; shift >= 0; shift -= 4){
			digits.push_back((s[i] >> shift) & 0xF);
		}
	}
	deleteLeadingZeroes();
}

std::string LongHex::toString(){
	std::string result = "";
	for (int i = 0; i < digits.size(); i++){
		result += kAlphabet[digits[i]];
	}
	return result;
}

bool LongHex::operator < (LongHex const &other) const {

	if (this->digits.size() != other.digits.size()){
		return this->digits.size() < other.digits.size();
	}

	for (int i = 0; i < digits.size(); i++){
		if (this->digits[i] != other.digits[i]){
			return this->digits[i] < other.digits[i];
		}
	}

	return false;
}