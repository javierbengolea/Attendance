/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package att;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

/**
 *
 * @author javier
 */
public class Hora_1 {

    int horas;
    int minutos;
    int segundos;
    int tipo;
    int valor;
    
    static final int FRACCION = 1800;

    public final int BASICA = 0;
    public final int CON_SEGUNDOS = 0;

    public Hora_1() {
        this.horas = 0;
        this.minutos = 0;
        this.segundos = 0;
        this.tipo = 0;
    }

    public Hora_1(String hora) {
        try {

            StringTokenizer stk = new StringTokenizer(hora, ":");

            /*horas = new Integer(hora.substring(0, 2));
             minutos = new Integer(hora.substring(3, 5));            */
            horas = new Integer(stk.nextToken());
            minutos = new Integer(stk.nextToken());

            valor = segundos + (minutos * 60) + (horas * 3600);
            
            if(hora.charAt(0) == '-')
                valor *= -1;

        } catch (Exception e) {
            this.horas = 0;
            this.minutos = 0;
            this.segundos = 0;
            this.tipo = 0;
        }
    }

    public Hora_1(int valor) {
       

        horas = valor / (3600);        

        minutos = (valor % 3600)/60;
        
           //      segundos = valor % (60 * 24);
    }

    public static void main(String[] args) throws Exception {

        GregorianCalendar hora = new GregorianCalendar();
        Hora_1 ahora = new Hora_1();

        ahora.setMinutos(hora.get(Calendar.MINUTE));

        ahora.setHoras(hora.get(Calendar.HOUR_OF_DAY));

        System.out.println(hora.get(Calendar.HOUR_OF_DAY));

        System.out.println(ahora);
        
        System.out.println(new Hora_1(difHoras(new Hora_1("19:08"), new Hora_1("16:39"))));
        
        System.out.println(new Hora_1(difHorasExacta(ahora, new Hora_1("14:00"))));

        /*System.out.println(new Hora(ahora.getValor()));

        System.out.println("1899-12-30 05:57:00.000000".substring(11, 16));

        System.out.println(new Hora("00:45").getValor());*/

    }

    @Override
    public String toString() {

        DecimalFormat formato = new DecimalFormat("00");

        if (getValor() >= 0) {
            return formato.format(horas) + ":" + formato.format(minutos);// + " - " + this.getValor();
        } else {
            return "-" + (formato.format(-horas) + ":" + formato.format(-minutos));
        }
    }

    public void setHoras(int horas) {
        this.horas = horas;
    }

    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }

    public void setSegundos(int segundos) {
        this.segundos = segundos;
    }

    public int getHoras() {
        return horas;
    }

    public String getHorass() {
        if (horas < 10) {
            return "0" + horas;
        }
        return "" + horas;
    }

    public String getMinutoss() {
        if (minutos < 10) {
            return "0" + minutos;
        }
        return "" + minutos;
    }

    public int getMinutos() {
        return minutos;
    }

    public int getSegundos() {
        return segundos;
    }

    public int getValor() {
        valor = segundos + (minutos * 60) + (horas * 3600);
        return valor;
    }

    public static int difHoras(Hora_1 hora1, Hora_1 hora2) {
        int dif1 = hora1.getValor() - hora2.getValor();
        //System.out.println("d: " + dif1);
         int dif2 = (dif1 / FRACCION) * FRACCION;

         return dif2;
       /*int dif1 = hora1.getValor() - hora2.getValor();       
        return new Hora(dif1).getValor();*/
    }

    public static int difHorasMedia(Hora_1 hora1, Hora_1 hora2) {
        int dif1 = hora1.getValor() - hora2.getValor();
        
        int dif2 = (dif1 / FRACCION) * FRACCION;

        return dif2;
    }

    public static int difHorasExacta(Hora_1 hora1, Hora_1 hora2) {
        int dif1 = hora1.getValor() - hora2.getValor();
        //int dif2 = (dif1/FRACCION)*FRACCION;
        return new Hora_1(dif1).getValor();
    }

}
