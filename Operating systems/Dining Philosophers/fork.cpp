#include "fork.hpp"

int Fork::getOrder(){
	return this->_order;
}

void Fork::pick(){
	this->_lock.lock();
}

void Fork::put(){
	this->_lock.unlock();
}

Fork::Fork(int order){
	this->_order = order;
}