/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package att;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author javier
 */
public class ConectarBD {
    
    static String DRIVER_MYSQL = "com.mysql.jdbc.Driver";
   // static String DRIVER_ACCESS = "net.ucanaccess.jdbc.UcanloadDriver";
    static String URL_MYSQL = "jdbc:mysql://localhost:3306/rrhh";
   // static String URL_ACCESS = "net.ucanaccess.jdbc.UcanloadDriver";
    static String USER_MYSQL = "root";
    static String PASS_MYSQL = "admin";
    
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here

        Class.forName(DRIVER_MYSQL);

        //String URL = "jdbc:ucanaccess:///media/javier/Datos/bd/access/horas_extras.accdb";

        //net.ucanaccess.jdbc.UcanloadDriver;
        Connection con = DriverManager.getConnection(URL_MYSQL, USER_MYSQL, PASS_MYSQL);

        Statement stmt = con.createStatement();

        //String consulta = "select fecha, agente from marcaciones_ene_2016 group by fecha, agente order by agente, fecha";
        String consulta = "select codigo from marcaciones_ene_2016";

        int cantMarcaciones = 0;
        int cantEntradas = 0;
        int cantSalidas = 0;

        ResultSet datos = stmt.executeQuery(consulta);

        ResultSetMetaData meta = datos.getMetaData();

        int cols = meta.getColumnCount();

        int temp = 0;

        while (datos.next()) {
            for (int i = 0; i < cols; i++) {
                //System.out.print(datos.getString(i+1) + " - ");                
            }

            temp = datos.getInt(1);

            if (temp == 20) {
                cantEntradas++;
            } else {
                cantSalidas++;
            }


            /*System.out.print(datos.getInt(9) + " ");
             System.out.println(datos.getInt(1));
             System.out.println(datos.getString(2));
             System.out.println(datos.getInt(3));
             System.out.println(datos.getDate(4));
             System.out.println(datos.getTime(5));
             System.out.println(datos.getInt(6));
             System.out.println(datos.getInt(7));
             System.out.println(datos.getDate(8));*/
            cantMarcaciones++;
            //  System.out.println("");
        }

        consulta = "select tarjeta from marcaciones_ene_2016 group by tarjeta order by tarjeta";

        datos = stmt.executeQuery(consulta);

        //datos.getMetaData().
        ArrayList agentes = new ArrayList();

        while (datos.next()) {
            agentes.add(datos.getInt(1));
        }

        for (int i = 0; i < agentes.size(); i++) {
     //       System.out.println(agentes.get(i));

        }

        ArrayList dias = new ArrayList();

        consulta = "select fecha from marcaciones_ene_2016 group by fecha order by fecha";

        datos = stmt.executeQuery(consulta);

        while (datos.next()) {
            dias.add(datos.getDate(1));
        }

        for (int i = 0; i < dias.size(); i++) {
   //         System.out.println(dias.get(i));

        }

        int cantAgentes = agentes.size();
        int cantDias = dias.size();

        System.out.println("Agentes: " + cantAgentes);
        System.out.println("Dias: " + cantDias);

        //System.out.println("Estimadas: " + (cantAgentes * 20*2));
        System.out.println("Reales: " + cantMarcaciones);

        System.out.println("Entradas: " + cantEntradas);
        System.out.println("Salidas: " + cantSalidas);

        String consultaTemp;
        /*
         for (int i = 0; i < cantAgentes; i++) {
            
         temp = ((Integer) agentes.get(i));
            
         consultaTemp = "select tarjeta, fecha, codigo, hora from marcaciones_ene_2016 where tarjeta = " + temp + ";";
            
         datos = stmt.executeQuery(consultaTemp);
            
         while(datos.next()){
                
         System.out.print(datos.getInt(1) + ", ");
         System.out.print(datos.getDate(2) + ", ");
         System.out.print(datos.getInt(3) + ", ");
         System.out.println(datos.getTime(4));
                
         }
            
         //System.out.println("Agente " + temp + ": " + datos.getInt(1) + " marcaciones.");
            
         }
         */
        String agenteTemp;
        String fechaTemp;
        int marcaciones;
        int suma;
        int id;

        ArrayList<String> unaMarcacion = new ArrayList<>();
        ArrayList<String> dosMarcaciones = new ArrayList<>();
        ArrayList<String> variasMarcaciones = new ArrayList<>();
        ArrayList<String> vacias = new ArrayList<>();

        for (int i = 0; i < cantDias; i++) {

            fechaTemp = dias.get(i).toString();

            for (int j = 0; j < cantAgentes; j++) {

                agenteTemp = agentes.get(j) + "";

                consultaTemp = "select count(codigo), sum(codigo), min(id) from marcaciones_ene_2016 where tarjeta = " + agenteTemp
                        + " and fecha = '" + fechaTemp + "' and tipo <> 4 order by hora;";

                datos = stmt.executeQuery(consultaTemp);

                datos.next();

                marcaciones = datos.getInt(1);
                suma = datos.getInt(2);
                id = datos.getInt(3);

                if (marcaciones == 1) {
                    unaMarcacion.add("select * from marcaciones_ene_2016 where id = " +id +";");
                }

                if (marcaciones == 2) {
                    dosMarcaciones.add(consultaTemp);
                }

                if (marcaciones > 2) {
                    variasMarcaciones.add(consultaTemp);
                }

                if (marcaciones == 0) {
                    vacias.add(consultaTemp);
                }
                
                if(marcaciones == 1){
                    System.out.println(fechaTemp+ "; " +agenteTemp+ "; " +marcaciones+ "; " +suma);
                }

                if (marcaciones == 2 && suma != 41) {
                    System.out.println(fechaTemp+ "; " +agenteTemp+ "; " +marcaciones+ "; " +suma);
                }
                
                if(marcaciones == 3){
                    System.out.println(fechaTemp+ "; " +agenteTemp+ "; " +marcaciones+ "; " +suma);
                }
                
                if (marcaciones == 4 && suma != 82) {
                    System.out.println(fechaTemp+ "; " +agenteTemp+ "; " +marcaciones+ "; " +suma);
                }
                
                if (marcaciones == 4) {
                    System.out.println(fechaTemp+ "; " +agenteTemp+ "; " +marcaciones+ "; " +suma);
                }

            }

        }

        System.out.println("Sin marca: " + vacias.size());

        System.out.println("Dos marcas: " + dosMarcaciones.size());
        System.out.println("Varias Marcas: " + variasMarcaciones.size());
        
        for (int i = 0; i < unaMarcacion.size(); i++) {
            System.out.println(unaMarcacion.get(i));
            
        }
        System.out.println("Una marca: " + unaMarcacion.size());
    }

}
