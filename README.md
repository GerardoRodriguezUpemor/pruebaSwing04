# PruebaSwing04

This project is a Java Swing application following the Model-View-Controller (MVC) pattern. It manages users and user types, with a clear separation between entities, repositories, controllers, and views. The project uses generics for flexible and type-safe controllers and repositories, and includes documentation to help understand the architecture.

## Features
- User and user type management
- MVC architecture
- Generic controllers and repositories for code reuse
- SQLite database integration
- Well-documented code and structure

## Structure
- `src/main/java/org/upemor/pruebaswing04/model/entity/`: Entity classes (e.g., `Usuario`, `TipoUsuario`)
- `src/main/java/org/upemor/pruebaswing04/model/repository/`: Data access and repository classes
- `src/main/java/org/upemor/pruebaswing04/controller/`: Controllers for business logic
- `src/main/java/org/upemor/pruebaswing04/view/`: Swing UI dialogs and tools
- `docs/`: Diagrams and SQL scripts
- `prueba_swing.db`: SQLite database file
- `EXPLANATION_CONTROLLER.md`: Detailed explanation of the generic controller pattern

## Getting Started
1. Clone the repository
2. Build with Maven (`mvn clean install`)
3. Run the application

## Author
cerimice
