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

public class AdaptadorMySQL {

    static String DRIVER_MYSQL = "com.mysql.cj.jdbc.Driver";

    //static String DRIVER_ACCESS = "net.ucanaccess.jdbc.UcanloadDriver";
    static String URL_MYSQL = "jdbc:mysql://localhost:3307/rrhh?useUnicode=true";

    static String USER_ACCESS = "root";
    static String PASS_ACCESS = "admin2020";

    static String USER_MYSQL = "root";
    static String PASS_MYSQL = "Admin_2024";

    //  public static final String USER_BD_SEGURIDAD_MYSQL =  util.Global.USR_ADMINISTRADOR_DB;
    // public static final String PASSWORD_BD_SEGURIDAD_MYSQL =  "admin";
    /* private static final int CONECTAR_ACCESS = 0;
     private static final int CONECTAR_MYSQL  = 1;*/
    private Connection conexion;
    private Statement declaracion;
    public int Debug = 0;
    public ResultSet resultados;
    public boolean encontrado;

    public AdaptadorMySQL() {
        this(URL_MYSQL, DRIVER_MYSQL, USER_MYSQL, PASS_MYSQL);
    }

    public AdaptadorMySQL(String usuario, String password) {
        this(URL_MYSQL, DRIVER_MYSQL, usuario, password);
    }

    /* public AdaptadorJDBC(int baseDatos){
     this(URL_BD_SEGURIDAD_ACCESS, ODBC_DRIVER, USER_BD_SEGURIDAD_ACCESS, PASSWORD_BD_SEGURIDAD_ACCESS);
     }*/
    public AdaptadorMySQL(String url, String driverName, String user, String passwd) {
        try {
            Class.forName(driverName);
            if (Debug != 0) {
                System.out.println("Abriendo la conexion a la BD");
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
        // System.err.println(consulta);
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
            System.err.println("No hay base de datos en donde ejecutar la actualizaci�n");
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
        AdaptadorMySQL ad = new AdaptadorMySQL();
        try {

            ad.consultar("SELECT * from marcaciones_dic_2023;");

            while (ad.resultados.next()) {

                for (int i = 0; i < ad.resultados.getMetaData().getColumnCount(); i++) {
                    System.out.println(ad.resultados.getMetaData().getColumnName(i + 1)
                            + " : " + ad.resultados.getString(i + 1));
                }

                System.out.println("");
            }

            ResultSetMetaData meta = ad.resultados.getMetaData();
            int cols = meta.getColumnCount();

            for (int i = 0; i < cols; i++) {
                System.out.println(meta.getColumnTypeName(i + 1));

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

}
