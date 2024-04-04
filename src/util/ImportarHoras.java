/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.ArrayList;

/**
 *
 * @author SistemasRC0011
 */
public class ImportarHoras {

    public static void main(String[] args) throws Exception {
        AdaptadorMySQL datos = new AdaptadorMySQL();

        String query = "select tarjeta, time_to_sec(horas_50)/3600 as al_50 , time_to_sec(horas_100)/3600 as al_100\n"
                + "from horas_extras_abr_2023\n"
                + "where time_to_sec(horas_50)+time_to_sec(horas_100) > 0";

        datos.consultar(query);

        ArrayList<Leg50100> legajosHoras = new ArrayList();

        while (datos.resultados.next()) {
            Leg50100 leg = new Leg50100(datos.resultados.getInt(1), datos.resultados.getDouble(2), datos.resultados.getDouble(3));
            legajosHoras.add(leg);
        }

        System.out.println(legajosHoras);
        datos.cerrar();

        AdaptadorFB datosFB = new AdaptadorFB();

        String queryFDB = "select cod_interno, primer_apellido || ', ' || nombres, id_modalida_contratacion, cedula_cuil from personal p where p.activo = 'S' and id_modalida_contratacion <> 65";

        datosFB.consultar(queryFDB);

        ArrayList<Emp> empleados = new ArrayList();

        while (datosFB.resultados.next()) {
            Emp empleado = new Emp(datosFB.resultados.getInt(1),
                    datosFB.resultados.getString(2),
                    datosFB.resultados.getInt(3),
                    datosFB.resultados.getString(4));
            empleados.add(empleado);
        }

        System.out.println(empleados);
        ArrayList<String> n11700 = new ArrayList();
        ArrayList<String> n11710 = new ArrayList();

        for (int i = 0; i < legajosHoras.size(); i++) {

            Leg50100 legTemp = legajosHoras.get(i);

            for (int j = 0; j < empleados.size(); j++) {
                Emp empTemp = empleados.get(j);

                if (legTemp.legajo == empTemp.legajo) {
                    int legajo = legTemp.legajo;
                    String cuil = empTemp.cuil;
                    String nombre = empTemp.nombre;
                    double horas50 = legTemp.al50;
                    double horas100 = legTemp.al100;
                    int modalidad = empTemp.modalidad;
                    String codigo = "";

                    if (horas50 > 0) {
                        if (modalidad == 205) {
                            codigo = "032";
                        } else if (modalidad == 38) {
                            codigo = "070";
                        } else if (modalidad == 37) {
                            codigo = "170";
                        }
                        System.out.println(legajo + "\t" + cuil + "\t" + codigo + "\t" + horas50 + "\t 0.00 " + "\t \t" + nombre);
                        String lineaVisual = String.format("%08d", legajo) + "" + String.format("%010.5f", horas50) + "0000000.000000.00";
                        n11700.add(lineaVisual);

//                        System.out.println(String.format("%08d", legajo) + "" + String.format("%010.5f", horas50)+"0000000.000000.00");
                    }

                    if (horas100 > 0) {
                        if (modalidad == 205) {
                            codigo = "033";
                        } else if (modalidad == 38) {
                            codigo = "071";
                        } else if (modalidad == 37) {
                            codigo = "171";
                        }
                        System.out.println(legajo + "\t" + cuil + "\t" + codigo + "\t" + horas100 + "\t 0.00 " + "\t \t" + nombre);
                        String lineaVisual = String.format("%08d", legajo) + "" + String.format("%010.5f", horas100) + "0000000.000000.00";
                        n11710.add(lineaVisual);
                    }

                    //  System.out.println(empTemp + " " + legTemp);
                }

            }

        }
        
        for(String l: n11710){
            System.out.println(l);
        }

    }

}

class Leg50100 {

    int legajo;
    double al50;
    double al100;

    public Leg50100(int legajo, double al50, double al100) {
        this.legajo = legajo;
        this.al50 = al50;
        this.al100 = al100;
    }

    @Override
    public String toString() {
        return legajo + ": " + al50 + " " + al100; //To change body of generated methods, choose Tools | Templates.
    }

}

class Emp {

    int legajo;
    String nombre;
    int modalidad;
    String cuil;

    public Emp(int legajo, String nombre, int modalidad, String cuil) {
        this.legajo = legajo;
        this.nombre = nombre;
        this.modalidad = modalidad;
        this.cuil = cuil;
    }

    @Override
    public String toString() {
        return "" + legajo + " " + nombre + " " + modalidad + " " + cuil; //To change body of generated methods, choose Tools | Templates.
    }

}
