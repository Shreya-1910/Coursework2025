# Developing Maintainable Software Coursework

A **Tetris game** developed using **JavaFX** and **Maven** with a strong emphasis on **clean architecture, maintainability, refactoring, and testability**.


## Table of Contents

1. [Introduction](#introduction)
2. [Features and Additions](#features-and-additions)
3. [Refactoring Activities](#refactoring-activities)
4. [Testing](#testing)
5. [Repackaging and Project Structure](#repackaging-and-project-structure)
6. [How to Run the Game](#how-to-run-the-game)
7. [Dependencies](#dependencies)

---

## Introduction

This project is a Tetris game built using **JavaFX** as part of the *Developing Maintainable Software* coursework.

The primary focus of the project was to demonstrate:

- Maintainable software design
- Clean separation of concerns
- Modular and scalable architecture
- Use of refactoring techniques to improve legacy-style code
- Add additional features

## Features and Additions

The project enhances the traditional Tetris experience by introducing multiple gameplay and usability improvements.

### Gameplay Enhancements

- **Hold Piece System** – Players can store a piece and reuse it later, introducing strategic depth.
- **Next Three Piece Preview** – Displays upcoming pieces so that players can plan moves in advance.
- **Garbage Block Spawning** – After Level 3, additional garbage blocks are introduced to increase difficulty.
- **Ghost Piece** – A translucent preview shows where a piece will land, improving placement accuracy.
- **Level Progression** – Levels increase after every 5 lines cleared.
- **Speed Scaling** – The falling speed of pieces increases with higher levels.

### User Interface Improvements

- Instruction screen for first-time users
- Main menu with easy navigation
- High-score display and tracking
- Real-time UI updates

### Feedback Systems

- Live score updates
- Real-time updates for hold and next-piece panels

### Additional Controls

- Pause and resume button functionality
- New game / reset option
- On-screen indicators for garbage mode and current level

## Refactoring Activities

A significant portion of the project involved **refactoring an initially monolithic and tightly coupled codebase** into a cleaner, more maintainable architecture.

### Simplification of `GuiController`

Originally, `GuiController` was responsible for:

- Input handling
- Rendering
- Game timing
- Score management
- Game state updates
- Garbage brick updates

This violated the **Single Responsibility Principle (SRP)** and made the class extremely difficult to maintain.

To resolve this, functionality was split into focused components:

#### Input Handler
Manages all keyboard input, including movement, rotation, hold, and hard drop.

**Benefits:**
- Removes input logic from UI code
- Improves testability
- Makes control schemes easier to modify

#### GameRenderer
Responsible only for visual drawing of the board, active piece, ghost piece, and UI panels.

**Benefits:**
- Decouples rendering from game logic
- Makes UI changes safer and easier
- Improves readability

#### High Score Manager
Handles saving and loading of high scores.

**Benefits:**
- Separates persistence logic from UI
- Easier to extend in future (e.g., online leaderboards)

#### Game Loop
Centralises timing and tick-based updates.

**Benefits:**
- Isolates timing bugs
- Makes speed scaling easier to manage
- Cleaner separation of real-time logic from UI code

### GarbageManager
Manages the garbage brick behavior and its display in the UI. Tracks whether garbage bricks 
should appear based on the current level, and updates the garbage info label.

**Benefits:**

- Single Responsibility: Now GuiController no longer handles garbage display logic. Its job is only to render what the manager tells it.
- Decouples UI from game logic: GarbageManager encapsulates all rules for when garbage bricks should be considered active.
- Easier maintenance and testing: You can independently test whether garbage should be active based on level, without touching GuiController or GameEngine.
- Clearer flow: The GUI just asks GarbageManager to update the label whenever the level changes.


### Simplification of `GameController`

Core game rules were extracted into a dedicated **GameEngine**.

This includes:

- Collision detection
- Piece movement logic
- Line clearing
- Scoring calculations
- Level progression
- Garbage block handling

**Benefits:**

- Higher cohesion of game rules
- Easier unit testing
- Lower coupling with the GUI
- Safer extension of gameplay features

### Elimination of Code Duplication

Two reusable helper methods were introduced:

- `updateBoardViewData(Runnable action)` – Centralises action + board refresh logic
- `updateGameDisplay()` – Centralises all visual refresh logic

**Benefits:**

- Fewer repeated code blocks
- More consistent program behaviour
- Easier debugging and maintenance


## Testing

A comprehensive testing strategy was adopted using **JUnit 5**.

### Tested Components

Unit tests were created for the following parts of the system:

- **Game Engine** – movement, collisions, line clearing, level logic
- **Game Renderer** – correctness of visual data output
- **Game Loop** – tick handling and timing accuracy
- **High Score Manager** – save/load/update behaviour
- **Brick Rotator** – rotation correctness and boundary handling
- **All Brick Types** – shape definitions and rotation states
- **NextShapeInfo** – next-piece queue logic
- **Score System** – scoring rules and scaling
- **BoardViewData** – correct mapping of board state

## Running Tests

To run the tests:

1. Open a terminal in the project root directory.
2. Run the following command:

   ```bash 
   mvn test
   
### How to Run the Game

This project is built using **Maven** and **JavaFX**.

### Requirements
- **Java JDK 23** installed  
- **Apache Maven** installed and added to your system PATH  

### Steps to Run

1. Open a terminal in the project root directory.
2. Run the following command:

   ```bash
   mvn clean javafx:run
