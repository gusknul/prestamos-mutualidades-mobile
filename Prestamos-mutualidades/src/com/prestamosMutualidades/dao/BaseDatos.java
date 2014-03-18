package com.prestamosMutualidades.dao;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import com.prestamosMutualidades.beans.Cobro;
import com.prestamosMutualidades.beans.Pago;
import com.prestamosMutualidades.beans.Socio;
import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

@SuppressLint("SimpleDateFormat")
public class BaseDatos extends SQLiteOpenHelper
{
	

	public static final String NOMBRE_BASE_DATOS = "prestamos-mutualidades";
	
	/*Datos para la tabla de socio
	 * 
	 */
	public static final String TABLA_SOCIO = "socio";
	public static final String ID_SOCIO = "id";
	public static final String NOMBRE_SOCIO = "nombre";
	public static final String DIRECCION_SOCIO = "direccion";
	public static final String TELEFONO_SOCIO = "telefono";
	
	/**
	 * Datos para la tabla de cobros
	 */
	public static final String TABLA_COBRO = "cobro";
	public static final String ID_COBRO = "id_cobro";
	public static final String ID_SOCIO_COBRO ="id_socio";
	public static final String ID_MUTUALIDAD_COBRO = "id_mutualidad";
	public static final String FECHA_COBRO = "fecha";
	public static final String MONTO_COBRO = "monto";
    public static final String ESTADO_COBRO = "estado";
    public static final String FOLIO_COBRO = "folio";
    public static final String RECARGO_COBRO = "recargo";
    public static final String ATRASO_COBRO = "atraso";
    public static final String NUMERO_SORTEO_COBRO = "numero_sorteo";
	
	
	public static final String TABLA_PAGO = "pago";
	
	public static final String ID_PAGO = "id_pago";
	public static final String ID_SOCIO_PAGO = "id_socio";
	public static final String ID_MUTUALIDAD_PAGO = "id_mutualidad";
	public static final String FECHA_PAGO = "fecha";
	public static final String MONTO_PAGO = "monto";
	public static final String ESTADO_PAGO = "estado";
	public static final String NUMERO_SORTEO_PAGO = "sorteo";
	public static final String ATRASO_PAGO = "atraso";
	public static final String NUMERO_BLOC_PAGO = "numero_bloc";
	public static final String RECARGO_PAGO = "recargo";
	SimpleDateFormat dateFormat;
	
	
	
	private static int version = 1;
	
	public static final String[] COLUMNAS_SOCIO = new String[] {ID_SOCIO,NOMBRE_SOCIO,DIRECCION_SOCIO,TELEFONO_SOCIO};
	
	
	
	public BaseDatos(Context context) 
	{
		super(context, NOMBRE_BASE_DATOS, null, version);
		dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		onUpgrade(this.getWritableDatabase(), 1, 2);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase dataBase) 
	{
		// TODO Auto-generated method stub
		dataBase.execSQL(crearSocio());
		dataBase.execSQL(crearPagos());
		dataBase.execSQL(crearCobros());
		
		ArrayList<Pago> pagos = agregarPagos();
		ArrayList<Cobro> cobros = agregarCobros();
		ArrayList<Socio> socios = agregarSocio();
		
		for(int i = 0; i<pagos.size();i++){
			dataBase.execSQL(insertarPago(pagos.get(i)));
		}
		
		for(int i = 0; i<cobros.size();i++){
			dataBase.execSQL(insertarCobro(cobros.get(i)));
		}
		
		for (int i = 0; i <socios.size() ; i++) {
			dataBase.execSQL(insertarSocio(socios.get(i)));
		}
		
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase dataBase, int arg1, int arg2) 
	{
		// TODO Auto-generated method stubS
		Log.w("Base de datos","Upgrading application's database from version " + arg1+ " to " + arg2 + ", which will destroy all old data!");
		dataBase.execSQL(" DROP TABLE IF EXISTS " + TABLA_SOCIO);
		dataBase.execSQL(" DROP TABLE IF EXISTS " + TABLA_PAGO);
		dataBase.execSQL(" DROP TABLE IF EXISTS " + TABLA_COBRO);
		// Recreate new database:
		
		onCreate(dataBase);
		
	}

	
	public String crearSocio()
	{
		String query = " CREATE TABLE " + TABLA_SOCIO + " ( "
				+ ID_SOCIO +" INTEGER PRIMARY KEY AUTOINCREMENT ,"
				+ NOMBRE_SOCIO + " VARCHAR NOT NULL,"
				+ DIRECCION_SOCIO + " VARCHAR NOT NULL, "
				+ TELEFONO_SOCIO + " VARCHAR NOT NULL"
				+ " ) ; ";
		return query;	
	}
	
