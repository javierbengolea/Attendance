/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

//import com.mysql.jdbc.Util;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.swing.JFrame;
import att.Lector;

/**
 *
 * @author Administrador
 */
public class Varios {

    public static final String[] MESES = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
        "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};

    public static void main(String[] args) throws Exception {
        //leerArchivo();
        /*  int legajo = 1071;
        
         String consulta1 = crearConsultaMarcacion("marcaciones_feb_2016", legajo, "2016-02-14", "05:00", 20);
         String consulta2 = crearConsultaMarcacion("marcaciones_feb_2016", legajo, "2016-02-14", "12:00", 21);
        
         System.out.println(consulta1);
         System.out.println(consulta2);
        
         AdaptadorMySQL datos = new AdaptadorMySQL();
        
         datos.actualizar(consulta1);
         datos.actualizar(consulta2);*/

        //   System.out.println(getSelectAgentes());
        //   String ruta = "\\\\silviapersonal\\Silvia1 (C)\\Personal\\Registra\\back";
        String ruta = "c:\\azar.csv";

        //  BufferedReader br = new BufferedReader(new FileReader(new File(ruta)));
        /*JFrame ventana = new CrearMarcacionComplementaria("103725080620");

         ventana.setVisible(true);*/
//        new CrearMarcacionComplementaria("103725080620").setVisible(true);
        String consulta = "select dia, sum(cast(diferencia as time))/10., sum(cast(dh.al_50 as time))/10., sum(cast(dh.al_100 as time))/10.,dh.tipo_dia\n"
            + "from detalle_horas_extras_dic_2018 dh\n"
            + "where tarjeta = 1046\n"
            + "group by dia, tipo_dia";

        AdaptadorMySQL datos = new AdaptadorMySQL();

        ResultSet resultados = datos.consultar(consulta);

        int cols = resultados.getMetaData().getColumnCount();

        Mes mes = new Mes(2017, Lector.MES, "Varios.main");

        double[] info = new double[mes.getDias().size()];

        for (int i = 0; i < info.length; i++) {
            info[i] = -1;
        }
        /*
        while (resultados.next()) {

            int dia = resultados.getInt(1);

            double horas = procesaHora(resultados.getDouble(2));
            double _50 = procesaHora(resultados.getDouble(3));
            double _100 = procesaHora(resultados.getDouble(4));

            System.out.print(dia + " \t ");
            System.out.print(horas + " \t ");
            System.out.print(_50 + " \t ");
            System.out.print(_100);

            info[dia - 1] = horas;

            System.out.println("");
        }

        for (int i = 0; i < info.length; i++) {
            double d = info[i];
            System.out.println(d);

        }*/
 /*        double[] mapa = verMapaHoras(995, "detalle_horas_extras_dic_2018");

        for (int i = 0; i < mapa.length; i++) {
            System.out.println((i + 1) + " dia: " + mapa[i]);

        }
         */
        // leerArchivo();
    }

    public static synchronized ArrayList<Double> verMapaHoras(int tarjeta, String tabla) throws Exception {
        String consulta = "select dia, sum(cast(diferencia as time))/10., sum(cast(dh.al_50 as time))/10., sum(cast(dh.al_100 as time))/10.,dh.tipo_dia\n"
            + "from " + tabla + " dh\n"
            + "where tarjeta = " + tarjeta + "\n"
            + "group by dia, tipo_dia";

        AdaptadorMySQL datos = new AdaptadorMySQL();

        ArrayList<Double> salida = new ArrayList();

        ResultSet resultados = datos.consultar(consulta);

        // int cols = resultados.getMetaData().getColumnCount();
        //System.err.println(consulta);
        Mes mes = new Mes(Lector.ANIO, Lector.MES, "Varios.verMapaHoras");

        double[] info = new double[mes.getDias().size()];

        for (int i = 0; i < info.length; i++) {
            info[i] = -1;
        }

        while (resultados.next()) {

            int dia = resultados.getInt(1);

            double horas = procesaHora(resultados.getDouble(2));
            double _50 = procesaHora(resultados.getDouble(3));
            double _100 = procesaHora(resultados.getDouble(4));

            info[dia - 1] = horas;

        }

        datos.cerrar();

        for (int i = 0; i < info.length; i++) {
            double d = info[i];

            if (info[i] < 0) {
                salida.add(0.0);
            } else {
                salida.add(info[i]);
            }

        }

        return salida;
    }

    public static String getSelectAgentes() {
        String salida = "<select id=\"agenteTarjeta\">";

        try {

            AdaptadorMySQL datos = new AdaptadorMySQL();

            datos.consultar("select tarjeta, nombres from agentes where horas_extras = 1 order by nombres");

            while (datos.resultados.next()) {
                salida += "<option value=\"" + datos.resultados.getString("tarjeta") + "\">"
                    + datos.resultados.getString("nombres") + "</option>\n";
            }

        } catch (Exception e) {

        }

        return salida + "</select>";
    }

