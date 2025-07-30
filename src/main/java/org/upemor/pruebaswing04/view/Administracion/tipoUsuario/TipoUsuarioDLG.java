package org.upemor.pruebaswing04.view.Administracion.tipoUsuario;

import java.util.List;

import javax.swing.JOptionPane;

import org.upemor.pruebaswing04.controller.ControllerTipoUsuario;
import org.upemor.pruebaswing04.model.entity.TipoUsuario;
import org.upemor.pruebaswing04.view.Tools.BaseDLG;

public class TipoUsuarioDLG extends BaseDLG {

    private ControllerTipoUsuario controllerTipoUsuario;

    public TipoUsuarioDLG(){
        super();// Call the constructor of BaseDLG to initialize components
        init();
    }

    private void init() {

        try{
            controllerTipoUsuario = new ControllerTipoUsuario();
        }catch (Exception ex){
            System.out.println("Error: " + ex.getMessage());
        }

        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Nombre");
        // Initialize components specific to TipoUsuarioDLG
        this.setVisible(true);
    }

    @Override
    protected void eventoBotonBuscar() {
        try {
            while (modeloTabla.getRowCount() > 0) {
                modeloTabla.removeRow(0);
            }
            String parametro = tfBuscar.getText() + "%";
            List<TipoUsuario> lista = controllerTipoUsuario.getRepository().obtenerPorNombre(parametro);
            for(TipoUsuario obj : lista) {
                Object datos[] = {
                    obj.getId(),
                    obj.getNombre()
                };
                modeloTabla.addRow(datos);
            }
        }catch (Exception ex) {
            JOptionPane.showMessageDialog(this,ex.getMessage(), "Tipo de usuario", JOptionPane.WARNING_MESSAGE);
        // Implementar evento para botón Buscar
        }
    }

    @Override
    protected void eventoBotonEliminar() {
        try {
            long ID = seleccionarID();
            if (ID <= 0) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un tipo de usuario", "Tipo de usuario", JOptionPane.WARNING_MESSAGE);
                return;
            }
            controllerTipoUsuario.getRepository().delete(ID);
            eventoBotonBuscar(); // Refresh the table after deletion
        }catch (Exception e) {
            JOptionPane.showMessageDialog(this,e.getMessage(), "Tipo de usuario", JOptionPane.WARNING_MESSAGE);
        // Implementar evento para botón Eliminar
        }
    }

    @Override
    protected void eventoBotonEditar() {
        try {
        }catch (Exception e) {
            JOptionPane.showMessageDialog(this,e.getMessage(), "Tipo de usuario", JOptionPane.WARNING_MESSAGE);
        }
        // Implementar evento para botón Editar
    }

    @Override
    protected void eventoBotonAgregar() {
        try {
        }catch (Exception e) {
            JOptionPane.showMessageDialog(this,e.getMessage(), "Tipo de usuario", JOptionPane.WARNING_MESSAGE);
        }
        // Implementar evento para botón Agregar
    }
}