#pragma once 
#include <string>
#include <limits.h>
#include "long_hex.h"

using byte = unsigned char;

class SHA256{
private: 
	const static unsigned int k[];
	const static byte kEndMessageByte = byte(128);
	const static byte kZeroByte = byte(0);
	const static unsigned int kBlockSize = 512;
	const static unsigned int kNumRounds = 64;

	unsigned int h[8];

	void init();
	byte* preprocess(byte* message, unsigned int &message_bytes);
	void processBlock(byte *message, int start, unsigned int *w);
public:
	LongHex hash(byte* message, unsigned int nb);
};