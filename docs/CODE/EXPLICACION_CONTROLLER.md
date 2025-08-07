# Explicación detallada de la clase `Controller<R extends Repository<E>, E extends Entity>`

A continuación se presenta el código completo de la clase `Controller.java` y una explicación de su funcionamiento, métodos, constructores y variables.

---

## Código completo de `Controller.java`

```java
/**
 * Abstract generic controller for managing entities and their repositories.
 * <p>
 * This class provides common operations for saving and retrieving entities,
 * delegating data access to a repository. It enforces validation before saving
 * and handles error reporting.
 *
 * <p>Type Parameters:
 * <ul>
 *   <li><b>R</b> the type of repository, must extend Repository&lt;E&gt;</li>
 *   <li><b>E</b> the type of entity, must extend Entity</li>
 * </ul>
 *
 * Example usage:
 * <pre>
 * public class UserController extends Controller&lt;UserRepository, User&gt; { ... }
 * </pre>
 *
 * @author cerimice
 */
package org.upemor.pruebaswing04.controller;

import java.util.List;
import org.upemor.pruebaswing04.model.entity.Entity;
import org.upemor.pruebaswing04.model.repository.Repository;

import lombok.Getter;

public abstract class Controller<R extends Repository<E>,E extends Entity>{

    /**
     * The repository instance used for data access.
     */
    @Getter 
    protected R repository;

    /**
     * Validates the given entity before saving.
     * Subclasses must implement this method to provide specific validation logic.
     *
     * @param obj the entity to validate
     * @return true if valid, false otherwise
     * @throws Exception if validation fails
     */
    protected abstract boolean validate(E obj) throws Exception;
    
    /**
     * Saves the given entity.
     * <p>
     * If the entity's ID is 0, it creates a new record; otherwise, it updates the existing record.
     * Validation is performed before saving.
     *
     * @param obj the entity to save
     * @return true if the operation was successful, false otherwise
     * @throws Exception if an error occurs during saving
     */
    public boolean save(E obj) throws Exception {
        try {
            if (validate(obj)) {
                if (obj.getId() == 0)
                    return repository.create(obj);
                return repository.update(obj);
            }
            return false;
        } catch (Exception ex) {
            System.out.println(
                "Error: " + ex.getMessage()
                + " in method: save()"
                + " in class: " + this.getClass().getName()
            );
            throw ex;
        }
    }
    
    /**
     * Retrieves all entities from the repository.
     *
     * @return a list of all entities
     * @throws Exception if an error occurs during retrieval
     */
    public List<E> getAll() throws Exception {
        try {
            return repository.readAll();
        } catch (Exception ex) {
            System.out.println(
                "Error: " + ex.getMessage()
                + " in method: getAll()"
                + " in class: " + this.getClass().getName()
            );
            throw ex;
        }
    }
    
}
```

---

## Explicación de la clase

### 1. Propósito general
Esta clase abstracta y genérica sirve como controlador base para cualquier entidad y su repositorio asociado. Centraliza la lógica común de validación, guardado y recuperación de entidades, siguiendo el patrón MVC.

### 2. Parámetros genéricos
- `R extends Repository<E>`: El tipo de repositorio asociado a la entidad.
- `E extends Entity`: El tipo de entidad que se va a gestionar.

### 3. Variables
- `protected R repository;`  
  Instancia protegida del repositorio, usada para acceder a los datos. Se marca con `@Getter` de Lombok para exponer un método getter automáticamente.

### 4. Métodos

#### a) `protected abstract boolean validate(E obj) throws Exception;`
Método abstracto que obliga a las subclases a implementar la lógica de validación específica para cada entidad antes de guardar.

#### b) `public boolean save(E obj) throws Exception`
- Valida la entidad usando `validate(obj)`.
- Si el ID es 0, crea un nuevo registro (`repository.create(obj)`), si no, actualiza el existente (`repository.update(obj)`).
- Maneja y reporta errores.

#### c) `public List<E> getAll() throws Exception`
- Recupera todas las entidades usando `repository.readAll()`.
- Maneja y reporta errores.

### 5. Uso típico
Las subclases concretas (por ejemplo, `UserController`) extienden esta clase, definen el repositorio y la entidad concreta, e implementan el método de validación.

```java
public class UserController extends Controller<UserRepository, User> {
    @Override
    protected boolean validate(User obj) {
        // Lógica de validación específica para User
        return obj.getName() != null && !obj.getName().isEmpty();
    }
}
```

### 6. Resumen
Esta clase promueve la reutilización de código, la validación centralizada y la separación de responsabilidades, facilitando la implementación de controladores para diferentes entidades en el proyecto.
