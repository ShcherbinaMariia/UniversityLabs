cmake_minimum_required(VERSION 3.14)
set(CMAKE_CXX_STANDARD 17)

project(diningPhilosophers)

set(SOURCE_EXE main.cpp fork.cpp philosopher.cpp)
add_executable(philosophers ${SOURCE_EXE})