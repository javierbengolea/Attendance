/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package att;

import java.io.File;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringTokenizer;
import javax.swing.UnsupportedLookAndFeelException;
import util.AdaptadorMySQL;
import util.Fecha;

import util.Varios;

/**
 *
 * @author javier
 */
public class Lector_1 {

    static String DRIVER_MYSQL = "com.mysql.jdbc.Driver";
    static String URL_MYSQL = "jdbc:mysql://localhost:3306/rrhh";
    static String USER_MYSQL = "root";
    static String PASS_MYSQL = "Admin2020";

    // static int TOPE = 1800;
    static int TOPE = 1800;
    static int TOPE_HORAS = 1800 * 2 * 3;

    public static final int MES = 4;
    public static final int ANIO = 2023;

    /**
     * Mes
     */
    public static final String[] MES_ABREV = {
        "ene", "feb", "mar", "abr", "may", "jun", "jul", "ago", "sep", "oct", "nov", "dic"
    };

    public static final String TABLA_MARCACIONES = String.format("marcaciones_%s_%s", MES_ABREV[MES - 1], ANIO);
    public static final String TABLA_DETALLE_HORAS_EXTRAS = "detalle_horas_extras_" + MES_ABREV[MES - 1] + "_" + ANIO + "";
    public static final String TABLA_HORAS_EXTRAS = "horas_extras_" + MES_ABREV[MES - 1] + "_" + ANIO + "";

    public static final int TODOS = 0;

    static AdaptadorMySQL datos;

    static {

        datos = new AdaptadorMySQL();

    }

    static ArrayList<Irregularidad> irregu;
    static HashMap<Integer, String> emp;
    static HashMap<Integer, String> entradas;
    static HashMap<Integer, String> salidas;
    static HashMap<Integer, String> deficits;
    static ArrayList<TDO> marcas;
    static ArrayList<Verificacion> verif;

    static ArrayList<Integer> agentes;

    static {
        try {
            agentes = getAgentesMA(Agente.TODOS);
//            agentes = getAgentesMA(new int[]{974});
        } catch (Exception e) {
            agentes = new ArrayList<>();
            System.err.println(e);
        }

        try {
            emp = getEmpleados();
        } catch (Exception e) {
            emp = new HashMap<>();
            System.err.println(e);
        }

        try {
            entradas = getEntradas();
        } catch (Exception e) {
            entradas = new HashMap<>();
            System.err.println(e);
        }

        try {
            salidas = getSalidas();
        } catch (Exception e) {
            salidas = new HashMap<>();
        }

        try {
            deficits = getDeficitHoras();
        } catch (Exception e) {
            deficits = new HashMap<>();
        }

        try {
            marcas = getMarcas();
        } catch (Exception e) {
            marcas = new ArrayList<>();
        }

        try {
            verif = getVerificaciones();
        } catch (Exception e) {
            verif = new ArrayList<>();
        }

        try {
            irregu = new ArrayList<>();
        } catch (Exception e) {

        }
    }

