cmake_minimum_required(VERSION 3.14)
set(CMAKE_CXX_STANDARD 11)

project(readingAnotherProcessMemory)

add_executable(another another.cpp)
add_executable(main main.cpp)