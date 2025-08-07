# Explicación detallada de la clase `Repository<T extends Entity>`

A continuación se presenta el código completo de la clase `Repository.java` y una explicación de su funcionamiento, propósito, variables, métodos y uso típico, siguiendo el estilo detallado de documentación.

---

## Código completo de `Repository.java`

```java
package org.upemor.pruebaswing04.model.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;
import org.upemor.pruebaswing04.model.entity.Entity;

/** @author cerimice **/

public abstract class Repository<T extends Entity>{
    
    protected MiConexion myConnection;
    protected PreparedStatement statement;
    
    /* query definition */
    protected String queryCreate;   // INSERT INTO tabla VALUES(?,?,?)
    protected String queryRead;     // SELECT * FROM tabla WHERE id IN (?);
    protected String queryReadAll;  // SELECT * FROM tabla;
    protected String queryUpdate;   // REPLACE INTO tabla VALUES(?)
    protected String queryDelete;   // DELETE FROM tabla WHERE id IN (?);
    
    public Repository(String tabla,long parameters) throws Exception{
        try{
            myConnection = MiConexion.getInstancia();
            initQueries(tabla,parameters);
        }catch(Exception ex){
            System.out.println(
                "Error: "+ex.getMessage()
                +" in method: constructor()"
                +" in class: "+this.getClass().getName()
            );
            throw ex;
        }
    }
    
    protected void initQueries(String tabla,long parameters){
        queryCreate = "INSERT INTO "+tabla+" VALUES(NULL";
        for(int x=1;x<=(parameters-1);x++) queryCreate += ",?";
        queryCreate += ")";
        queryRead = "SELECT * FROM "+tabla+" WHERE id IN (?)";
        queryReadAll = "SELECT * FROM "+tabla+"";
        queryUpdate = "REPLACE INTO "+tabla+" VALUES(";
            for(int x=1;x<=parameters;x++) queryUpdate += ",?";
            queryUpdate += ")";
            queryUpdate = queryUpdate.replace("(,?","(?");
        queryDelete = "DELETE FROM "+tabla+" WHERE id IN (?)";
    }

    /**
     * Método abstracto que debe implementar la clase hija concreta.
     * Convierte una fila del ResultSet en un objeto de tipo T (la entidad específica).
     */
    protected abstract T objectMapping(ResultSet rs) throws Exception;

    /**
     * Método abstracto que debe implementar la clase hija concreta.
     * Asigna los valores del objeto a los parámetros del PreparedStatement.
     */
    protected abstract void setStatementParameters(PreparedStatement statement,T obj,boolean newObj) throws Exception;
    
    /**
     * Inserta un nuevo registro en la base de datos usando el objeto recibido.
     */
    public boolean create(T obj) throws Exception{
        try{
            statement = myConnection.conectar().prepareStatement(queryCreate);
            setStatementParameters(statement,obj,true);
            int result = statement.executeUpdate();
            return result>=0;
        }catch(Exception ex){
            System.out.println(
                "Error: "+ex.getMessage()
                +" in method: create()"
                +" in class: "+this.getClass().getName()
            );
            throw ex;
        }
    }
    
    /**
     * Busca un registro por su id y lo convierte en un objeto de tipo T.
     */
    public T read(long id) throws Exception{
        try{
            statement = myConnection.conectar().prepareStatement(queryRead);
            statement.setLong(1,id);
            ResultSet rs  = statement.executeQuery();
            T obj = null;
            while(rs.next()){obj = objectMapping(rs);}
            return obj;
        }catch(Exception ex){
            System.out.println(
                "Error: "+ex.getMessage()
                +" in method: read()"
                +" in class: "+this.getClass().getName()
            );
            throw ex;
        }
    }
    
    /**
     * Obtiene todos los registros de la tabla y los convierte en una lista de objetos de tipo T.
     */
    public List<T> readAll() throws Exception{
        try{
            statement = myConnection.conectar().prepareStatement(queryReadAll);
            ResultSet rs  = statement.executeQuery();
            T obj = null;
            List<T> list = new LinkedList<>();
            while(rs.next()){
                obj = objectMapping(rs);
                list.add(obj);
            }
            return list;
        }catch(Exception ex){
            System.out.println(
                "Error: "+ex.getMessage()
                +" in method: readAll()"
                +" in class: "+this.getClass().getName()
            );
            throw ex;
        }
    }
    
    /**
     * Actualiza un registro existente en la base de datos usando el objeto recibido.
     */
    public boolean update(T obj) throws Exception{
        try{
            if(obj.getId() <= 0) throw new Exception("El id no puede ser cero en la actualizacion");
            statement = myConnection.conectar().prepareStatement(queryUpdate);
            setStatementParameters(statement,obj,false);
            int result = statement.executeUpdate();
            return result>=0;
        }catch(Exception ex){
            System.out.println(
                "Error: "+ex.getMessage()
                +" in method: update()"
                +" in class: "+this.getClass().getName()
            );
            throw ex;
        }
    }
    
    /**
     * Elimina un registro de la base de datos según su id.
     */
    public boolean delete(long id) throws Exception{
        try{
            statement = myConnection.conectar().prepareStatement(queryDelete);
            statement.setLong(1,id);
            int result = statement.executeUpdate();
            return result>=0;
        }catch(Exception ex){
            System.out.println(
                "Error: "+ex.getMessage()
                +" in method: delete()"
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
La clase abstracta `Repository<T extends Entity>` centraliza la lógica de acceso y manipulación de datos en la base de datos para cualquier entidad. Permite implementar operaciones CRUD (crear, leer, actualizar, eliminar) de forma genérica y reutilizable, siguiendo el patrón Repository.

### 2. Variables
- `protected MiConexion myConnection;` — Instancia de la clase de conexión a la base de datos.
- `protected PreparedStatement statement;` — Objeto para ejecutar sentencias SQL parametrizadas.
- Cadenas para las consultas SQL: `queryCreate`, `queryRead`, `queryReadAll`, `queryUpdate`, `queryDelete`.

### 3. Métodos principales
- `public Repository(String tabla, long parameters)` — Constructor que inicializa la conexión y las consultas SQL según la tabla y el número de parámetros.
- `protected void initQueries(String tabla, long parameters)` — Construye dinámicamente las consultas SQL para la tabla.
- Métodos abstractos:
  - `protected abstract T objectMapping(ResultSet rs)` — Convierte una fila del ResultSet en un objeto de tipo T.
  - `protected abstract void setStatementParameters(PreparedStatement statement, T obj, boolean newObj)` — Asigna los valores del objeto a los parámetros del SQL.
- Métodos CRUD:
  - `public boolean create(T obj)` — Inserta un nuevo registro.
  - `public T read(long id)` — Busca un registro por id.
  - `public List<T> readAll()` — Obtiene todos los registros.
  - `public boolean update(T obj)` — Actualiza un registro existente.
  - `public boolean delete(long id)` — Elimina un registro por id.

### 4. Uso típico
Se utiliza como clase base para repositorios concretos de cada entidad, permitiendo implementar solo los métodos de mapeo y asignación de parámetros.

```java
public class UsuarioRepository extends Repository<Usuario> {
    // Implementar objectMapping y setStatementParameters
}
```

### 5. Resumen
La clase `Repository` promueve la reutilización de código, la centralización de la lógica de acceso a datos y la separación de responsabilidades, facilitando la implementación de repositorios para diferentes entidades en el proyecto.
