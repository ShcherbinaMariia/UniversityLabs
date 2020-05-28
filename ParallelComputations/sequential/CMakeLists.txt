cmake_minimum_required(VERSION 3.14)
set(CMAKE_CXX_STANDARD 17)

project(parallelComputations)

include_directories(../common)
link_directories(../common/SHA256)

set(SOURCE_EXE main.cpp ../common/checker.cpp)
add_executable(sequential ${SOURCE_EXE})

target_link_libraries(sequential SHA256)