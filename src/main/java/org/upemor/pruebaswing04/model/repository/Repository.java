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
        
        //System.out.println(queryCreate);
        //System.out.println(queryRead);
        //System.out.println(queryUpdate);
        //System.out.println(queryDelete);
        
    }
    protected abstract T objectMapping(ResultSet rs) throws Exception;
    protected abstract void setStatementParameters(PreparedStatement statement,T obj,boolean newObj) throws Exception;
    
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
