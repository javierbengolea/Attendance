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
public class LectorRelojes {

    public static void main(String[] args) throws Exception {

        // String ruta = "D:\\Personal_2017\\horas extras\\Enero\\prom_hum\\ene_2017.txt";
        String ruta = "C:\\2024\\02-Febrero\\ene_2024.csv";

        //Scanner scan = new Scanner(new File(ruta));
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(ruta)));

        //int nroLinea = 0;
        String linea;
        StringTokenizer stk;
        int tarjeta = 0;
        Fecha dia;
        Hora hora;
        int reloj = 2;
        int codigo;
        int nos;
        int nos2;
        int legajo = 0;
        String agente = "";
        int count = 1;
        
        //linea = scan.nextLine();
        linea = br.readLine();
        

        while (linea.length() > 10) {
        linea = br.readLine();
        
        if(linea == null){
            break;
        }
            //nroLinea++;
//            System.out.print(nroLinea +": ");
            stk = new StringTokenizer(linea, ";");

            try {
                legajo = new Integer(stk.nextToken());
                    //dia = new Fecha().setFecha_DD_MM_AA(stk.nextToken(), '/');
                agente = stk.nextToken();
                tarjeta = new Integer(stk.nextToken());
                dia = new Fecha().setFecha_DD_MM_AAAA(stk.nextToken(), '/');
                hora = new Hora(stk.nextToken());
                reloj = new Integer(stk.nextToken());
                reloj = 1;
                codigo = new Integer(stk.nextToken());
                //System.out.println(codigo);
                //nos = new Integer(stk.nextToken());
                //    nos2 = new Integer(stk.nextToken());
            } catch (Exception e) {
                System.err.println("Error leyendo lineas: " + linea + Varios.bytesToHex(linea.getBytes()));
                System.out.println(e);
                continue;
            }
          
            try{
            Marcacion marca = new Marcacion();

            marca.setTarjeta(tarjeta);
            marca.setLegajo(tarjeta);
            marca.setFecha(dia);
            marca.setHora(hora);
            marca.setCodigo(codigo);
            marca.setReloj(reloj);
            marca.setId(marca.calcularId());
            //marca.setAgente(Agente.getNombres(tarjeta));
            marca.setAgente(agente);
            marca.setFechaProceso(new Fecha());

            //   System.out.println(tarjeta + ": " + marca.getAgente());
            System.out.println(marca.getConsultaInsert() + ";");
            
            
         /*       System.out.println(count);
                count++;*/
            }catch(Exception e){
                System.err.println("Exception: ");
            }
        }
    }
}
