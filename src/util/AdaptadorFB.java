/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;


/*
 * @(#)JDBCAdapter.java	1.10 99/04/23
 * Copyright (c) 1997-1999 by Sun Microsystems, Inc. All Rights Reserved.
 * An adaptor, transforming the JDBC interface to the TableModel interface.
 * @version 1.20 09/25/97
 * @author Philip Milne
 * Versi�n simplificada para utilizacion en ejercicios
 */
import java.sql.*;
import java.util.Properties;

public class AdaptadorFB {

    static String DRIVER_MYSQL = "org.firebirdsql.jdbc.FBDriver";

    //static String DRIVER_ACCESS = "net.ucanaccess.jdbc.UcanloadDriver";
    static String URL_MYSQL = "jdbc:firebirdsql://localhost:3050/E:\\Vsueldos5\\db\\VSUELDOS.FDB";
    //static String URL_ACCESS = "jdbc:ucanaccess:///media/javier/KINGSTON/Personal_2016/horas extras/horas_extras.accdb";
    // static String URL_ACCESS = "jdbc:ucanaccess://H:/per_2/horas extras/horas_extras.accdb";
    //static String URL_ACCESS = "jdbc:ucanaccess:///media/javier/KINGSTON/Personal_2016/horas extras/horas_extras.accdb";
    // static String URL_ACCESS = "jdbc:ucanaccess://d:/Personal_2016/horas extras/horas_extras.accdb";
    // static String URL_ACCESS = "jdbc:ucanaccess://d:/Personal_2016/horas extras/horas_extras.accdb";
    static String USER_ACCESS = "root";
    static String PASS_ACCESS = "admin";

    static String USER_MYSQL = "sysdba";
    static String PASS_MYSQL = "masterkey";

    //  public static final String USER_BD_SEGURIDAD_MYSQL =  util.Global.USR_ADMINISTRADOR_DB;
    // public static final String PASSWORD_BD_SEGURIDAD_MYSQL =  "admin";
    /* private static final int CONECTAR_ACCESS = 0;
     private static final int CONECTAR_MYSQL  = 1;*/
    private Connection conexion;
    private Statement declaracion;
    public int Debug = 0;
    public ResultSet resultados;
    public boolean encontrado;

    public AdaptadorFB() {
        this(URL_MYSQL, DRIVER_MYSQL, USER_MYSQL, PASS_MYSQL);
    }

    public AdaptadorFB(String usuario, String password) {
        this(URL_MYSQL, DRIVER_MYSQL, usuario, password);
    }

    /* public AdaptadorJDBC(int baseDatos){
     this(URL_BD_SEGURIDAD_ACCESS, ODBC_DRIVER, USER_BD_SEGURIDAD_ACCESS, PASSWORD_BD_SEGURIDAD_ACCESS);
     }*/
    public AdaptadorFB(String url, String driverName, String user, String passwd) {
        try {
            Class.forName(driverName);
            if (Debug != 0) {
                System.out.println("Abriendo la conexi�n a la BD");
            }

            Properties conf = new Properties();

            conf.setProperty("userName", user);
            conf.setProperty("password", passwd);
            conf.setProperty("charSet", "utf-8");

            //     Connection conn = DriverManager.getConnection(url, conf);
            conexion = DriverManager.getConnection(url, conf);
            declaracion = conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
        } catch (ClassNotFoundException ex) {
            System.err.println("No se pueden encontrar las clases de los driver de la Base de Datos.");
            System.err.println(ex);
        } catch (SQLException ex) {
            System.err.println("No se puede conectar a la Base de Datos.");
            System.err.println(ex);
        }
    }

    public ResultSet consultar(String consulta) {
        //  System.err.println(consulta);
        if (conexion == null || declaracion == null) {
            System.err.println("No existe la Base de Datos en donde consultar.");
            return null;
        }
        try {
            if (Debug != 0) {
                System.out.println("SEL:" + consulta);
            }
            resultados = declaracion.executeQuery(consulta);
            if (resultados.next()) {
                encontrado = true;
            }
            resultados.beforeFirst();
        } catch (SQLException ex) {
            System.err.println(ex);
            return null;
        }
        return resultados;
    }

