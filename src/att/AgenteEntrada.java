/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package att;

import java.util.ArrayList;
import util.AdaptadorMySQL;

/**
 *
 * @author Administrador
 */
public class AgenteEntrada {

    static AdaptadorMySQL datos;
    ArrayList<Agente> agentes;

    public static void main(String[] args) throws Exception {

        int[] tipoDias = {
            0, 0, 0, 0, 0, 1,
            1, 1, 1, 0, 0, 0, 1,
            1, 0, 0, 0, 0, 0, 1,
            1, 0
        };

        datos = new AdaptadorMySQL();

        int tarjeta = 20;

        String consulta = "select entrada from agentes where tarjeta = " + tarjeta;
        datos.consultar(consulta);

        datos.resultados.next();

        Hora entrada = new Hora(datos.resultados.getString(1));
        System.out.println(entrada);
        consulta = "select hora, day(fecha) from marcaciones_ene_2024 where tarjeta = "
                + tarjeta + " and codigo = 20 and tipo <> 4 order by fecha";
        datos.consultar(consulta);

        Hora temp;
        int dia;

        while (datos.resultados.next()) {

            temp = new Hora(datos.resultados.getString(1));
            dia = datos.resultados.getInt(2);

            System.out.println(dia + ": " + tipoDias[dia-1] + " : " + Hora.difHorasExacta(temp, entrada));

        }

    }

}
