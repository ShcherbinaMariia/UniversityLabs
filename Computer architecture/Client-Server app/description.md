# Client-server app

A simple application with client-server architecture, which implements custom protocol and solving individual task.

## Task : Sets synchronization

A set of number is stored in server. Each client connects to server, then send it's currently stored set value by value
and for each of them get message from server, remove invalid elements. After this, server send it's set to the client - 
only that values that weren't in client`s set.
