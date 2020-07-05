import socket
import pickle

SERVER_ADDRESS = ('0.0.0.0', 1057)

BUFFER_SIZE = 1024
SYNC_CMD = "SYNC"
END_CMD = "END"
WHO_CMD = "WHO"
CLS_CONN_CMD = "CLOSE"

stored_data = set()

#initialize stored set
def init_data():
	input_data = map(int , input("Enter elements of the set: ").split(" "))
	return set(input_data)

#send all elements from stored set to server and return set of only valid elements
def get_valid_data():
	valid_data = set()
	for element in stored_data:
		sock.send(pickle.dumps(element))
		response = sock.recv(BUFFER_SIZE)
		is_valid = pickle.loads(response)
		if is_valid:
			valid_data.add(element)
	sock.send(pickle.dumps(END_CMD))
	return valid_data

#receive elements from server that are absent in client`s set
def get_extra_data():
	extra_data = set()
	while True:
		response = sock.recv(BUFFER_SIZE)
		element = pickle.loads(response)
		if element == END_CMD:
			break
		extra_data.add(element)
	return extra_data

def synchronize():
	global stored_data
	sock.send(pickle.dumps(SYNC_CMD))
	stored_data = get_valid_data()
	stored_data.update(get_extra_data())
	print("Synchronizing done")

def print_stored_data():
	print ("Current stored set " + str(stored_data))

def help():
	print("Allowed commands are:")
	print("s or Synchronize - synchronize stored data with data on server")
	print("p or Print - prints stored data")
	print("m or Modify - add/remove element from stored data")
	print("h or Help - print this message")
	print("w or Who - info")
	print("e or End - end program")

def modify():
	command = input("Enter r for removing element or a for adding: ")
	element = int(input("Enter element: "))
	if command == "r":
		if element in stored_data:
			stored_data.remove(element)
	if command == "a":
		stored_data.add(element)

def who():
	sock.send(pickle.dumps(WHO_CMD))
	response = sock.recv(BUFFER_SIZE)
	print(pickle.loads(response))

def exit():
	sock.send(pickle.dumps(CLS_CONN_CMD))

def main():
	global stored_data
	stored_data = init_data()
	help()
	while True:
		command = input()
		if command == "Synchronize" or command == "s":
			synchronize()
		if command == "Print" or command == "p":
			print_stored_data()
		if command == "Modify" or command == "m":
			modify()
		if command == "Help" or command == "h":
			help()
		if command == "Who" or command == "w":
			who()
		if command == "End" or command == "e":
			exit()
			return 

#socket initialization
sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
sock.connect(SERVER_ADDRESS)

main()

sock.close()
