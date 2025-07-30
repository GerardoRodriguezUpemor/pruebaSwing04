package org.upemor.pruebaswing04.view;


import org.upemor.pruebaswing04.view.Administracion.tipoUsuario.TipoUsuarioDLG;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

/** @author cerimice **/

public class PrincipalDlg extends JFrame{
    
    public PrincipalDlg(){
        init();
    }
    
    private void init(){
        
        JMenuBar menuPricipal = new JMenuBar();
            JMenu menuAdministracion = new JMenu("Administración");
                JMenu menuUsuario = new JMenu("Usuarios");
                    JMenuItem usuarios = new JMenuItem("Gestionar usuarios");
                    JMenuItem tipoUsuarios = new JMenuItem("Gestionar tipos de usuarios");
                        tipoUsuarios.addActionListener(cmd -> cambiarContenido(new TipoUsuarioDLG()));
                    menuUsuario.add(tipoUsuarios);
                    menuUsuario.add(usuarios);
                menuAdministracion.add(menuUsuario);
            menuPricipal.add(menuAdministracion);
        this.setJMenuBar(menuPricipal);
        
        this.setSize(800,600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    
    private void cambiarContenido(JPanel contenido){
        this.getContentPane().removeAll();
        this.getContentPane().add(contenido);
        this.setVisible(true);
    }
    
}
