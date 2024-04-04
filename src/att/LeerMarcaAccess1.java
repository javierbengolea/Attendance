/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package att;

//import necessary packages
import java.io.File;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import util.Fecha;

/**
 *
 * @author SistemasRC0011
 */
public class LeerMarcaAccess1 {

    public static final String URL_ACCESS = "jdbc:ucanaccess://C:\\Users\\SistemasRC\\Desktop\\ATT2000.MDB";

    public static void main(String[] args) {
        // Declare the JDBC objects.
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        /*int mes = 2;
        int anio = 2024;*/
        try {
            // Establish the connection.
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            //con = DriverManager.getConnection("jdbc:ucanaccess://E:\\Users\\Public\\Documents\\RRHH2020\\Sueldos\\Liquidaciones\\2023\\05-Mayo\\ATT2000.MDB");
            con = DriverManager.getConnection(URL_ACCESS);

            // Create and execute an SQL statement that returns some data.
            /*String SQL = "SELECT ui.badgenumber, checktime, checktype, nombres FROM userinfo ui inner join checkinout ch on ch.userid = ui.userid "
                    + "inner join agentes a on a.tarjeta = ui.badgenumber "
                    + " where month(CHECKTIME) = "+mes+" and year(CHECKTIME) = "+anio+" and activo > 0";*/
            String SQL = """
                         SELECT userid, CHECKTIME, CHECKTYPE , ui.name                   
                         FROM CHECKINOUT ch                          
                         inner join userinfo ui on ch.userid = ui.userid
                         where year(checktime) = 2024 and month(checktime) = 2;
                     """
                                                                            ;

            //System.err.println(SQL);
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);
            int count = 0;
            StringBuilder sb = new StringBuilder("");

            // Iterate through the data in the result set and display it.
            while (rs.next()) {
                count++;
                int userid = rs.getInt(4);
                String checktime = rs.getString(2);
                String fecha = checktime.split(" ")[0];
                String hora = checktime.split(" ")[1].substring(0, 5);
                String checktipe = rs.getString(3);

                int codigo = 20;

                if ("0".equals(checktipe)) {
                    codigo = 21;
                }

                ///System.out.println(userid + ", \"" + fecha + "\", \"" + hora + "\", " + codigo);
                sb.append(userid).append(", ").append(fecha).append(", ").append(hora).append(", ").append(codigo).append("\n");
                if (count == 1000000) {
                    break;
                }
            }

            try {
                PrintWriter pw = new PrintWriter(new File("marcaciones.csv"));

                pw.print(sb.toString());
                
                pw.flush();
                pw.close();
            } catch (Exception e) {

            }

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
