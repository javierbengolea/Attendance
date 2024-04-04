/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package att;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import javax.crypto.interfaces.DHPrivateKey;
import util.AdaptadorMySQL;

/**
 *
 * @author javier
 */
public class MarcacionVieja {

    int id;
    int legajo;
    String agente;
    int tarjeta;
    Date fecha;
    Time hora;
    int reloj;
    int codigo;
    Date fechaProceso;
    int tipo;

    public static void main(String[] args) throws Exception {
        
        ArrayList<MarcacionVieja> marcaciones = leer();
        
        

        for (int i = 0; i < marcaciones.size(); i++) {
            System.out.println(marcaciones.get(i));
            
        }

    }

    public MarcacionVieja() {
    }

    public MarcacionVieja(int id, int legajo, String agente, int tarjeta, Date fecha, Time hora, int reloj, int codigo, Date fechaProceso, int tipo) {
        this.id = id;
        this.legajo = legajo;
        this.agente = agente;
        this.tarjeta = tarjeta;
        this.fecha = fecha;
        this.hora = hora;
        this.reloj = reloj;
        this.codigo = codigo;
        this.fechaProceso = fechaProceso;
        this.tipo = tipo;
    }

    public String getAgente() {
        return agente;
    }

    public int getCodigo() {
        return codigo;
    }

    public Date getFecha() {
        return fecha;
    }

    public Date getFechaProceso() {
        return fechaProceso;
    }

    public Time getHora() {
        return hora;
    }

    public int getId() {
        return id;
    }

    public int getLegajo() {
        return legajo;
    }

    public int getReloj() {
        return reloj;
    }

    public int getTarjeta() {
        return tarjeta;
    }

    public int getTipo() {
        return tipo;
    }

    public void setAgente(String agente) {
        this.agente = agente;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setFechaProceso(Date fechaProceso) {
        this.fechaProceso = fechaProceso;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLegajo(int legajo) {
        this.legajo = legajo;
    }

    public void setReloj(int reloj) {
        this.reloj = reloj;
    }

    public void setTarjeta(int tarjeta) {
        this.tarjeta = tarjeta;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public static ArrayList<MarcacionVieja> leer() throws Exception {
        AdaptadorMySQL datos = new AdaptadorMySQL();
        ArrayList<MarcacionVieja> salida = new ArrayList<>();

        datos.consultar("select * from marcaciones_ene_2016 where tipo <> 4 order by agente, fecha, hora");

        while (datos.resultados.next()) {

            salida.add(
                    new MarcacionVieja(datos.resultados.getInt(1),
                            datos.resultados.getInt(2),
                            datos.resultados.getString(3),
                            datos.resultados.getInt(4),
                            datos.resultados.getDate(5),
                            datos.resultados.getTime(6),
                            datos.resultados.getInt(7),
                            datos.resultados.getInt(8),
                            datos.resultados.getDate(9),
                            datos.resultados.getInt(10)
                    ));
        }
        return salida;
    }

    @Override
    public String toString() {
        String salida = 
                id + "\t"+
                 legajo + "\t"+
                agente + "\t"+
                tarjeta + "\t"+
                fecha + "\t"+
                hora + "\t"+
                reloj + "\t"+
                codigo + "\t"+
                fechaProceso + "\t"+
                tipo;
        
        
        return salida; //To change body of generated methods, choose Tools | Templates.
    }

    
}
