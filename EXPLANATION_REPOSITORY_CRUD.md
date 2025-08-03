# Explicación detallada de los métodos CRUD en Repository<T extends Entity>

Este documento explica a detalle el funcionamiento de los métodos `create`, `read`, `readAll`, `update` y `delete` en la clase abstracta `Repository<T extends Entity>`, así como los tipos de retorno, el uso de `PreparedStatement`, y la relación con la herencia y la API de Java SQL.

---

## ¿Por qué los métodos retornan `boolean` o `T`?

- **boolean:** Los métodos que modifican la base de datos (`create`, `update`, `delete`) retornan un `boolean` para indicar si la operación fue exitosa (`true`) o no (`false`). Esto se determina por el número de filas afectadas por la consulta SQL.
- **T:** Los métodos que recuperan información (`read`) retornan un objeto de tipo `T` (la entidad específica, como `Usuario`, `TipoUsuario`, etc.), o `null` si no se encontró ningún registro.
- **List<T>:** El método `readAll` retorna una lista de objetos de tipo `T`, representando todos los registros de la tabla.

---

## ¿Qué es `statement`?

- `statement` es una variable de tipo `PreparedStatement` de la API de Java SQL (`java.sql.PreparedStatement`).
- Un `PreparedStatement` es un objeto que representa una consulta SQL precompilada con parámetros (`?`). Permite ejecutar consultas de forma segura y eficiente, evitando inyecciones SQL y mejorando el rendimiento cuando se ejecutan múltiples veces.
- Se obtiene llamando a `prepareStatement(sql)` sobre una conexión (`Connection`).

---

## Métodos explicados paso a paso


### 1. `public boolean create(T obj)`
Este método inserta un nuevo registro en la base de datos usando el objeto recibido.

1. **Preparar el statement:**
   - `statement = myConnection.conectar().prepareStatement(queryCreate);`
   - Se obtiene una conexión a la base de datos y se prepara un `PreparedStatement` con la consulta de inserción generada dinámicamente.
2. **Asignar parámetros:**
   - `setStatementParameters(statement, obj, true);`
   - Llama al método implementado en la clase hija concreta para asignar los valores del objeto a los `?` del SQL. El parámetro `true` indica que es un objeto nuevo (insert).
3. **Ejecutar la consulta:**
   - `int result = statement.executeUpdate();`
   - Ejecuta la consulta SQL. Devuelve el número de filas afectadas (debe ser 1 si fue exitoso).
4. **Retornar resultado:**
   - `return result >= 0;`
   - Si la operación afectó al menos una fila, retorna `true`.
5. **Manejo de errores:**
   - Si ocurre una excepción, imprime un mensaje detallado y relanza el error para que pueda ser manejado externamente.


### 2. `public T read(long id)`
Este método busca un registro por su id y lo convierte en un objeto de tipo `T`.

1. **Preparar el statement:**
   - `statement = myConnection.conectar().prepareStatement(queryRead);`
   - Prepara la consulta SQL para buscar por id.
2. **Asignar parámetro:**
   - `statement.setLong(1, id);`
   - Asigna el valor del id al primer parámetro del statement.
3. **Ejecutar la consulta:**
   - `ResultSet rs = statement.executeQuery();`
   - Ejecuta la consulta y obtiene el resultado.
4. **Mapeo del resultado:**
   - Si hay resultados, recorre el ResultSet y llama a `objectMapping(rs)` (implementado en la clase hija concreta) para convertir la fila en un objeto de tipo `T`.
   - Así, el método no solo ejecuta el query, sino que transforma el resultado de la base de datos en una instancia de la entidad correspondiente.
5. **Retornar resultado:**
   - Retorna el objeto encontrado o `null` si no existe.
6. **Manejo de errores:**
   - Si ocurre una excepción, imprime un mensaje detallado y relanza el error.


### 3. `public List<T> readAll()`
Este método obtiene todos los registros de la tabla y los convierte en una lista de objetos de tipo `T`.

1. **Preparar el statement:**
   - `statement = myConnection.conectar().prepareStatement(queryReadAll);`
   - Prepara la consulta SQL para obtener todos los registros.
2. **Ejecutar la consulta:**
   - `ResultSet rs = statement.executeQuery();`
   - Ejecuta la consulta y obtiene el resultado.
3. **Recorrer resultados:**
   - Crea una lista vacía. Por cada fila del ResultSet, llama a `objectMapping(rs)` para convertirla en un objeto de tipo `T` y lo agrega a la lista.
