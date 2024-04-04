/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package att;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Arrays;

import util.*;

public class Marcacion_1 implements java.io.Serializable {

//DECLARACI?N DE ATRIBUTOS
    String id = "";
    int legajo = 0;
    String agente = "";
    int tarjeta = 0;
    Fecha fecha;
    Hora hora;
    int reloj = 0;
    int codigo = 0;
    Fecha fechaProceso;
    int tipo = 0;

//CONSTANTES RELACIONALES
    public static final String MARCACION_TABLA = "marcaciones_" + Lector.MES_ABREV[Lector.MES - 1] + "_" + Lector.ANIO;
    public static final String CAMPO_ID_TABLA = "ID";
    public static final String CAMPO_LEGAJO_TABLA = "LEGAJO";
    public static final String CAMPO_AGENTE_TABLA = "AGENTE";
    public static final String CAMPO_TARJETA_TABLA = "TARJETA";
    public static final String CAMPO_FECHA_TABLA = "FECHA";
    public static final String CAMPO_HORA_TABLA = "HORA";
    public static final String CAMPO_RELOJ_TABLA = "RELOJ";
    public static final String CAMPO_CODIGO_TABLA = "CODIGO";
    public static final String CAMPO_FECHAPROCESO_TABLA = "FECHA_PROCESO";
    public static final String CAMPO_TIPO_TABLA = "TIPO";
    
    public static final int ANULADA = 4;

    public Marcacion_1() {

    }

    public Marcacion_1(String id, int legajo, String agente, int tarjeta, Fecha fecha, Hora hora, int reloj, int codigo, Fecha fechaProceso, int tipo) {
        this.id = id;
        this.legajo = legajo;
        this.agente = agente;
        this.tarjeta = tarjeta;
        this.fecha = fecha;
        this.hora = hora;
        this.reloj = reloj;
        this.codigo = codigo;
        this.fechaProceso = fechaProceso;
        this.tipo = tipo;
    }

    public Marcacion_1(int legajo, String agente, int tarjeta, Fecha fecha, Hora hora, int reloj, int codigo, Fecha fechaProceso) {
        this.legajo = legajo;
        this.agente = agente;
        this.tarjeta = tarjeta;
        this.fecha = fecha;
        this.hora = hora;
        this.reloj = reloj;
        this.codigo = codigo;
        this.fechaProceso = fechaProceso;
        this.tipo = 0;
        id = calcularId();
    }

    public String calcularId() {
        String id;
        id = new DecimalFormat("0000").format(tarjeta);
        id += new DecimalFormat("00").format(fecha.getDia());
        id += new DecimalFormat("00").format(hora.getHoras());
        //id += new DecimalFormat("00").format(hora.getMinutos());
        id += hora.getMinutoss();
        id += codigo;
        return id;
    }

    public String getId() {
        return id;
    }

    public int getLegajo() {
        return legajo;
    }

    public String getAgente() {
        return agente;
    }

    public int getTarjeta() {
        return tarjeta;
    }

    public Fecha getFecha() {
        return fecha;
    }

    public Hora getHora() {
        return hora;
    }

    public int getReloj() {
        return reloj;
    }

    public int getCodigo() {
        return codigo;
    }

    public Fecha getFechaProceso() {
        return fechaProceso;
    }

