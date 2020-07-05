#include <iostream>
#include <sys/ipc.h>
#include <sys/msg.h>
#include <unistd.h>
#include "msg_queue.cpp"

int f(int x){
	return x * x;
}

int main() {

	int msq_id;
	char *key_base = strdup("./f");

	openQueue(key_base, msq_id);

	int x;
	msg_struct msg_buf;
	receive(msq_id, 1, msg_buf, x);

	int result = f(x);

    send(msq_id, 2, result, msg_buf);
}