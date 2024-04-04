/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 *
 * @author JavierPro
 */
public class LectorInsertar {

    public static void main(String[] args) throws Exception {
        LectorArchivo lector = new LectorArchivo("h:\\febrero_21_16.csv");

        PrintWriter out = new PrintWriter("h:\\febrero_consultas.txt");

        StringTokenizer stk;

        String linea = lector.leerLinea(1);

        String consulta;

        AdaptadorMySQL datos = new AdaptadorMySQL();

        for (int i = 0;; i++) {
            linea = lector.leerLinea(1);
            if (linea == null) {
                break;
            } else {
                consulta = "insert into marcaciones_feb_2016 values('";
                // System.out.println(linea);
                stk = new StringTokenizer(linea, ";");
                // while(stk.hasMoreTokens()){
                consulta += stk.nextToken() + "','";
                consulta += stk.nextToken() + "','";
                consulta += stk.nextToken() + "','";
                consulta += stk.nextToken() + "','";
                consulta += new Fecha(stk.nextToken()).getFechaMySQL() + "','";

                consulta += stk.nextToken() + "','";
                consulta += stk.nextToken() + "','";
                consulta += stk.nextToken() + "','";
                consulta += new Fecha(stk.nextToken()).getFechaMySQL() + "',0);";

                try {
                    datos.actualizar(consulta);
                } catch (Exception e) {

                    System.out.println(consulta);
                }

                // }*/
            }

        }
        out.flush();
        out.close();
    }

}

class LectorArchivo {

    static BufferedReader br;
    static FileReader fr;
    static File f;

    public LectorArchivo(String archivoSt) {

        try {

            f = new File(archivoSt);
            fr = new FileReader(f);
            br = new BufferedReader(fr);

        } catch (Exception e) {
            System.err.println("Error al leer el archivo: " + archivoSt);
        }

    }

    public String leerLinea(int nLinea) {

        String linea = "";
        try {
            for (int i = 0; i < nLinea - 1; i++) {
                br.readLine();
            }
            linea = br.readLine();
        } catch (Exception e) {

        }
        return linea;
    }

}
