# Developing Maintainable Software Coursework

A **Tetris game** developed using **JavaFX** and **Maven** with a strong emphasis on **clean architecture, maintainability, refactoring, and testability**.


## Table of Contents

1. [Introduction](#introduction)
2. [Github Repository](#github-repository)
3. [How to Run the Game](#how-to-run-the-game)
4. [Features and Additions](#features-and-additions)
5. [Refactoring Activities](#refactoring-activities)
6. [Testing](#testing)
7. [Unexpected problems](#unexpected-problems)
8. [Planned features but not implemented](#planned-features-but-not-implemented)


---

## Introduction

This project is a Tetris game built using **JavaFX** as part of the *Developing Maintainable Software* coursework.

The primary focus of the project was to demonstrate:

- Maintainable software design
- Clean separation of concerns
- Modular and scalable architecture
- Use of refactoring techniques to improve legacy-style code
- Add additional features

## Github Repository

- Link: https://github.com/Shreya-1910/Coursework2025.git

## How to run the game

This project is built using **Maven** and **JavaFX**.

### Requirements
- **Java JDK 23** installed
- **Maven** 

### Steps to Run
1. Clone the repository from the link above.
2. Open the project as a maven project in Intellij.
3. Under file, project structure, libraries set JDK to Java 23.
4. Click on the Maven toolbar on the right side panel and navigate to plugins->javafx->javafx:run
5. Click run.


## Features and Additions

The project enhances the traditional Tetris experience by introducing multiple gameplay and usability improvements.



### Gameplay Enhancements

#### Features that are added and working properly:

1. **Hold Piece System** – A Tetris mechanic that allows a player to temporarily store the current falling piece and swap it with a previously held piece. This adds strategic depth to the gameplay.

   1.1 **First-Time Hold**

   When the player presses the *hold key* (**C** key):

    - The currently active tetromino is moved into the **hold slot**.
    - The next tetromino from the **piece queue** immediately becomes the new active piece.
    - Because the hold slot was previously empty, **no swapping occurs** during the first use.

   This allows the player to defer using an unfavourable piece without losing game flow.

   1.2 **Subsequent Holds**

   On later uses of the hold function, if a tetromino is already stored:

    - The current falling piece is **swapped** with the piece in the hold slot.
    - The swapped-in piece is reset to its **default spawn position** and **default rotation state**.
    - This ensures consistent and predictable behaviour, preventing unfair positional advantages.

   This mechanic enables players to plan ahead and reserve critical pieces (such as the I-piece) for high-value placements.

   1.3 **Lock Restriction (Anti-Abuse Mechanism)**

   To maintain balance and prevent infinite swapping:

    - The player can only use the hold function **once per piece drop**.
    - A boolean flag (e.g. `hasHeldThisTurn`) disables multiple holds during the same turn.
    - The hold ability is only **re-enabled after the current piece locks into the grid**.

   1.4 **Why This Feature Matters**

    - Encourages **strategic thinking** rather than purely reactive play.
    - Improves **player control** over difficult pieces.


2. **Next Three Piece Preview** – Displays the upcoming three tetrominoes so that players can plan their moves in advance.

   2.1 **How It Works**

    - The game maintains a **piece queue** that stores the next three tetrominoes.
    - As each piece is placed, the queue **shifts**, and a new random piece is added to the end.
    - The UI displays the next three pieces in a separate panel, giving the player foresight to plan placements and combos.

   2.2 **Why This Feature Matters**

    - Allows for **strategic planning** rather than reactive play.
    - Helps players **set up combos** and avoid placing pieces in awkward positions.
    - Enhances the overall **gameplay experience** by providing clear information about upcoming moves.

3. **Garbage Block Spawning** – After Level 3, additional garbage blocks are introduced to increase difficulty.

   3.1 **How It Works**

    - Garbage blocks start spawning once the player reaches **Level 3** (`GARBAGE_START_LEVEL`).
    - Blocks are represented by a specific value in the board matrix (e.g., `8`) to distinguish them from normal tetrominoes.
    - The system has **multiple spawning mechanisms**:
        - **Random spawn during gameplay** – Occurs based on a chance that increases with the current level (`GARBAGE_SPAWN_CHANCE + level-based increment`).
        - **Spawn after clearing lines** – Adds additional garbage blocks probabilistically to maintain challenge.
        - **Add a full garbage row** – Moves all rows up by one and adds a new bottom row with 1–2 random holes to increase stack height.
    - During spawning, only **valid empty positions** are used, and placement is randomized to make the challenge unpredictable.
    - This gradual addition of garbage blocks increases game pressure and encourages strategic line clearing.

   3.2 **Why This Feature Matters**

    - Introduces a **higher level of challenge** as the game progresses.
    - Encourages **faster decision-making** and careful planning.
    - Adds depth to the gameplay by **scaling difficulty dynamically**, keeping the game engaging for advanced players.

4. **Ghost Piece** – A translucent preview that shows where the current tetromino will land, improving placement accuracy.

   4.1 **How It Works**

    - The game calculates the **landing position** of the active tetromino based on the current board state.
    - A **semi-transparent copy** of the tetromino is drawn at that position without affecting the actual gameplay.
    - The ghost piece **updates in real-time** as the player moves or rotates the active piece.
    - This allows players to **anticipate placement** and plan rotations or drops more precisely.

   4.2 **Why This Feature Matters**

    - Improves **placement accuracy**, especially in fast-paced gameplay.
    - Helps players plan **complex moves** like T-spins or combo setups.
    - Enhances **visual clarity**, giving better spatial awareness of the board.

5. **Level Progression** – Levels increase after every 5 lines cleared, gradually increasing game difficulty.

   5.1 **How It Works**

    - The game keeps track of the **total lines cleared** by the player.
    - For every **5 lines cleared**, the **level increments by 1**.
    - As the level increases:
        - The **fall speed of tetrominoes** increases.
        - Features like **garbage block spawning** may become active or more frequent.
    - The UI updates the **current level display** in real-time to keep the player informed.

   5.2 **Why This Feature Matters**

    - Provides a **clear sense of progression** and achievement for the player.
    - Increases **game difficulty dynamically**, keeping the challenge engaging.
    - Encourages players to **clear lines efficiently** and plan ahead.

6. **Speed Scaling** – The falling speed of tetrominoes increases as the player progresses to higher levels, making the game more challenging.

   6.1 **How It Works**

    - The game calculates the **fall speed** of the current piece based on the level using a formula such as:
      ```java
      int newSpeed = Math.max(80, 400 - (currentLevel - 1) * 50);
      ```
    - As the **current level increases**, the falling speed decreases (pieces fall faster).
    - A **minimum speed limit** ensures the game remains playable at higher levels.

   6.2 **Why This Feature Matters**

    - Increases **game difficulty dynamically** as the player progresses.
    - Encourages **faster reaction times** and strategic planning.
    - Makes the gameplay more **intense and engaging** in advanced stages.

### User Interface Improvements

1. **Instruction Screen** – Provides first-time users with a clear guide on how to play the game.

   1.1 **How It Works**

    - The game displays a **dedicated instructions screen** when accessed from the home menu.
    - The screen uses a **StackPane with a background image** or gradient fallback if the image is missing.
    - Information is divided into **two columns**:
        - **Controls** – Shows key bindings (move, rotate, drop, pause, new game).
        - **Gameplay** – Lists scoring rules, line clearing, level progression, speed scaling, and garbage block spawning.
    - The UI is styled with **neon-like fonts, shadows, and spacing** for readability and aesthetic appeal.
    - A **"Back to Menu"** button allows users to return to the home screen without starting the game.
    - Text and labels dynamically apply **drop shadow effects** for clarity against the background.

   1.2 **Why This Feature Matters**

    - Provides **new players with immediate guidance**, reducing the learning curve.
    - Improves **usability and accessibility** of the game.
    - Enhances the overall **user experience** by combining clear instructions with an attractive, readable interface.

2. **Main Menu** – Provides an easy-to-navigate starting point for the game.

   2.1 **How It Works**

    - The main menu is displayed when the game launches.
    - Includes clearly labeled buttons for:
        - **Start Game** – Begins a new game session.
        - **Instructions** – Opens the instructions screen for first-time users.
    - Layout ensures **intuitive navigation**, with all primary actions accessible from a single screen.

   2.2 **Why This Feature Matters**

    - Improves **usability and accessibility**, especially for new players.
    - Provides a **central hub** for accessing all game features efficiently.

3. **High-Score Display and Tracking** – Keeps track of players' best scores and displays them in the game.

   3.1 **How It Works**

    - The game stores **high scores** in a file to preserve them between sessions.
    - The **high-score screen** displays the top scores, allowing players to see their achievements.
    - After each game, if the player beats the previous high score, it is **automatically updated**.
    - The UI updates the high-score display in real-time when a new record is achieved.

   3.2 **Why This Feature Matters**

    - Encourages **replayability**, motivating players to improve their performance.
    - Adds a sense of **achievement and competition**, even for single-player games.
    - Enhances the overall **polish and completeness** of the game experience.
4. **Game Interface and Controls** – Enhancements that improve player awareness and interaction.

    4.1 **Real-Time Score Updates**
    - The player's current score is updated immediately as lines are cleared or bonuses are earned.

    4.2 **Hold Piece and Next-Piece Panels**
    - The hold slot and upcoming pieces preview panels update dynamically to reflect game state changes.

    4.3 **Level Display**
    - Shows the current level and updates in real-time as the player progresses.

    4.4 **Garbage Mode Indicator**
    - A label next to the board displays **“Garbage Brick: OFF”** in yellow by default.
    - Turns **red** when garbage blocks become active (Level 3+), providing immediate visual feedback.

    4.5 **Pause / Resume and New Game Buttons**
    - Pause or resume gameplay without losing progress.
    - Start a new game or reset the current session at any time.

    4.6 **Why This Matters**
    - Provides **instant visual feedback**, improving situational awareness.
    - Enhances **control and usability**, allowing players to manage gameplay effectively.
    - Makes the interface feel **responsive and polished**, contributing to overall player experience.

### Refactoring Activities

#### 1. New Classes Added

As part of improving maintainability, readability, and testability, the following **new classes** were introduced to better organize the Tetris project:

1. **HomeScreen**
    - Handles the **main menu UI**, including buttons for starting a new game, viewing instructions, high scores, and exiting.
    - Separates UI logic from the game mechanics.

2. **InstructionsScreen**
    - Displays instructions for first-time users in a clear and visually appealing layout.
    - Modularizes instructional UI elements, making them easy to update independently.

3. **GameEngine**
    - Encapsulates the **core game logic**, including piece movement, rotation, collision detection, line clearing, scoring, and level progression.
    - Separates logic from GameController, enabling **testing of gameplay mechanics**.

4. **GameRenderer**
    - Handles **drawing the game board, tetrominoes, ghost pieces, and UI elements** on the screen.
    - Keeps rendering responsibilities separate from game logic.

5. **GameLoop**
    - Manages the **timing and update cycles** of the game, controlling piece falling speed and frame updates.
    - Ensures smooth and consistent gameplay updates.

6. **InputHandler**
    - Processes **keyboard inputs** (move, rotate, drop, hold, pause) and sends commands to the game engine.
    - Improves modularity and allows **testing input handling separately**.

7. **HighScoreManager**
    - Manages **high score tracking, saving, and retrieval**.
    - Allows easy updating and display of player achievements.

8. **GarbageManager**
    - Handles **garbage block spawning and garbage mode logic**, including the addition of new rows and random block placement.
    - Separates garbage-related mechanics from the main game logic for clarity.

9. **HighScore**
    - Represents an individual **high score entry**, encapsulating player score and related metadata.
    - Used by `HighScoreManager` to maintain a structured and testable score system.

10. **BlockType (enum)**
    - Encapsulates each **brick type and its corresponding colour**.
    - Replaces the large switch statement in `getFillColor(int i)`, improving **readability and  maintainability**.
    - Demonstrates **polymorphism** by allowing block behaviour (colour) to be associated directly with the block type.
   

- The original `GuiController` was identified as a **God class**, as it was responsible for rendering, game logic, input handling, state updates, and UI management.
- This design violated the **Single Responsibility Principle (SRP)** and made the codebase difficult to maintain and test.
- Thus, I created GameRenderer,GameLoop,GarbageManager,HighScoreManager and Input handler. This refactor improved separation of concerns, reduced coupling between components, and made the codebase easier to maintain, test, and extend.
- Core game logic was extracted into a dedicated **GameEngine**, separating gameplay mechanics from the GameController class.
- The controller was simplified to act purely as a **coordinator** between the UI and the game state.
- Helper methods such as `updateBoardViewData(...)` and `updateGameDisplay()` were introduced in the `GameController` to centralise repeated logic.
- These refactoring changes reduced **code duplication**, improved **readability**, and made the system more **modular, testable, and easier to extend**.
- Switch statement used in mapping colours in GuiController was identified as a code smell.Switches centralize behavior, are hard to maintain, and violate OOP principles.
- The block colour mapping was refactored into an **enum-based design** using a `BlockType` enum, where each block type encapsulates its own colour data.
- This enum approach replaces type codes with **polymorphic behavior**, improving maintainability, readability, and extensibility.
- Classes were reorganized into logical packages such as `model`, `view`, `controller`, `events`, `logic`, and `bricks`, improving **project structure, discoverability, and maintainability**.


## Testing

The project was tested using **JUnit 5**.

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
   
## Bugs faced and fixed

1. **Speed and Level Progression Mismatch**
- **Issue:** Falling speed wasn’t updating properly when the player leveled up.
- **Solution:** Added `updateLevelAndSpeed()` method in `GameController` to dynamically calculate speed using:
  ```java
  int newSpeed = Math.max(80, 400 - (currentLevel - 1) * 50);
  ```
  and updated the `GameLoop` to reflect the new speed immediately after a level change.

2. **Random Garbage Blocks Overwriting Active Pieces**

- **Issue:** Garbage blocks could appear on top of the currently falling piece.

- **Solution:** Validated positions in spawnRandomGarbageBlocks() to ensure blocks spawn only where no active piece is located.



   
## Planned features but not implemented

- **Audio Integration (Planned but Removed)**
    - Initially attempted to add background music to enhance gameplay.
    - Tried using an MP4 file but encountered compatibility issues; eventually considered WAV format for better support.
    - Implemented basic playback, but several challenges arose:
        - Music did not restart properly when starting a new game mid-session.
        - Adding audio increased the responsibilities of `GUIController`, which conflicted with single responsibility principles.
        - Limited time prevented implementing additional features such as volume control, pause/resume music, or dedicated audio buttons.
    - Decision: Removed audio integration to maintain code simplicity and focus on core gameplay features.

   
