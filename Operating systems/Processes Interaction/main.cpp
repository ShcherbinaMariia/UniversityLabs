#include <iostream>
#include <sys/ipc.h>
#include <sys/msg.h>
#include <unistd.h>
#include "msg_queue.cpp"
#include <signal.h>
using namespace std;

struct ProcessInfo {
	pid_t pid;
	int msq_id;
	char* source_name;
};

void createProcess(ProcessInfo& p){
	pid_t pid = fork();

	if (pid == 0){
		char *args[]={p.source_name, NULL};
		execvp(args[0], args);
	} else {
		p.pid = pid;
	}
}

void openQueue(ProcessInfo& p){
	openQueue(p.source_name, p.msq_id);
}

void killProcess(ProcessInfo& p){
	if (p.pid){
		kill(p.pid, SIGTERM);
	}
}

void closeQueue(int msq_id){
	msqid_ds *buff;
	msgctl(msq_id, IPC_RMID, NULL);
}

int main(){ 

	ProcessInfo f, g;
	f.source_name = strdup("./f");
	g.source_name = strdup("./g");

	createProcess(f);
	createProcess(g);

	openQueue(f);
	openQueue(g);

	int x;
	cout << "x = ";
	cin >> x;

	msg_struct msg_buf;
	send(f.msq_id, 1, x, msg_buf);
	send(g.msq_id, 1, x, msg_buf);

	int fx, gx, result;
	bool finished_f = false, finished_g = false, cancelled = false, always_continue = false;
	int slept_time = 0, interval = 1; 

	while (true){

		if (finished_f && finished_g){
			result = fx * gx;
			break;
		}

		if (!finished_f){
			if (receive(f.msq_id, 2, msg_buf, fx, IPC_NOWAIT)){
				finished_f = true;
				if (fx == 0) {
					killProcess(g);
					result = 0;
					break;
				}
			}
		}

		if (!finished_g) {
			if (receive(g.msq_id, 2, msg_buf, gx, IPC_NOWAIT)){
				finished_g = true;
				if (gx == 0) {
					killProcess(f);
					result = 0;
					break;
				}
			}
		}

		sleep(interval);
		slept_time += interval;

		if (slept_time >= 10){
			if (!always_continue){
				int cmd;
				cout << "Do you want to continue computations?\n" << "1 - Yes\n2 - No\n3 - Yes, don't ask anymore\n";
				cin >> cmd;
				if (cmd == 2){
					killProcess(f);
					killProcess(g);
					cancelled = true;
					break;
				}

				if (cmd == 3){
					always_continue = true;
				}
			}
			slept_time = 0;
		}
	}

	if (!cancelled)
		cout << "f(x) * g(x) = " << result << "\n";

	closeQueue(f.msq_id);
	closeQueue(g.msq_id);

	return 0;
}