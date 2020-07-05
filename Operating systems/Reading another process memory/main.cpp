#include <iostream>
#include <stdio.h>
#include <mach/mach.h>
#include <unistd.h>
using namespace std;

int main(){
	freopen("another_process_info.txt", "r", stdin);
	
	mach_port_t mach_port;
	pid_t pid;

	cin >> mach_port >> pid;
	task_t task;
	task_for_pid(mach_port, pid, &task);

	vm_offset_t buf;
    uint32_t sz;
    int n;
    vm_address_t addr;
	
	cin >> n >> hex >> addr;

    char result[n];

    if (vm_read(task, addr, n, &buf, &sz) == KERN_SUCCESS) {

        memcpy(result, (const void *)buf, sz);
        printf("%s", result);
    }
}