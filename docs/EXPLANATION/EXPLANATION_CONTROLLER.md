# Explanation of Controller<R extends Repository<E>, E extends Entity>

## Table of Contents
1. [Overview](#overview)
2. [Why Use Generics?](#why-use-generics)
3. [Example Usage](#example-usage)
4. [Summary](#summary)
5. [How the `save` Method Works](#how-the-save-method-works)
6. [How the `getAll` Method Works](#how-the-getall-method-works)
7. [About Type Parameters and Arguments in Generics](#about-type-parameters-and-arguments-in-generics)
8. [Specialization: ControllerTipoUsuario](#specialization-controllertipousuario)

---

## Overview

`Controller<R extends Repository<E>, E extends Entity>` is an abstract generic class in Java.
- `E` is a type parameter that represents an entity. It must inherit from the base class `Entity`.
- `R` is a type parameter that represents a repository. It must inherit from `Repository<E>`, meaning it is a repository designed to work with the entity type `E`.

## Why Use Generics?

- This ensures that the controller and the repository always work with the same type of entity, keeping your code type-safe and consistent.
- The controller can use all the methods from `Entity` on objects of type `E` (for example, `getId()`), and all the methods from `Repository` on objects of type `R`.
- You can create controllers for different entities and repositories, but always with the correct relationship between them.

## Example Usage

Suppose you have:
- `User` class that extends `Entity`
- `UserRepository` class that extends `Repository<User>`

You can create a controller like this:

```java
public class UserController extends Controller<UserRepository, User> {
    // Implementation here
}
```

- Here, `R` is `UserRepository` and `E` is `User`.
- The controller will work with a repository that is specialized for `User` entities.

## Summary

- `E` is always an entity type.
- `R` is always a repository that works with that entity type.
- This makes your code flexible, safe, and easy to maintain.
- All repository operations are guaranteed to be compatible with the entity type.

---

## How the `save` Method Works

- The `save` method first validates the entity using a method (`validate`) that is specific for each type of entity and repository.
- If validation passes:
  - If the entity's ID is 0, it means the entity is new, so it creates a new record in the database.
  - If the entity's ID is not 0, it means the entity already exists, so it updates the existing record in the database.
- If validation fails, it returns `false` and does not save anything.
- Any errors are printed and rethrown.

**In summary:**
- Each controller subclass implements its own validation logic for its entity type.
- The `save` method uses this validation and then decides whether to create or update the entity in the database, depending on the entity's ID.

---

## How the `getAll` Method Works

- The repository is of type `R`, which is determined by the entity type `E` you choose for your controller.
- The repository instance (`repository`) implements the method `readAll`, which returns all entities of type `E` stored in that repository.
- The controller's `getAll` method simply calls `repository.readAll()` and returns a list of all entities of type `E`.

**In summary:**
- The controller always works with the correct repository and entity types, so when you call `getAll`, you get a list of the specific entities managed by that repository.

---

## About Type Parameters and Arguments in Generics

In the base class declaration:

```java
public abstract class Controller<R extends Repository<E>, E extends Entity>
```

- `R` and `E` are type parameters (placeholders for types).
- When you create a subclass, you provide the actual types (arguments), for example:

```java
public class ControllerTipoUsuario extends Controller<RepositoryTipoUsuario, TipoUsuario>
```

Here, you are specifying the concrete types for `R` and `E`.
So, in the base class, they are parameters; in the subclass, you pass the arguments.

---

## Specialization: ControllerTipoUsuario

The class `ControllerTipoUsuario` works specifically with the repository `RepositoryTipoUsuario` and the entity `TipoUsuario` because of its declaration:

```java
public class ControllerTipoUsuario extends Controller<RepositoryTipoUsuario, TipoUsuario>
```

This means:
- All methods and variables in the controller are adapted to manage entities of type `TipoUsuario` using the corresponding repository `RepositoryTipoUsuario`.
- The `validate` method in this controller is designed to check the attributes of a `TipoUsuario` object, ensuring the data is correct before saving.