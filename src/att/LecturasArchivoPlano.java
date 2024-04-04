/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package att;

import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer;
import util.Fecha;
import util.Varios;

/**
 *
 * @author Administrador
 */
public class LecturasArchivoPlano {

    public static void main(String[] args) throws Exception {

        // String ruta = "D:\\Personal_2017\\horas extras\\Enero\\prom_hum\\ene_2017.txt";
        String ruta = "D:\\Personal_2017\\horas extras\\Noviembre\\20171127111540.rcs";

        Scanner scan = new Scanner(new File(ruta));

        //int nroLinea = 0;
        String linea;
        StringTokenizer stk;
        int tarjeta = 0;
        Fecha dia;
        Hora hora;
        int reloj = 1;
        int codigo;
        int nos;
        int nos2;

        while (scan.hasNextLine()) {
            linea = scan.nextLine();
            //nroLinea++;
//            System.out.print(nroLinea +": ");
            stk = new StringTokenizer(linea, " \t");

            try {
                tarjeta = new Integer(stk.nextToken());
                    //dia = new Fecha().setFecha_DD_MM_AA(stk.nextToken(), '/');
                dia = new Fecha().setFecha_DD_MM_AA(stk.nextToken(), '/');
                hora = new Hora(stk.nextToken());
                reloj = new Integer(stk.nextToken());
                //reloj = 2;
                codigo = new Integer(stk.nextToken());
                //System.out.println(codigo);
                //nos = new Integer(stk.nextToken());
                //    nos2 = new Integer(stk.nextToken());
            } catch (Exception e) {
                System.err.println("Error leyendo lineas: " + linea + Varios.bytesToHex(linea.getBytes()));
                System.out.println(e);
                continue;
            }
            Marcacion marca = new Marcacion();

            marca.setTarjeta(tarjeta);
            marca.setLegajo(tarjeta);
            marca.setFecha(dia);
            marca.setHora(hora);
            marca.setCodigo(codigo);
            marca.setReloj(reloj);
            marca.setId(marca.calcularId());
            marca.setAgente(Agente.getNombres(tarjeta));
            marca.setFechaProceso(new Fecha());

            //   System.out.println(tarjeta + ": " + marca.getAgente());
            System.out.println(marca.getConsultaInsert() + ";");
        }
    }
}
