/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package att;

import java.io.*;
import java.util.StringTokenizer;

/**
 *
 * @author Administrador
 */
public class LectorCSV {

    public static void main(String[] args) throws Exception {

        String ruta = "D:\\Personal_2018\\horas_extras\\06-junio\\regihi.csv";

        File archivo = new File(ruta);

        String linea = "";

        BufferedReader br = new BufferedReader(
                new FileReader(archivo));

        while ((linea = br.readLine()) != null) {
            //Nregi;Legajo;Tarjeta;Fecha;Hora;Reloj;Codigo;Fprocesa;Ver
            StringTokenizer stk = new StringTokenizer(linea, ";");

            while (stk.hasMoreElements()) {
                int nregi = new Integer(stk.nextToken());
                int legajo = new Integer(stk.nextToken());
                int tarjeta = new Integer(stk.nextToken());
                String fecha = stk.nextToken();
                String hora = stk.nextToken();
                int reloj = new Integer(stk.nextToken());
                int codigo = new Integer(stk.nextToken());
                String fechaProcesa = stk.nextToken();

                System.out.print(nregi + "\t");
                System.out.print(legajo + "\t");
                System.out.print(tarjeta + "\t");
                System.out.print(fecha + "\t");
                System.out.print(new Hora(hora) + "\t");
                 //new Hora(hora).getValor()

                boolean pm = (hora.substring(hora.length() - 5, hora.length() - 1)).equals(" p.m.");

                Hora horaH;

                if (pm) {
                    horaH = new Hora(new Hora(hora).getValor() + new Hora("11:59").getValor());
                } else {
                    horaH = new Hora(hora);
                }

                //System.out.print(hora.substring(hora.length() - 5, hora.length() - 1) + "\t");
                System.out.print(horaH + "\t");
                System.out.print(reloj + "\t");
                System.out.print(codigo + "\t");
                System.out.print(fechaProcesa + "\t");
//                 new Hora(hora);

            }
            System.out.println("");
        }
    }
}
