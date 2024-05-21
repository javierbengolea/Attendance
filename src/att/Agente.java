/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package att;

import fabrica.DepartamentosConstantes;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import util.AdaptadorMySQL;
import general.Persona;
import personal.Encargado;

/**
 *
 * @author JavierPro
 */
public class Agente extends Persona {

    String nombre;
    int tarjeta;
    String legajo;
    Hora entrada;
    Hora salida;
    Hora deficit;
    Boolean horasExtras;
    Encargado encargado;
    Boolean activo;

    public HashMap<Integer, String> nombres = new LinkedHashMap<>();

    public Agente() {
        try {
            AdaptadorMySQL datos = new AdaptadorMySQL();

            datos.consultar("select tarjeta, nombres from agentes where activo = 1 order by nombres;");

            /*    int tarjeta;
             String nombre;
             */
            while (datos.resultados.next()) {

                tarjeta = datos.resultados.getInt(1);
                nombre = datos.resultados.getString(2);

                nombres.put(tarjeta, nombre);
                //   System.out.println(tarjeta + ": " + nombre);
            }
            datos.cerrar();
        } catch (SQLException e) {
        }
    }

    public String getNombre() {
        return nombre;
    }

    public int getTarjeta() {
        return tarjeta;
    }

    public Agente(int tarjeta, String nombre) {
        this.tarjeta = tarjeta;
        this.nombre = nombre;

    }

    public static HashMap<Integer, String> agentesHorasExtras() {

        HashMap<Integer, String> salida = new LinkedHashMap<>();
        try {
            AdaptadorMySQL datos = new AdaptadorMySQL();

            datos.consultar("select tarjeta, nombres from agentes where activo = 1 order by nombres;");

            int tarjeta;
            String nombre;

            while (datos.resultados.next()) {

                tarjeta = datos.resultados.getInt(1);
                nombre = datos.resultados.getString(2);

                salida.put(tarjeta, nombre);
                //   System.out.println(tarjeta + ": " + nombre);
            }

        } catch (SQLException e) {
        }

        return salida;
    }

    public void setTarjeta(int tarjeta) {
        this.tarjeta = tarjeta;
    }

    public void setSalida(Hora salida) {
        this.salida = salida;
    }

    public void setNombres(HashMap<Integer, String> nombres) {
        this.nombres = nombres;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setLegajo(String legajo) {
        this.legajo = legajo;
    }

    public void setHorasExtras(Boolean horasExtras) {
        this.horasExtras = horasExtras;
    }

    public void setEntrada(Hora entrada) {
        this.entrada = entrada;
    }

    public void setEncargado(Encargado encargado) {
        this.encargado = encargado;
    }

    public void setDeficit(Hora deficit) {
        this.deficit = deficit;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Hora getSalida() {
        return salida;
    }

    public HashMap<Integer, String> getNombres() {
        return nombres;
    }

    public String getLegajo() {
        return legajo;
    }

    public Boolean getHorasExtras() {
        return horasExtras;
    }

    public Hora getEntrada() {
        return entrada;
    }

    public Encargado getEncargado() {
        return encargado;
    }

    public Hora getDeficit() {
        return deficit;
    }

    public Boolean getActivo() {
        return activo;
    }

    public static void main(String[] args) {
        Agente agentes = new Agente();

        System.out.println(DepartamentosConstantes.OPP);

        System.out.println(agentes.nombres);
    }

    public static String getHoraEntrada(int tarjeta) {
        AdaptadorMySQL datos = new AdaptadorMySQL();

        String horaEntrada = "";

        datos.consultar("select entrada from agentes where tarjeta = " + tarjeta);

        try {
            datos.resultados.next();
            horaEntrada = datos.resultados.getString(1);
            datos.cerrar();
        } catch (SQLException e) {
            System.err.println(e);
        }

        return horaEntrada;
    }

    public static String[][] getPadronAgentes() {

        AdaptadorMySQL datos = new AdaptadorMySQL();

        String[][] agentes;
        ArrayList<String[]> vectAgentes = new ArrayList();

        datos.consultar("select * from agentes where activo = 1;");

        try {
            while (datos.resultados.next()) {
                String[] temp = new String[6];

                for (int i = 0; i < 6; i++) {
                    temp[i] = datos.resultados.getString(i + 1);
                }
                vectAgentes.add(temp);
            }
            datos.cerrar();
        } catch (SQLException e) {
            System.err.println(e);
        }

        agentes = new String[vectAgentes.size()][6];

        for (int i = 0; i < agentes.length; i++) {
            System.arraycopy(vectAgentes.get(i), 0, agentes[i], 0, agentes[0].length);
        }

        return agentes;
    }

    public static String getHoraSalida(int tarjeta) {
        AdaptadorMySQL datos = new AdaptadorMySQL();

        String horaSalida = "";

        datos.consultar("select salida from agentes where tarjeta = " + tarjeta);

        try {
            datos.resultados.next();
            horaSalida = datos.resultados.getString(1);
            datos.cerrar();
        } catch (SQLException e) {

        }
        return horaSalida;
    }

    public static String getNombres(int tarjeta) {
        AdaptadorMySQL datos = new AdaptadorMySQL();

        String nombres = "";

        datos.consultar("select nombres from agentes where tarjeta = " + tarjeta);

        try {
            datos.resultados.next();
            nombres = datos.resultados.getString(1);
            datos.cerrar();
        } catch (SQLException e) {
            System.err.println(tarjeta);
        }

        return nombres;
    }

    public static String getNombreMonotrib(int codigo) {
        AdaptadorMySQL datos = new AdaptadorMySQL();

        String nombres = "";

        datos.consultar("select nombres from monotrib where codigo = " + codigo);

        try {
            datos.resultados.next();
            nombres = datos.resultados.getString(1);
            datos.cerrar();
        } catch (SQLException e) {
            System.err.println(e + ": " + codigo);
        }
        return nombres;
    }

    public static String getNombre(int tarjeta) {
        String nombre = "";

        return nombre;
    }

    @Override
    public String toString() {

        return tarjeta + " - " + nombre;
    }

}