	public String crearPagos()
	{
		String query = " CREATE TABLE " + TABLA_PAGO + " ( "
				+ ID_PAGO + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ ID_SOCIO_PAGO + " INTEGER,"
				+ ID_MUTUALIDAD_PAGO + " INTEGER ,"
				+ FECHA_PAGO + " DATE ,"
				+ MONTO_PAGO + " DOUBLE ,"
				+ ESTADO_PAGO + " VARCHAR,"
				+ NUMERO_SORTEO_PAGO + " INTEGER ,"
				+ NUMERO_BLOC_PAGO + " INTEGER ,"
				+ RECARGO_PAGO + " DOUBLE ,"
				+ ATRASO_PAGO + " INTEGER "
				+ " ) ; ";
		return query;	
	}
	
	private String crearCobros(){
		String query = "CREATE TABLE " + TABLA_COBRO + " ( "
				+ ID_COBRO + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ ID_SOCIO_COBRO + " INTEGER , "
				+ ID_MUTUALIDAD_COBRO + " INTEGER , "
				+ FECHA_COBRO + " DATE , "
				+ MONTO_COBRO + " DOUBLE , "
				+ ESTADO_COBRO + " VARCHAR , "
				+ FOLIO_COBRO + " INTEGER , "
				+ ATRASO_COBRO + " INTEGER , "
				+ NUMERO_SORTEO_COBRO + " INTEGER , "
				+ RECARGO_COBRO + " DOUBLE "
				+ " ) ; ";
		return query;
	}
	
	public String insertarSocio(Socio s){
		String query = null;
		query = " INSERT INTO " + TABLA_SOCIO
				+ " ( nombre , direccion , telefono ) " 
				+ " VALUES ( " 
				+ " ' " + s.getNombreCompleto() + " ' , "
				+ " ' " +  s.getDireccion() + " ' , "
				+ " ' " + s.getTelefono()  + " ' "
				+ ")";
		return query;
	}
	
	public String insertarPago(Pago pago){
		String query = null;
		
		query = " INSERT INTO " + TABLA_PAGO
				+ " ( id_socio , id_mutualidad , fecha , monto , estado , sorteo, numero_bloc, recargo , atraso ) " 
				+ " VALUES ( " 
				+ " ' " +  pago.getIdSocio() + " ' , "
				+ " ' " +  pago.getIdMutualidad() + " ' , "
				+ " ' " +  pago.getFecha() + " ' , "
				+ " ' " +  pago.getMonto() + " ' , "
				+ " ' " +  pago.getEstado() + " ' , "
				+ " ' " +  pago.getSorteo() + " ' , "
				+ " ' " +  pago.getNumeroBloc() + " ' , "
				+ " ' " +  pago.getRecargo() + " ' , "
				+ " ' " +  pago.getAtraso()  + " ' "
				+ ")";
		return query;
	}
	
