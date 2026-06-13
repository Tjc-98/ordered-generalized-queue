# ordered-generalized-queue

A generic queue that maintains sorted order on insertion in Java.

---

## About

Written in Java, this project implements an ordered queue backed by a doubly linked circular list. Elements are inserted in ascending sorted order and removed from the front (smallest element first). The queue state is printed after each operation, and an error message is shown when dequeuing from an empty queue.

## Usage

Run the program to execute the built-in test cases. The output shows the sorted queue state after each operation in the format `[x],[y],[z]`.

## Getting Started

### Prerequisites

- Java 21 or later
- Maven

### Building

**Unix / Windows**
```
mvn compile
```

### Running

**Unix / Windows**
```
mvn exec:java -Dexec.mainClass="Main"
```

---

MIT License - see [LICENSE](LICENSE)
