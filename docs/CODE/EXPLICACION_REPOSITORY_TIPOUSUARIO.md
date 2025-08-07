# Explicación detallada de la clase `RepositoryTipoUsuario`

A continuación se presenta el código completo de la clase `RepositoryTipoUsuario.java` y una explicación de su funcionamiento, propósito, variables, métodos y uso típico, siguiendo el estilo detallado de documentación.

---

## Código completo de `RepositoryTipoUsuario.java`

```java
package org.upemor.pruebaswing04.model.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

import org.upemor.pruebaswing04.model.entity.TipoUsuario;

/** @author cerimice **/

public class RepositoryTipoUsuario extends Repository<TipoUsuario>{
    
    public RepositoryTipoUsuario() throws Exception{
        super("tipo_usuario",2);
    }

    @Override
    protected TipoUsuario objectMapping(ResultSet rs) throws Exception{
        TipoUsuario obj = new TipoUsuario();
            obj.setId(rs.getLong("id"));
            obj.setNombre(rs.getString("nombre"));
        return obj;
    }

    @Override
    protected void setStatementParameters(PreparedStatement statement, TipoUsuario obj, boolean newObj) throws Exception{
        int i=1;
        if(!newObj) statement.setLong(i++,obj.getId());
        statement.setString(i++,obj.getNombre());
    }
    
    public List<TipoUsuario> obtenerPorNombre(String nombre) throws Exception{
        try{
            String sql = "SELECT * FROM tipo_usuario WHERE nombre LIKE (?)";
            statement = myConnection.conectar().prepareStatement(sql);
                statement.setString(1, nombre);
            ResultSet rs = statement.executeQuery();
            TipoUsuario obj = null;
            List<TipoUsuario> lista = new LinkedList<>();
            while(rs.next()){
                obj = objectMapping(rs);
                lista.add(obj);
            }
            return lista;
        }catch (Exception ex){
            System.out.println(
                "Error: "+ex.getMessage()
                +" in method: obtenerPorNombre()"
                +" in class: "+this.getClass().getName()
            );
            throw ex;
        }
    }
}
```

---

## Explicación de la clase

### 1. Propósito general
La clase `RepositoryTipoUsuario` es un repositorio concreto que extiende la clase genérica `Repository` para la entidad `TipoUsuario`. Centraliza la lógica de acceso a datos y operaciones CRUD para los tipos de usuario en la base de datos.

### 2. Variables
- Hereda todas las variables de la clase base `Repository`, como la conexión, el statement y las consultas SQL.

### 3. Métodos principales
- `public RepositoryTipoUsuario()`: Constructor que inicializa el repositorio para la tabla `tipo_usuario` con 2 parámetros (id y nombre).
- `protected TipoUsuario objectMapping(ResultSet rs)`: Convierte una fila del ResultSet en un objeto `TipoUsuario`.
- `protected void setStatementParameters(PreparedStatement statement, TipoUsuario obj, boolean newObj)`: Asigna los valores del objeto a los parámetros del SQL.
- `public List<TipoUsuario> obtenerPorNombre(String nombre)`: Busca tipos de usuario cuyo nombre coincida con el parámetro.

### 4. Uso típico
Se utiliza para gestionar los tipos de usuario en la base de datos, permitiendo crear, leer, actualizar, eliminar y buscar por nombre.

```java
RepositoryTipoUsuario repo = new RepositoryTipoUsuario();
List<TipoUsuario> admins = repo.obtenerPorNombre("Administrador");
```

### 5. Resumen
La clase `RepositoryTipoUsuario` facilita la gestión de los tipos de usuario, centralizando la lógica de acceso a datos y promoviendo la reutilización y el mantenimiento del código.
