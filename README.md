# Java Interactive Graph Editor

## Overview

This project is a graphical application for creating and managing graphs. It allows users to add, edit, and delete nodes (sommets) and edges (arcs) in a graphical interface. The application also includes functionality to calculate the shortest path between nodes.

 ![image](https://github.com/user-attachments/assets/3608b4d6-3a87-4cb2-af99-e16cd06ebb83)

## Features

- Add, edit, and delete nodes (sommets)
- Add, edit, and delete edges (arcs)
- Calculate the shortest path between nodes
- Toggle between light and dark mode

## Usage

1. Run the `Main` class to start the application.
2. Use the graphical interface to interact with the graph:
    - Click once on the canvas to select a node.
    - Double-click on the canvas to add a new node.
    - Click on an edge to select it.
    - Use the buttons to create or delete nodes and edges.
    - Use the "Chemin le plus court" button to calculate the shortest path between two nodes.

## Project Structure

- `src/controller`: Contains the controller classes (`Maincontrol`, `Mousecontrol`, etc.)
- `src/modele`: Contains the model classes (`Graphe`, `Sommet`, `Arc`, etc.)
- `src/vue`: Contains the view classes (`GrapheView`, `SommetView`, `ArcView`, etc.)
- `src/Main.java`: The main entry point of the application