    public static void main(String[] args) {

        try {
            ArrayList dias = getDias();
            // System.err.println(dias);
            //ArrayList<Integer> dias = new ArrayList();
            //dias.add(2);

            ArrayList tipoDias = getTipoDias();

            int tarjeta;

            ArrayList<String> consultass = new ArrayList<>();
            ArrayList<String> temp;

            for (int i = 0; i < agentes.size(); i++) {
                tarjeta = agentes.get(i);
                Hora entrada = new Hora(entradas.get(tarjeta));
                String defHora = deficits.get(tarjeta);

                if (tarjeta > 0) {
                    temp = getMarcacionesIndividual_Nueva(dias, tarjeta, tipoDias, defHora, entrada);
                    for (int j = 0; j < temp.size(); j++) {
                        consultass.add(temp.get(j));
                    }
                }
            }

            int largo = consultass.size();

            StringBuilder sb = new StringBuilder();

            String consultas_detalle = "INSERT INTO " + TABLA_DETALLE_HORAS_EXTRAS + " VALUES";
            String consultas_hora = "INSERT INTO " + TABLA_HORAS_EXTRAS + " VALUES";

            int largo_cabecera_det = ("INSERT INTO " + TABLA_DETALLE_HORAS_EXTRAS + " VALUES").length();
            int largo_cabecera_hor = ("INSERT INTO " + TABLA_HORAS_EXTRAS + " VALUES").length();

            for (int i = 0; i < largo; i++) {

                String st_temp = consultass.get(i);

                if (st_temp.startsWith("INSERT INTO detalle")) {
                    //  System.out.println(st_temp + "\n" + st_temp.substring(48, st_temp.length()-1));
                    consultas_detalle += st_temp.substring(largo_cabecera_det, st_temp.length() - 1) + ",\n";
                }
                if (st_temp.startsWith("INSERT INTO horas")) {
                    //  System.out.println(st_temp + "\n" + st_temp.substring(48, st_temp.length()-1));
                    consultas_hora += st_temp.substring(largo_cabecera_hor, st_temp.length() - 1) + ",\n";
                }

                // datos.actualizar(consultass.get(i));
                sb.append(st_temp).append("\n");

            }

            consultas_detalle = consultas_detalle.substring(0, consultas_detalle.length() - 2) + ";";
            consultas_hora = consultas_hora.substring(0, consultas_hora.length() - 2) + ";";

            //new VerConsultas(consultas_detalle, consultas_hora).setVisible(true);
            System.out.println(consultas_detalle);
            System.out.println(consultas_hora);

            getMarcacionesIndividuales_();

            String consultaI = crearConsultaIrregularidades(irregu);

            ArrayList<ArrayList<String>> arrayMI = new ArrayList<>();
            ArrayList<String> tempo;

            datos = new AdaptadorMySQL();

            datos.consultar(consultaI);

            while (datos.resultados.next()) {
                tempo = new ArrayList<>();

                for (int i = 0; i < 7; i++) {
                    tempo.add(datos.resultados.getString(i + 1));

                }
                arrayMI.add(tempo);
            }

            try {
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ("Windows".equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {

            }
            //</editor-fold>
            if (irregu.size() > 0) {
                new ManejadorIrregularidades(arrayMI).setVisible(true);
            }
        } catch (Exception e) {
            ArrayList<StackTraceElement> stea = new ArrayList<>();

            stea.addAll(Arrays.asList(e.getStackTrace()));

            System.out.println(stea);
        }

    }

    public static String crearConsultaIrregularidades(ArrayList<Irregularidad> irregus) {
        String salida;

        ArrayList<Integer> tarjetas = new ArrayList<>();
        ArrayList<Integer> dias = new ArrayList<>();
        Irregularidad temp;
        HashMap<Integer, ArrayList<Integer>> agenteDias = new HashMap<>();
        ArrayList<Integer> auxT = new ArrayList<>();

        int tarjeta;
        String aux = "";

        for (int i = 0; i < irregus.size(); i++) {
            temp = irregus.get(i);
            tarjeta = temp.agente.getTarjeta();
            if (!agenteDias.containsKey(tarjeta)) {
                agenteDias.put(tarjeta, new ArrayList<>());
                auxT.add(tarjeta);
            }

            agenteDias.get(tarjeta).add(temp.fecha.getDia());

        }

        for (int i = 0; i < auxT.size(); i++) {
            tarjeta = auxT.get(i);
            aux += "select id, tarjeta, agente, fecha, hora, codigo, tipo from " + TABLA_MARCACIONES + " where tarjeta = "
                    + tarjeta + " and tipo <> 4 and day(fecha) in " + agenteDias.get(tarjeta).toString().
                            replace("[", "(").replace("]", ")") + " union ";
        }

        int leng = aux.length();
        System.err.println("aux: " + aux);
        salida = aux.substring(0, leng - 7) + ";";//" order by tarjeta, agente, fecha, hora, codigo; ";

        return salida;
    }

    public static ArrayList<Irregularidad> getIrregularidades() throws Exception {

        getMarcacionesIndividuales_();

        String consultaI = crearConsultaIrregularidades(irregu);

        System.err.println("Consulta Irregularidades");
        System.out.println(consultaI);
        // System.exit(0);

        ArrayList<ArrayList<String>> arrayMI = new ArrayList<>();
        ArrayList<String> tempo;

        datos = new AdaptadorMySQL();

        datos.consultar(consultaI);

        while (datos.resultados.next()) {
            tempo = new ArrayList<>();

            for (int i = 0; i < 7; i++) {
                tempo.add(datos.resultados.getString(i + 1));
            }
            arrayMI.add(tempo);
        }

        System.out.println(arrayMI);
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException
                | IllegalAccessException
                | InstantiationException
                | UnsupportedLookAndFeelException e) {

        }
        //</editor-fold>
        if (irregu.size() > 0) {
            //  new ManejadorIrregularidades(arrayMI).setVisible(true);
        }
        return irregu;
    }

    public static HashMap<Integer, String> getEmpleados() throws Exception {
        HashMap<Integer, String> salida = new HashMap<>();

        datos = new AdaptadorMySQL();

        String consulta = "select tarjeta, nombres from agentes";

        datos.consultar(consulta);

        while (datos.resultados.next()) {
            salida.put(datos.resultados.getInt(1),
                    datos.resultados.getString(2));
        }

        return salida;
    }

    public static String[] leerMarcacion(String id) throws Exception {

        String[] salida = new String[10];

        AdaptadorMySQL data = new AdaptadorMySQL();

        //String consulta = "select * from " + TABLA_MARCACIONES + " where id = '" + id + "';";
        ResultSet _datos;
        _datos = data.resultados;

        _datos.next();

        salida[0] = _datos.getString(1) + "";
        salida[1] = _datos.getInt(2) + "";
        salida[2] = _datos.getString(3);
        salida[3] = _datos.getInt(4) + "";
        salida[4] = _datos.getDate(5) + "";
        salida[5] = _datos.getTime(6) + "";
        salida[6] = _datos.getInt(7) + "";
        salida[7] = _datos.getInt(8) + "";
        salida[8] = _datos.getDate(9) + "";
        salida[9] = _datos.getInt(10) + "";

        _datos.close();

        return salida;
    }

    public static ArrayList<String[]> leerMarcaciones() throws Exception {

        ArrayList<String[]> salidass = new ArrayList<>();

        String[] salida;

        AdaptadorMySQL data = new AdaptadorMySQL();

        String consulta = String.format("select * from %s where tipo <> %s;", TABLA_MARCACIONES, Marcacion.ANULADA);
        //JOptionPane.showMessageDialog(null, consulta);

        data.consultar(consulta);

        ResultSet datae = data.resultados;

        while (datae.next()) {

            salida = new String[10];
            for (int i = 0; i < salida.length; i++) {
                salida[i] = new String();

            }

            salida[0] = datae.getString(1) + "";
            salida[1] = datae.getInt(2) + "";
            salida[2] = datae.getString(3);
            salida[3] = datae.getInt(4) + "";
            salida[4] = datae.getDate(5) + "";
            salida[5] = datae.getTime(6) + "";
            salida[6] = datae.getInt(7) + "";
            salida[7] = datae.getInt(8) + "";
            salida[8] = datae.getDate(9) + "";
            salida[9] = datae.getInt(10) + "";

            salidass.add(salida);
        }

        datae.close();

        return salidass;
    }

    public static HashMap<Integer, Hora> getAgenteEntrada() {
        HashMap<Integer, Hora> agenteEntrada = new HashMap<>();

        String consulta = "select tarjeta, entrada from agentes/* where horas_extras = 1*/";

        datos = new AdaptadorMySQL();

        try {
            datos.consultar(consulta);

            while (datos.resultados.next()) {

                int tarjeta = datos.resultados.getInt("tarjeta");
                Hora entrada = new Hora(datos.resultados.getString("entrada"));

                agenteEntrada.put(tarjeta,
                        entrada);
                System.out.println(tarjeta);
            }
        } catch (SQLException e) {
            System.err.println("Error en getEntrada: " + e);
        }

        return agenteEntrada;
    }

    public static int leerTarjetaMarcacion(String id) throws Exception {

        int salida;

        String consulta = String.format("select tarjeta from %s where id = '%s';", TABLA_MARCACIONES, id);

        ResultSet resultados;
        resultados = datos.consultar(consulta);

        resultados.next();
        salida = resultados.getInt(1);
        datos.cerrar();

        return salida;
    }

    public static ArrayList getMarcacionesIndividuales_() throws Exception {

        try {
            verif = getVerificaciones();
        } catch (Exception e) {
            verif = new ArrayList<>();
        }

        AdaptadorMySQL data = new AdaptadorMySQL();

        String consulta;

        consulta = String.format("select codigo from %s where tipo <> 4 and fecha < '%s';", TABLA_MARCACIONES, new Fecha().getFechaMySQL());

        int cantMarcaciones = 0;
        int cantEntradas = 0;
        int cantSalidas = 0;

        ResultSet datos = data.consultar(consulta);
        ResultSetMetaData meta = datos.getMetaData();

        int cols = meta.getColumnCount();

        int temp;

        while (datos.next()) {

            temp = datos.getInt(1);

            if (temp == 20) {
                cantEntradas++;
            } else {
                cantSalidas++;
            }
            cantMarcaciones++;
        }

        ArrayList<Integer> dias = new ArrayList<>();

        consulta = "select day(fecha) from "
                + TABLA_MARCACIONES
                + " where month(fecha) =  "
                + MES + "  and tipo <> 4 "
                + "  and fecha < '"
                + new Fecha().getFechaMySQL() + "' "
                + " group by fecha order by fecha";

        datos = data.consultar(consulta);

        while (datos.next()) {
            //dias.add(datos.getDate(1) + " 00:00:00");
            dias.add(datos.getInt(1));
        }

        int cantAgentes = agentes.size();
        int cantDias = dias.size();

        String consultaTemp;

        int agenteTemp;
        String fechaTemp;
        int marcaciones;
        int suma;
        String id;

        ArrayList<String> unaMarcacion = new ArrayList<>();
        ArrayList<String> dosMarcaciones = new ArrayList<>();
        ArrayList<String> variasMarcaciones = new ArrayList<>();
        ArrayList<String> vacias = new ArrayList<>();

        for (int i = 0; i < cantDias; i++) {

            //if (dias.get(i) >= 10) {
            //  fechaTemp = ANIO + "-0" + MES + "-" + dias.get(i);
            fechaTemp = new Fecha(dias.get(i), MES, ANIO).getFechaMySQL();

            for (int j = 0; j < cantAgentes; j++) {

                CSI csi = getCsi(agentes.get(j), dias.get(i));

                agenteTemp = agentes.get(j);

                consultaTemp = "select count(codigo), sum(codigo), min(id) from " + TABLA_MARCACIONES + " where tarjeta = " + agenteTemp
                        + " and fecha = \"" + fechaTemp + "\" and tipo <> 4 ORDER BY TARJETA;";

                //datos = data.consultar(consultaTemp);
                //datos.next();

                /*marcaciones = datos.getInt(1);
                 suma = datos.getInt(2);
                 id = datos.getString(3);*/
                marcaciones = csi.getCuenta();
                suma = csi.getSuma();
                id = csi.getId();

                if (marcaciones == 1) {
                    unaMarcacion.add(id + "");
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

                if (marcaciones == 1) {
                    System.out.println(fechaTemp + "; " + agenteTemp + "; " + emp.get(agenteTemp) + "; " + marcaciones + "; " + suma + "; " + id);
                    try {
                        //irregu.add(new Irregularidad(new Agente(agenteTemp, emp.get(agenteTemp)), new Fecha().setFecha_AAAA_MM_DD(fechaTemp.substring(0, 10), '-'), suma, Irregularidad.UNA_MARCACION));
                    } catch (Exception e) {
                    }
                }

                if (marcaciones == 2 && suma != 41) {
                    System.out.println(fechaTemp + "; " + agenteTemp + "; " + emp.get(agenteTemp) + "; " + marcaciones + "; " + suma);
                    try {
                        irregu.add(new Irregularidad(new Agente(agenteTemp, emp.get(agenteTemp)), new Fecha().setFechaMySQL(fechaTemp), suma, Irregularidad.DOS_MARCACIONES_IGUAL));
                    } catch (Exception e) {
                        System.err.println("Marcaciones 2: " + e.getMessage() + "-" + fechaTemp);
                    }
                }

                if (marcaciones == 3) {
                    System.out.println(fechaTemp + "; " + agenteTemp + "; " + emp.get(agenteTemp) + "; " + marcaciones + "; " + suma);
                    try {
                        irregu.add(new Irregularidad(new Agente(agenteTemp, emp.get(agenteTemp)), new Fecha().setFechaMySQL(fechaTemp), suma, Irregularidad.TRES_MARCACIONES));
                    } catch (Exception e) {
                        System.err.println("Marcaciones 3:");
                    }
                }

                if (marcaciones == 4 && suma != 82) {
                    System.err.println(fechaTemp + "; " + agenteTemp + "; " + emp.get(agenteTemp) + "; " + marcaciones + "; " + suma);
                    String temp_st = String.format("%s; %s; %s; %s; %s", fechaTemp, agenteTemp, emp.get(agenteTemp),
                            marcaciones, suma);
                    System.out.println(temp_st);
                    try {
                        irregu.add(new Irregularidad(new Agente(agenteTemp, emp.get(agenteTemp)), new Fecha().setFechaMySQL(fechaTemp), suma, Irregularidad.CUATRO_MARCACIONES));
                    } catch (Exception e) {
                        System.err.println("Marcaciones 4: " + e.getMessage());
                    }
                }

                if (marcaciones == 5) {
                    System.err.println(fechaTemp + "; " + agenteTemp + "; " + emp.get(agenteTemp) + "; " + marcaciones + "; " + suma);
                    try {
                        irregu.add(new Irregularidad(new Agente(agenteTemp, emp.get(agenteTemp)), new Fecha().setFechaMySQL(fechaTemp), suma, Irregularidad.TRES_MARCACIONES));
                    } catch (Exception e) {
                        System.err.println("Marcaciones 5:");
                    }
                }

                if (marcaciones == 6 && suma != 123) {
                    String temp_st = String.format("%s; %s; %s; %s; %s", fechaTemp, agenteTemp, emp.get(agenteTemp),
                            marcaciones, suma);
                    System.out.println(temp_st);
                    try {
                        irregu.add(new Irregularidad(new Agente(agenteTemp, emp.get(agenteTemp)), new Fecha().setFechaMySQL(fechaTemp), suma, Irregularidad.CUATRO_MARCACIONES));
                    } catch (Exception e) {
                        System.err.println("Marcaciones 6: " + e.getMessage());
                    }
                }

                if (marcaciones > 6) {
                    System.out.println(fechaTemp + "; " + agenteTemp + "; " + emp.get(agenteTemp) + "; " + marcaciones + "; " + suma);
                    try {
                        irregu.add(new Irregularidad(new Agente(agenteTemp, emp.get(agenteTemp)), new Fecha().setFechaMySQL(fechaTemp), suma, Irregularidad.MUCHAS_MARCACIONES));
                    } catch (Exception e) {
                        System.err.println(new Agente(agenteTemp, emp.get(agenteTemp)));
                        System.err.println(emp);
                    }
                }
                datos.close();
            }

        }

        return unaMarcacion;
    }

    public static ArrayList getAgentes() throws Exception {

        ArrayList salida = new ArrayList();

        datos = new AdaptadorMySQL();

        String consulta = "select tarjeta from " + TABLA_MARCACIONES + " group by tarjeta order by tarjeta";

        ResultSet resultados = datos.consultar(consulta);

        while (resultados.next()) {
            salida.add(resultados.getInt(1));
        }

        return salida;
    }

    public static ArrayList getAgentesMA(int tipo) throws Exception {

        ArrayList salida = new ArrayList();

        datos = new AdaptadorMySQL();

        String consulta;// = "select * from " + TABLA_MARCACIONES;

        switch (tipo) {
            case Agente.TODOS:
                consulta = "select a.tarjeta, a.nombres from " + TABLA_MARCACIONES + ", agentes a "
                        + " where a.tarjeta in (select aa.tarjeta from agentes aa where aa.horas_extras = 1)  "
                        + " and a.tarjeta in (select aa.tarjeta from agentes aa where aa.activo = 1)  "
                        //       + " and id_encargado not in (0,1) "
                        // + " and a.tarjeta < 1320 "
                        + "group by a.tarjeta order by a.nombres";
                break;
            case Agente.OPP:
                consulta = "select a.tarjeta, a.nombres from " + TABLA_MARCACIONES + ", agentes a "
                        + " where a.tarjeta in (select aa.tarjeta from agentes aa where aa.horas_extras = 1)  "
                        + " and id_encargado  in (1) "
                        /* + "and a.tarjeta not in (900,1232,718,1008,1287,681,915,813,748,954,1239,1081,770,997,774,452,453,292,906,955,"
                        + "1181, 410, 958, 946, 1282, 990  ) "*/
                        //   + " and a.tarjeta in (1134, 1067, 1005, 900, 1046, 1282, 1112, 943, 1071, 1072, 1177, 1011, 1183, 690, 956, 1012, 813, 1221, 993, 946, 997, 1176, 1127,  1186, 905, 1231, 1148, 644, "
                        //   + " 1182, 1158, 1089, 1135, 462, 1280, 792, 1019, 1181, 410, 992) "
                        // + " and a.tarjeta < 1320 "
                        + "group by a.tarjeta order by a.nombres";
                break;
            case 2:
                consulta = "select a.tarjeta, a.nombres from " + TABLA_MARCACIONES + ", agentes a "
                        + " where a.tarjeta in (select aa.tarjeta from agentes aa where aa.horas_extras = 1)  "
                        + " and id_encargado IN (2) "
                        + "group by a.tarjeta order by a.nombres";
                break;
            case 3:
                consulta = "select a.tarjeta, a.nombres from " + TABLA_MARCACIONES + ", agentes a "
                        + " where a.tarjeta in (select aa.tarjeta from agentes aa where aa.horas_extras = 1)  "
                        + " and id_encargado NOT IN (1,2,16,22) "
                        + "group by a.tarjeta order by a.nombres";
                break;
            case Agente.LOGISTICA:
                consulta = "select a.tarjeta, a.nombres from " + TABLA_MARCACIONES + ", agentes a "
                        + " where a.tarjeta in (select aa.tarjeta from agentes aa where aa.horas_extras = 1)  "
                        //+ "and a.tarjeta in ('1075', '1282', '1112', '1008', '955', '1177', '1287', '1245', '1156', '1014', '1236') "
                        + "and a.tarjeta in ('1075', '1282', '1112', '1008', '955', '1177', '1287', '1245') "
                        // + "and a.tarjeta in ('1234,1236,1245') "
                        + "group by a.tarjeta order by a.nombres";
                break;
            case 23:
                consulta = "select a.tarjeta, a.nombres from " + TABLA_MARCACIONES + ", agentes a "
                        + " where a.tarjeta in (select aa.tarjeta from agentes aa where aa.horas_extras = 1)  "
                        // + "and a.tarjeta in ('1075', '1282', '1112', '1008', '955', '1177', '1287', '1234','1236') "
                        + "and a.id_encargado = 23 "
                        + "group by a.tarjeta order by a.nombres";
                break;
            case Agente.LICENCIAS:
                consulta = "select a.tarjeta, a.nombres from " + TABLA_MARCACIONES + ", agentes a "
                        + " where a.tarjeta in (select aa.tarjeta from agentes aa where aa.horas_extras = 1)  "
                        + "and a.tarjeta in ('1175', '1202') "
                        + "group by a.tarjeta order by a.nombres";
                break;
            case Agente.SALUD:
                consulta = "select a.tarjeta, a.nombres from " + TABLA_MARCACIONES + ", agentes a "
                        + " where a.tarjeta in (select aa.tarjeta from agentes aa where aa.horas_extras = 1)  "
                        + "and a.tarjeta in ('990', '740', '1347', '1057', '938', '1354', '1165', '1115', '1151', '1251', '1157', '3', '979', '3', '808', '1342', '1352', '1165', '1057', '1115', '1251', '1157', '1347', '1354', '990', '808', '1342', '1352', '990', '1057', '938', '1347', '1165', '1115', '1151', '1157', '1251', '1354', '1342', '740', '990', '1057', '1251', '1347', '1165', '1115', '1352', '938', '1157', '1354', '808', '1342', '1352', '740', '990', '1057', '938', '1165', '1115', '1151', '1347', '1251', '1157', '1354', '3', '808', '3', '990', '1342', '740', '1165', '1115', '1251', '1347', '1352', '1354', '1157', '1342', '808', '1352', '740', '979', '1347', '1347', '808', '1352', '808', '1352', '990', '740', '1057', '938', '1165', '1115', '1151', '1354', '1251', '1347', '1157', '3', '3', '808', '1342', '740', '1119', '1057', '1251', '1151', '1347', '1115', '1157', '938', '1352', '1354', '808', '1342', '1352', '990', '1119', '740', '1057', '938', '1165', '1115', '1151', '1347', '1354', '1157', '808', '1342', '1057', '1347', '1165', '1115', '1251', '1352', '938', '1157', '990', '1354', '1342', '1352', '990', '740', '1057', '1347', '1251', '938', '1151', '1165', '1115', '1354', '1157', '979', '808', '990', '1342', '1165', '1115', '1057', '1151', '1347', '1251', '938', '1157', '1352', '1354', '808', '1342', '1352', '990', '740', '1057', '1347', '1347', '1115', '1151', '938', '1251', '1157', '1352', '1354', '808', '990', '740', '1119', '1057', '1251', '1115', '1354', '1157', '1347', '1342', '808', '1352', '1119', '1119', '1119', '740', '990', '990', '1057', '1151', '1347', '938', '1115', '1251', '1157', '3', '1354', '808', '3', '1342', '1342', '1057', '1119', '1347', '1347', '1251', '1157', '1354', '1352', '808', '1342', '1352', '740', '979', '1352', '808', '1352', '979', '979', '1354', '1354', '808', '740', '1119', '990', '1057', '938', '1151', '1115', '1347', '1251', '1354', '1157', '3', '979', '740', '1342', '1119', '1057', '1151', '1115', '938', '1157', '1352', '1354', '1347', '990', '1342', '1352', '740', '990', '445', '1057', '1352', '938', '1151', '1115', '1251', '1354', '979', '1347', '1342', '1151', '1057', '1115', '1251', '1157', '1354', '1347', '1342', '1342', '1352', '990', '445', '740', '1057', '1151', '938', '1115', '1347', '1251', '1157', '3', '979', '1354', '3', '740', '445', '740', '1342', '1119', '1057', '1151', '1251', '1115', '1352', '938', '1157', '1354', '1347', '1342', '1342', '990', '1352', '990', '445', '740', '1057', '938', '1347', '1115', '1151', '1354', '1251', '1157', '445', '740', '1342', '1057', '1115', '1251', '1352', '938', '1157', '1354', '990', '1347', '1342', '1352', '445', '990', '1057', '938', '1115', '1251', '1151', '1347', '1354', '1157', '3', '3', '445', '1342', '1057', '1151', '1115', '1354', '1352', '1347', '1347', '1342', '990', '1352', '979', '990', '990', '1119', '990', '1347', '1119', '1347', '990', '979', '990', '1352', '990', '1352', '990', '990', '445', '990', '1119', '740', '1057', '938', '1347', '1115', '1251', '1151', '1354', '3', '979', '3', '445', '1342', '1119', '1347', '1251', '1057', '1115', '938', '1157', '1352', '740', '1354', '1342', '1342', '990', '1352', '740', '445', '938', '1151', '1115', '1057', '1354', '1251', '1157', '1347', '445', '1342', '1057', '1251', '1115', '1157', '938', '1354', '1342', '1347', '740', '445', '1057', '938', '1115', '1115', '1354', '1347', '1151', '1251', '3', '3', '445', '1342', '1119', '1151', '1057', '1251', '1115', '1347', '1352', '1354', '1342', '1352', '990', '1119', '740', '979', '1347', '740', '1347', '1347', '990', '1354', '1354', '740', '990', '1057', '938', '1347', '1115', '1165', '1157', '1354', '3', '3', '740', '1342', '1119', '1347', '1251', '1057', '1115', '1165', '1352', '938', '1157', '1354', '990', '1342', '1119', '1352', '990', '740', '445', '938', '974','1057', '1354', '1115', '1165', '1251', '1119', '1119', '1157', '1347', '445', '1342', '1347', '1119', '1165', '1115', '1352', '938', '1157', '990', '1354', '1342', '1352') "
                        + "group by a.tarjeta order by a.nombres";
                break;
            default:
                consulta = "select a.tarjeta, a.nombres from " + TABLA_MARCACIONES + ", agentes a "
                        + " where a.tarjeta in (select aa.tarjeta from agentes aa where aa.horas_extras = 1)  "
                        + "group by a.tarjeta order by a.nombres";
        }

        //System.err.println(consulta);
        try {
            ResultSet resultados = datos.consultar(consulta);

            while (resultados.next()) {
                int tarjeta = resultados.getInt(1);
                if (salida.lastIndexOf(tarjeta) == -1) {
                    salida.add(tarjeta);
                }
            }

        } catch (SQLException e) {
            System.err.println(e);
        }

        return salida;
    }

    public static ArrayList getAgentesMA(int[] tarjetas) throws Exception {

        ArrayList salida = new ArrayList();

        datos = new AdaptadorMySQL();

        String legajos = "(";

        for (int i = 0; i < tarjetas.length; i++) {
            legajos += tarjetas[i] + ",";
        }

        legajos = legajos.substring(0, legajos.length() - 1) + ")";

        String consulta;// = "select * from " + TABLA_MARCACIONES;

        consulta = "select a.tarjeta, a.nombres from " + TABLA_MARCACIONES + ", agentes a "
                + " where a.tarjeta in (select aa.tarjeta from agentes aa where aa.horas_extras = 1)  "
                + " and a.tarjeta in (select aa.tarjeta from agentes aa where aa.activo = 1)  "
                //       + " and id_encargado not in (0,1) "
                + " and a.tarjeta in " + legajos
                + "group by a.tarjeta order by a.nombres";

        //System.err.println(consulta);
        try {
            ResultSet resultados = datos.consultar(consulta);

            while (resultados.next()) {
                int tarjeta = resultados.getInt(1);
                if (salida.lastIndexOf(tarjeta) == -1) {
                    salida.add(tarjeta);
                }
            }

        } catch (SQLException e) {
            System.err.println(e);
        }

        return salida;
    }

    public static ArrayList getAgentesNM() throws Exception {

        ArrayList salida = new ArrayList();

        datos = new AdaptadorMySQL();

        String consulta = "select tarjeta, agente from " + TABLA_MARCACIONES + " where "
                + "tarjeta in (select tarjeta from agentes where horas_extras = 1 and activo = 1)  "
                //+ "tarjeta = 20 "
                //+ "and tarjeta in (select tarjeta from maquinistas) "
                + "group by tarjeta order by agente";

        ResultSet resultados = datos.consultar(consulta);

        while (resultados.next()) {
            salida.add(resultados.getInt(1));
        }

        return salida;
    }

    public static ArrayList getDiasSinMarca() throws Exception {

        ArrayList<Agente> salida = new ArrayList<>();

        datos = new AdaptadorMySQL();

        String consulta = "select tarjeta, agente from " + TABLA_MARCACIONES + " where "
                + "tarjeta in (select tarjeta from agentes where horas_extras = 1) "
                //+ "tarjeta = 20 "
                //+ "and tarjeta in (select tarjeta from maquinistas) "
                + "group by tarjeta order by agente";

        String subconsulta;
        ResultSet resultados = datos.consultar(consulta);

        while (resultados.next()) {
            salida.add(new Agente(resultados.getInt(1), resultados.getString(2)));
        }

        datos.resultados.close();

        datos = new AdaptadorMySQL();

        for (Agente actual : salida) {
            subconsulta = "select nombre, dia\n"
                    + "from dias d\n"
                    + "where tipo = 0\n"
                    + "and mes = 10\n"
                    + "and d.dia not in (select day(mo.fecha)from "
                    + TABLA_MARCACIONES + " mo where tarjeta = " + actual.getTarjeta() + ")"
                    + " order by dia, nombre";

            datos.consultar(subconsulta);

            while (datos.resultados.next()) {
                System.out.println(actual + " - " + datos.resultados.getString("nombre") + " "
                        + datos.resultados.getInt("dia"));
            }
        }
        return agentes;
    }

    public static ArrayList getAgentesMCuad() throws Exception {

        ArrayList salida = new ArrayList();

        datos = new AdaptadorMySQL();

        String consulta = "select tarjeta, agente from " + TABLA_MARCACIONES + " where "
                + "tarjeta in (select tarjeta from agentes where horas_extras = 1) "
                + "/*and tarjeta in (select tarjeta from maquinistas) */ "
                + "group by tarjeta order by agente";

        ResultSet resultados = datos.consultar(consulta);

        while (resultados.next()) {
            salida.add(resultados.getInt(1));
        }

        return salida;
    }

    public static ArrayList getAgentesH() throws Exception {

        ArrayList salida = new ArrayList();

        datos = new AdaptadorMySQL();

        String consulta = "SELECT a.tarjeta \n"
                + "FROM rrhh." + TABLA_HORAS_EXTRAS + " h, rrhh.agentes a \n"
                + "where (horas_50 <> '00:00' \n"
                + "or horas_100 <> '00:00')\n"
                + "and h.tarjeta = a.tarjeta\n"
                + "order by nombres;";

        ResultSet resultados = datos.consultar(consulta);
        System.out.println(consulta);

        while (resultados.next()) {
            salida.add(resultados.getInt(1));
        }

        return salida;
    }

    public static HashMap<Integer, String> getDeficitHoras() throws Exception {
        HashMap<Integer, String> salida = new HashMap<>();

        datos = new AdaptadorMySQL();

        String consulta = "select tarjeta, horas_def from agentes;";

        ResultSet resultados = datos.consultar(consulta);

        while (resultados.next()) {
            salida.put(resultados.getInt(1), resultados.getString(2));
        }
        datos.cerrar();

        return salida;
    }

    public static HashMap<Integer, String> getEntradas() throws Exception {
        HashMap<Integer, String> salida = new HashMap<>();

        String consulta = "select tarjeta, entrada from agentes;";

        ResultSet resultados = datos.consultar(consulta);

        while (resultados.next()) {
            salida.put(resultados.getInt(1), resultados.getString(2));
        }

        //    System.err.println(salida);
        datos.cerrar();

        return salida;
    }

    public static HashMap<Integer, String> getSalidas() throws Exception {
        HashMap<Integer, String> salida = new HashMap<>();

        datos = new AdaptadorMySQL();

        String consulta = "select tarjeta, salida from agentes;";

        ResultSet resultados = datos.consultar(consulta);

        while (resultados.next()) {
            salida.put(resultados.getInt(1), resultados.getString(2));
        }

        return salida;
    }

    public static ArrayList getEntradaAgentes() throws Exception {
        ArrayList<Hora> entradaAgentes = new ArrayList();
        String consulta;
        System.err.println("Entrada Agente: " + entradaAgentes);

        datos = new AdaptadorMySQL();

        //ArrayList agentes = getAgentesM();
        ResultSet resultados;
        Hora entrada;

        for (int i = 0; i < agentes.size(); i++) {
            consulta = "select entrada from agentes where tarjeta = " + agentes.get(i) + " order by nombres";
            resultados = datos.consultar(consulta);
            resultados.next();
            entrada = new Hora(resultados.getString(1));
            entradaAgentes.add(entrada);
        }

        datos.cerrar();

        return entradaAgentes;
    }

    public static String getEntradaAgente(int tarjeta) throws Exception {

        return entradas.get(tarjeta);

    }

    public static ArrayList getDeficitHorasAgentes() throws Exception {
        ArrayList horasDefAgentes = new ArrayList();
        String consulta;

        datos = new AdaptadorMySQL();

//        ArrayList agentes = getAgentesM();
        ResultSet resultados;

        for (int i = 0; i < agentes.size(); i++) {
            consulta = "select horas_def from agentes where tarjeta = " + agentes.get(i) + " order by nombres";
            resultados = datos.consultar(consulta);
            resultados.next();
            horasDefAgentes.add(resultados.getString(1));
        }

        datos.cerrar();
        return horasDefAgentes;
    }

    public static ArrayList getDias() throws Exception {

        ArrayList<String> dias = new ArrayList();

        datos = new AdaptadorMySQL();

        String consulta = "select id_dia from dias where month(id_dia) = " + Lector_1.MES + "";

        ResultSet resultados = datos.consultar(consulta);

        while (resultados.next()) {
            dias.add(resultados.getString(1).substring(8, 10));
        }
        //  System.err.println(dias);
        return dias;
    }

    public static ArrayList getDias(int tarjeta) throws Exception {

        ArrayList<String> dias = new ArrayList();

        datos = new AdaptadorMySQL();

        String consulta = "select fecha from " + TABLA_MARCACIONES
                + " where month(fecha) = " + MES
                + " and tipo <> 4 and tarjeta = " + tarjeta
                + " group by fecha order by fecha";

        ResultSet resultados = datos.consultar(consulta);

        while (resultados.next()) {
            dias.add(resultados.getString(1).substring(8, 10));
        }

        datos.cerrar();
        return dias;
    }

    public static ArrayList getTipoDias() throws Exception {

        ArrayList dias = new ArrayList();

        datos = new AdaptadorMySQL();

        String consulta = "select tipo from dias where mes = " + MES + " and anio = " + ANIO + " order by dia";

        ResultSet resultados = datos.consultar(consulta);

        while (resultados.next()) {
            dias.add(resultados.getInt(1));
        }

        return dias;
    }

    public static void getAgentesHorasExtras() throws Exception {

        File reporte = new File("H:\\Personal_2016\\reporte_dic_2015.html");

        PrintWriter out;
        out = new PrintWriter(reporte);

        String consulta = "select * from agentes_horas_extras_dic_2015 order by nombres";

        datos = new AdaptadorMySQL();

        ResultSet resultados = datos.consultar(consulta);

        int tarjetaTemp;
        String agenteTemp;
        Hora horas50Temp;
        Hora horas100Temp;

        String salida = "<table>\n";
        salida += "<tr><th>Tarjeta</th><th>Agente</th><th>Horas al 50%</th><th>Horas al 100%</th></tr>\n";

        while (resultados.next()) {

            tarjetaTemp = resultados.getInt(1);
            agenteTemp = resultados.getString(2);
            horas50Temp = new Hora(resultados.getString(3));
            horas100Temp = new Hora(resultados.getString(4));

            salida += "<tr>";

            salida += "<td>" + tarjetaTemp + "</td>";
            salida += "<td>" + agenteTemp + "</td>";

            if (horas50Temp.getValor() < 0) {
                salida += "<td>" + new Hora(-horas50Temp.getValor()) + "</td>";
            } else {
                salida += "<td>" + horas50Temp + "</td>";
            }

            if (horas100Temp.getValor() > 0) {
                salida += "<td><b>" + horas100Temp + "</b></td>";
            } else {
                salida += "<td>" + horas100Temp + "</td>";
            }

            salida += "</tr>\n";

            System.out.print(tarjetaTemp + " ");
            System.out.print(agenteTemp + " ");
            System.out.print(horas50Temp + " ");
            System.out.print(horas100Temp + " ");
            System.out.println("");
        }
        salida += "</table>";

        out.println(salida);

        out.flush();
        out.close();

    }

    public static ArrayList<String> getMarcacionesIndividual_a(ArrayList dias, int tarjeta, ArrayList<Integer> tipoDias, String deficit,
            Hora entrada) throws Exception {

        int nDias = dias.size();

        datos = new AdaptadorMySQL();
        ResultSet resultados;

        ArrayList<String> consultas = new ArrayList<>(nDias);
        String temp;

        int horasAl100 = 0;
        int horasAl50 = 0;

        temp = "select day(fecha), codigo, hora from " + TABLA_MARCACIONES
                + " where tarjeta  = " + tarjeta
                + " and tipo <> 4 group by day(fecha), codigo, hora "
                + " order by day(fecha), hora;";

        resultados = datos.consultar(temp);

        ArrayList<Integer>[] codigos = new ArrayList[dias.size()];
        ArrayList<Hora>[] horas = new ArrayList[dias.size()];

        for (int i = 0; i < dias.size(); i++) {
            codigos[i] = new ArrayList<>();
            horas[i] = new ArrayList<>();

        }

        while (resultados.next()) {

            int dia = resultados.getInt(1);

            int codigo = resultados.getInt(2);
            String hora = resultados.getString(3);

            try {
                codigos[dia - 1].add(codigo);
                horas[dia - 1].add(new Hora(hora));
            } catch (Exception e) {

            }
        }

        datos.cerrar();

        for (int i = 0; i < nDias; i++) {

            for (int j = 0; j < 1; j++) {

                int plus = new Hora(deficit).getValor();

                String info;

                int tipoDiaH = 0;
                try {
                    tipoDiaH = tipoDias.get(i);
                } catch (Exception e) {

                }

                if (codigos[i].size() == 2) {

                    if (codigos[i].get(1) > codigos[i].get(0)) {
                        if (tipoDiaH == 1) {
                            int aSumar = Hora.difHoras(horas[i].get(1), horas[i].get(0));

                            info = tarjeta + "\t" + tipoDiaH + "\t" + dias.get(i) + "\t" + horas[i].get(0) + "\t" + horas[i].get(1) + "\t" + new Hora(aSumar)
                                    + "\t00:00\t" + new Hora(aSumar) + "\t1";

                            consultas.add(Varios.infoAConsulta(TABLA_DETALLE_HORAS_EXTRAS, tarjeta + "" + dias.get(i) + horas[i].get(0) + horas[i].get(1), info));

                            horasAl100 += aSumar;

                            info += " - " + new Hora(aSumar);

                        }

                        if (tipoDiaH == 0) {

                            int entro = horas[i].get(0).getValor();
                            int entradaReal = entro;
                            boolean entradaCambiada = false;

                            int aSumar;

                            if ((entro - 360) < entrada.getValor()) {

                                horas[i].set(0, entrada);
                                entradaCambiada = true;
                            }

                            aSumar = Hora.difHoras(horas[i].get(1), horas[i].get(0));
                            aSumar = aSumar - 25200 + plus;

                            if (plus > 0) {

                            }

                            if (entradaCambiada) {
                                info = tarjeta + "\t" + tipoDiaH + "\t" + dias.get(i) + "\t" + horas[i].get(0) + " ("
                                        + new Hora(entradaReal) + ")" + "\t"
                                        + horas[i].get(1) + "\t"
                                        + new Hora(Hora.difHoras(horas[i].get(1), horas[i].get(0))) + "\t"
                                        + new Hora(aSumar) + "\t00:00\t";

                            } else {
                                info = tarjeta + "\t" + tipoDiaH + "\t" + dias.get(i) + "\t" + horas[i].get(0)
                                        + "\t"
                                        + horas[i].get(1) + "\t"
                                        + new Hora(Hora.difHoras(horas[i].get(1), horas[i].get(0))) + "\t"
                                        + new Hora(aSumar) + "\t00:00\t";
                            }
                            if (aSumar >= TOPE) {
                                info += "1";
                            } else {
                                info += "0";
                            }
                            consultas.add(Varios.infoAConsulta(TABLA_DETALLE_HORAS_EXTRAS, tarjeta + "" + dias.get(i) + horas[i].get(0) + horas[i].get(1), info));

//                            System.err.println(TOPE_HORAS);
//                            if (aSumar > TOPE_HORAS) {
//                                aSumar = TOPE_HORAS;
//                            }

                            if (aSumar >= TOPE) {
                                horasAl50 += aSumar;
                            }
                            if (aSumar < TOPE) {
                            }
                        }
                    } else {
                        System.err.println(codigos[i] + " - " + tarjeta + " - " + dias.get(i) + " - " + tarjeta + " linea 1112");
                        irregu.add(new Irregularidad(new Agente(tarjeta, emp.get(tarjeta)), new Fecha().setFechaMySQL(Lector_1.ANIO + "-" + Lector_1.MES + "-" + dias.get(i)), 99, Irregularidad.DOS_MARCACIONES_INV));
                    }
                } else {

                }

                if (codigos[i].size() == 4) {

                    if (codigos[i].get(1) > codigos[i].get(0)) {
                        if (tipoDiaH == 1) {

                            int aSumar1 = Hora.difHoras(horas[i].get(1), horas[i].get(0));

                            info = tarjeta + "\t" + tipoDiaH + "\t" + dias.get(i) + "\t" + horas[i].get(0) + "\t"
                                    + horas[i].get(1) + "\t" + new Hora(aSumar1) + "\t00:00" + "\t" + new Hora(aSumar1) + "\t1";

                            consultas.add(Varios.infoAConsulta(TABLA_DETALLE_HORAS_EXTRAS, tarjeta + "" + dias.get(i) + horas[i].get(0) + horas[i].get(1), info));

                            horasAl100 += aSumar1;
                            int aSumar2 = Hora.difHoras(horas[i].get(3), horas[i].get(2));

                            info = tarjeta + "\t" + tipoDiaH + "\t" + dias.get(i) + "\t" + horas[i].get(2) + "\t"
                                    + horas[i].get(3) + "\t" + new Hora(aSumar2) + "\t00:00" + "\t" + new Hora(aSumar2) + "\t1";

                            consultas.add(Varios.infoAConsulta(TABLA_DETALLE_HORAS_EXTRAS, tarjeta + "" + dias.get(i) + horas[i].get(2) + horas[i].get(3), info));

                            horasAl100 += aSumar2;

                        }

                        if (tipoDiaH == 0) {

                            int aSumar1;
                            int aSumar2;

                            int entro = horas[i].get(0).getValor();

                            if (entro < entrada.getValor()) {
                                horas[i].set(0, entrada);
                            }

                            aSumar1 = Hora.difHoras(new Hora(Hora.difHoras(horas[i].get(1), horas[i].get(0))), new Hora("03:30"));
                            aSumar1 += plus / 2;

                            info = tarjeta + "\t" + tipoDiaH + "\t" + dias.get(i) + "\t" + horas[i].get(0) + "\t"
                                    + horas[i].get(1) + "\t"
                                    + new Hora(Hora.difHoras(horas[i].get(1), horas[i].get(0))) + "\t"
                                    + new Hora(aSumar1) + "\t00:00\t";

                            if (aSumar1 > 0) {

                                horasAl50 += aSumar1;

                                info += "1";
                            } else {
                                info += "0";
                            }

                            consultas.add(Varios.infoAConsulta(TABLA_DETALLE_HORAS_EXTRAS, tarjeta + "" + dias.get(i) + horas[i].get(0) + horas[i].get(1), info));

                            aSumar2 = Hora.difHoras(new Hora(Hora.difHoras(horas[i].get(3), horas[i].get(2))), new Hora("03:30"));
                            aSumar2 += plus / 2;

                            info = tarjeta + "\t" + tipoDiaH + "\t" + dias.get(i) + "\t" + horas[i].get(2) + "\t"
                                    + horas[i].get(3) + "\t"
                                    + new Hora(Hora.difHoras(horas[i].get(3), horas[i].get(2))) + "\t"
                                    + new Hora(aSumar2) + "\t00:00\t";

                            if (aSumar2 > 0) {
                                info += "1";
                            } else {
                                info += "0";
                            }

                            horasAl50 += aSumar2;

                            consultas.add(Varios.infoAConsulta(TABLA_DETALLE_HORAS_EXTRAS, tarjeta + "" + dias.get(i) + horas[i].get(2) + horas[i].get(3), info));
                        }
                    }
                }
            }

        }

        consultas.add("INSERT INTO " + TABLA_HORAS_EXTRAS + " VALUES (" + tarjeta + ", '" + new Hora(horasAl50) + "', '" + new Hora(Hora.difHoras(new Hora(horasAl100), new Hora("00:00"))) + "','','','','');");

        return consultas;
    }

    public static ArrayList<String> getMarcacionesIndividual_Nueva(ArrayList dias, int tarjeta, ArrayList<Integer> tipoDias, String deficit,
            Hora entrada) throws Exception {

        ArrayList<TDO> marcasAgente = new ArrayList<>();

        for (int i = 0; i < marcas.size(); i++) {
            if (marcas.get(i).getTarjeta() == tarjeta) {
                marcasAgente.add(marcas.get(i));
            }

        }

        int nDias = dias.size();

        //    AdaptadorMySQL datos = new AdaptadorMySQL();
        ArrayList<String> consultas = new ArrayList<>(nDias);

        int horasAl100 = 0;
        int horasAl50 = 0;

        ArrayList<Integer>[] codigos = new ArrayList[dias.size()];
        ArrayList<Hora>[] horas = new ArrayList[dias.size()];

        for (int i = 0; i < dias.size(); i++) {
            codigos[i] = new ArrayList<>();
            horas[i] = new ArrayList<>();

        }

        for (int i = 0; i < marcasAgente.size(); i++) {
            TDO temp = marcasAgente.get(i);
            Operacion tempOp = temp.getOperacion();

            int dia = temp.getDia();

            int codigo = tempOp.getCodigo();
            String hora = tempOp.getHora().toString();

            try {
                codigos[dia - 1].add(codigo);
                horas[dia - 1].add(new Hora(hora));
            } catch (Exception e) {

            }
        }

        for (int i = 0; i < nDias; i++) {

            for (int j = 0; j < 1; j++) {

                int plus = new Hora(deficit).getValor();

                String info;

                int tipoDiaH = 0;
                try {
                    tipoDiaH = tipoDias.get(i);
                } catch (Exception e) {

                }

                if (codigos[i].size() == 2) {

                    if (codigos[i].get(1) > codigos[i].get(0)) {
                        if (tipoDiaH == 1) {
                            int aSumar = Hora.difHoras(horas[i].get(1), horas[i].get(0));

                            info = tarjeta + "\t" + tipoDiaH + "\t" + dias.get(i) + "\t" + horas[i].get(0) + "\t" + horas[i].get(1) + "\t" + new Hora(aSumar)
                                    + "\t00:00\t" + new Hora(aSumar) + "\t1";

                            consultas.add(Varios.infoAConsulta(TABLA_DETALLE_HORAS_EXTRAS, tarjeta + "" + dias.get(i) + horas[i].get(0) + horas[i].get(1), info));

                            // if(aSumar > 7200){
//                                aSumar = TOPE_HORAS;
//                            System.err.println(tarjeta + "\t" + "al_100" + "\t" + dias.get(i));

                            //}
                            horasAl100 += aSumar;

                            info += " - " + new Hora(aSumar);

                        }

                        if (tipoDiaH == 0) {

                            int entro = horas[i].get(0).getValor();
                            int entradaReal = entro;
                            boolean entradaCambiada = false;

                            int aSumar;

                            if ((entro - 360) < entrada.getValor()) {

                                horas[i].set(0, entrada);
                                entradaCambiada = true;
                            }

                            aSumar = Hora.difHoras(horas[i].get(1), horas[i].get(0));
                            aSumar = aSumar - 25200 + plus;

                            /* if(aSumar > 18000)
                                aSumar -= 10800;
                             */
                            if (plus > 0) {

                            }

                            if (entradaCambiada) {
                                info = tarjeta + "\t" + tipoDiaH + "\t" + dias.get(i) + "\t" + horas[i].get(0) + " ("
                                        + new Hora(entradaReal) + ")" + "\t"
                                        + horas[i].get(1) + "\t"
                                        + new Hora(Hora.difHoras(horas[i].get(1), horas[i].get(0))) + "\t"
                                        + new Hora(aSumar) + "\t00:00\t";

                            } else {
                                info = tarjeta + "\t" + tipoDiaH + "\t" + dias.get(i) + "\t" + horas[i].get(0)
                                        + "\t"
                                        + horas[i].get(1) + "\t"
                                        + new Hora(Hora.difHoras(horas[i].get(1), horas[i].get(0))) + "\t"
                                        + new Hora(aSumar) + "\t00:00\t";
                            }
                            if (aSumar >= TOPE) {
                                info += "1";
                            } else {
                                info += "0";
                            }
                            consultas.add(Varios.infoAConsulta(TABLA_DETALLE_HORAS_EXTRAS, tarjeta + "" + dias.get(i) + horas[i].get(0) + horas[i].get(1), info));

                            if (aSumar >= TOPE) {
                                horasAl50 += aSumar;
                            }
                            if (aSumar < TOPE) {
                            }

                            if (aSumar > 7200) {
//                                aSumar = TOPE_HORAS;
//                                System.err.println(tarjeta + "\t" + "al_50");
                            }
                        }
                    } else {

                        /*String diaSt;
                         if (new Integer(dias.get(i).toString()) < 10) {
                         diaSt = "0" + dias.get(i);
                         } else {
                         diaSt = dias.get(i) + "";
                        
                         }*/
                        String mesSt = "";

                        if (Lector_1.MES < 10) {
                            mesSt = "0" + Lector_1.MES;
                        } else {
                            mesSt = Lector_1.MES + "";

                        }

                        Irregularidad irre = new Irregularidad(new Agente(tarjeta, emp.get(tarjeta)),
                                new Fecha().setFechaMySQL(Lector_1.ANIO + "-" + mesSt + "-" + dias.get(i)), 99, Irregularidad.DOS_MARCACIONES_INV);

                        irregu.add(irre);
                        System.err.println(codigos[i] + " - " + tarjeta + " - " + dias.get(i) + " - " + emp.get(tarjeta) + " linea 1346");
                        System.err.println(irre);
                    }
                } else /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                if (codigos[i].size() == 4) {

                    if (codigos[i].get(1) > codigos[i].get(0)) {
                        if (tipoDiaH == 1) {

                            int aSumar1 = Hora.difHoras(horas[i].get(1), horas[i].get(0));

                            info = tarjeta + "\t" + tipoDiaH + "\t" + dias.get(i) + "\t" + horas[i].get(0) + "\t"
                                    + horas[i].get(1) + "\t" + new Hora(aSumar1) + "\t00:00" + "\t" + new Hora(aSumar1) + "\t1";

                            consultas.add(Varios.infoAConsulta(TABLA_DETALLE_HORAS_EXTRAS, tarjeta + "" + dias.get(i) + horas[i].get(0) + horas[i].get(1), info));

                            if (aSumar1 > TOPE_HORAS) {

//                                aSumar = TOPE_HORAS;
//                                System.err.println(tarjeta + "\t" + "al_100" + "\t " + dias.get(i));

                            }

                            horasAl100 += aSumar1;
                            int aSumar2 = Hora.difHoras(horas[i].get(3), horas[i].get(2));

                            info = tarjeta + "\t" + tipoDiaH + "\t" + dias.get(i) + "\t" + horas[i].get(2) + "\t"
                                    + horas[i].get(3) + "\t" + new Hora(aSumar2) + "\t00:00" + "\t" + new Hora(aSumar2) + "\t1";

                            consultas.add(Varios.infoAConsulta(TABLA_DETALLE_HORAS_EXTRAS, tarjeta + "" + dias.get(i) + horas[i].get(2) + horas[i].get(3), info));

                            if (aSumar2 > TOPE_HORAS) {

//                                aSumar = TOPE_HORAS;
//                                System.err.println(tarjeta + "\t" + "al_100" + "\t" + dias.get(i));

                            }

                            horasAl100 += aSumar2;

                        }

                        if (tipoDiaH == 0) {

                            int aSumar1;
                            int aSumar2;

                            int entro = horas[i].get(0).getValor();

                            if (entro < entrada.getValor()) {
                                horas[i].set(0, entrada);
                            }

                            aSumar1 = Hora.difHoras(new Hora(Hora.difHoras(horas[i].get(1), horas[i].get(0))), new Hora("03:30"));
                            aSumar1 += plus / 2;

                            info = tarjeta + "\t" + tipoDiaH + "\t" + dias.get(i) + "\t" + horas[i].get(0) + "\t"
                                    + horas[i].get(1) + "\t"
                                    + new Hora(Hora.difHoras(horas[i].get(1), horas[i].get(0))) + "\t"
                                    + new Hora(aSumar1) + "\t00:00\t";

                            if (aSumar1 > 0) {

                                horasAl50 += aSumar1;

                                info += "1";
                            } else {
                                info += "0";
                            }

                            consultas.add(Varios.infoAConsulta(TABLA_DETALLE_HORAS_EXTRAS, tarjeta + "" + dias.get(i) + horas[i].get(0) + horas[i].get(1), info));

                            aSumar2 = Hora.difHoras(new Hora(Hora.difHoras(horas[i].get(3), horas[i].get(2))), new Hora("03:30"));
                            aSumar2 += plus / 2;

                            info = tarjeta + "\t" + tipoDiaH + "\t" + dias.get(i) + "\t" + horas[i].get(2) + "\t"
                                    + horas[i].get(3) + "\t"
                                    + new Hora(Hora.difHoras(horas[i].get(3), horas[i].get(2))) + "\t"
                                    + new Hora(aSumar2) + "\t00:00\t";

                            if (aSumar2 > 0) {
                                info += "1";
                            } else {
                                info += "0";
                            }

                            horasAl50 += aSumar2;

                            consultas.add(Varios.infoAConsulta(TABLA_DETALLE_HORAS_EXTRAS, tarjeta + "" + dias.get(i) + horas[i].get(2) + horas[i].get(3), info));
                        }
                    }
                }

                //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                if (codigos[i].size() == 6) {

                    if (codigos[i].get(1) > codigos[i].get(0)) {
                        if (tipoDiaH == 1) {

                            int aSumar1 = Hora.difHoras(horas[i].get(1), horas[i].get(0));

                            info = tarjeta + "\t" + tipoDiaH + "\t" + dias.get(i) + "\t" + horas[i].get(0) + "\t"
                                    + horas[i].get(1) + "\t" + new Hora(aSumar1) + "\t00:00" + "\t" + new Hora(aSumar1) + "\t1";

                            consultas.add(Varios.infoAConsulta(TABLA_DETALLE_HORAS_EXTRAS, tarjeta + "" + dias.get(i) + horas[i].get(0) + horas[i].get(1), info));

                            horasAl100 += aSumar1;
                            int aSumar2 = Hora.difHoras(horas[i].get(3), horas[i].get(2));

                            info = tarjeta + "\t" + tipoDiaH + "\t" + dias.get(i) + "\t" + horas[i].get(2) + "\t"
                                    + horas[i].get(3) + "\t" + new Hora(aSumar2) + "\t00:00" + "\t" + new Hora(aSumar2) + "\t1";

                            consultas.add(Varios.infoAConsulta(TABLA_DETALLE_HORAS_EXTRAS, tarjeta + "" + dias.get(i) + horas[i].get(2) + horas[i].get(3), info));

                            horasAl100 += aSumar2;

                        }

                        if (tipoDiaH == 0) {

                            if (tarjeta == 1152) {
                                System.err.println(Arrays.asList(horas));
                            }

                            int aSumar1;
                            int aSumar2;
                            int aSumar3;

                            int entro = horas[i].get(0).getValor();

                            /*  if (entro < entrada.getValor()) {
                                horas[i].set(0, entrada);
                            }*/
                            aSumar1 = Hora.difHoras(new Hora(Hora.difHoras(horas[i].get(1), horas[i].get(0))), new Hora("02:20"));
                            aSumar1 += plus / 3;

                            info = tarjeta + "\t" + tipoDiaH + "\t" + dias.get(i) + "\t" + horas[i].get(0) + "\t"
                                    + horas[i].get(1) + "\t"
                                    + new Hora(Hora.difHoras(horas[i].get(1), horas[i].get(0))) + "\t"
                                    + new Hora(aSumar1) + "\t00:00\t";

                            if (aSumar1 > 0) {

                                horasAl50 += aSumar1;

                                info += "1";
                            } else {
                                info += "0";
                            }

                            consultas.add(Varios.infoAConsulta(TABLA_DETALLE_HORAS_EXTRAS, tarjeta + "" + dias.get(i) + horas[i].get(0) + horas[i].get(1), info));

                            aSumar2 = Hora.difHoras(new Hora(Hora.difHoras(horas[i].get(3), horas[i].get(2))), new Hora("02:20"));
                            aSumar2 += plus / 3;

                            info = tarjeta + "\t" + tipoDiaH + "\t" + dias.get(i) + "\t" + horas[i].get(2) + "\t"
                                    + horas[i].get(3) + "\t"
                                    + new Hora(Hora.difHoras(horas[i].get(3), horas[i].get(2))) + "\t"
                                    + new Hora(aSumar2) + "\t00:00\t";

                            if (aSumar2 > 0) {
                                info += "1";
                            } else {
                                info += "0";
                            }

                            horasAl50 += aSumar2;

                            consultas.add(Varios.infoAConsulta(TABLA_DETALLE_HORAS_EXTRAS, tarjeta + "" + dias.get(i) + horas[i].get(2) + horas[i].get(3), info));

                            ///////////////////////////////////////////////////////////////////////////////
                            aSumar3 = Hora.difHoras(new Hora(Hora.difHoras(horas[i].get(5), horas[i].get(4))), new Hora("02:20"));
                            aSumar3 += plus / 3;

                            info = tarjeta + "\t" + tipoDiaH + "\t" + dias.get(i) + "\t" + horas[i].get(4) + "\t"
                                    + horas[i].get(5) + "\t"
                                    + new Hora(Hora.difHoras(horas[i].get(5), horas[i].get(4))) + "\t"
                                    + new Hora(aSumar3) + "\t00:00\t";

                            if (aSumar3 > 0) {
                                info += "1";
                            } else {
                                info += "0";
                            }

                            horasAl50 += aSumar3;

                            consultas.add(Varios.infoAConsulta(TABLA_DETALLE_HORAS_EXTRAS, tarjeta + "" + dias.get(i) + horas[i].get(4) + horas[i].get(5), info));

                            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
                        }
                    }
                }

                /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////    
            }

        }

        consultas.add("INSERT INTO " + TABLA_HORAS_EXTRAS + " VALUES (" + tarjeta + ", '" + new Hora(horasAl50) + "', '" + new Hora(Hora.difHoras(new Hora(horasAl100), new Hora("00:00"))) + "','','','','');");

        return consultas;
    }

    static ArrayList<TDO> getMarcas() throws Exception {
        ArrayList<TDO> salida = new ArrayList<>();

        String consulta = "select tarjeta, day(fecha), codigo, hora from " + TABLA_MARCACIONES + " where tipo <> 4 group by tarjeta, day(fecha), codigo, hora  order by day(fecha), hora;";

        datos = new AdaptadorMySQL();

        ResultSet resultados = datos.consultar(consulta);

        while (resultados.next()) {
            salida.add(new TDO(resultados.getInt(1), resultados.getInt(2),
                    new Operacion(resultados.getInt(3), new Hora(resultados.getString(4)))));
        }

        return salida;
    }

    static ArrayList<Verificacion> getVerificaciones() throws Exception {
        ArrayList<Verificacion> salida = new ArrayList<>();

        datos = new AdaptadorMySQL();

        String consulta = "select tarjeta, day(fecha), count(codigo), sum(codigo), min(id), group_concat(codigo) \n"
                + "from " + TABLA_MARCACIONES + " \n"
                + "where tipo <> 4 \n"
                + "group by tarjeta, day(fecha) \n"
                + "ORDER BY TARJETA, day(fecha);";
      //  System.err.println(consulta);
        datos.consultar(consulta);

        while (datos.resultados.next()) {
            salida.add(new Verificacion(datos.resultados.getInt(1),
                    datos.resultados.getInt(2),
                    new CSI(datos.resultados.getInt(3),
                            datos.resultados.getInt(4),
                            datos.resultados.getString(5),
                            datos.resultados.getString(6))
            ));
        }

        return salida;
    }

    static ArrayList<Verificacion> getVerificaciones(int tarjeta) {
        ArrayList<Verificacion> salida = new ArrayList<>();

        for (int i = 0; i < verif.size(); i++) {
            if (verif.get(i).getTarjeta() == tarjeta) {
                salida.add(verif.get(i));
            }

        }

        return salida;
    }

    static ArrayList<Verificacion> getVerificaciones(int tarjeta, int dia) {
        ArrayList<Verificacion> salida = new ArrayList<>();

        for (int i = 0; i < verif.size(); i++) {
            if (verif.get(i).getTarjeta() == tarjeta && verif.get(i).getDia() == dia) {
                salida.add(verif.get(i));
            }

        }

        return salida;
    }

    static CSI getCsi(int tarjeta, int dia) {
        CSI salida = new CSI();

        for (int i = 0; i < verif.size(); i++) {
            if (verif.get(i).getTarjeta() == tarjeta && verif.get(i).getDia() == dia) {
                return verif.get(i).getCsi();
            }

        }

        return salida;
    }
}

