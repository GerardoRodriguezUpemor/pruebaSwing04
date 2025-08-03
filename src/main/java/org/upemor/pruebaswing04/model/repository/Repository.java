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
            // This catch block handles any exception that occurs during the construction of the repository.
            // It prints a detailed error message including the exception, the method, and the class name.
            // This helps you quickly identify where and why an error happened, making debugging easier.
            // After logging, it rethrows the exception so it can be handled further up if needed.
            System.out.println(
                "Error: "+ex.getMessage()
                +" in method: constructor()"
                +" in class: "+this.getClass().getName()
            );
            throw ex;
        }
    }
    
    protected void initQueries(String tabla,long parameters){
        // Build the INSERT query dynamically:
        // - The first value is NULL (for the auto-increment id)
        // - The for loop adds a '?' for each additional column (parameter)
        //   For example, if there are 5 columns besides id, it will add 5 '?'
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
        
        //System.out.println(queryCreate);
        //System.out.println(queryRead);
        //System.out.println(queryUpdate);
        //System.out.println(queryDelete);
        
    }

    /**
     * Método abstracto que debe implementar la clase hija concreta.
     * Convierte una fila del ResultSet en un objeto de tipo T (la entidad específica).
     * Ejemplo: mapear columnas de la base de datos a atributos de Usuario.
     */
    protected abstract T objectMapping(ResultSet rs) throws Exception;

    /**
     * Método abstracto que debe implementar la clase hija concreta.
     * Asigna los valores del objeto a los parámetros del PreparedStatement.
     * @param statement PreparedStatement donde se asignan los valores
     * @param obj Objeto de tipo T con los datos a guardar o actualizar
     * @param newObj true si es inserción, false si es actualización
     */
    protected abstract void setStatementParameters(PreparedStatement statement,T obj,boolean newObj) throws Exception;
    

    /**
     * Inserta un nuevo registro en la base de datos usando el objeto recibido.
     * 1. Prepara el PreparedStatement con la consulta de inserción.
     * 2. Asigna los valores del objeto a los parámetros del SQL.
     * 3. Ejecuta la consulta y retorna true si fue exitosa.
     * 4. Maneja excepciones imprimiendo un mensaje y relanzando el error.
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
     * 1. Prepara el PreparedStatement con la consulta de búsqueda.
     * 2. Asigna el id al parámetro del statement.
     * 3. Ejecuta la consulta y obtiene el ResultSet.
     * 4. Si hay resultados, usa objectMapping para convertir la fila en un objeto T.
     * 5. Retorna el objeto encontrado o null si no existe.
     * 6. Maneja excepciones imprimiendo un mensaje y relanzando el error.
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
     * 1. Prepara el PreparedStatement con la consulta para todos los registros.
     * 2. Ejecuta la consulta y recorre el ResultSet.
     * 3. Por cada fila, usa objectMapping para convertirla en un objeto T y lo agrega a la lista.
     * 4. Retorna la lista de objetos.
     * 5. Maneja excepciones imprimiendo un mensaje y relanzando el error.
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
     * 1. Verifica que el id del objeto sea válido (>0).
     * 2. Prepara el PreparedStatement con la consulta de actualización.
     * 3. Asigna los valores del objeto a los parámetros del SQL.
     * 4. Ejecuta la consulta y retorna true si fue exitosa.
     * 5. Maneja excepciones imprimiendo un mensaje y relanzando el error.
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
     * 1. Prepara el PreparedStatement con la consulta de borrado.
     * 2. Asigna el id al parámetro del statement.
     * 3. Ejecuta la consulta y retorna true si fue exitosa.
     * 4. Maneja excepciones imprimiendo un mensaje y relanzando el error.
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
