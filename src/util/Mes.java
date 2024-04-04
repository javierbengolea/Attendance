/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import java.util.ArrayList;


/**
 *
 * @author Administrador
 */
public class Mes {
    
    static ArrayList<Dia> dias;
    
    public Mes(int anio, int mes, String clase){
        AdaptadorMySQL datos = new AdaptadorMySQL();
        
        dias = new ArrayList<>();
        
        String consulta = "select * from dias where mes = " + mes + " and anio = " + anio + " order by anio, mes, dia; ";
        
        //System.err.println("la clase " + clase + " pide: " +consulta);
        
        datos.consultar(consulta);
        
        try{
        while(datos.resultados.next()){
            dias.add(new Dia(new Fecha().setFechaMySQL(datos.resultados.getString(1)), datos.resultados.getString(5),
                    datos.resultados.getInt(6)));
            
            /*System.out.println(datos.resultados.getString(4));
            System.out.println(datos.resultados.getString(5));
            System.out.println(datos.resultados.getString(6));*/
        }
           datos.cerrar();
        }catch(Exception e){
         //   e.printStackTrace();
        }
     
    }
    
    public ArrayList<Dia> getDias() {
        return dias;
    }
    
    public static void main(String[] args){
        
        
        Mes mes = new Mes(2022,2,"Mes.main");
        
        
       
        System.out.println(mes.dias);
        
    }
    
}
