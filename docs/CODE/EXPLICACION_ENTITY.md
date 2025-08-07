# Explicación detallada de la clase `Entity`

A continuación se presenta el código completo de la clase `Entity.java` y una explicación de su funcionamiento, propósito, variables y uso típico, siguiendo el estilo detallado de documentación.

---

## Código completo de `Entity.java`

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

---

## Explicación de la clase

### 1. Propósito general
La clase `Entity` es la clase base para todas las entidades del modelo de datos en el sistema. Su objetivo es proporcionar un identificador único (`id`) y servir como superclase para otras entidades, facilitando la herencia y la gestión uniforme de identificadores.

### 2. Variables
- `protected long id;`
  - Identificador único de la entidad. Es protegido para que las subclases puedan accederlo directamente.
  - Los métodos getter y setter son generados automáticamente por Lombok (`@Getter` y `@Setter`).

### 3. Métodos
- Métodos getter y setter para el campo `id`, generados por Lombok.
- No define otros métodos, ya que su propósito es ser una clase base simple y reutilizable.

### 4. Uso típico
Las entidades concretas del sistema (por ejemplo, `Usuario`, `TipoUsuario`) extienden esta clase para heredar el campo `id` y su gestión.

```java
public class Usuario extends Entity {
    // ...otros campos y métodos...
}
```

### 5. Resumen
La clase `Entity` promueve la reutilización de código y la consistencia en la definición de identificadores para todas las entidades del sistema, simplificando la implementación y el mantenimiento del modelo de datos.
