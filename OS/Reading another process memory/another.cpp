#include <iostream>
#include <mach/mach.h>
#include <unistd.h>
using namespace std;

int main(){
	char secret_msg[77] = "Answer to the Ultimate Question of Life, the Universe, and Everything is 42.";

	freopen("another_process_info.txt", "w", stdout);
	cout << current_task() << " " << getpid() << "\n"; 
	cout << 77 << " " << &secret_msg << "\n";
	fclose (stdout);
	while(true){}
	return 0;
}

