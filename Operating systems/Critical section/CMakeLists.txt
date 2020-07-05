cmake_minimum_required(VERSION 3.14)
set(CMAKE_CXX_STANDARD 17)

project(criticalSection)

set(SOURCE_EXE main.cpp peterson_mutex.cpp)
add_executable(criticalSection ${SOURCE_EXE})