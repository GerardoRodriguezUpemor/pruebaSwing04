# Explanation of Repository.java

## Overview

The `Repository<T extends Entity>` class is an abstract, generic base class for all repositories in the project. It provides a template for data access operations (CRUD) for any entity type that extends `Entity`.

## Code Structure
- **Generic Type `T`:** Represents the entity type the repository will manage. Ensures type safety and flexibility.
- **Database Connection:** Uses `MiConexion` for database access and `PreparedStatement` for executing SQL queries.
- **Query Strings:** Defines SQL queries for create, read, update, and delete operations, which are initialized based on the table and number of parameters.
- **Abstract Methods:**
  - `objectMapping(ResultSet rs)`: Maps a database row to an entity object.
  - `setStatementParameters(PreparedStatement statement, T obj, boolean newObj)`: Sets the parameters for SQL statements based on the entity's fields.

    > **How do you implement objectMapping and use ResultSet?**
    >
    > When you implement the `objectMapping(ResultSet rs)` method in a subclass of `Repository`, you create an instance of your entity (for example, `Usuario` which extends `Entity`) and use methods like `rs.getInt("columna")`, `rs.getString("columna")`, etc., to fill its variables (fields) with the values from the database.
    >
    > For example, if you have a `Usuario` table with columns `id`, `nombre`, and `edad`, your implementation might look like this:
    >
    > ```java
    > @Override
    > protected Usuario objectMapping(ResultSet rs) throws Exception {
    >     Usuario usuario = new Usuario();
    >     usuario.setId(rs.getInt("id"));
    >     usuario.setNombre(rs.getString("nombre"));
    >     usuario.setEdad(rs.getInt("edad"));
    >     return usuario;
    > }
    > ```
    >
    > This way, you extract the data from the current row and create a Java object with those values. The `ResultSet` is not created by this method; it is passed in, already containing the data from the database query.
  - `setStatementParameters(PreparedStatement statement, T obj, boolean newObj)`: Sets the parameters for SQL statements based on the entity's fields.

    > **Why do we need both PreparedStatement and T obj in setStatementParameters?**
    >
    > You need both objects because:
    > - `PreparedStatement statement` is the SQL statement where you will assign the values to the `?` placeholders using methods like `statement.setInt(1, value)`, `statement.setString(2, value)`, etc.
    > - `T obj` is the entity (for example, a `Usuario`, `TipoUsuario`, etc.) that contains the data you want to save or update in the database. You use its getters to get the values and assign them to the `PreparedStatement`.
    > - `boolean newObj` tells you if the object is new (insert) or existing (update), so you know whether to include the id or not, depending on the operation.
    >
    > In summary: `T` is the entity you receive and from which you extract the data to put into the `PreparedStatement`, which is the object that actually executes the query in the database. Both are necessary to correctly map your Java object's data to the SQL statement.
    >
    > For example, if you have a `Usuario` object and want to insert or update it in the database, your implementation of `setStatementParameters` might look like this:
    >
    > ```java
    > @Override
    > protected void setStatementParameters(PreparedStatement statement, Usuario usuario, boolean newObj) throws Exception {
    >     int index = 1;
    >     if (!newObj) {
    >         statement.setInt(index++, usuario.getId()); // For update, include the id
    >     }
    >     statement.setString(index++, usuario.getNombre());
    >     statement.setInt(index++, usuario.getEdad());
    >     // Add more fields as needed
    > }
    > ```
    >
    > In this example, you use the getters of the entity (`usuario`) to get the values and assign them to the corresponding placeholders in the `PreparedStatement`. The `index` variable helps you keep track of the parameter positions. The `newObj` flag lets you decide whether to include the id (for update) or not (for insert).
    
    > **What does 'int result = statement.executeUpdate();' do?**
    >
    > This line declares an integer variable (`result`) and assigns it the value returned by executing the prepared SQL statement (`statement`).
    > - `statement` is a `PreparedStatement` object that contains the SQL query with all parameters set.
    > - The method `executeUpdate()` runs the SQL command (such as INSERT, UPDATE, or DELETE) in the database.
    > - The returned value is the number of rows affected by the operation. For example, if you insert one row, `result` will be 1.
    > - This allows you to check if the operation was successful (e.g., `result >= 0`).
    >
    > In summary: you are creating a variable that receives the result of executing a prepared SQL statement, which tells you how many records were modified in the database.
  - **CRUD Methods:**
  - `create(T obj)`: Inserts a new entity into the database.
  - `read(long id)`: Retrieves an entity by its ID.
  - `readAll()`: Retrieves all entities from the table.
  - `update(T obj)`: Updates an existing entity in the database.
  - `delete(long id)`: Deletes an entity by its ID.

  > **Why are the query variables important and declared this way?**
  >
  > These query variables (`queryCreate`, `queryRead`, `queryReadAll`, `queryUpdate`, `queryDelete`) are essential because they define the SQL statements used for all CRUD operations in a generic and reusable way:
  >
  > - **Flexibility:** By building the queries dynamically with string concatenation and loops, the repository can adapt to any table structure. You only need to specify the table name and the number of columns, and the correct SQL is generated automatically.
  > - **Reusability:** This approach allows the same repository code to be used for different entities/tables, reducing code duplication and making maintenance easier.
  > - **Parameterization:** Using `?` placeholders in the queries makes them safe for use with `PreparedStatement`, which helps prevent SQL injection and allows you to set values dynamically at runtime.
  > - **Auto-increment id:** For `queryCreate`, `NULL` is used for the id column, assuming it is auto-incremented by the database. The loop adds the right number of `?` for the other columns.
  > - **Consistency:** All CRUD operations are handled in a consistent way, making the codebase easier to understand and extend.
  >
  > In summary, declaring and building these queries this way makes your repository generic, safe, and easy to maintain for any entity in your application.

