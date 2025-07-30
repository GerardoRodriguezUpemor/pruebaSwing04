package org.upemor.pruebaswing04.view.Tools;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;

public abstract class BaseModelDLG extends JDialog {

    private JButton btnAceptar;
    private JButton btnCancelar;

    public BaseModelDLG(JFrame padre, boolean modal) {
        super(padre, modal);
        init();
    }

    private void init(){
        btnAceptar = new JButton("Aceptar");
            btnAceptar.setForeground(Color.decode("#C5E7CD"));
            btnAceptar.setFont(this.getFont().deriveFont(Font.BOLD));
            btnAceptar.setBackground(Color.decode("#007AFF"));
            btnAceptar.addActionListener(event -> {eventoBotonAceptar();});

        btnCancelar = new JButton("Cancelar");
            btnCancelar.setForeground(Color.decode("#C5E7CD"));
            btnCancelar.setFont(this.getFont().deriveFont(Font.BOLD));
            btnCancelar.setBackground(Color.decode("#FF2D55"));
            btnCancelar.addActionListener(event -> {eventoBotonCancelar();});

        setLayout(new GridBagLayout());

        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    protected void eventoBotonCancelar() {
        this.dispose();
    }

    protected abstract void eventoBotonAceptar();{
    }

    private GridBagConstraints constraints;
    protected GridBagConstraints crearRestricciones(
        int posicionX,int posicionY,int tamanioX,int tamanioY,boolean responsivo){
        if (constraints == null) constraints = new GridBagConstraints();
            constraints.gridx = posicionX; // Posicion en columna (X)
            constraints.gridy = posicionY; // Posicion en fila (Y)
            constraints.gridwidth= tamanioX; // Tamaño en columnas (X)
            constraints.gridheight= tamanioY; // tamaño en filas (Y)
            constraints.ipadx = 10; // Padding X
            constraints.ipady = 10; // Padding Y
            constraints.fill = GridBagConstraints.HORIZONTAL;
            constraints.weightx = responsivo?1.0:0.0;
        return constraints;    
    }

}
