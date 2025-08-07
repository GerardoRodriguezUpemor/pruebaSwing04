# Explicación detallada de la clase `ControllerUsuario`

A continuación se presenta el código completo de la clase `ControllerUsuario.java` y una explicación de su funcionamiento, métodos y propósito dentro del patrón MVC.

---

## Código completo de `ControllerUsuario.java`

```java
package org.upemor.pruebaswing04.controller;

import org.upemor.pruebaswing04.model.entity.Usuario;
import org.upemor.pruebaswing04.model.repository.RepositoryUsuario;

/** @author cerimice **/

public class ControllerUsuario extends Controller<RepositoryUsuario,Usuario>{
    
    public ControllerUsuario() throws Exception{
        repository = new RepositoryUsuario();
    }

    @Override
    protected boolean validate(Usuario obj) throws Exception{
        if(obj.getNombre().isEmpty())
            throw new Exception("El nombre no ha sido proporcionado");
        if(obj.getApellidos().isEmpty())
            throw new Exception("El apellido no ha sido proporcionado");
        if(obj.getEmail().isEmpty())
            throw new Exception("El email no ha sido proporcionado");
        if(obj.getPassword().isEmpty())
            throw new Exception("El password no ha sido proporcionado");
        if(obj.getTipoUsuario()<= 0)
            throw new Exception("El tipo de usuario no ha sido proporcionado");
        return true;
    }
    
}
```

---

## Explicación de la clase

### 1. Propósito general
`ControllerUsuario` es un controlador concreto que extiende la clase genérica `Controller`, especializado para la entidad `Usuario` y su repositorio correspondiente. Su función principal es gestionar la lógica de negocio y validación relacionada con los usuarios.

### 2. Constructor
- `public ControllerUsuario() throws Exception`:
  - Inicializa el repositorio específico (`RepositoryUsuario`) que se usará para acceder y manipular los datos de los usuarios.

### 3. Método de validación
- `protected boolean validate(Usuario obj) throws Exception`:
  - Verifica que los campos obligatorios del usuario no estén vacíos: nombre, apellidos, email, password y tipo de usuario.
  - Si alguno de estos campos no es válido, lanza una excepción con un mensaje descriptivo.
  - Si la validación es exitosa, retorna `true`.

### 4. Herencia y reutilización
- Hereda de `Controller<RepositoryUsuario, Usuario>`, por lo que obtiene métodos genéricos para guardar y recuperar entidades, centralizando la lógica común y permitiendo que este controlador se enfoque en la validación específica de la entidad `Usuario`.

### 5. Resumen
Esta clase facilita la gestión de los usuarios en la aplicación, asegurando que los datos sean válidos antes de ser almacenados o modificados, y promoviendo la reutilización de código mediante la herencia del controlador base.
