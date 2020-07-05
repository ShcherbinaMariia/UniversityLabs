#include <atomic>

class PetersonMutex {
	std::atomic_int favoredThread;
	std::atomic_bool wantsToEnter[2];

public:
	PetersonMutex();
	void lock(int curr);
	void unlock(int curr);
};