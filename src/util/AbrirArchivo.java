/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import java.io.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Administrador
 */
public class AbrirArchivo extends javax.swing.JFileChooser{

    public AbrirArchivo() {
    super();
    }
    
    
    
    public static void main(String[] args) throws Exception{
        System.out.println(AbrirArchivo.abrir());
    }
    
    public static File abrir() throws Exception{
        File archivo = new File("\\\\SilviaPersonal\\");
        
        AbrirArchivo abrir = new AbrirArchivo();
        
        abrir.setCurrentDirectory(new File("\\\\silviapersonal\\Silvia1 (C)\\Personal\\"));
        
       // abrir.addChoosableFileFilter(new FileNameExtensionFilter("*.csv","csv"));
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Excel", "xls");
        abrir.setFileFilter(filtro);
        
        int option = abrir.showOpenDialog(null);
        
        
        
        archivo = abrir.getSelectedFile();
        
        return archivo;
    }
    
}