    public int getTipo() {
        return tipo;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLegajo(int legajo) {
        this.legajo = legajo;
    }

    public void setLegajo(String legajo) throws Exception {
        this.legajo =  Integer.parseInt(legajo);
    }

    public void setAgente(String agente) {
        this.agente = agente;
    }

    public void setTarjeta(int tarjeta) {
        this.tarjeta = tarjeta;
    }

    public void setTarjeta(String tarjeta) throws Exception {
        this.tarjeta = Integer.parseInt(tarjeta);
    }

    public void setFecha(Fecha fecha) {
        this.fecha = fecha;
    }

    public void setFecha(String fecha) throws Exception {
        this.fecha = new Fecha(fecha);
    }

    public void setHora(Hora hora) {
        this.hora = hora;
    }

    public void setHora(String hora) throws Exception {
        this.hora = new Hora(hora);
    }

    public void setReloj(int reloj) {
        this.reloj = reloj;
    }

    public void setReloj(String reloj) throws Exception {
        this.reloj = Integer.parseInt(reloj);
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setCodigo(String codigo) throws Exception {
        this.codigo = Integer.parseInt(codigo);
    }

    public void setFechaProceso(Fecha fechaProceso) {
        this.fechaProceso = fechaProceso;
    }

    public void setFechaProceso(String fechaProceso) throws Exception {
        this.fechaProceso = new Fecha(fechaProceso);
    }

    public Marcacion_1 setTipo(int tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(String tipo) throws Exception {
        this.tipo = Integer.parseInt(tipo);
    }
// GUARDAR EN LA BASE DE DATOS

    public synchronized void guardarEnBD(String usuario, String clave) throws Exception {
        AdaptadorMySQL datos = new AdaptadorMySQL(usuario, clave);

        String consulta = "INSERT INTO " + MARCACION_TABLA + " VALUES( '"
                + id + "', "
                + legajo + ", '"
                + agente + "', "
                + tarjeta + ", '"
                + fecha.getFechaMySQL() + "', '"
                + hora + "', "
                + reloj + ", "
                + codigo + ", '"
                + fechaProceso.getFechaMySQL() + "', "
                + tipo + ")";

        datos.actualizar(consulta);
        datos.cerrar();

    }

    public static String getEncabezadoConsultaInsert() {
        return "INSERT INTO " + MARCACION_TABLA + " ('id','legajo','agente','tarjeta','fecha','hora','reloj','codigo','fecha_proceso','tipo') VALUES ";
    }

    public String getConsultaInsert() {
        String consulta = "INSERT INTO " + MARCACION_TABLA + " VALUES( '"
                + id + "', "
                + legajo + ", '"
                + agente + "', "
                + tarjeta + ", '"
                + fecha.getFechaMySQL() + "', '"
                + hora + "', "
                + reloj + ", "
                + codigo + ", '"
                + fechaProceso.getFechaMySQL() + "', "
                + tipo + ")";
        return consulta;
    }

    public String getConsultaInsert(String tabla) {
        String consulta = "INSERT INTO " + tabla + " VALUES( '"
                + id + "', "
                + legajo + ", '"
                + agente + "', "
                + tarjeta + ", '"
                + fecha.getFechaMySQL() + "', '"
                + hora + "', "
                + reloj + ", "
                + codigo + ", '"
                + fechaProceso.getFechaMySQL() + "', "
                + tipo + ")";
        return consulta;
    }

    public synchronized void guardarEnBD() throws Exception {
        guardarEnBD("root", "admin");
    }

    public Marcacion_1 actualizarBD(String usuario, String clave) throws Exception {
        AdaptadorMySQL datos = new AdaptadorMySQL(usuario, clave);
        String consulta = "UPDATE " + MARCACION_TABLA + " SET \n";
        consulta += CAMPO_ID_TABLA + " = '" + getId() + "', \n";
        consulta += CAMPO_LEGAJO_TABLA + " = '" + getLegajo() + "', \n";
        consulta += CAMPO_AGENTE_TABLA + " = '" + getAgente() + "', \n";
        consulta += CAMPO_TARJETA_TABLA + " = '" + getTarjeta() + "', \n";
        consulta += CAMPO_FECHA_TABLA + " = '" + getFecha() + "', \n";
        consulta += CAMPO_HORA_TABLA + " = '" + getHora() + "', \n";
        consulta += CAMPO_RELOJ_TABLA + " = '" + getReloj() + "', \n";
        consulta += CAMPO_CODIGO_TABLA + " = '" + getCodigo() + "', \n";
        consulta += CAMPO_FECHAPROCESO_TABLA + " = '" + getFechaProceso() + "', \n";
        consulta += CAMPO_TIPO_TABLA + " = '" + getTipo() + "' \n";

        consulta += "WHERE " + CAMPO_ID_TABLA + " = '" + getId() + "';";

        datos.actualizar(consulta);
        return this;
    }

// LEER DE LA BASE DE DATOS
    public static synchronized Marcacion_1[] leerBD(String restriccion, String usuario, String clave) {

        Marcacion_1 marcacions[];
        java.util.ArrayList<Marcacion_1> vectorMarcacions = new java.util.ArrayList<>();

        try {
            AdaptadorMySQL datos = new AdaptadorMySQL(usuario, clave);

            String consulta = "SELECT * FROM " + MARCACION_TABLA + " WHERE " + restriccion + ";";

            System.err.println(consulta);

            datos.consultar(consulta);

            while (datos.resultados.next()) {
                vectorMarcacions.add(new Marcacion_1(datos.resultados.getString(CAMPO_ID_TABLA),
                        datos.resultados.getInt(CAMPO_LEGAJO_TABLA),
                        datos.resultados.getString(CAMPO_AGENTE_TABLA),
                        datos.resultados.getInt(CAMPO_TARJETA_TABLA),
                        new Fecha().setFechaMySQL(datos.resultados.getString(CAMPO_FECHA_TABLA)),
                        new Hora(datos.resultados.getString(CAMPO_HORA_TABLA)),
                        datos.resultados.getInt(CAMPO_RELOJ_TABLA),
                        datos.resultados.getInt(CAMPO_CODIGO_TABLA),
                        new Fecha().setFechaMySQL(datos.resultados.getString(CAMPO_FECHAPROCESO_TABLA)),
                        datos.resultados.getInt(CAMPO_TIPO_TABLA)));
            }

            datos.cerrar();

            marcacions = new Marcacion_1[vectorMarcacions.size()];

            for (int i = 0; i < marcacions.length; i++) {
                marcacions[i] = ((Marcacion_1) vectorMarcacions.get(i));
            }

            vectorMarcacions.clear();

            return marcacions;

        } catch (Exception e) {
            System.err.println("Excepci?n leyendo de la BD: " + e.getMessage());
            return null;
        }
    }

    /**
     * Lee todas las instancias de la clase en la base de datos requiere la
     * validacion
     */
    public static synchronized Marcacion_1[] leerBD(String usuario, String clave) throws Exception {
        return leerBD("1=1", usuario, clave);
    }

    public static String seleccionarMarcacion(String marcs, String usuario, String clave) {
        String cadena = "<SELECT NAME=\"" + marcs + "\">\n";

        cadena += "<OPTION VALUE=\"\"> ---\n";
        Marcacion_1[] marcacions = leerBD("1=1 ORDER BY LEGAJO", usuario, clave);

        for (Marcacion_1 marcacion : marcacions) {
            cadena += "<OPTION VALUE=\"" + marcacion.toStringArray()[0] + "\">" + marcacion.toStringArray()[1] + "\n";
        }
        cadena += "</SELECT>";
        return cadena;
    }

    public static String seleccionarMarcacion(String marcs, Marcacion_1[] marcacions) {
        String cadena = "<SELECT NAME=\"" + marcs + "\">\n";

        for (int i = 0; i < marcacions.length; i++) {
            cadena += "<OPTION VALUE=\"" + marcacions[i].toStringArray()[0] + "\">"
                    + marcacions[i].toStringArray()[1] + "\n";
        }
        cadena += "</SELECT>";
        return cadena;
    }

    public String toString() {
        return "\nid: " + id
                + "\nlegajo: " + legajo
                + "\nagente: " + agente
                + "\ntarjeta: " + tarjeta
                + "\nfecha: " + fecha
                + "\nhora: " + hora
                + "\nreloj: " + reloj
                + "\ncodigo: " + codigo
                + "\nfechaProceso: " + fechaProceso
                + "\ntipo: " + tipo;
    }

    public String[] toStringArray() {

        String[] marcacionStringArray = {
            id,
            legajo + "",
            agente,
            tarjeta + "",
            fecha + "",
            hora + "",
            reloj + "",
            codigo + "",
            fechaProceso + "",
            tipo + ""
        };

        return marcacionStringArray;
    }

    public static Marcacion_1 leerMarcaPorId(String id, String usuario, String clave) {
        return leerBD(CAMPO_ID_TABLA + " = '" + id + "'", usuario, clave)[0];
    }

    public static void crearMarcaciones() throws Exception {

        ArrayList<String[]> diasHorarios = new ArrayList<>();

        System.out.println(diasHorarios);
        int legajo = 1173;
        String nombre = "NIEVA, GERMAN";

        diasHorarios.add(new String[]{"1", "07:00", "13:00"});
        diasHorarios.add(new String[]{"1", "15:00", "20:00"});
        diasHorarios.add(new String[]{"2", "07:00", "13:00"});
        diasHorarios.add(new String[]{"2", "15:00", "20:00"});
        diasHorarios.add(new String[]{"4", "08:00", "13:00"});
        diasHorarios.add(new String[]{"4", "15:00", "20:00"});
        diasHorarios.add(new String[]{"5", "07:00", "13:00"});
        diasHorarios.add(new String[]{"5", "15:00", "20:00"});
        diasHorarios.add(new String[]{"6", "07:00", "13:00"});
        diasHorarios.add(new String[]{"6", "15:00", "20:00"});
        diasHorarios.add(new String[]{"7", "07:00", "14:00"});

        for (int i = 0; i < diasHorarios.size(); i++) {

            //System.out.print((i+1) + ": \n");
            String[] temp = diasHorarios.get(i);

            Marcacion_1 marcaEntrada = new Marcacion_1(legajo, nombre, legajo, new Fecha(temp[0] + "/10/2020"),
                    new Hora(temp[1]), 1, 20, new Fecha()).setTipo(2);

            Marcacion_1 marcaSalida = new Marcacion_1(legajo, nombre, legajo, new Fecha(temp[0] + "/10/2020"),
                    new Hora(temp[2]), 1, 21, new Fecha()).setTipo(2);

            /*System.out.println(Arrays.toString(marcaEntrada.toStringArray()));
                System.out.println(Arrays.toString(marcaSalida.toStringArray()));*/
            System.out.println(marcaEntrada.getConsultaInsert() + ";");
            System.out.println(marcaSalida.getConsultaInsert() + ";");

        }

    }

    public static void main(String[] args) throws Exception {

        //      Marcacion[] marcacion = leerBD("tarjeta = 1058 and tipo <> 4 order by fecha, hora, codigo","root", "admin");

        /*for (int i = 0; i < marcacion.length / 2; i++) {
            System.out.print("id: " + marcacion[i].calcularId());
            System.out.println(marcacion[i]);
        }*/
        //      for(Marcacion marca: marcacion){
//            System.out.println(marca);
        //    }
        /*Marcacion marca = leerMarcaPorId("9882214062143", "root", "Admin_2020");

        System.out.println(marca.getFecha());*/
        ArrayList<int[]> diasHorarios = new ArrayList<>();
        /*
            diasHorarios.add(new int[]{1,15,19});
        diasHorarios.add(new int[]{2,15,19});
        diasHorarios.add(new int[]{3,15,19});
        
        diasHorarios.add(new int[]{5,6,12});
        
        diasHorarios.add(new int[]{7,15,19});
        diasHorarios.add(new int[]{8,15,19});
        diasHorarios.add(new int[]{9,15,19});
        diasHorarios.add(new int[]{10,15,19});
        
        diasHorarios.add(new int[]{12,6,12});
        
        diasHorarios.add(new int[]{14,15,19});
        diasHorarios.add(new int[]{15,5,12});
        diasHorarios.add(new int[]{16,15,19});
        diasHorarios.add(new int[]{17,15,19});
        
        diasHorarios.add(new int[]{19,6,12});
        
        diasHorarios.add(new int[]{21,15,19});
        diasHorarios.add(new int[]{22,15,19});
        diasHorarios.add(new int[]{23,15,19});
        diasHorarios.add(new int[]{24,15,19});
        diasHorarios.add(new int[]{25,15,19});
        
        diasHorarios.add(new int[]{26,6,12});
        
        diasHorarios.add(new int[]{28,15,19});
        diasHorarios.add(new int[]{29,15,19});
        diasHorarios.add(new int[]{30,15,19});
        
        
         */

        System.out.println(diasHorarios);
        int legajo = 1126;
        String nombre = "GONZALEZ MAXIMILIANO";

        // diasHorarios.add(new int[]{11, 7, 19});
        diasHorarios.add(new int[]{14, 7, 19});
        diasHorarios.add(new int[]{16, 7, 18});
        diasHorarios.add(new int[]{17, 7, 18});
        //diasHorarios.add(new int[]{18, 7, 19});

        diasHorarios.add(new int[]{19, 6, 12});
        diasHorarios.add(new int[]{21, 7, 18});
        diasHorarios.add(new int[]{22, 7, 18});
        //diasHorarios.add(new int[]{23, 7, 19});

        /* diasHorarios.add(new int[]{9, 7, 19});
        diasHorarios.add(new int[]{10, 7, 19});
        diasHorarios.add(new int[]{11, 7, 19});*/
        //diasHorarios.add(new int[]{30,15,19});
        for (int i = 0; i < diasHorarios.size(); i++) {

            //System.out.print((i+1) + ": \n");
            int[] temp = diasHorarios.get(i);

            Marcacion_1 marcaEntrada = new Marcacion_1(legajo, nombre, legajo, new Fecha(temp[0] + "/10/2020"),
                    new Hora(new DecimalFormat("00").format(temp[1]) + ":00"), 1, 20, new Fecha()).setTipo(2);

            Marcacion_1 marcaSalida = new Marcacion_1(legajo, nombre, legajo, new Fecha(temp[0] + "/10/2020"),
                    new Hora(new DecimalFormat("00").format(temp[2]) + ":00"), 1, 21, new Fecha()).setTipo(2);

            /*System.out.println(Arrays.toString(marcaEntrada.toStringArray()));
                System.out.println(Arrays.toString(marcaSalida.toStringArray()));*/
 /*   System.out.println(marcaEntrada.getConsultaInsert() + ";");
            System.out.println(marcaSalida.getConsultaInsert() + ";");*/
        }

        //System.out.println(new Hora("14:28").getMinutos());
        crearMarcaciones();

    }
}
