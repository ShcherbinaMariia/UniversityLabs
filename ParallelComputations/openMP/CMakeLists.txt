cmake_minimum_required(VERSION 3.14)
set(CMAKE_CXX_STANDARD 17)

project(parallelComputations)

set(CMAKE_CXX_COMPILER "/usr/local/Cellar/llvm/10.0.0_3/bin/clang++")
set(OpenMP_CXX_FLAGS "-Xpreprocessor -fopenmp")
set(OpenMP_CXX_LIB_NAMES "omp")
set(OpenMP_omp_LIBRARY omp)

set(OPENMP_LIBRARIES "/usr/local/Cellar/llvm/10.0.0_3/lib")
set(OPENMP_INCLUDES "/usr/local/Cellar/llvm/10.0.0_3/include")

set(OPENMP_LIBRARIES "/usr/local/opt/llvm/lib")
link_directories(${OPENMP_LIBRARIES})

set(CMAKE_CXX_FLAGS ${CMAKE_CXX_FLAGS} "-mlinker-version=405")

find_package(OpenMP REQUIRED)

include_directories(../common)
link_directories(../common/SHA256)

set(SOURCE_EXE main.cpp ../common/checker.cpp)
add_executable(openMP ${SOURCE_EXE})

target_link_libraries(openMP SHA256 OpenMP::OpenMP_CXX)