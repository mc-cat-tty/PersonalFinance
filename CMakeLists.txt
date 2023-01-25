project(PersonalFinance)
cmake_minimum_required(VERSION 3.21.0)

find_package(Java 11 REQUIRED)
include(UseJava)

file(
  GLOB assetsList
  LIST_DIRECTORIES false
  assets/*
)

add_jar(
  PersonalFinance
  SOURCES Main.java
  RESOURCES NAMESPACE assets ${assetsList}
  INCLUDE_JARS jOpenDocument.jar
  ENTRY_POINT Main
)

add_custom_target(
  run
  COMMAND java -jar PersonalFinance.jar 
)

add_custom_target(
  gendoc
  COMMAND doxygen
  WORKING_DIRECTORY ${PROJECT_SOURCE_DIR}
)