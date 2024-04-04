/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

/**
 *
 * @author Administrador
 */
public class Condicion {
    
    public static void main(String[] args) {
        int tipoDia = 0;
        double valor = 10;
        
        
        
        
        
        System.out.println(verCondicion(tipoDia, valor));
    }
    
    
    public static String verCondicion(int tipoDia, double valor){
        String mensaje = "";
        
        if(tipoDia == 1){
            
            if(valor <= 0.0){
                mensaje = "&nbsp;";
            }else{
                mensaje = "" +Varios.verNumero(valor);
            }
            
        }
        if(tipoDia == 0){
            if(valor <= 0.0){
                mensaje = "<p style='color: red'>x&nbsp;</p>" ;
            }
            
            if(valor > 0 && valor <=7.){
                mensaje = "&nbsp;";
            }
                if(valor> 7.){
                    mensaje = "" + Varios.verNumero(valor - 7.);
                }
        }
        
        return mensaje;
    }
}
