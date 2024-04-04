/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author usuario
 */
public class Varios2 {

    public static void main(String[] args) {

        String TABLA_MARCACIONES = "marcaciones_nov_2017";

        String consulta = "select a.tarjeta, agente from " + TABLA_MARCACIONES + ", agentes a "
                + " where a.tarjeta in (select aa.tarjeta from agentes aa where aa.horas_extras = 1)  "
                //      + "and a.tarjeta = 998 "
                //+ "and reloj = 2 "
                //      + "and a.tarjeta in (444,998,1016,1039,1064,1130) "
                //      + " and id_encargado = 5 "
                //+ "and tarjeta in (select tarjeta from maquinistas) "
                + "group by a.tarjeta order by a.nombres";

       // System.out.println(consulta);

        Drawable d2 = () -> {
            System.out.println(consulta);
        };
        
        d2.draw();

    }

}

interface Drawable {

    void draw();
}
