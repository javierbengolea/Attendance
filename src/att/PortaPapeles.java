/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package att;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

/**
 *
 * @author javier
 */
public class PortaPapeles implements ClipboardOwner {

    public PortaPapeles() {
/*
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        StringSelection data = new StringSelection("Enviando al portapales!!!");

        clipboard.setContents(data, this);*/

    }
    
    public void setClipBoard(String texto){
        
        StringSelection txt = new StringSelection(texto);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(txt, this);
        
    }

    public static void main(String[] args) {
        
      Porta p = new Porta();
      
      p.setVisible(true);
        

    }

    
    
    @Override
    public void lostOwnership(Clipboard clipboard, Transferable contents) {
    //To change body of generated methods, choose Tools | Templates.
    }

}