    public static String infoAConsulta(String tabla, String prim, String info) {

        StringTokenizer stk = new StringTokenizer(info, "\t");

        String consulta = "INSERT INTO " + tabla + " VALUES('" + prim.replace(':', '0') + "', ";

        consulta += stk.nextToken() + ", ";
        consulta += stk.nextToken() + ", ";
        consulta += stk.nextToken() + ", '";
        consulta += stk.nextToken() + "', '";
        consulta += stk.nextToken() + "', '";
        consulta += stk.nextToken() + "', '";
        consulta += stk.nextToken() + "', '";
        consulta += stk.nextToken() + "', ";
        consulta += stk.nextToken() + ");";

        return consulta;

    }

    public static void leerArchivo() {
        String archivo = "C:\\Documents and Settings\\Administrador\\Escritorio\\regihi2.csv";
        try {
            /*   BufferedReader br = new BufferedReader(new FileReader(new File(archivo)));

             String linea = br.readLine();

             for (int i = 0; i < 500000; i++) {
             br.readLine();

             }*/
            LectorArchivo lector = new LectorArchivo(archivo);

            String linea = lector.leerLinea(500000);

            while (linea != null) {

                procesarLinea(linea);
                System.out.println(linea);

                linea = lector.leerLinea(1);
            }

        } catch (Exception e) {

        }
    }

    public static void procesarLinea(String linea) {
        StringTokenizer stk = new StringTokenizer(linea, ";");

        while (stk.hasMoreTokens()) {
            System.out.print(stk.nextToken() + "\t");
        }

    }

    public static String crearConsultaMarcacion(String tabla, int tarjeta,
        String fecha, String hora, int codigo) {
        int legajo = 0;
        String agente = "";

        AdaptadorMySQL datos = new AdaptadorMySQL();

        datos.consultar("select * from " + tabla + " where tarjeta = " + tarjeta);

        try {
            datos.resultados.next();

            legajo = datos.resultados.getInt("legajo");
            agente = datos.resultados.getString("agente");

        } catch (Exception e) {

        }

        String id = tarjeta + fecha.substring(fecha.length() - 2) + hora.substring(0, 2) + ""
            + hora.substring(3, 5) + codigo;
        String salida = "INSERT INTO " + tabla + " VALUES(" + id + ", "
            + legajo + ", '" + agente + "', " + tarjeta + ", '" + fecha + "', '"
            + hora + "', 1, " + codigo + ", '" + new Fecha().getFechaMySQL() + "', 2);";

        return salida;
    }

    public static String getSelectCodigo(int codigo, String nombre) {
        String salida = "<select id=" + nombre + ">\n";

        if (codigo == 20) {
            salida += "<option value=20 selected>Entrada\n";
            salida += "<option value=21>Salida\n";
        }

        if (codigo == 21) {
            salida += "<option value=20>Entrada\n";
            salida += "<option value=21 selected>Salida\n";
        }

        return salida + "</select>";
    }

    public static String getSelectTipoMarcacion(int codigo, String nombre) {

        String salida = "<select id='" + nombre + "'>";
        String[] tipos = {"Leida", "Modificada", "Creada", "--------------", "Ignorada"};

        for (int i = 0; i < tipos.length; i++) {
            salida += "<option value='" + codigo + "' ";
            if (codigo == i) {
                salida += "selected";
            }
            salida += ">" + tipos[i] + "\n";

        }

        return salida;
    }

    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static double procesaHora(double hora) {
        double salida = Math.floor(hora / 1000.);
        double remanente = (double) (((int) hora) % 1000);

        if (hora < 0) {
            return 0.0;
        }

        if (remanente == 300) {
            salida += 0.5;
        }

        if (remanente == 600) {
            salida += 1;
        }

        return salida;
    }

    public static String verNumero(double v) {

        if (Math.floor(v) == v) {
            return new Double(v).intValue() + "";
        }

        return v + "";

    }

    public static String cadena() {
        String cadena = " <div class=\"row\">";
        cadena += "                <div class=\"col-1\">";
        cadena += "                    Dia";
        cadena += "                </div>";
        cadena += "                <div class=\"col-2\">";
        cadena += "                    Entrada";
        cadena += "                </div>";
        cadena += "                <div class=\"col-2\">";
        cadena += "                    Salida";
        cadena += "                </div>";
        cadena += "                <div class=\"col-2\">";
        cadena += "                    Entrada";
        cadena += "                </div>";
        cadena += "                <div class=\"col-2\">";
        cadena += "                    Salida";
        cadena += "                </div>";
        cadena += "                <div class=\"col-1\">";
        cadena += "                    Firma";
        cadena += "                </div>";
        cadena += "                <div class=\"col-2\">";
        cadena += "                    Horas";
        cadena += "                </div>";
        cadena += "            </div>";

        return cadena;
    }

    public static double getValor(String valor) {
        try {
            return Double.valueOf(valor);
        } catch (Exception e) {

        }
        return 0;
    }
}
