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
public class Dia {

    Fecha fecha;
    String nombreDia;
    int tipo;

    public Dia(Fecha fecha, String nombreDia, int tipo) {
        this.fecha = fecha;
        this.nombreDia = nombreDia;
        this.tipo = tipo;
    }

    public static void main(String[] args) throws Exception {

    }

    @Override
    public String toString() {
        return fecha + "-" + nombreDia + "-" + tipo;
    }

    public Fecha getFecha() {
        return fecha;
    }

    public String getNombreDia() {
        return nombreDia;
    }

    public int getTipo() {
        return tipo;
    }

    
    
}
