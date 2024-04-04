/*
 * AdaptadorJDBC.java
 *
 * Created on 22 de junio de 2009, 12:35
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package util;

/*
 * @(#)JDBCAdapter.java	1.10 99/04/23
 * Copyright (c) 1997-1999 by Sun Microsystems, Inc. All Rights Reserved.
 * An adaptor, transforming the JDBC interface to the TableModel interface.
 * @version 1.20 09/25/97
 * @author Philip Milne
 * Version simplificada para utilizacion en ejercicios
 */
import java.sql.*;

public class AdaptadorAccess {

    // static String DRIVER_MYSQL = "com.mysql.jdbc.Driver";

    static String DRIVER_ACCESS = "net.ucanaccess.jdbc.UcanloadDriver";
//    static String URL_MYSQL = "jdbc:mysql://localhost:3306/registraciones";
    //static String URL_ACCESS = "jdbc:ucanaccess:///media/javier/KINGSTON/Personal_2016/horas extras/horas_extras.accdb";
    static String URL_ACCESS = "jdbc:ucanaccess://H:/per_2/horas extras/horas_extras.accdb";
    //static String URL_ACCESS = "jdbc:ucanaccess:///media/javier/KINGSTON/Personal_2016/horas extras/horas_extras.accdb";
   // static String URL_ACCESS = "jdbc:ucanaccess://d:/Personal_2016/horas extras/horas_extras.accdb";
   // static String URL_ACCESS = "jdbc:ucanaccess://d:/Personal_2016/horas extras/horas_extras.accdb";
    static String USER_ACCESS = "root";
    static String PASS_ACCESS = "admin";

  //  public static final String USER_BD_SEGURIDAD_MYSQL =  util.Global.USR_ADMINISTRADOR_DB;
   // public static final String PASSWORD_BD_SEGURIDAD_MYSQL =  "admin";
    /* private static final int CONECTAR_ACCESS = 0;
     private static final int CONECTAR_MYSQL  = 1;*/
    private Connection conexion;
    private Statement declaracion;
    public int Debug = 0;
    public ResultSet resultados;
    public boolean encontrado;

    public AdaptadorAccess() {
        this(URL_ACCESS, DRIVER_ACCESS, USER_ACCESS, PASS_ACCESS);
    }

    public AdaptadorAccess(String usuario, String password) {
        this(URL_ACCESS, DRIVER_ACCESS, usuario, password);
    }

    /* public AdaptadorJDBC(int baseDatos){
     this(URL_BD_SEGURIDAD_ACCESS, ODBC_DRIVER, USER_BD_SEGURIDAD_ACCESS, PASSWORD_BD_SEGURIDAD_ACCESS);
     }*/
    public AdaptadorAccess(String url, String driverName, String user, String passwd) {
        try {
            Class.forName(driverName);
            if (Debug != 0) {
                System.out.println("Abriendo la conexi�n a la BD");
            }
            conexion = DriverManager.getConnection(url, user, passwd);
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

    public void actualizar(String psql) throws SQLException {
        if (conexion == null || declaracion == null) {
            System.err.println("No hay base de datos en donde ejecutar la actualizaci�n");
            return;
        }
        //try {
        if (Debug != 0) {
            System.out.println("SQL:" + psql);
        }
        declaracion.executeUpdate(psql);
        //} catch (SQLException ex) {
        //  ex.printStackTrace();
        //}

    }

    public void cerrar() throws SQLException {
        if (Debug != 0) {
            System.out.println("Cerrando la conexi�n a la BD");
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
        AdaptadorAccess ad = new AdaptadorAccess();
        try {

            ad.consultar("SELECT * from entradas;");

            while (ad.resultados.next()) {

                for (int i = 0; i < ad.resultados.getMetaData().getColumnCount(); i++) {
                    System.out.println(ad.resultados.getMetaData().getColumnName(i + 1)
                            + " : " + ad.resultados.getString(i + 1));
                }

                System.out.println("");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

}
