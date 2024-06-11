# SkipList Project

## Overview
This project implements a SkipList data structure in Java, along with a Database class that utilizes the SkipList to manage rectangles. The SkipList allows efficient insertion, removal, and search operations in logarithmic time complexity. The Database class provides methods for inserting, removing, and searching rectangles by name or coordinates.

## Project Structure
The project consists of the following files:

- SkipList.java: Implementation of the SkipList data structure.
- KVPair.java: Key-Value Pair class used in the SkipList.
- Database.java: Class responsible for interfacing between the command processor and the SkipList, managing rectangles.
- SkipListTest.java: JUnit tests for the SkipList and Database classes.

## Usage
- SkipList Class:

The SkipList class provides methods for inserting, removing, and searching elements.
To use the SkipList, instantiate an object of the SkipList class and call its methods accordingly.

- Database Class:

The Database class interfaces with the SkipList to manage rectangles.
Use the Database class methods to insert, remove, search, and perform other operations on rectangles.


## Running Tests
- Run the JUnit tests provided in the SkipListTest.java file to ensure the correctness of the implementation.
- The tests cover various scenarios for inserting, removing, and searching rectangles in the SkipList.
