# Explicación detallada de la clase `MiConexion`

A continuación se presenta el código completo de la clase `MiConexion.java` y una explicación de su funcionamiento, propósito, variables, métodos y uso típico, siguiendo el estilo detallado de documentación.

---

## Código completo de `MiConexion.java`

```java
package org.upemor.pruebaswing04.model.repository;

import java.sql.Connection;
import java.sql.DriverManager;

/** @author cerimice **/

public class MiConexion{
    
    public static int SQLITE = 1;
    public static int MARIADB = 2;
    
    private String url;
    private String driverClass;
    private String servidor;
    private long puerto;
    private String baseDeDatos;
    private String usuario;
    private String contrasenia;
    
    private void cargarDatosSQLite(){
        driverClass = "org.sqlite.JDBC";
        servidor = "";
        puerto = 0;
        baseDeDatos = "prueba_swing.db";
        usuario = "";
        contrasenia = "";
        url = "jdbc:sqlite:"+baseDeDatos;
    }
    
    private void cargarDatosMariaDB(){
        driverClass = "org.mariadb.jdbc.Driver";
        servidor = "localhost";
        puerto = 3306;
        baseDeDatos = "prueba_swing";
        usuario = "prueba";
        contrasenia = "prueba123";
        url = "jdbc:mariadb://"+servidor+":"+puerto+"/"+baseDeDatos;
    }
    
    private static MiConexion instancia;
    public static MiConexion getInstancia() throws Exception{
        if(instancia == null){
            instancia = new MiConexion(SQLITE);
        }
        return instancia;
    }
    
    private MiConexion(int manejador) throws Exception{
        switch(manejador){
            case 1: cargarDatosSQLite(); break;
            case 2: cargarDatosMariaDB(); break;
            default:
                throw new Exception("El manejador solicitado no esta configurado");
        }
    }
    
    private Connection conexion;
    public Connection conectar() throws Exception{
        try{
            if(conexion == null){
                Class.forName(driverClass);
                conexion = DriverManager.getConnection(url,usuario,contrasenia);
            }
            return conexion;
        }catch(Exception ex){
            System.out.println("Error: "+this.getClass().getName()+" => "+ ex.getMessage());
            throw ex;
        }
    }
    
    public boolean desconectar() throws Exception{
        try{
            if(conexion != null)
                conexion.close();
            return true;
        }catch(Exception ex){
            System.out.println("Error: "+this.getClass().getName()+" => "+ ex.getMessage());
            throw ex;
        }
    }
}
```

---

## Explicación de la clase

### 1. Propósito general
La clase `MiConexion` centraliza la gestión de la conexión a la base de datos en la aplicación. Permite conectarse tanto a SQLite como a MariaDB, facilitando el cambio de motor de base de datos y el manejo de la conexión de forma singleton.

### 2. Variables
- `public static int SQLITE`, `MARIADB`: Constantes para identificar el tipo de manejador de base de datos.
- Variables privadas para los parámetros de conexión: `url`, `driverClass`, `servidor`, `puerto`, `baseDeDatos`, `usuario`, `contrasenia`.
- `private static MiConexion instancia;`: Implementa el patrón Singleton para asegurar una única instancia de conexión.
- `private Connection conexion;`: Objeto de conexión JDBC.

### 3. Métodos
- `private void cargarDatosSQLite()`: Configura los parámetros para conexión SQLite.
- `private void cargarDatosMariaDB()`: Configura los parámetros para conexión MariaDB.
- `public static MiConexion getInstancia()`: Devuelve la instancia única de la clase (Singleton).
- `private MiConexion(int manejador)`: Constructor privado que selecciona el manejador de base de datos.
- `public Connection conectar()`: Establece y retorna la conexión JDBC, inicializándola si es necesario.
- `public boolean desconectar()`: Cierra la conexión si está abierta.

### 4. Uso típico
Se utiliza para obtener una conexión a la base de datos de forma centralizada y reutilizable en toda la aplicación.

```java
MiConexion conexion = MiConexion.getInstancia();
Connection conn = conexion.conectar();
// ...operaciones con la base de datos...
conexion.desconectar();
```

### 5. Resumen
La clase `MiConexion` facilita la gestión de conexiones a diferentes motores de base de datos, promueve el uso eficiente de recursos y centraliza la configuración, siguiendo buenas prácticas de diseño como el patrón Singleton.
