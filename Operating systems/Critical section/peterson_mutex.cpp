#include "peterson_mutex.hpp"

PetersonMutex::PetersonMutex(){
	favoredThread.store(0);
	wantsToEnter[0].store(false);
	wantsToEnter[1].store(false);
}

void PetersonMutex::lock(int curr) {
	int other = 1 - curr;
	wantsToEnter[curr].store(true);
	favoredThread.store(other);
	while (wantsToEnter[other].load() && favoredThread.load() == other);
}

void PetersonMutex::unlock(int curr){
	wantsToEnter[curr].store(false);
}