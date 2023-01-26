project(PersonalFinance NONE)
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
  OUTPUTDIR ..
)

add_custom_target(
  run
  COMMAND java -cp "PersonalFinance.jar:jOpenDocument.jar" Main
  WORKING_DIRECTORY ${PROJECT_SOURCE_DIR}
)

add_custom_target(
  gendoc
  COMMAND doxygen
  WORKING_DIRECTORY ${PROJECT_SOURCE_DIR}
)