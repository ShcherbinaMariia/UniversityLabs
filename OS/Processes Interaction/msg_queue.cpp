#include "types.h"
#include <iostream>

void openQueue(char* key_base, int& msq_id){
	key_t key = ftok(key_base, 0);
	msq_id = msgget(key, (IPC_CREAT | 0666));
}

void send(int msq_id, int msg_type, int msg, msg_struct& msg_buf){
	int buf_length = sizeof(int);
	msg_buf.mtype = msg_type;
	msg_buf.mtext[0] = msg;
	
	msgsnd(msq_id, &msg_buf, buf_length, 0);
}

bool receive(int msq_id, int msg_type, msg_struct& msg_buf, int& result, int flag = 0) {
	if (msgrcv(msq_id, &msg_buf, MSGSZ, msg_type, flag) < 0)
		return false;

	result = msg_buf.mtext[0];
	return true;
}