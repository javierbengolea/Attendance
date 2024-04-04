/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package att;

import util.Fecha;

/**
 *
 * @author RRHH-SERVER
 */
public class Novedad {

    int id_novedad;
    String cod;
    String descripcion;
    Fecha inicio;
    Fecha fin;
    boolean presentismo;

    public static void main(String[] args) {
        System.out.println("    int id_novedad;\n"
                + "    String cod;\n"
                + "    String descripcion;\n"
                + "    Fecha inicio;\n"
                + "    Fecha fin;\n"
                + "    boolean presentismo;");
    }
}
