/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package att;

import util.*;

/**
 *
 * @author Administrador
 */
public class Irregularidad {
    
    public static final int SIN_MARCACION = 0;
    public static final int UNA_MARCACION = 1;
    public static final int DOS_MARCACIONES_IGUAL = 2;
    public static final int DOS_MARCACIONES_INV = 3;
    public static final int TRES_MARCACIONES = 4;
    public static final int CUATRO_MARCACIONES = 5;
    public static final int MUCHAS_MARCACIONES = 6;
    
    public static String [] DESCRIPCION = {
        "Sin marcacion",
        "Una marcacion",
        "Dos marcaciones",
        "Dos marcas Inv.",
        "Tres marcaciones",
        "Cuatro marcaciones",
        "Muchas marcas"
    };
    
    Agente agente;
    Fecha fecha;
    int suma;
    int tipo;

    public Irregularidad() {
    }

    public Irregularidad(Agente agente, Fecha fecha, int suma, int tipo) {
        this.agente = agente;
        this.fecha = fecha;
        this.suma = suma;
        this.tipo = tipo;
    }
    
    
    
    
    public static void main(String[] args) {
        
    }

    @Override
    public String toString() {
    String salida = "";
    
    salida += this.agente + " - ";
    salida += this.fecha + " - ";
    salida += this.suma + " - ";
    salida += DESCRIPCION[this.tipo];
    
    return salida;
    }
    
    
}
