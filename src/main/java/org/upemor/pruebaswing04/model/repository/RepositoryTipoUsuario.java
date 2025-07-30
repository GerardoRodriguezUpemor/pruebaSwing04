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