## Repository vs Controller: What is the difference?

- **Repository:**
  - The `Repository<T extends Entity>` class is responsible for all data access logic. It handles the connection to the database, builds and executes SQL queries, and maps database rows to Java entity objects. Its main job is to provide a generic, reusable, and type-safe way to interact with the database for any entity.
  - The repository does NOT contain business logic or application flow; it only knows how to store, retrieve, update, and delete data.
  - Example: `RepositoryUsuario` knows how to save and load `Usuario` objects from the database, but it does not decide when or why to do it.

- **Controller:**
  - The Controller is responsible for the business logic and application flow. It receives requests (for example, from the user interface), validates data, and decides what actions to take.
  - The controller uses the repository to perform data operations, but it also handles validation, error messages, and coordinates between the view (UI) and the model (entities and repositories).
  - Example: `ControllerUsuario` might check if a username is unique before calling the repository to save a new user, or handle what happens if saving fails.

**In summary:**
- The repository is focused on data access and persistence.
- The controller is focused on business logic, validation, and coordinating the application's actions.
- Both are essential in the MVC (Model-View-Controller) pattern, but each has a clear and separate responsibility.

## Key Points
  - By using `<T extends Entity>`, the repository can be reused for any entity type, ensuring all repositories have a consistent interface and behavior.
  - Subclasses must implement how to map database rows to entity objects and how to set SQL parameters for their specific entity.
  - The constructor and `initQueries` method dynamically build SQL queries based on the table name and number of fields, making the repository adaptable to different entities.
  - Each method includes error handling that prints detailed messages and rethrows exceptions for easier debugging.

## Dynamic SQL Query Generation in the Constructor and initQueries

The constructor of the `Repository` class receives the table name (`tabla`) and the number of parameters (columns) for the entity. It then calls the `initQueries` method, which is responsible for dynamically building the SQL queries for all CRUD operations (Create, Read, Update, Delete).

### How it works:
- **Dynamic Construction:** The method uses string concatenation and loops to build the SQL statements, so they adapt to any table structure as long as you provide the correct number of parameters.
    - **Concatenation:** This means joining together pieces of text (strings) to form a complete SQL query. For example, the code adds the table name and the correct number of placeholders (`?`) for parameters to build the final SQL statement.
        - **What are placeholders (`?`) in SQL?** Placeholders are special symbols used in SQL queries (usually represented by a question mark `?`) that act as variables for values that will be provided later. When using `PreparedStatement` in Java, these placeholders are replaced with actual values at runtime. This approach helps prevent SQL injection attacks and makes the code safer and more flexible, since you don't have to manually concatenate user input into your SQL strings.
            - **What is SQL injection?** SQL injection is a type of security vulnerability that occurs when an attacker is able to insert or "inject" malicious SQL code into a query. This can happen if user input is directly concatenated into SQL statements without proper validation or parameterization. Attackers can use this to access, modify, or delete data in the database, or even gain unauthorized access to the system. Using placeholders (`?`) with `PreparedStatement` helps prevent this by separating the SQL code from the data, making it much harder for attackers to manipulate the query.
    - **Loops:** A loop (such as a `for` loop) is used to repeat an action multiple times. In this case, the loop adds the right number of `?` placeholders to the SQL query, depending on how many columns the table has. This way, the code automatically adjusts to tables with different numbers of columns, making the repository flexible and reusable.
