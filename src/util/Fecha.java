/*
 * Fecha.java
 *
 * Created on 25 de enero de 2007, 18:10
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package util;

/*
 * Fecha.java
 *
 * Created on 4 de mayo de 2006, 19:09
 */

/**
 *
 * @author  Javier
 */

import java.text.DecimalFormat;
import java.util.GregorianCalendar;
import java.util.Calendar;
import java.util.StringTokenizer;
//import java.util.Date;

public final class Fecha implements java.io.Serializable{
  
  
  public static final int MENOR_QUE = 0;
  public static final int MENOR_IGUAL_QUE = 1;
  public static final int IGUAL_QUE = 2;
  public static final int IGUAL_MAYOR_QUE = 3;
  public static final int MAYOR_QUE = 4;
  
  static public final String SEMANA[] =
  {"Domingo","Lunes","Martes","Miercoles","Jueves","Viernes","Sabado"};
  
  static public final String MESES[] =
  {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio",
   "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
  
  
  int dia;
  int diaSEMANA;
  String nomDia;
  int mes;
  String nomMes;
  int anio;
  int horas;
  int minutos;
  int segundos;
  Fecha hoy;
  GregorianCalendar hoyGC = new java.util.GregorianCalendar();
  
  //hoyGC.s
  
  public static final int AAAA_MM_DD = 0;
  public static final int DD_MM_AAAA = 1;
  
  public Fecha() {
    //hoyGC.
    dia = hoyGC.get(Calendar.DAY_OF_MONTH);
    diaSEMANA = hoyGC.get(Calendar.DAY_OF_WEEK);
    nomDia = SEMANA[diaSEMANA-1];
    mes = hoyGC.get(Calendar.MONTH);
    nomMes = MESES[mes];
    anio = hoyGC.get(Calendar.YEAR);
    horas = hoyGC.get(Calendar.HOUR_OF_DAY);
    minutos = hoyGC.get(Calendar.MINUTE);
    segundos = hoyGC.get(Calendar.SECOND);
    
  }
  
  public Fecha(String fecha, int modo, char separador) throws Exception{
    if(fecha.length()<10) throw new ExcepcionFecha("No tiene los caracteres requeridos!");
    if(fecha.length()>10) fecha = fecha.substring(0,10);
    switch(modo)   {
      case AAAA_MM_DD:
        setFecha_AAAA_MM_DD(fecha, separador);
        break;
        
      case DD_MM_AAAA:
        setFecha_DD_MM_AAAA(fecha, separador);
        break;
        
      default: throw new ExcepcionFecha("Modo incorrecto");
    }
  }
  
  public Fecha setFecha_AAAA_MM_DD(String fecha, char separador)
  throws ExcepcionFecha, Exception{
    if((fecha.charAt(4)!=separador)||(fecha.charAt(7)!=separador)){
        System.err.println(fecha);
      throw new ExcepcionFecha("Separador(es) Incorrecto(s): "+fecha.charAt(4)+" || "+
        fecha.charAt(7));
    }
    
    this.anio = new Integer(fecha.substring(0,4));
    this.mes = new Integer(fecha.substring(5,7)) - 1;
    this.dia = new Integer(fecha.substring(8,10));
    
    return this;
  }
  
  public Fecha setFechaMySQL(String fecha) throws Exception{
    return setFecha_AAAA_MM_DD(fecha, '-');
  }
  
  public Fecha setFecha_DD_MM_AAAA(String fecha, char separador)
  throws ExcepcionFecha, Exception{
    if((fecha.charAt(2)!=separador)||(fecha.charAt(5)!=separador))
      throw new ExcepcionFecha("Separador(es) Incorrecto(s): "+
              fecha);
    
    this.anio = new Integer(fecha.substring(6,10));
    this.mes = new Integer(fecha.substring(3,5)) - 1;
    this.dia = new Integer(fecha.substring(0,2));
    
    return this;
  }
  
   public Fecha setFecha_DD_MM_AA(String fecha, char separador)
  throws ExcepcionFecha, Exception{
    if((fecha.charAt(2)!=separador)||(fecha.charAt(5)!=separador))
      throw new ExcepcionFecha("Separador(es) Incorrecto(s): "+fecha.charAt(4)+" || "+
        fecha.charAt(7));
    
    this.anio = new Integer(fecha.substring(6,8)) + 2000;
    this.mes = new Integer(fecha.substring(3,5)) - 1;
    this.dia = new Integer(fecha.substring(0,2));
    
    return this;
  }
  
  public Fecha(String dia, String mes, String anio) throws Exception{
    this.dia = Integer.valueOf(dia);
    this.mes = Integer.valueOf(mes);
    this.anio = Integer.parseInt(anio);
    
    if(!validarFecha()) throw new ExcepcionFecha("Fecha no valida: " + dia + "-" + mes +"-"+anio);
  }
  
  public static Fecha hoy(){
      return new Fecha();
  }
  
  public Fecha(String fechaSt) throws Exception{
    /*    dia = new Integer(fecha.substring(0,2)).intValue();
        mes = new Integer(fecha.substring(3,5)).intValue();
        anio = new Integer(fecha.substring(6,10)).intValue();*/
    
    StringTokenizer stk = new StringTokenizer(fechaSt, "/");
    
        /*dia = new Fecha().setFecha_DD_MM_AAAA(fecha, '/').getDia();
        mes = new Fecha().setFecha_DD_MM_AAAA(fecha, '/').getMes();
        anio = new Fecha().setFecha_DD_MM_AAAA(fecha, '/').getAnio();*/
    
    dia = Integer.parseInt(stk.nextToken());
    mes = Integer.parseInt(stk.nextToken()) - 1;
    anio = Integer.parseInt(stk.nextToken());
    
        /*Fecha fecha = new Fecha().setFecha_DD_MM_AAAA(fechaSt,'/');
         
        dia = fecha.getDia();
        mes = fecha.getMes();
        anio = fecha.getAnio();*/
    
    //this();
    
    if(!validarFecha()) throw new Exception("Fecha no valida: " + this);
    
  }
  
  public Fecha FechaAccess(String fecha) {
    java.util.StringTokenizer stk;
    
    Fecha salida = new Fecha();
    
    try{
      stk = new java.util.StringTokenizer(fecha.substring(0,10),"-");
      
      salida.anio= Integer.parseInt(stk.nextToken());
      salida.mes = Integer.parseInt(stk.nextToken());
      salida.dia = Integer.parseInt(stk.nextToken());
      
    }catch (Exception e){
      System.out.println("La fecha Fallida es: "+fecha);
      dia = 0;
      mes = 0;
      anio = 0;
    }
    
    return this;
    //return salida.getFecha3();
  }
  
  
  
  /** Creates a new instance of Fecha
     * @param dia
     * @param mes
     * @param anio */
  public Fecha(int dia, int mes, int anio) {
      
    this();
    
    this.dia=dia;
    this.mes=mes-1;
    this.anio=anio;
    
    String nomDia=SEMANA[diaSEMANA-1];
     
    String nomMes=MESES[mes-1];
    
    hoy=actualizarGC(hoyGC);
  }

    public Fecha setDia(int dia) {
        this.dia = dia;
        return this;
    }

    public Fecha setMes(int mes) {
        this.mes = mes;
        return this;
    }

    public Fecha setAnio(int anio) {
        this.anio = anio;
        return this;
    }
  

  
  public static Fecha sumarDias(Fecha fecha, int dias) {
    GregorianCalendar fech = new java.util.GregorianCalendar(
      fecha.anio,fecha.mes,fecha.dia);
    
    long fechaNro = fech.getTimeInMillis();
    
    fechaNro += 86400000L * ((long) dias);
    
    fech.setTimeInMillis(fechaNro);
    
    //fech.add(Calendar.DATE,dias);
    fecha=actualizarGC(fech);
    
    
    return fecha;
  }
  
    public Fecha sumarDias(int dias) {
    GregorianCalendar fech = new java.util.GregorianCalendar(
      anio, mes, dia);
    
    long fechaNro = fech.getTimeInMillis();
    
    fechaNro += 86400000L * ((long) dias);
    
    fech.setTimeInMillis(fechaNro);
    
    dia = fech.get(Calendar.DATE);
    mes = fech.get(Calendar.MONTH);
    anio = fech.get(Calendar.YEAR);
    
    return this;
  }
  
  public static Fecha actualizarGC(GregorianCalendar fech) {
    Fecha fecha = new Fecha();
    fecha.dia=fech.get(Calendar.DATE);
    fecha.mes=fech.get(Calendar.MONTH);
    fecha.anio=fech.get(Calendar.YEAR);
    return fecha;
  }
  
  /*No me gusta mucho pero mi cerebro no da para mas*/
  
  boolean verificarFecha(int dia, int mes, int anio) {
    Fecha fh = new Fecha(dia,mes,anio);
    GregorianCalendar fhGC = new GregorianCalendar(anio,mes,dia);
    
    return (fh.dia==fhGC.get(Calendar.DATE))&&(fh.mes==fhGC.get(Calendar.MONTH))
            &&(fh.anio==fhGC.get(Calendar.YEAR));
  }
  
  public int getDia() {
    return dia;}
  
  public int getMes(){
    return mes;
  }
  
  public int getAnio(){
    return anio;
  }
  
  public String getFecha1() {
    return "" + dia + ( mes + 1) + anio;}
  
  public String getFecha2() {
    return dia + "-" + (mes + 1) + "-" + anio;}
  
  public String getFecha3() {
    String fecha="";
    fecha += dia < 10? "0" : "";
    fecha += dia;
    fecha += (mes + 1) < 10? "0" : "";
    fecha += (mes + 1);
    fecha += (anio-2000)<10? "0" : "";
    fecha += anio-2000;
    return fecha;
  }
  
  public String getFecha3(char ch) {
    String fecha="";
    fecha+=dia<10? "0" : "";
    fecha+=dia+""+ch;
    fecha+=(mes + 1)<10? "0" : "";
    fecha+=(mes + 1)+""+ch;
    //fecha+=(anio-2000)<10? "0" : "";
    //fecha+=anio-2000;
    fecha+=anio;
    return fecha;}
  
  public String getFecha_DD_MM_AAAA(char ch) {
    String fecha="";
    
    fecha+=new java.text.DecimalFormat("00").format(dia)+""+ch;
    fecha+=new java.text.DecimalFormat("00").format((mes + 1))+""+ch;
    fecha+=new java.text.DecimalFormat("0000").format(anio);
    
    return fecha;
  }
  
  public String getFecha_AAAA_MM_DD(char ch) {
    String fecha="";
    
    fecha+=new java.text.DecimalFormat("0000").format(anio)+""+ch;
    fecha+=new java.text.DecimalFormat("00").format((mes + 1))+""+ch;
    fecha+=new java.text.DecimalFormat("00").format(dia);
    
    return fecha;
  }
  
  
  //new java.text.DecimalFormat(
  
  @Override
  public String toString(){
    return this.getFecha3('/');
  }
  
  public String toStringAccess(){
    //return getFecha_AAAA_MM_DD('/');
    String salida = "";
    
    java.text.DecimalFormat df = new java.text.DecimalFormat("0000");
    
    salida += df.format(anio)+"/";
    df = new java.text.DecimalFormat("00");
    salida += df.format((mes + 1)) + "/";
    salida += df.format(dia);
    
    return salida;

  }
  
  public static Fecha mySQL(String fecha) throws Exception{
    return new Fecha().setFechaMySQL(fecha);
  }
  
  public String getFechaMySQL(){
    return getFecha_AAAA_MM_DD('-');
  }
  
  public String getFecha(){
    return getFecha_DD_MM_AAAA('/');
  }
  
  public String getFechaCorta(){
      return dia + " / " + MESES[mes].substring(0,3);
  }
  
  public boolean validarFecha(){
    /*int duracionMeses [] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    boolean valida = true;
    
    //if(mes > 12) throw new Exception("Mes no valido");
    
    try{
      
      if(dia > duracionMeses[mes]) valida = false;
      
      if((dia==29) && (mes==1) && ((anio%400==0) ||
        (anio%4==0 && anio%100!=0))) return true;
      
    }catch(Exception e){
      valida = false;
    }
    
    return valida;*/
      return true;
    
  }
  
  /**
   * Metodo que devuelve la fecha en letras
     * @return 
   */
  
  public String getFechaLiteral(){
    String salida = "";
    
    GregorianCalendar fecha = new GregorianCalendar();
    
    fecha.set(GregorianCalendar.DAY_OF_MONTH, dia);
    fecha.set(GregorianCalendar.MONTH, mes);
    fecha.set(GregorianCalendar.YEAR, anio);
    
    salida += SEMANA[fecha.get(GregorianCalendar.DAY_OF_WEEK)-1]+" ";
    salida += dia +" de ";
    salida += MESES[mes]+" de ";
    salida += anio;
    
    return salida;
  }
  
  public String getNombreDia(){
    String salida = "";
    
    GregorianCalendar fecha = new GregorianCalendar();
    
    fecha.set(GregorianCalendar.DAY_OF_MONTH, dia);
    fecha.set(GregorianCalendar.MONTH, mes);
    fecha.set(GregorianCalendar.YEAR, anio);
    
    salida += SEMANA[fecha.get(GregorianCalendar.DAY_OF_WEEK)-1];/*+" ";
    salida += dia +" de ";
    salida += MESES[mes]+" de ";
    salida += anio;
    */
    return salida;
  }
  
  public static boolean esFechaMenor(Fecha fecha1, Fecha fecha2)
  throws Exception{
    return compararFechas(fecha1, fecha2, MENOR_QUE);
  }
  
  public static boolean esFechaMenor(String fecha1, String fecha2)
  throws Exception{
    
    Fecha fechaUno = new Fecha().setFechaEstandar(fecha1);
    Fecha fechaDos = new Fecha().setFechaEstandar(fecha2);
    
    
    return compararFechas(fechaUno, fechaDos, MENOR_QUE);
  }
  
  public Fecha setFechaEstandar(String fecha) throws Exception{
    
    setFecha_DD_MM_AAAA(fecha, '/');
    
    return this;
  }
  
  public static String estandarAMySql(String fechaEstandar) throws Exception{
    String salida;
    
    salida = new Fecha().setFecha_DD_MM_AAAA(fechaEstandar, '/').
      getFecha_AAAA_MM_DD('-');
    
    return salida;
  }
  
  public static String mysqlAEstandar(String fechaMySql) throws Exception{
    String salida;
    
    salida = new Fecha().setFecha_AAAA_MM_DD(fechaMySql, '-').
      getFecha_DD_MM_AAAA('/');
    
    return salida;
  }
  
  public static long diasEntreFechas(Fecha fechaUno, Fecha fechaDos){
    long nDias;
    
    //Date fecha1 = new Date(fechaUno.getFecha_DD_MM_AAAA('/'));
    //Date fecha2 = new Date(fechaDos.getFecha_DD_MM_AAAA('/'));
    
    GregorianCalendar fecha1 = new GregorianCalendar();
    GregorianCalendar fecha2 = new GregorianCalendar();
    
    fecha1.set(fechaUno.anio, fechaUno.mes, fechaUno.dia);
    fecha2.set(fechaDos.anio, fechaDos.mes, fechaDos.dia);
    
    nDias = (fecha2.getTimeInMillis() - fecha1.getTimeInMillis()) / 86400000L;
    
    //javax.swing.JOptionPane.showMessageDialog(null, nDias + "" );
    
    //nDias = fecha2.compareTo(fecha1);
    
    return nDias;
  }
  
  
  public static boolean compararFechas(Fecha fecha1, Fecha fecha2, int criterio){
    boolean salida = false;
    
    GregorianCalendar fechaUno = new GregorianCalendar();
    GregorianCalendar fechaDos = new GregorianCalendar();
    
    fechaUno.set(Calendar.DATE, fecha1.getDia());
    fechaUno.set(Calendar.MONTH, fecha1.getMes() - 1);
    fechaUno.set(Calendar.YEAR, fecha1.getAnio());
    fechaUno.set(Calendar.HOUR_OF_DAY, 0);
    fechaUno.set(Calendar.MINUTE, 0);
    fechaUno.set(Calendar.MINUTE, 0);
    fechaUno.set(Calendar.SECOND, 0);
    fechaUno.set(Calendar.MILLISECOND, 0);
    
    fechaDos.set(Calendar.DATE, fecha2.getDia());
    fechaDos.set(Calendar.MONTH, fecha2.getMes() - 1);
    fechaDos.set(Calendar.YEAR, fecha2.getAnio());
    fechaDos.set(Calendar.HOUR_OF_DAY, 0);
    fechaDos.set(Calendar.MINUTE, 0);
    fechaDos.set(Calendar.MINUTE, 0);
    fechaDos.set(Calendar.SECOND, 0);
    fechaDos.set(Calendar.MILLISECOND, 0);
    
    if(criterio == MENOR_QUE)
      return fechaUno.getTimeInMillis() < fechaDos.getTimeInMillis();
    
    if(criterio == MENOR_IGUAL_QUE)
      return fechaUno.getTimeInMillis() <= fechaDos.getTimeInMillis();
    
    if(criterio == IGUAL_QUE)
      return fechaUno.getTimeInMillis() == fechaDos.getTimeInMillis();
    
    if(criterio == IGUAL_MAYOR_QUE)
      return fechaUno.getTimeInMillis() >= fechaDos.getTimeInMillis();
    
    if(criterio == MAYOR_QUE)
      return fechaUno.getTimeInMillis() > fechaDos.getTimeInMillis();
    
    return salida;
  }  
  
  public static int menor(int [] arregloPrueba){
    int pos = 0;
    
    for (int i = 0; i < arregloPrueba.length; i++) {
      if(arregloPrueba[i] <= 0 ) continue;
      if(arregloPrueba[i]  < arregloPrueba[pos]) pos = i;
    }
    
    return pos;
  }
  
  public static boolean verfDisp(int [] diferenciasLlegadas, int [] diferenciasPartidas){
    //System.out.println(diferenciasLlegadas[ultimoPositivo(diferenciasLlegadas)+1]);
    
    if(diferenciasLlegadas[0] < 0 && diferenciasPartidas[0] < 0)
      return true;
    
    if(diferenciasLlegadas[diferenciasLlegadas.length-1] > 0 &&
      diferenciasPartidas[diferenciasPartidas.length-1] > 0 )
      return true;
    
    if(menor(diferenciasLlegadas) < 0 && menor(diferenciasPartidas) < 0)
      return true;
    
    return diferenciasLlegadas[menor(diferenciasLlegadas)] > 0 &&
      diferenciasPartidas[menor(diferenciasPartidas)] < 0 ;
  }
  
  public static boolean todosNegativos(int [] arre){
    boolean salida = true;
    
    for(int i=0; i < arre.length; i++)
      if(arre[i] >= 0) return false;
    
    return salida;
  }
  
    public static void main(String [] args) throws Exception{
    
    GregorianCalendar hoy = new java.util.GregorianCalendar();
    
    System.out.println(new Fecha("05/01/2007").getFechaCorta());
    
        System.out.println(new Fecha().setHora("12:12:02").getHora());
    
  }

    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }

