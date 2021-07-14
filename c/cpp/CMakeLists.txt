cmake_minimum_required (VERSION 3.6)
project(hello-discord)

add_executable(hello-discord hello-discord.cpp)

add_subdirectory(deps/sleepy-discord)
target_link_libraries(hello-discord sleepy-discord)
