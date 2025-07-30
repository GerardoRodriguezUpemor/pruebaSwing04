package org.upemor.pruebaswing04.model.entity;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

/** @author cerimice **/

@Setter
@Getter
public class Usuario extends Entity{
    
    private String nombre;
    private String apellidos;
    private String email;
    private String password;
    private LocalDate fechaDeNacimiento;
    private long tipoUsuario;
    
    @Override
    public String toString(){
        return nombre;
    }
    
}
