import socket
import pickle

SERVER_ADDRESS = ('0.0.0.0', 1057)
MAXN = 65535
WHO_MSG = "Shcherbina Mariia, Variant 30, Sets synchronization"

BUFFER_SIZE = 1024
END_CMD = "END"
WHO_CMD = "WHO"
SYNC_CMD = "SYNC"
CLS_CONN_CMD = "CLOSE"

stored_data = set()
client_set = set()

#return false if element is inappropriate and should be deleted
def check(x):
	#allow to store even elements greater than zero and less than MAXN
	return (x % 2 == 0 and x > 0 and x < MAXN)

#get numbers from socket and return for each is it correct
def process_data():
	global client_set
	while True:
		data = conn.recv(BUFFER_SIZE)
		element = pickle.loads(data)
		if element == END_CMD:
			return
		is_valid = check(element)
		if is_valid:
			client_set.add(element)
		conn.send(pickle.dumps(is_valid))

#send values from server`s data that aren't stored in client`s set
def send_data():
	for element in stored_data:
		if not element in client_set:
			conn.send(pickle.dumps(element))
	stored_data.update(client_set)
	connection.send(pickle.dumps(END_CMD))

def who():
	connection.send(pickle.dumps(WHO_CMD))

def sync():
	global client_set
	client_set.clear()
	process_data()
	print("Data from client processing done")
	send_data()
	print("Sending extra data from server done")

def end():
	connection.close()
	print("Connection closed")

listening_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
listening_socket.bind(SERVER_ADDRESS)
listening_socket.listen(10)

print("Server is running")

while True:
	connection, address = listening_socket.accept()
	print("Accepted connection from ", address[0], ":" , address[1])
	while True:
		command = pickle.loads(connection.recv(BUFFER_SIZE))
		print("COMMAND " + command)
		if command == WHO_CMD:
			who()
		if command == SYNC_CMD:
			sync()
		if command == CLS_CONN_CMD:
			end()
			print("Current stored set: " + str(stored_data))
			break