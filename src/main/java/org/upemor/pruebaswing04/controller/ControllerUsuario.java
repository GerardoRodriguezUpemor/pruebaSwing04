package org.upemor.pruebaswing04.controller;

import org.upemor.pruebaswing04.model.entity.Usuario;
import org.upemor.pruebaswing04.model.repository.RepositoryUsuario;

/** @author cerimice **/

public class ControllerUsuario extends Controller<RepositoryUsuario,Usuario>{
    
    public ControllerUsuario() throws Exception{
        repository = new RepositoryUsuario();
    }

    @Override
    protected boolean validate(Usuario obj) throws Exception{
        if(obj.getNombre().isEmpty())
            throw new Exception("El nombre no ha sido proporcionado");
        if(obj.getApellidos().isEmpty())
            throw new Exception("El apellido no ha sido proporcionado");
        if(obj.getEmail().isEmpty())
            throw new Exception("El email no ha sido proporcionado");
        if(obj.getPassword().isEmpty())
            throw new Exception("El password no ha sido proporcionado");
        if(obj.getTipoUsuario()<= 0)
            throw new Exception("El tipo de usuario no ha sido proporcionado");
        return true;
    }
    
}
