/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package att;

import fabrica.DepartamentosConstantes;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;
import util.AdaptadorMySQL;

/**
 *
 * @author dell01
 */
public class ReporteSabana {

    static String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto",
        "Septiembre", "Octubre", "Noviembre", "Diciembre"};

    static int mes = Lector.MES;
    static int anio = Lector.ANIO;

    public static void main(String[] args) throws Exception {

        String html = """
                      <!DOCTYPE html>
                      <html>                      
                       <head>
                              <meta http-equiv="Content-Type" content="text/html; charset=windows-1251">
                              <title>Detalle Horas Extras %s %s</title>
                              <link rel="stylesheet" type="text/css" href="css/main.css">
                              <!--link href="https://unpkg.com/tailwindcss@^2/dist/tailwind.min.css" rel="stylesheet"-->
                      
                              <!--link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
                              <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script-->
                      <link rel="stylesheet" href="css/bootstrap.min.css">
                                                    <script src="scripts/bootstrap.bundle.min.js"></script>
                        </head>
                      
                        <script type="text/javascript" src="scripts/jquery-3.1.0.js"></script>                      
                      
                      <div class="container">  
                      <center>
                        <h1>DETALLE HORAS EXTRAS %s %s</h1>
                      </center>
                      
                      """.formatted(meses[mes - 1], anio, meses[mes - 1].toUpperCase(), anio);

        //String tarjeta;
        String TABLA_DETALLE_HORAS_EXTRAS = Lector.TABLA_DETALLE_HORAS_EXTRAS;

        String archivo = "C:\\2024\\03-mar\\feb_2024_OPP.html";

        PrintWriter out = new PrintWriter(new File(archivo));

        AdaptadorMySQL adaptadorMySQL = new AdaptadorMySQL();

        ArrayList<ArrayList<String>> registraciones = new ArrayList();

        int tipo_agentes = DepartamentosConstantes.TODOS;

        String consulta;

        ArrayList agentes = new ArrayList();

        try {
            agentes = Lector.getAgentesMA(tipo_agentes);
        } catch (Exception e) {
        }

        for (int i = 0; i < agentes.size(); i++) {

            consulta = "select a.nombres, dh.dia, dh.tipo_dia, dh.entrada, dh.salida, diferencia as \"horas\", al_50, al_100, suma, d.nombre \n                     from ";
            consulta += TABLA_DETALLE_HORAS_EXTRAS + " dh, agentes a, dias d \n"
                    + "where a.tarjeta = dh.tarjeta \n"
                    + "and a.horas_extras = 1 \n"
                    + "and a.tarjeta = " + agentes.get(i) + "\n"
                    + "and d.dia = dh.dia\n"
                    + "and d.anio = " + anio + "\n"
                    + "and d.mes = " + mes + "\n "
                    //+ "and d.dia = 2 "
                    //+ "and a.tarjeta in (select tarjeta from maquinistas)\n"
                    + "order by id_encargado, nombres, dia, dh.entrada";

            ResultSet datoss = adaptadorMySQL.consultar(consulta);

            try {

                html += """                                        
                <table border="0" cellspacing="0" cellpadding="0" id="tabla_sabana">
                        <thead>
                        """;
                String tarjeta = agentes.get(i).toString();
                String agente = new Agente().nombres.get(agentes.get(i));

                html += "<tr><th colspan=7 style=\"text-align: left\">" + tarjeta + " - " + agente + " </th></tr>\n";

                html += """
                       
            <tr>    <th>
                    Dia
                </th>
                <th>
                    Nombre
                </th>
                <th>
                    Entrada
                </th>
                <th>
                    Salida
                </th>
                <th>
                    Horas
                </th>
                <th>
                    Al 50%
                </th>
                <th>
                    Al 100%
                </th></tr>
                        </thead>
                        <tbody>""";

                while (datoss.next()) {
                    int suma = datoss.getInt(9);

                    String horas100 = datoss.getString(8);
                    String horas50 = datoss.getString(7);
                    int dia = datoss.getInt(2);
                    String nombre = datoss.getString("nombre");

                    String entrada = datoss.getString(4);
                    String salida = datoss.getString(5);
                    String horas = datoss.getString(6);

                    if (horas100.equals("00:00")) {
                        horas100 = "-";
                    }

                    if (horas50.equals("00:00")) {
                        horas50 = "-";
                    }

                    boolean algo = !(horas100.equals("00:00") && horas50.equals("00:00"));
                    //System.out.println(algo);

                    if ((suma < 2) && algo) {

                        int tipoDia = datoss.getInt(3);

                        String clase = "";

                        if (tipoDia == 1) {
                            clase += " al_100";
                        }

                        try {
                            int tipo = datoss.getInt(11);
                            if (tipo == 2) {
                                clase += " fic";
                            }
                        } catch (SQLException e) {

                        }

                        html += "<tr class=\"" + clase + "\">";

                        html += "<td>" + dia + "</td>";
                        html += "<td>" + nombre + "</td>";
                        html += "<td>" + entrada + "</td>";
                        html += "<td>" + salida + "</td>";
                        html += "<td>" + horas + "</td>";
                        html += "<td>" + horas50 + "</td>";
                        html += "<td>" + horas100 + "</td>";

                        html += "</tr>\n";
                    }

                }
                html += "</tbody>\n";

                html += """
                                <tfoot>
                                               <tr>
                                                   <td>TOTALES</td>
                               """;

                consulta = "select * from " + Lector.TABLA_HORAS_EXTRAS + " where tarjeta = " + agentes.get(i);

                //datoss = stmt.executeQuery(consulta);
                datoss = adaptadorMySQL.consultar(consulta);

                while (datoss.next()) {
                    String hs_50 = datoss.getString(2);
                    String hs_100 = datoss.getString(3);
                    html += "<td></td><td></td><td></td><td></td>";
                    html += "<td>" + hs_50 + "</td>";
                    html += "<td>" + hs_100 + "</td>";

                    System.out.println(tarjeta + "\t" + agente + "\t" + "\t " + formatHora(hs_50) + "\t" + formatHora(hs_100));
                }

                html += """
                          </tr>
                                    </tfoot>
                                </table>
                        """;

            } catch (Exception e) {

            }

        }

        html += """
                </div>
                </body>
                </html>
                """;

        out.print(html);

        out.flush();
        out.close();
        System.out.println(archivo);
    }

    public static String formatHora(String hora) {
        String salida;

        double nuevo_valor = (double)(new Hora(hora).getValor()) / 3600.0;

        if (nuevo_valor == 0) {
            salida = "";
        } else {
            salida = nuevo_valor + "";
        }

        return salida;
    }
}
