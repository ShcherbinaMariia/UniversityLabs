#pragma once

const int MSGSZ = 4;

struct msg_struct {
    long      mtype;                /* message type */
    int mtext[MSGSZ / sizeof(int)]; /* message text of length MSGSZ bytes */
};
