package org.upemor.pruebaswing04;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import javax.swing.UIManager;
import org.upemor.pruebaswing04.view.PrincipalDlg;

/** @author cerimice **/

public class PruebaSwing04{
    public static void main(String[] args) throws Exception{
        System.out.println("Hello World!");
        
        FlatMacLightLaf.setup();
        UIManager.put("Button.arc", 999);
        UIManager.put("Component.arc", 999);
        UIManager.put("ProgressBar.arc", 999);
        UIManager.put("TextComponent.arc", 999);
        UIManager.setLookAndFeel(new FlatMacLightLaf());

        PrincipalDlg principalDlg = new PrincipalDlg();
    }
}