    public int actualizar(String psql) throws SQLException {
        if (conexion == null || declaracion == null) {
            System.err.println("No hay base de datos en donde ejecutar la actualizacion");
            return 0;
        }
        try {
            if (Debug != 0) {
                System.out.println("SQL:" + psql);
            }

        } catch (Exception ex) {
            //  ex.printStackTrace();
        }

        int count = declaracion.executeUpdate(psql);

        return count;

    }

    public void cerrar() throws SQLException {
        if (Debug != 0) {
            System.out.println("Cerrando la conexion a la BD");
        }
        if (!(resultados == null)) {
            resultados.close();
        }
        declaracion.close();
        conexion.close();
    }

    protected void finalizar() throws Throwable {
        cerrar();
        super.finalize();
    }

    public static void main(String[] args) {
        AdaptadorFB ad = new AdaptadorFB();
        try {

            String consulta;/* = "SELECT r.item, trim(r.CONCEPTO) AS CONCEPTO \n"
                    + "FROM recibos r\n"
                    + "WHERE r.ID_LIQUIDACION IN ((SELECT max(l.id_liquidacion) FROM liquidaciones l),\n"
                    + "(SELECT max(l.id_liquidacion) FROM liquidaciones l)-1)\n"
                    + "GROUP BY r.item, r.CONCEPTO \n"
                    + "ORDER BY r.ITEM ";

            consulta = "SELECT COD_INTERNO, trim(trailing ',' from trim(PRIMER_APELLIDO)) as apellido, trim(nombres) as nombres, observaciones, sum(importe) \n"
                    + "FROM personal p\n"
                    + "JOIN recibos r ON r.ID_PERSONAL = p.ID_PERSONAL \n"
                    + "WHERE activo = 'S'\n"
                    + "AND ID_LIQUIDACION = 157\n"
                    + "AND ITEM  = 402\n"
                    + "GROUP BY COD_INTERNO, PRIMER_APELLIDO, NOMBRES, OBSERVACIONES ORDER BY primer_apellido, nombres, CAST(COD_INTERNO AS integer) ";

            consulta = "SELECT cod_interno, PRIMER_APELLIDO , nombres, (SELECT sum(importe) FROM recibos r WHERE r.ID_PERSONAL = p.id_personal AND ID_LIQUIDACION = 162 AND tipo =1),\n"
                    + "(SELECT sum(importe) FROM recibos r WHERE r.ID_PERSONAL = p.id_personal AND ID_LIQUIDACION = 162 AND tipo in (2, 4)),\n"
                    + "(SELECT sum(importe) FROM recibos r WHERE r.ID_PERSONAL = p.id_personal AND ID_LIQUIDACION = 162 AND tipo = 3)\n"
                    + "FROM personal p\n"
                    + "WHERE activo = 'S'\n"
                    + "ORDER BY cast(cod_interno AS integer)";
             */
            consulta = "SELECT COD_INTERNO, trim(trailing ',' from trim(PRIMER_APELLIDO)) as apellido, trim(nombres) as nombres, observaciones, sum(importe) \n"
                    + "FROM personal p\n"
                    + "JOIN recibos r ON r.ID_PERSONAL = p.ID_PERSONAL \n"
                    + "WHERE activo = 'S'\n"
                    + "AND ID_LIQUIDACION = " + 174 + "\n"
                    //                    + "AND ITEM  = " + id_concepto + "\n"
                    //+ "AND ITEM  IN (496,497,498)\n"
                    //      + "AND ITEM  IN (195,95)\n"
                    //   + "AND ITEM  IN (32,33,70,71,170,171)\n"
                    //+ "AND ITEM  IN (404, 405, 406, 409, 411, 414, 415)\n"
                    //  + "AND ITEM  IN (495, 496, 497, 498)\n"
                    //            + " AND ITEM  IN (96,194)\n"
                    + "GROUP BY COD_INTERNO, PRIMER_APELLIDO, NOMBRES, OBSERVACIONES "
                    + "ORDER BY /* cast(cod_interno as integer), */Observaciones,cast(cod_interno as integer), primer_apellido, nombres ";

            //System.out.println(consulta);
            ad.consultar(consulta);

            while (ad.resultados.next()) {
                for (int i = 0; i < 4; i++) {
                    System.out.print(ad.resultados.getString(i + 1) + " ");
                }
                System.out.println("");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

}
