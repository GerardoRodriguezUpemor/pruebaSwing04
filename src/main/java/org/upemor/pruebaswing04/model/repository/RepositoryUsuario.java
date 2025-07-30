package org.upemor.pruebaswing04.model.repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.upemor.pruebaswing04.model.entity.Usuario;

/** @author cerimice **/

public class RepositoryUsuario extends Repository<Usuario>{
    
    private LocalDate dateToLocaDate(Date fecha){
        LocalDate nuevaFecha = LocalDate.of(
            fecha.getYear()+1900
            ,fecha.getMonth()+1
            ,fecha.getDate()
        );
        return nuevaFecha;
    }
    
    public RepositoryUsuario() throws Exception{
        super("usuario",7);
    }

    @Override
    protected Usuario objectMapping(ResultSet rs) throws Exception{
        Usuario obj = new Usuario();
            obj.setId(rs.getLong("id"));
            obj.setNombre(rs.getString("nombre"));
            obj.setApellidos(rs.getString("apellidos"));
            obj.setEmail(rs.getString("email"));
            obj.setPassword(rs.getString("password"));
            obj.setFechaDeNacimiento(dateToLocaDate(rs.getDate("fecha_de_nacimiento")));
            obj.setTipoUsuario(rs.getLong("tipo_usuario"));
        return obj;
    }

    @Override
    protected void setStatementParameters(PreparedStatement statement, Usuario obj, boolean newObj) throws Exception{
        int i=1;
        if(!newObj) statement.setLong(i++,obj.getId());
        statement.setString(i++,obj.getNombre());
        statement.setString(i++,obj.getApellidos());
        statement.setString(i++,obj.getEmail());
        statement.setString(i++,obj.getPassword());
        statement.setString(i++,obj.getFechaDeNacimiento().format(DateTimeFormatter.ISO_DATE));
        statement.setLong(i++,obj.getTipoUsuario());
    }
    
}
