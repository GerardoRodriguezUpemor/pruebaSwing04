# Explicación detallada de la clase `TipoUsuario`

A continuación se presenta el código completo de la clase `TipoUsuario.java` y una explicación de su funcionamiento, propósito, variables, métodos y uso típico, siguiendo el estilo detallado de documentación.

---

## Código completo de `TipoUsuario.java`

```java
package org.upemor.pruebaswing04.model.entity;

import lombok.Getter;
import lombok.Setter;

/** @author cerimice **/

@Setter
@Getter
public class TipoUsuario extends Entity{
    
    private String nombre;
    
    @Override
    public String toString(){
        return nombre;
    }
    
}
```

---

## Explicación de la clase

### 1. Propósito general
La clase `TipoUsuario` representa los diferentes tipos o roles que puede tener un usuario dentro del sistema (por ejemplo, administrador, usuario estándar, etc.). Facilita la gestión de permisos y categorización de usuarios.

### 2. Variables
- `private String nombre;` — Nombre o descripción del tipo de usuario (por ejemplo, "Administrador").

### 3. Métodos
- Métodos getter y setter para el campo `nombre`, generados por Lombok.
- `public String toString()` — Retorna el nombre del tipo de usuario, útil para mostrar en listas o interfaces gráficas.

### 4. Herencia
Hereda de la clase base `Entity`, por lo que incluye el campo `id` y sus métodos asociados.

### 5. Uso típico
Se utiliza para definir y manipular los distintos tipos de usuario en la aplicación, permitiendo asociar cada usuario a un tipo específico.

```java
TipoUsuario tipo = new TipoUsuario();
tipo.setNombre("Administrador");
```

### 6. Resumen
La clase `TipoUsuario` centraliza la información sobre los roles o categorías de usuario, facilitando la administración de permisos y la organización de los usuarios en el sistema.
