# Explicación detallada de la clase `PrincipalDlg`

A continuación se presenta el código completo de la clase `PrincipalDlg.java` y una explicación de su funcionamiento, propósito, variables, métodos y uso típico, siguiendo el estilo detallado de documentación.

---

## Código completo de `PrincipalDlg.java`

```java
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
```

---

## Explicación de la clase

### 1. Propósito general
La clase `PrincipalDlg` representa la ventana principal de la aplicación Swing. Centraliza la creación de la interfaz gráfica principal, el menú de administración y la gestión dinámica del contenido mostrado.

### 2. Variables
- No define variables de instancia propias, ya que todos los componentes se crean y configuran dentro del método `init()`.

### 3. Métodos principales
- `public PrincipalDlg()`: Constructor que inicializa la ventana principal llamando a `init()`.
- `private void init()`: Configura la interfaz gráfica, crea la barra de menús, los menús y los ítems, y define el comportamiento de la ventana.
- `private void cambiarContenido(JPanel contenido)`: Permite cambiar dinámicamente el contenido del panel principal de la ventana.

### 4. Uso típico
Se utiliza como punto de entrada visual de la aplicación, permitiendo al usuario navegar entre las diferentes funcionalidades mediante menús.

```java
PrincipalDlg ventana = new PrincipalDlg();
```

### 5. Resumen
La clase `PrincipalDlg` facilita la gestión de la interfaz principal de la aplicación, centralizando la navegación y el cambio de vistas mediante menús y paneles, siguiendo buenas prácticas de diseño en aplicaciones Swing.
