/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package att;

//import necessary packages
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import util.Fecha;

/**
 *
 * @author SistemasRC0011
 */
public class LeerMarcaAccess {

    public static final String URL_ACCESS = "jdbc:ucanaccess://C:\\Users\\SistemasRC\\Desktop\\ATT2000.MDB";

    public static void main(String[] args) {
        // Declare the JDBC objects.
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        int mes = 2;
        int anio = 2024;

        try {
            // Establish the connection.
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            //con = DriverManager.getConnection("jdbc:ucanaccess://E:\\Users\\Public\\Documents\\RRHH2020\\Sueldos\\Liquidaciones\\2023\\05-Mayo\\ATT2000.MDB");
            con = DriverManager.getConnection(URL_ACCESS);

            // Create and execute an SQL statement that returns some data.
            String SQL = "SELECT ui.badgenumber, checktime, checktype, nombres FROM userinfo ui inner join checkinout ch on ch.userid = ui.userid "
                    + "inner join agentes a on a.tarjeta = ui.badgenumber "
                    + " where month(CHECKTIME) = " + mes + " and year(CHECKTIME) = " + anio + " and activo > 0";

            //System.err.println(SQL);
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);
            ArrayList<String> marcas = new ArrayList();

            // Iterate through the data in the result set and display it.
            while (rs.next()) {

                int legajo = rs.getInt(1);
                String fechaHora = rs.getString(2);
                Hora hora = new Hora(fechaHora.substring(11, 16));

                Fecha fecha = new Fecha().setFechaMySQL(fechaHora.substring(0, 10));

                String cod = rs.getString(3);
                String nombre = rs.getString(4);

                int codigo = cod.equals("I") ? 20 : 21;

                String marca = legajo + "\t" + nombre + "\t" + legajo + "\t" + fecha + "\t" + hora + "\t1\t " + codigo + "\t" + new Fecha();

                marcas.add(marca);

                System.out.println(marca);
            }
            System.out.println(marcas.size());

        } // Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (Exception e) {
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                }
            }
        }
    }
}
