# Explicación detallada de la clase `BaseModelDLG`

A continuación se presenta el código completo de la clase `BaseModelDLG.java` y una explicación de su funcionamiento, propósito, variables, métodos y uso típico, siguiendo el estilo detallado de documentación.

---

## Código completo de `BaseModelDLG.java`

```java
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
```

---

## Explicación de la clase

### 1. Propósito general
La clase abstracta `BaseModelDLG` sirve como base para la creación de diálogos personalizados en aplicaciones Swing. Centraliza la configuración de botones estándar (Aceptar y Cancelar), el layout y la gestión de eventos básicos.

### 2. Variables
- `private JButton btnAceptar;` — Botón para aceptar la acción del diálogo.
- `private JButton btnCancelar;` — Botón para cancelar y cerrar el diálogo.
- `private GridBagConstraints constraints;` — Restricciones para el layout GridBag.

### 3. Métodos principales
- `public BaseModelDLG(JFrame padre, boolean modal)`: Constructor que inicializa el diálogo y llama a `init()`.
- `private void init()`: Configura los botones, colores, fuentes, layout y comportamiento de cierre.
- `protected void eventoBotonCancelar()`: Cierra el diálogo al presionar Cancelar.
- `protected abstract void eventoBotonAceptar()`: Método abstracto que debe implementar la subclase para definir la acción al presionar Aceptar.
- `protected GridBagConstraints crearRestricciones(...)`: Facilita la creación de restricciones para el layout GridBag.

### 4. Uso típico
Se utiliza como clase base para diálogos personalizados, permitiendo a las subclases definir la lógica específica del botón Aceptar y aprovechar la configuración estándar de botones y layout.

```java
public class MiDialogo extends BaseModelDLG {
    public MiDialogo(JFrame padre, boolean modal) {
        super(padre, modal);
        // Configuración adicional
    }
    @Override
    protected void eventoBotonAceptar() {
        // Lógica al aceptar
    }
}
```

### 5. Resumen
La clase `BaseModelDLG` promueve la reutilización de código y la consistencia en la creación de diálogos, facilitando la implementación de interfaces gráficas modales en aplicaciones Swing.
