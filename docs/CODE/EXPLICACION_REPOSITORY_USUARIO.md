# Explicación detallada de la clase `RepositoryUsuario`

A continuación se presenta el código completo de la clase `RepositoryUsuario.java` y una explicación de su funcionamiento, propósito, variables, métodos y uso típico, siguiendo el estilo detallado de documentación.

---

## Código completo de `RepositoryUsuario.java`

```java
package org.upemor.pruebaswing04.model.repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.upemor.pruebaswing04.model.entity.Usuario;

/** @author cerimice **/

public class RepositoryUsuario extends Repository<Usuario>{
    
    private LocalDate dateToLocaDate(Date fecha){
        LocalDate nuevaFecha = LocalDate.of(
            fecha.getYear()+1900
            ,fecha.getMonth()+1
            ,fecha.getDate()
        );
        return nuevaFecha;
    }
    
    public RepositoryUsuario() throws Exception{
        super("usuario",7);
    }

    @Override
    protected Usuario objectMapping(ResultSet rs) throws Exception{
        Usuario obj = new Usuario();
            obj.setId(rs.getLong("id"));
            obj.setNombre(rs.getString("nombre"));
            obj.setApellidos(rs.getString("apellidos"));
            obj.setEmail(rs.getString("email"));
            obj.setPassword(rs.getString("password"));
            obj.setFechaDeNacimiento(dateToLocaDate(rs.getDate("fecha_de_nacimiento")));
            obj.setTipoUsuario(rs.getLong("tipo_usuario"));
        return obj;
    }

    @Override
    protected void setStatementParameters(PreparedStatement statement, Usuario obj, boolean newObj) throws Exception{
        int i=1;
        if(!newObj) statement.setLong(i++,obj.getId());
        statement.setString(i++,obj.getNombre());
        statement.setString(i++,obj.getApellidos());
        statement.setString(i++,obj.getEmail());
        statement.setString(i++,obj.getPassword());
        statement.setString(i++,obj.getFechaDeNacimiento().format(DateTimeFormatter.ISO_DATE));
        statement.setLong(i++,obj.getTipoUsuario());
    }
    
}
```

---

## Explicación de la clase

### 1. Propósito general
La clase `RepositoryUsuario` es un repositorio concreto que extiende la clase genérica `Repository` para la entidad `Usuario`. Centraliza la lógica de acceso a datos y operaciones CRUD para los usuarios en la base de datos.

### 2. Variables
- Hereda todas las variables de la clase base `Repository`, como la conexión, el statement y las consultas SQL.
- No define variables adicionales propias.

### 3. Métodos principales
- `public RepositoryUsuario()`: Constructor que inicializa el repositorio para la tabla `usuario` con 7 parámetros (id, nombre, apellidos, email, password, fecha_de_nacimiento, tipo_usuario).
- `private LocalDate dateToLocaDate(Date fecha)`: Convierte un objeto `Date` de SQL a `LocalDate` de Java.
- `protected Usuario objectMapping(ResultSet rs)`: Convierte una fila del ResultSet en un objeto `Usuario`.
- `protected void setStatementParameters(PreparedStatement statement, Usuario obj, boolean newObj)`: Asigna los valores del objeto a los parámetros del SQL.

### 4. Uso típico
Se utiliza para gestionar los usuarios en la base de datos, permitiendo crear, leer, actualizar y eliminar usuarios, así como mapear los datos entre la base y la aplicación.

```java
RepositoryUsuario repo = new RepositoryUsuario();
// Crear, leer, actualizar o eliminar usuarios usando los métodos heredados de Repository
```

### 5. Resumen
La clase `RepositoryUsuario` facilita la gestión de los usuarios, centralizando la lógica de acceso a datos y promoviendo la reutilización y el mantenimiento del código.
