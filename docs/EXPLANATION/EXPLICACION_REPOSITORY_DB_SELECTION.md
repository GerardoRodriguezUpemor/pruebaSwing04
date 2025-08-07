# Repository and Database Connection Selection

## How Repository Selects the Database Connection

The `Repository` class uses the `MiConexion` class to obtain a database connection. By default, it calls:

```java
myConnection = MiConexion.getInstancia();
```

This means:
- The connection type (SQLite or MariaDB) depends on how the method `getInstancia()` is implemented in `MiConexion`.
- If `getInstancia()` returns a MariaDB connection, all repositories will use MariaDB. If it returns SQLite, all will use SQLite.

## How to Change the Database Engine
- If you want to use a different engine (for example, switch from SQLite to MariaDB), you must change the implementation of `getInstancia()` in `MiConexion`.
- Alternatively, you can modify the `Repository` constructor to accept a parameter and call `MiConexion.getInstancia(manejador)` to select the engine dynamically.

## Example for Dynamic Selection
You can make your repository flexible by doing:

```java
public Repository(String tabla, long parameters, int manejador) throws Exception {
    myConnection = MiConexion.getInstancia(manejador);
    initQueries(tabla, parameters);
}
```

Then, when creating a repository, you can specify which engine to use:

```java
Repository<Usuario> repo = new UsuarioRepository("usuario", 7, MiConexion.MARIADB);
```

## Summary
- The database engine used by your repositories is determined by how you obtain the connection from `MiConexion`.
- For maximum flexibility, allow the engine to be selected via a parameter.
- If you always want to use one engine, set it in `MiConexion.getInstancia()`.

---
**Tip:**
If you change the default engine in `MiConexion`, all repositories using the default constructor will switch to that engine automatically.
