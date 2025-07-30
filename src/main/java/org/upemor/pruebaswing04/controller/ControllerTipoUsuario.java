package org.upemor.pruebaswing04.controller;

import org.upemor.pruebaswing04.model.entity.TipoUsuario;
import org.upemor.pruebaswing04.model.repository.RepositoryTipoUsuario;

/** @author cerimice **/

public class ControllerTipoUsuario extends Controller<RepositoryTipoUsuario,TipoUsuario>{
    
    public ControllerTipoUsuario() throws Exception{
        repository = new RepositoryTipoUsuario();
    }

    @Override
    protected boolean validate(TipoUsuario obj) throws Exception{
        if(obj.getNombre().isEmpty()) throw new Exception("El nombre no ha sido proporcionado");
        return true;
    }
    
}