- **Generic Usage:** This approach allows the repository to be generic and reusable for different entities, without hardcoding table or column names.
- **Example:**
    - For a table with 3 columns (besides the id), `initQueries` will generate:
        - `queryCreate`: `INSERT INTO tabla VALUES(NULL,?,?,?)`
        - `queryRead`: `SELECT * FROM tabla WHERE id IN (?)`
        - `queryReadAll`: `SELECT * FROM tabla`
        - `queryUpdate`: `REPLACE INTO tabla VALUES(?,?,?,?)`
        - `queryDelete`: `DELETE FROM tabla WHERE id IN (?)`
- **Flexibility:** This makes it easy to extend the repository for new entities by simply specifying the table name and number of columns.

> **This approach allows you to reuse the same logic for any table, just by changing the table name and the number of columns.**
> The dynamic construction of queries means you don't have to write separate SQL for each entity; the repository adapts automatically to the structure you provide.

### Adaptación según el tipo de repositorio y entidad

El método `initQueries` y la lógica de construcción dinámica de queries en la clase `Repository` son genéricos y reutilizables. Sin embargo, el comportamiento específico de cada repositorio depende del tipo de entidad y la tabla correspondiente:

- **initQueries:** Recibe el nombre de la tabla y el número de columnas (parámetros) para construir automáticamente las consultas SQL básicas (CRUD) para cualquier entidad.
- **Subclases:** Cada subclase de `Repository` (por ejemplo, `RepositoryUsuario`, `RepositoryTipoUsuario`) implementa cómo mapear los datos (`objectMapping`) y cómo establecer los parámetros en el `PreparedStatement` (`setStatementParameters`) según la entidad específica.
- **Reutilización:** Así, puedes reutilizar la lógica base para cualquier entidad, solo cambiando el nombre de la tabla y el número de columnas en el constructor del repositorio concreto. Las operaciones CRUD se adaptan automáticamente al tipo de entidad y tabla que maneja cada repositorio.

Por ejemplo, para un repositorio de usuarios (`RepositoryUsuario`), solo necesitas indicar el nombre de la tabla y el número de columnas, e implementar el mapeo y los parámetros para la entidad `Usuario`. El resto de la lógica CRUD ya está lista para usarse.

---
### Code Example:
```java
protected void initQueries(String tabla, long parameters) {
    queryCreate = "INSERT INTO " + tabla + " VALUES(NULL";
    for (int x = 1; x <= (parameters - 1); x++) queryCreate += ",?";
    queryCreate += ")";
    queryRead = "SELECT * FROM " + tabla + " WHERE id IN (?)";
    queryReadAll = "SELECT * FROM " + tabla;
    queryUpdate = "REPLACE INTO " + tabla + " VALUES(";
    for (int x = 1; x <= parameters; x++) queryUpdate += ",?";
    queryUpdate += ")";
    queryUpdate = queryUpdate.replace("(,?", "(?");
    queryDelete = "DELETE FROM " + tabla + " WHERE id IN (?)";
    //System.out.println(queryCreate);
    //System.out.println(queryRead);
    //System.out.println(queryUpdate);
    //System.out.println(queryDelete);
}
```

> **What are the commented System.out.println lines for?**
> These lines are used for debugging. If you remove the `//`, the program will print out the actual SQL queries that were generated and stored in each variable. This helps you verify that the dynamic query construction is working as expected and lets you see the exact SQL statements before they are executed. It's a useful way to check for errors or understand how your code is building the queries.

This design pattern is common in generic repository implementations, providing both flexibility and code reuse.
## Example Usage
public class RepositoryUsuario extends Repository<Usuario> {
    // Implement objectMapping and setStatementParameters for Usuario
}

Now, you can use all CRUD methods for `Usuario` objects with type safety and minimal code duplication.

## Summary
- The `Repository` class is the foundation for all data access in the project.
- It enforces a consistent, reusable, and type-safe approach to working with different entities and their database tables.
- Subclasses only need to implement entity-specific mapping and parameter logic.
