# Cayley Graph Rubix Cube Solver

Cayley Cube is a high performance, algorithmic Rubik's Cube solver built completely from scratch in vanilla Java. Instead of relying on heavy frameworks, third-party libraries, or web APIs, this project dives deep into core computer science fundamentals, treating the Rubik's Cube as a massive Cayley graph and finite permutation group (4.3 x 10^19 possible states).

What We Are Trying to Accomplish

Build a Zero-Bloat State Engine:
Represent the complex permutation and orientation states of 8 corners and 12 edges using compact primitive data types (byte[] arrays or bitpacked primitives) to maximize memory efficiency and processing speed.

Implement Group Theory & Move Transformations:
Code the exact mathematical permutation logic for all 18 cube moves ($U, D, L, R, F, B$ and their inverses/half-turns) as group generators, ensuring strict validity through automated JUnit testing.

Master State-Space Search (IDA*):
Overcome the exponential explosion of standard graph search (like BFS/DFS) by implementing Iterative Deepening A* (IDA*), allowing us to find short or optimal move sequences without running out of RAM.

Engineer Pattern Databases (Heuristics):
Build precomputed lookup tables (such as an 88MB corner distance table) to provide admissible heuristics, pruning millions of dead-end branches during the search in $O(1)$ time.

Create a Standout Engineering Portfolio Piece:
Deliver a lightning-fast command-line backend engine that solves random scrambles in milliseconds, proving deep competence in data structures, algorithms, memory optimization, and low-level Java performance.