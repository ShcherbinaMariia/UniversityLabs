cmake_minimum_required(VERSION 3.14)
set(CMAKE_CXX_STANDARD 17)

project(parallelComputations)

find_package(MPI REQUIRED)
set(CMAKE_CXX_COMPILE_FLAGS ${CMAKE_CXX_COMPILE_FLAGS} ${MPI_COMPILE_FLAGS})
set(CMAKE_CXX_LINK_FLAGS ${CMAKE_CXX_LINK_FLAGS} ${MPI_LINK_FLAGS})
SET(CMAKE_CXX_LINK_FLAGS "-headerpad_max_install_names")
include_directories(${MPI_INCLUDE_PATH})

include_directories(../common)
link_directories(../common/SHA256)

set(SOURCE_EXE main.cpp ../common/checker.cpp)
add_executable(mpi ${SOURCE_EXE})

target_link_libraries(mpi SHA256 ${MPI_LIBRARIES})