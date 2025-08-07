# Explicación detallada de la clase `Usuario`

A continuación se presenta el código completo de la clase `Usuario.java` y una explicación de su funcionamiento, propósito, variables, métodos y uso típico, siguiendo el estilo detallado de documentación.

---

## Código completo de `Usuario.java`

```java
package org.upemor.pruebaswing04.model.entity;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

/** @author cerimice **/

@Setter
@Getter
public class Usuario extends Entity{
    
    private String nombre;
    private String apellidos;
    private String email;
    private String password;
    private LocalDate fechaDeNacimiento;
    private long tipoUsuario;
    
    @Override
    public String toString(){
        return nombre;
    }
    
}
```

---

## Explicación de la clase

### 1. Propósito general
La clase `Usuario` representa a un usuario dentro del sistema. Modela los datos personales y de autenticación necesarios para la gestión de usuarios en la aplicación.

### 2. Variables
- `private String nombre;` — Nombre del usuario.
- `private String apellidos;` — Apellidos del usuario.
- `private String email;` — Correo electrónico del usuario.
- `private String password;` — Contraseña del usuario.
- `private LocalDate fechaDeNacimiento;` — Fecha de nacimiento.
- `private long tipoUsuario;` — Identificador del tipo de usuario (relación con la entidad `TipoUsuario`).

### 3. Métodos
- Métodos getter y setter para todos los campos, generados por Lombok.
- `public String toString()` — Retorna el nombre del usuario, útil para mostrar en interfaces gráficas o logs.

### 4. Herencia
Hereda de la clase base `Entity`, por lo que incluye el campo `id` y sus métodos asociados.

### 5. Uso típico
Se utiliza para representar y manipular la información de los usuarios en el sistema, tanto en la lógica de negocio como en la persistencia de datos.

```java
Usuario usuario = new Usuario();
usuario.setNombre("Juan");
usuario.setEmail("juan@ejemplo.com");
// ...otros setters...
```

### 6. Resumen
La clase `Usuario` centraliza los datos y operaciones básicas de los usuarios, facilitando su gestión y validación en la aplicación.