    public void setSegundos(int segundos) {
        this.segundos = segundos;
    }

    public void setHoras(int horas) {
        this.horas = horas;
    }

    public int getHoras() {
        return horas;
    }

    public int getSegundos() {
        return segundos;
    }

    public int getMinutos() {
        return minutos;
    }
    
    
    
    public String getHora(){
        
        DecimalFormat df = new DecimalFormat("00");
        
        return df.format(this.getHoras()) + ":" + df.format(this.getMinutos()) + ":" + df.format(this.getSegundos());
    }
    
    public Fecha setHora(String hora){
        this.setAnio(1900);
        this.setMes(1);
        this.setDia(1);
        
        this.setHoras(0);
        this.setMes(0);
        this.setSegundos(0);
        
        
        StringTokenizer stk = new StringTokenizer(hora, ":");
        
        int tokens = stk.countTokens();
        
        /*for (int i = 0; i < tokens; i++) {
            System.out.println(stk.nextToken());                        
        }*/
        
        try{
           
            this.setHoras(Integer.parseInt(stk.nextToken()));
            this.setMinutos(Integer.parseInt(stk.nextToken()));
            this.setSegundos(Integer.parseInt(stk.nextToken()));
            
        }catch(Exception e){
            
        }
        
        
        return this;
    }
  
}