4. **Retornar resultado:**
   - Retorna la lista de objetos.
5. **Manejo de errores:**
   - Si ocurre una excepción, imprime un mensaje detallado y relanza el error.


### 4. `public boolean update(T obj)`
Este método actualiza un registro existente en la base de datos usando el objeto recibido.

1. **Validar id:**
   - Verifica que el id del objeto sea mayor a 0. Si no, lanza una excepción.
2. **Preparar el statement:**
   - `statement = myConnection.conectar().prepareStatement(queryUpdate);`
   - Prepara la consulta SQL de actualización.
3. **Asignar parámetros:**
   - `setStatementParameters(statement, obj, false);`
   - Llama al método implementado en la clase hija concreta para asignar los valores del objeto a los parámetros del SQL. El parámetro `false` indica que es una actualización.
4. **Ejecutar la consulta:**
   - `int result = statement.executeUpdate();`
   - Ejecuta la consulta y obtiene el número de filas afectadas.
5. **Retornar resultado:**
   - Retorna `true` si la operación fue exitosa.
6. **Manejo de errores:**
   - Si ocurre una excepción, imprime un mensaje detallado y relanza el error.


### 5. `public boolean delete(long id)`
Este método elimina un registro de la base de datos según su id.

1. **Preparar el statement:**
   - `statement = myConnection.conectar().prepareStatement(queryDelete);`
   - Prepara la consulta SQL de borrado.
2. **Asignar parámetro:**
   - `statement.setLong(1, id);`
   - Asigna el id al parámetro del statement.
3. **Ejecutar la consulta:**
   - `int result = statement.executeUpdate();`
   - Ejecuta la consulta y obtiene el número de filas afectadas.
4. **Retornar resultado:**
   - Retorna `true` si la operación fue exitosa.
5. **Manejo de errores:**
   - Si ocurre una excepción, imprime un mensaje detallado y relanza el error.

---

## Métodos heredados y métodos de la API Java SQL

- **Métodos abstractos heredados:**
  - `objectMapping(ResultSet rs)`: Lo implementa la clase hija concreta (por ejemplo, `RepositoryUsuario`, `RepositoryTipoUsuario`, etc.) para convertir una fila del `ResultSet` en un objeto de tipo `T`.
  - `setStatementParameters(PreparedStatement statement, T obj, boolean newObj)`: Lo implementa la subclase para asignar los valores del objeto a los parámetros del SQL.
- **Métodos de la API Java SQL usados:**
  - `prepareStatement(String sql)`: De `Connection`, crea un `PreparedStatement`.
  - `setLong(int parameterIndex, long x)`, `setInt`, `setString`, etc.: De `PreparedStatement`, asignan valores a los parámetros (`?`).
  - `executeUpdate()`: Ejecuta una consulta de modificación (INSERT, UPDATE, DELETE) y retorna el número de filas afectadas.
  - `executeQuery()`: Ejecuta una consulta de selección (SELECT) y retorna un `ResultSet`.
  - `next()`: De `ResultSet`, avanza a la siguiente fila.
  - `getInt`, `getString`, etc.: De `ResultSet`, obtienen valores de las columnas.

---

## Resumen de la relación entre clases

- `Repository<T extends Entity>` es una clase abstracta genérica que implementa la lógica CRUD común para cualquier entidad.
- Las subclases (como `RepositoryUsuario`) implementan los métodos abstractos para mapear los datos específicos de cada entidad.
- Se apoya en la API estándar de Java SQL para la conexión, ejecución y manejo de resultados de la base de datos.

---

## Ejemplo de implementación de subclase

```java
public class RepositoryUsuario extends Repository<Usuario> {
    @Override
    protected Usuario objectMapping(ResultSet rs) throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getInt("id"));
        usuario.setNombre(rs.getString("nombre"));
        usuario.setEdad(rs.getInt("edad"));
        return usuario;
    }

    @Override
    protected void setStatementParameters(PreparedStatement statement, Usuario usuario, boolean newObj) throws Exception {
        int index = 1;
        if (!newObj) {
            statement.setInt(index++, usuario.getId());
        }
        statement.setString(index++, usuario.getNombre());
        statement.setInt(index++, usuario.getEdad());
    }
}
```

---

## Conclusión

Esta arquitectura permite tener un acceso a datos genérico, seguro y reutilizable, separando la lógica de acceso a datos de la lógica de negocio y de presentación, siguiendo el patrón MVC y las buenas prácticas de Java.