	public String insertarCobro(Cobro cobro){
		String query = null;
		
		query = " INSERT INTO " + TABLA_COBRO
				+ " ( id_socio , id_mutualidad , fecha , monto , estado , folio , atraso, numero_sorteo , recargo ) " 
				+ " VALUES ( " 
				+ " ' " +  cobro.getIdSocio() + " ' , "
				+ " ' " +  cobro.getIdMutualidad() + " ' , "
				+ " ' " +  cobro.getDate() + " ' , "
				+ " ' " +  cobro.getMonto() + " ' , "
				+ " ' " +  cobro.getEstado() + " ' , "
				+ " ' " +  cobro.getFolio() + " ' , "
				+ " ' " +  cobro.getAtraso() + " ' , "
				+ " ' " +  cobro.getNumeroSorteo() + " ' , "
				+ " ' " +  cobro.getRecargo()  + " ' "
				+ ")";
		return query;
	}
	
	
	public ArrayList<Pago> agregarPagos(){
		ArrayList<Pago> pagos  = new ArrayList<Pago>();
		Pago pago1 , pago2,pago3,pago4,pago5,pago8,pago9,pago10,pago11;
		
		Date date = new Date();
		
		pago1 = new Pago(1, 2, dateFormat.format(date), 111, "faltante", 13, 33 , 3 , 4.5);
		pago2 = new Pago(2, 3, dateFormat.format(date), 222, "faltante", 21, 44 , 5 , 6);
		pago3 = new Pago(3, 4, dateFormat.format(date), 333, "faltante", 22, 55 ,4 ,5);
		pago4 = new Pago(4, 2, dateFormat.format(date), 444, "faltante", 23, 66,5,7);
		pago5 = new Pago(5, 5, dateFormat.format(date), 555, "faltante", 27, 77,4,7);
		pago8 = new Pago(8, 2, dateFormat.format(date), 888, "faltante", 46, 24,4,6);
		pago9 = new Pago(9, 6, dateFormat.format(date), 999, "faltante", 243, 80,4,3);
		pago10 = new Pago(10, 9, dateFormat.format(date), 1010, "faltante", 28, 568,2,7);
		pago11 = new Pago(11, 6, dateFormat.format(date), 1111, "faltante", 260, 85,6,9);
		
		pagos.add(pago1);
		pagos.add(pago2);
		pagos.add(pago3);
		pagos.add(pago4);
		pagos.add(pago5);
		pagos.add(pago8);
		pagos.add(pago9);
		pagos.add(pago10);
		pagos.add(pago11);
		
		return pagos;
	}
	
	
	public ArrayList<Cobro> agregarCobros(){
		ArrayList<Cobro> cobros  = new ArrayList<Cobro>();
		Cobro cobro1 , cobro2,cobro4,cobro5,cobro6,cobro7,cobro8,cobro9,cobro10,cobro11;
		Date date = new Date();
		
		cobro1 = new Cobro(1, 2, dateFormat.format(date), 1054, "cobrado", 2, 3,4,7);
		cobro2 = new Cobro(2, 3, dateFormat.format(date), 1470, "cobrado", 13, 5,6,8);
		cobro4 = new Cobro(4, 2, dateFormat.format(date), 140, "cobrado", 4, 8,6,8);
		cobro5 = new Cobro(5, 5, dateFormat.format(date), 157, "faltante", 7, 9,3,7);
		cobro6 = new Cobro(6, 2, dateFormat.format(date), 970, "cobrado", 67, 9,3,3);
		cobro7 = new Cobro(7, 8, dateFormat.format(date), 97324, "cobrado", 132, 1,1,3);
		cobro8 = new Cobro(8, 9, dateFormat.format(date), 43270, "faltante", 98, 18,4,6);
		cobro9 = new Cobro(9, 65, dateFormat.format(date), 97423, "cobrado", 47, 6,7,7);
		cobro10 = new Cobro(10, 34, dateFormat.format(date), 2430, "cobrado", 54, 1,8,9);
		cobro11 = new Cobro(11, 12, dateFormat.format(date), 460, "faltante", 56, 4,7,8);
		
		
		cobros.add(cobro1);
		cobros.add(cobro2);
		cobros.add(cobro4);
		cobros.add(cobro5);
		cobros.add(cobro6);
		cobros.add(cobro7);
		cobros.add(cobro8);
		cobros.add(cobro9);
		cobros.add(cobro10);
		cobros.add(cobro11);
		
		
		return cobros;
	}
	
	public ArrayList<Socio> agregarSocio(){
		ArrayList<Socio> socios = new ArrayList<Socio>();
		Socio s1 , s2 , s3, s4 , s5, s6, s7, s8, s9, s10, s11;
		
		s1 = new Socio("gustavo canul", "cerritos mulchechen", "9992104610");
		s2 = new Socio("oswaldo ceballos", "fco. montejo", "9302405768");
		s3 = new Socio("abril sonda", "chenku", "3928476395");
		s4 = new Socio("odalys medina", "sta. isabel", "3859673923");
		s5 = new Socio("carlos rubio", "sta. isabel", "99918594332");
		s6 = new Socio("ivan ake", "ciudad industrial", "9991608526");
		s7 = new Socio("roxana", "chuburna", "9991070852");
		s8 = new Socio("cesar ricardez", "chuburna", "9991273843");
		s9 = new Socio("andree vela", "circuito", "2930473845");
		s10 = new Socio("irving caro", "pensiones", "0394857203");
		s11 = new Socio("Ada leaños", "pensiones", "9992017006");
		
		
		socios.add(s1);
		socios.add(s2);
		socios.add(s3);
		socios.add(s4);
		socios.add(s5);
		socios.add(s6);
		socios.add(s7);
		socios.add(s8);
		socios.add(s9);
		socios.add(s10);
		socios.add(s11);
		
		
		return socios;
	}
	

}
