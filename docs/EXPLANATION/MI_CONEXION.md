-## SQLite Connection

> **Note:**
> With SQLite, the entire database is managed as a single file (e.g., `prueba_swing.db`). You can copy or move this file to use the database on another system that supports SQLite. However, you **cannot** use this file directly in MariaDB or other database engines, because the file format is different.

> If you want to use the same data in MariaDB, you must export the structure and data from SQLite (for example, to a `.sql` file) and then import that `.sql` file into MariaDB. This process is called migration.

> **Where should the SQLite file be?**
> You can place the SQLite database file anywhere on your computer. If you only specify the file name (e.g., `prueba_swing.db`), it will be created or searched for in the same folder where your program runs. If you want to use a file located elsewhere (for example, on your Desktop), you must provide the full path:
> 
> ```java
> baseDeDatos = "C:/Users/your_user/Desktop/prueba_swing.db";
> url = "jdbc:sqlite:" + baseDeDatos;
> ```
> The program will access the file at the path you specify, as long as it has read/write permissions.
- **No Server:** There is no separate server process; the database runs in the same process as your application.
- **No Authentication:** No username or password is required by default.
- **Example:**
  ```java
  url = "jdbc:sqlite:" + baseDeDatos;
  // Only the file name is needed
  ```

> **Summary:**
> - SQLite: 1 file = 1 local database. Easy to copy and use with any SQLite-compatible tool.
> - MariaDB: Does not use SQLite files. Needs its own database created and data imported via SQL scripts.
- **Nature:** SQLite is an embedded database engine. The entire database is stored in a single local file.
- **Connection Simplicity:** To connect, you only need the file path (e.g., `prueba_swing.db`).

-## MariaDB Connection

> **Note:**
> With MariaDB (and other client-server databases), the database is not a single file you can copy. Instead, it is managed by a database server process, which stores the data in its own internal format (often spread across multiple files and folders, not directly portable).

> To move or share a MariaDB database, you typically export the structure and data to a `.sql` file (using tools like `mysqldump`), and then import that file into another MariaDB server. You cannot simply copy a file like with SQLite.

> **Summary:**
> - MariaDB: Data is managed by a server, not a single file. To migrate or share, export/import using SQL scripts.
> - SQLite: Data is a single file, easy to copy and move.
 **Nature:** MariaDB is a client-server database system. The database runs as a separate service, possibly on another machine.

 > **Important:**
 > To connect to MariaDB, the database server must be running (either on your computer or on a network server). You must also provide all the required connection parameters: server (host), port, database name, username, and password. If the server is not active or the parameters are incorrect, the connection will fail.

 **Connection Complexity:** You must specify:
  - **Server (host):** Where the MariaDB service is running (e.g., `localhost` for your own computer, or an IP/domain for a remote server). You set this value in your code (e.g., `servidor = "localhost"`).
  - **Port:** The network port MariaDB listens on (default: 3306). This is usually set to 3306 unless your server uses a custom port. You set this value in your code (e.g., `puerto = 3306`).
  - **Database Name:** The name of the database you want to use on the server. You must create this database in MariaDB first (e.g., with `CREATE DATABASE prueba_swing;`). You set this value in your code (e.g., `baseDeDatos = "prueba_swing"`).
  - **Username and Password:** The credentials of a MariaDB user with access to the database. These are created and managed in MariaDB (e.g., `CREATE USER 'prueba'@'localhost' IDENTIFIED BY 'prueba123';`). You set these values in your code (`usuario = "prueba"`, `contrasenia = "prueba123"`).
  - **Driver Class:** The JDBC driver for MariaDB. This is a Java library (JAR) you must add to your project dependencies (e.g., `org.mariadb.jdbc.Driver`). You set this value in your code (`driverClass = "org.mariadb.jdbc.Driver"`).
  
- **Example:**
  ```java
  url = "jdbc:mariadb://" + servidor + ":" + puerto + "/" + baseDeDatos;
  // Requires host, port, database, user, password
  ```

## Summary Table
| Parameter      | SQLite         | MariaDB         |
|----------------|---------------|-----------------|
| File/Database  | Yes           | Yes             |
| Server/Host    | No            | Yes             |
| Port           | No            | Yes             |
| Username       | No            | Yes             |
| Password       | No            | Yes             |
| Driver Class   | Yes           | Yes             |

## Why the Difference?
- **SQLite** is designed for simplicity and local use. It does not need network configuration or authentication.
- **MariaDB** is designed for multi-user, networked environments, so it needs more information to establish a secure, remote connection.

## Conclusion
The extra parameters for MariaDB are necessary because it is a networked, multi-user database system, while SQLite is a lightweight, file-based solution. This is why the `cargarDatosMariaDB()` method in `MiConexion.java` requires more connection details than `cargarDatosSQLite()`.
