/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package att;

/**
 *
 * @author usuario
 */
public class Operacion {
    
    int codigo = 20;
    Hora hora = new Hora();
    
    String [] OPERACION_ST  = {"ENTRADA", "SALIDA"};
    
    public static void main(String[] args) {
        
    }

    public Operacion() {
    }
    
    public Operacion(int codigo, Hora hora){
        this.codigo = codigo;
        this.hora = hora;
    }

    public int getCodigo() {
        return codigo;
    }

    public Hora getHora() {
        return hora;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setHora(Hora hora) {
        this.hora = hora;
    }
    
    public String getOperacionSt(){
        if(this.codigo == 21)
            return "SALIDA";
        
        return "ENTRADA";
    }
    
    

    @Override
    public String toString() {
        return getOperacionSt() + " - " + getHora();
    }
    
    
    
}
