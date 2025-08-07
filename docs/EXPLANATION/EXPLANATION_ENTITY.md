# Explanation of Entity.java

## Overview

The `Entity` class is a base class for all entities in the project. It defines a common structure and behavior that all data objects (entities) will inherit.
All entities in the system are required to have an `id` field, because they extend from this class. This ensures every entity can be uniquely identified and managed in the database and application logic.

## Code
```java
package org.upemor.pruebaswing04.model.entity;

import lombok.Getter;
import lombok.Setter;

/** @author cerimice **/

@Setter
@Getter
public class Entity{
    protected long id;
}
```

## Key Points
- **Lombok Annotations:**
  - `@Getter` and `@Setter` automatically generate getter and setter methods for the `id` field, reducing boilerplate code.
- **Field `id`:**
  - The `id` field is protected, so it is accessible to subclasses but not to unrelated classes.
  - Every entity that extends `Entity` will have an `id` property, which is useful for identification and database operations.
- **Inheritance:**
  - All other entity classes (like `Usuario`, `TipoUsuario`) should extend `Entity` to inherit the `id` field and its accessors.

## Why Use a Base Entity Class?
- Ensures all entities have a unique identifier (`id`).
- Simplifies code reuse and consistency across the model layer.
- Makes it easier to implement generic controllers and repositories that rely on the presence of an `id` field.

## Example
```java
public class Usuario extends Entity {
    // Other fields and methods
}
```

In this example, `Usuario` automatically has an `id` field with getter and setter methods, thanks to inheritance from `Entity` and Lombok annotations.

---

## About the toString() Method in TipoUsuario and Usuario

In both the `TipoUsuario` and `Usuario` classes, the `toString()` method is overridden to return only the value of the `nombre` field:

```java
@Override
public String toString() {
    return nombre;
}
```

### Why only `nombre`?
- The main reason is to provide a simple, clear, and user-friendly representation of the object, especially when displaying lists or combo boxes in the UI. Users can quickly identify each item by its name, without being overwhelmed by extra details.
- If `toString()` included all attributes, the output would be long and cluttered, making the UI harder to read and less intuitive for users.
- For most UI scenarios, showing just the name is enough to distinguish between objects.

### When would you include more attributes?
- If you need a more detailed representation (for debugging, logging, or exporting data), you can override `toString()` to include more fields, like:

```java
@Override
public String toString() {
    return "Usuario{" +
        "id=" + getId() +
        ", nombre='" + nombre + '\'' +
        ", apellidos='" + apellidos + '\'' +
        ", email='" + email + '\'' +
        ", fechaDeNacimiento=" + fechaDeNacimiento +
        ", tipoUsuario=" + tipoUsuario +
        '}';
}
```

#### What is debugging?
Debugging is the process of finding and fixing errors or unexpected behavior in your code. When debugging, developers often need to see all the details of an object to understand what is happening internally. A detailed `toString()` helps by showing all the object's data in logs or the console.

#### What is UI display?
UI (User Interface) display refers to how information is presented to the user in the application's graphical interface, such as lists, tables, or combo boxes. For UI display, it's best to show only the most relevant and readable information (like a user's name) to keep the interface clean and user-friendly.

### Summary
- Overriding `toString()` to return only `nombre` makes your UI and logs more readable and user-friendly.
- You can customize `toString()` as needed for different contexts (UI vs. debugging).
