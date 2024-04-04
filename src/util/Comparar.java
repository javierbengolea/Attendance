/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.*;

/**
 *
 * @author Administrador
 */
public class Comparar {

    public static void main(String[] args) throws Exception {

        String ruta = "C:\\Archivos de programa\\S.I.Ap\\AFIP\\SIJPCor\\SIJPCor.mdb";
        
        String arch2 = "C:\\Documents and Settings\\Administrador\\Mis documentos\\Base de datos1.mdb";

        File archivo = new File(ruta);
        File archivo2 = new File(arch2);

        FileInputStream fr = new FileInputStream(archivo);
        
        FileInputStream fr2 = new FileInputStream(archivo2);

        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();

        for (int i = 0; i < 256; i++) {            
            
            int car = fr.read();
            int car2 = fr2.read();
                        
            sb.append(car);
            sb.append(" ^ ");
            sb.append(car2);
            sb.append (" = ");
            sb.append(car^car2);
            sb.append("\t");
            
            if(((i+1)%16)== 0) sb.append("\n");            
        }

        System.out.println(sb.toString());
    }

}
