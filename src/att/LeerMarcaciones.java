/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package att;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.StringTokenizer;
import util.Fecha;

/**
 *
 * @author JavierPro
 */
public class LeerMarcaciones {

    public static void main(String[] args) throws Exception {

        //String archivoEntrada = "D:\\Personal_2016\\horas extras\\Abril\\marcaciones_abr_2016_unidas.csv";
        // String archivoEntrada = "C:\\2021\\dic\\dic_2021.csv";
        //String archivoEntrada = "C:\\Users\\Public\\Documents\\RRHH2020\\Sueldos\\Liquidaciones\\03-Marzo\\Febrero 2022.csv";
        //String archivoEntrada = "c:\\vs\\2023\\11-nov\\octubre_2023.csv";
        String archivoEntrada = "C:\\2024\\03-mar\\feb_2024.csv";
        Path ruta = Paths.get(archivoEntrada);

        //BufferedReader in = new BufferedReader(new FileReader(new File(archivoEntrada)));
        BufferedReader in = Files.newBufferedReader(ruta, StandardCharsets.ISO_8859_1);//, StandardCharsets.ISO_8859_1);

        //     List<String> list = Files.readAllLines(archivoEntrada, StandardCharsets.UTF_8);
        String linea = in.readLine();
        linea = in.readLine();

        StringTokenizer stk;
        Marcacion marca;

        ArrayList<String> ids = new ArrayList<>();

        System.out.println(Marcacion.getEncabezadoConsultaInsert());
        while (linea != null) {
//            System.out.println(linea);

            stk = new StringTokenizer(linea, ";");

            while (stk.hasMoreTokens()) {
                try {
                    // System.out.print(stk.nextToken() + " - ");
                    //System.out.println(linea);
                    int legajo = Integer.parseInt(stk.nextToken());
                    String nombre = stk.nextToken().replace("\"", "");
                    int tarjeta = Integer.parseInt(stk.nextToken().replace("\"", ""));
                    Fecha fecha = new Fecha().setFechaEstandar(stk.nextToken().replace("\"", ""));
                    Hora hora = new Hora(stk.nextToken().replace("\"", ""));
                    int reloj = Integer.parseInt(stk.nextToken().replace("\"", ""));
                    int codigo = Integer.parseInt(stk.nextToken().replace("\"", ""));
                    Fecha proceso = new Fecha().setFechaEstandar(stk.nextToken().replace("\"", ""));

                    marca = new Marcacion(legajo,
                            nombre,
                            tarjeta,
                            fecha,
                            hora,
                            reloj,
                            codigo,
                            proceso);

                    ids.add(marca.getId());

                    //System.out.println("");
                    //System.out.println(Marcacion.getEncabezadoConsultaInsert());
                    if (ids.lastIndexOf(marca.getId()) > -1) {
                        System.out.println(marca.getConsultaInsert() + ";");
                    }
                } catch (Exception e) {
                    System.err.println(e + ": " + linea);
                }
            }

//            System.out.println("");
            try {
                linea = in.readLine();
            } catch (Exception e) {
                System.err.println(e);                
                linea = null;
            }

        }

    }
}
