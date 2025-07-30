package org.upemor.pruebaswing04.model.entity;

import lombok.Getter;
import lombok.Setter;

/** @author cerimice **/

@Setter
@Getter
public class TipoUsuario extends Entity{
    
    private String nombre;
    
    @Override
    public String toString(){
        return nombre;
    }
    
}
