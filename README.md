# Naval-battle
This is my first more complex project with Java.

Project developed for the **Programming 1** course  
Master **MIASHS – DCISS**  

---

## Project Overview

This project consists of developing a **Battleship game** in Java, following a three-step progression:

1. Game model implementation (coordinates, ships, grid)
2. Game logic and player hierarchy
3. Graphical user interface (GUI)

The project focuses on **object-oriented programming**, **game logic design**, and **separation of concerns** between model, logic, and interface.

---

## Step 1 — Game Model

### 📍 `Coordonnee` Class
Represents a coordinate on a Battleship grid.

**Main features:**
- Conversion between Battleship coordinates (`A1` to `Z26`) and Java indices (`0–25`)
- Comparison and adjacency detection

**Key methods:**
- `toString()`
- `equals(Object obj)`
- `voisine(Coordonnee c)`
- `compareTo(Coordonnee c)`

---

### 🚢 `Navire` Class
Represents a ship placed on the grid.

**Main attributes:**
- Start and end coordinates
- Hit parts tracking
- Number of hits received

**Features:**
- Overlap and adjacency detection
- Shot handling
- Ship states: hit / sunk

---

### 🗺️ `GrilleNavale` Class
Represents the square game grid.

**Features:**
- Automatic or manual ship placement
- Shot tracking
- Text-based grid display
- Defeat detection

**Console display legend:**
- `.` : empty cell, not shot  
- `#` : ship  
- `O` : shot in water  
- `X` : hit ship part  

---

## Step 2 — Game Logic and Players

### 👤 Abstract Class `Joueur`
Superclass for all player types.

**Responsibilities:**
- Turn-based game flow
- Attack / defense communication
- Possible shot outcomes:  
  `TOUCHE`, `COULE`, `A_L_EAU`, `GAMEOVER`

---

### 🧠 Abstract Class `JoueurAvecGrille`
Extends `Joueur` by adding a defensive grid.

- Implements the `defendre(Coordonnee c)` method

---

### ⌨️ `JoueurTexte` Class
Human player interacting through the console.

- Keyboard input for attacks
- Console feedback for shot results

---

### 🤖 `JoueurAuto` Class
Computer-controlled player.

- Automatic attack selection
- Can be extended to implement different strategies

---

## Step 3 — Graphical Interface (Swing)

### 🎨 `GrilleNavaleGraphique` Class
Graphical extension of `GrilleNavale`.

**Color code:**
- 🟩 Green: ship
- 🔴 Red: successful hit
- 🔵 Blue: miss (water)

---

### 🪟 `FenetreJoueur` Class
Swing window representing a player interface.

- Attack grid
- Defense grid
- Optional automatic ship placement

---

### 🖱️ `JoueurGraphique` Class
Human player interacting via mouse clicks.

- Attack selection by clicking
- Feedback displayed using `JOptionPane`

---

### The window `BatailleNavale` Class
Main class used to start the game.

- Initializes two players
- Launches the game in a separate thread
- Window builder

---

## Running the Project

1. Import the project into a Java IDE (Eclipse, IntelliJ IDEA, etc.)
2. Ensure Swing is available
3. Run the `BatailleNavale` class
4. Enjoy

---

## Technologies Used

- Java
- Object-Oriented Programming (OOP)
- Swing (GUI)
- Console I/O

---

## Learning Objectives

- Design a complete object-oriented architecture
- Implement complex interactions between classes
- Develop full game logic
- Separate model, logic, and graphical interface

---

## Author

Academic project developed as part of the Master MIASHS program in Université Grenoble Alpes.
