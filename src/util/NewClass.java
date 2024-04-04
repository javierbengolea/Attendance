/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

//import util.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.StringTokenizer;
//import main.Hora;

/**
 *
 * @author Administrador
 */
public class NewClass {
    
    
    public static void main(String[] args) throws Exception{
        
        /*int horas = new Hora("42:43").getValor()+new Hora("08:30").getValor()-new Hora("02:22").getValor()
                -new Hora("06:16").getValor();*/
        
        //horas = new Hora("23:47").getValor() + new Hora("13:30").getValor();
        
       /* int horas = new Hora("19:00").getValor() - new Hora("14:00").getValor();
        
        System.out.println(new Hora(588*60));*/
        //File archivo = new File("/home/javier/Escritorio/artistas_vencimientos.csv");      
        File archivo = new File("d:\\libs\\mysql-connector-java-5.1.18-bin.jar");      
        
        BufferedReader br = new BufferedReader(new FileReader(archivo));
        
        String linea = br.readLine();
        int cuenta = 0;
       
       linea = br.readLine();
        
        while(linea != null && cuenta <= 1000){
           // System.out.println(procesarLinea(linea));
            cuenta++;
            System.out.println(linea+ "\n");
            linea = br.readLine();
        }
        
    }
    
    static String procesarLinea(String linea) throws Exception{
        String salida = "INSERT INTO artista VALUES(";
        
        StringTokenizer stk = new StringTokenizer(linea, ";");
        
        salida += stk.nextToken() + ", '";
        salida += stk.nextToken() + "', '";
        salida += stk.nextToken() + "', '";
        salida += stk.nextToken() + "', ";
        salida += stk.nextToken() + ", '";
        salida += new Fecha(stk.nextToken()).getFechaMySQL() + "');";
        
        
        
        return salida;
    }
}
