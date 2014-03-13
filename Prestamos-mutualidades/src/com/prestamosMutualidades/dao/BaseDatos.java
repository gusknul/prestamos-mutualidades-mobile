package com.prestamosMutualidades.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.prestamosMutualidades.beans.Cobro;
import com.prestamosMutualidades.beans.Pago;
import com.prestamosMutualidades.beans.Socio;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BaseDatos extends SQLiteOpenHelper
{
	

	public static final String NOMBRE_BASE_DATOS = "prestamos-mutualidades";
	
	public static final String TABLA_SOCIO = "socio";
	public static final String ID_SOCIO = "id";
	public static final String NOMBRE_SOCIO = "nombre";
	public static final String DIRECCION_SOCIO = "direccion";
	public static final String TELEFONO_SOCIO = "telefono";
	
	public static final String TABLA_COBRO = "cobro";
	public static final String ID_COBRO = "id_cobro";
	public static final String FK_ID_SOCIO ="id_socio";
	public static final String FK_ID_MUTUALIDAD = "id_mutualidad";
	public static final String FECHA_COBRO = "fecha";
	public static final String MONTO_COBRO = "monto";
    public static final String ESTADO_COBRO = "estado";
    public static final String FOLIO = "folio";
    public static final String RECARGO = "recargo";
	
	
	public static final String TABLA_PAGO = "pago";
	
	public static final String ID_SOCIO_PAGO = "id_socio";
	public static final String ID_MUTUALIDAD_PAGO = "id_mutualidad";
	public static final String FECHA_PAGO = "fecha";
	public static final String MONTO_PAGO = "monto";
	public static final String ESTADO_PAGO = "estado";
	public static final String SORTEO = "sorteo";
	public static final String ATRASO = "atraso";
	
	
	
	private static int version = 1;
	
	public static final String[] COLUMNAS_SOCIO = new String[] {ID_SOCIO,NOMBRE_SOCIO,DIRECCION_SOCIO,TELEFONO_SOCIO};
	
	
	
	public BaseDatos(Context context) 
	{
		super(context, NOMBRE_BASE_DATOS, null, version);
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
		
		for(int i = 0; i<pagos.size();i++){
			dataBase.execSQL(insertarPago(pagos.get(i)));
		}
			
		
		
		dataBase.execSQL(insertarSocio("gustavo canul", "cerritos mulchechen", "9992104610"));
		dataBase.execSQL(insertarSocio("oswaldo ceballos", "fco. montejo", "9302405768"));
		dataBase.execSQL(insertarSocio("ivan ake", "ciudad industrial", "9991608526"));
		dataBase.execSQL(insertarSocio("carlos rubio", "sta. isabel", "99918594332"));
		dataBase.execSQL(insertarSocio("odalys medina", "sta. isabel", "3859673923"));
		dataBase.execSQL(insertarSocio("abril sonda", "chenku", "3928476395"));
		Date d = new Date();
		
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase dataBase, int arg1, int arg2) 
	{
		// TODO Auto-generated method stub
		Log.w(NOMBRE_BASE_DATOS, "Upgrading application's database from version " + arg1
				+ " to " + arg2 + ", which will destroy all old data!");
		
		dataBase.execSQL(" DROP TABLE IF EXISTS " + TABLA_SOCIO);
		dataBase.execSQL(" DROP TABLE IF EXISTS " + TABLA_PAGO);
		dataBase.execSQL(" DROP TABLE IF EXISTS " + TABLA_COBRO);
		// Recreate new database:
		
		onCreate(dataBase);
		
	}
	
	@SuppressWarnings("deprecation")
	public ArrayList<Pago> agregarPagos(){
		ArrayList<Pago> pagos  = new ArrayList<Pago>();
		Pago pago1 , pago2,pago3,pago4,pago5,pago6;
		Date date = new Date();
		pago1 = new Pago(1, 2, date, 100, "cobrado", 2, 3);
		pago2 = new Pago(2, 3, date, 100, "cobrado", 2, 3);
		pago3 = new Pago(1, 4, date, 100, "cobrado", 2, 3);
		pago4 = new Pago(4, 2, date, 100, "cobrado", 2, 3);
		pago5 = new Pago(1, 5, date, 100, "faltante", 2, 3);
		pago6 = new Pago(6, 2, date, 100, "cobrado", 2, 3);
		pagos.add(pago1);
		pagos.add(pago2);
		pagos.add(pago3);
		pagos.add(pago4);
		pagos.add(pago5);
		pagos.add(pago6);
		return pagos;
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
				+ ID_SOCIO_PAGO + " INTEGER,"
				+ ID_MUTUALIDAD_PAGO + " INTEGER ,"
				+ FECHA_PAGO + " DATE ,"
				+ MONTO_PAGO + " DOUBLE ,"
				+ ESTADO_PAGO + " VARCHAR (2),"
				+ SORTEO + " INTEGER ,"
				+ ATRASO + " INTEGER "
				+ " ) ; ";
		return query;	
	}
	
	private String crearCobros(){
		String query = "CREATE TABLE " + TABLA_COBRO + " ( "
				+ ID_COBRO + " INTEGER PRIMARY KEY , "
				+ FK_ID_SOCIO + " INTEGER , "
				+ FK_ID_MUTUALIDAD + " INTEGER , "
				+ FECHA_COBRO + " DATE , "
				+ MONTO_COBRO + " DOUBLE , "
				+ ESTADO_COBRO + " VARCHAR (2), "
				+ FOLIO + " INTEGER , "
				+ RECARGO + " DOUBLE "
				+ " ) ; ";
		return query;
	}
	
	public String insertarSocio(String nombre , String direccion , String telefono){
		String query = null;
		query = " INSERT INTO " + TABLA_SOCIO
				+ " ( nombre , direccion , telefono ) " 
				+ " VALUES ( " 
				+ " ' " + nombre + " ' , "
				+ " ' " +  direccion + " ' , "
				+ " ' " + telefono  + " ' "
				+ ")";
		return query;
	}
	
	public String insertarPago(Pago pago){
		String query = null;
		
		query = " INSERT INTO " + TABLA_PAGO
				+ " ( id_socio , id_mutualidad , fecha , monto , estado , sorteo , atraso ) " 
				+ " VALUES ( " 
				+ " ' " +  pago.getIdSocio() + " ' , "
				+ " ' " +  pago.getIdMutualidad() + " ' , "
				+ " ' " +  pago.getFecha() + " ' , "
				+ " ' " +  pago.getMonto() + " ' , "
				+ " ' " +  pago.getEstado() + " ' , "
				+ " ' " +  pago.getSorteo() + " ' , "
				+ " ' " +  pago.getAtraso()  + " ' "
				+ ")";
		return query;
	}
	
	public String insertarCobro(Cobro cobro){
		String query = null;
//		query = " INSERT INTO " + TABLA_SOCIO
//				+ " ( nombre , direccion , telefono ) " 
//				+ " VALUES ( " 
//				+ " ' " + nombre + " ' , "
//				+ " ' " +  direccion + " ' , "
//				+ " ' " + telefono  + " ' "
//				+ ")";
		return query;
	}

}
