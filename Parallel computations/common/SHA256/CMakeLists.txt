cmake_minimum_required(VERSION 3.14)
set(CMAKE_CXX_STANDARD 17)

project(SHA256)

# library for working with sha256 and LongHex

set(SOURCE_LIB sha256.cpp long_hex.cpp)
add_library(SHA256 STATIC ${SOURCE_LIB})