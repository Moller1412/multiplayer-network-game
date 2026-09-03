# SynSpil – Multiplayer Network Game

A multiplayer game developed as part of the 3rd semester Distribution, Integration and Security course on the Computer Science programme at Erhvervsakademi Aarhus.

The project focuses on network programming and client-server communication using Java and TCP sockets.

## About the Project

SynSpil is a multiplayer game where multiple clients connect to a central server.

The server is responsible for handling connected players and communicating game state between the clients.

The project was developed as a group project with a focus on distributed systems, network communication and concurrent programming.

## Architecture

The application follows a client-server architecture:

```text
             ┌──────────────┐
             │    Server    │
             │              │
             │ TCP Server   │
             └──────┬───────┘
                    │
             ┌──────┴───────┐
             │              │
          TCP│              │TCP
             │              │
     ┌───────▼─────┐  ┌─────▼───────┐
     │   Client 1  │  │   Client 2  │
     │             │  │             │
     │   JavaFX    │  │   JavaFX    │
     └─────────────┘  └─────────────┘
Features
Multiplayer gameplay
Client-server architecture
TCP socket communication
Multiple simultaneous clients
Server-side player management
Communication between clients through the server
JavaFX graphical user interface
Real-time game state updates
Multithreaded server communication
Technologies
Java
JavaFX
TCP/IP
Java Sockets
Multithreading
Maven
Git
Project Structure
src/
├── App/
│   └── App.java
│
├── Gui/
│   └── GUI.java
│
├── Image/
│   ├── fire...
│   ├── hero...
│   ├── wall...
│   └── floor...
│
├── Player/
│   └── Player.java
│
└── Server/
    ├── TCPServer.java
    ├── ServerTraad.java
    ├── ReadThread.java
    └── WriteThread.java
Client

The client contains the JavaFX graphical interface and handles the interaction between the player and the game.

Server

The server manages incoming client connections and handles communication between connected players.

Each client connection is handled using separate threads for reading and writing data.

Networking

The project uses TCP sockets for communication between clients and the central server.

The server listens for incoming connections and maintains communication with multiple clients simultaneously.

The client connects to the server using:

localhost:6790

when running the server and client on the same machine.

Running the Project
Requirements
Java
Maven
IntelliJ IDEA or another Java IDE
Run the server

Start the server application first.

Run the client

After the server has started, launch the JavaFX client.

Multiple clients can be started to test the multiplayer functionality.

Project Context

This project was developed as part of the Distribution, Integration and Security course on the 3rd semester of the Computer Science programme at Erhvervsakademi Aarhus.

The project focused on:

Network programming
TCP/IP communication
Client-server architecture
Multithreading
Distributed systems
JavaFX
Concurrent client handling
Disclaimer

This repository contains a student project developed for educational purposes.
