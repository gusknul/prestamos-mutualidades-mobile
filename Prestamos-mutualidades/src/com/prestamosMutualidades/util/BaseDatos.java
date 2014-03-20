package com.prestamosMutualidades.util;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	List<Object> datos;
	
	public BaseDatos(Context context, List<Object> datos) 
	{
		super(context, NOMBRE_BASE_DATOS, null, version);
		dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		this.datos = datos;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase dataBase) 
	{
		// TODO Auto-generated method stub
		dataBase.execSQL(crearSocio());
		dataBase.execSQL(crearPagos());
		dataBase.execSQL(crearCobros());
		insertarDatos(dataBase,datos);
		
	}

	private void insertarDatos(SQLiteDatabase dataBase, List<Object> datos2) {
		// TODO Auto-generated method stub
		ArrayList<Socio> socios = null;
		ArrayList<Pago> pagos = null;
		ArrayList<Cobro> cobros = null;
		for(int i = 0; i < datos.size(); i++){
			socios = (ArrayList<Socio>) datos.get(0);
			pagos = (ArrayList<Pago>) datos.get(1);
			cobros = (ArrayList<Cobro>) datos.get(2);
		}
		
		for(int i = 0; i < socios.size(); i++){
			dataBase.execSQL(insertarSocio(socios.get(i)));
		}
		
		for (int j = 0; j < pagos.size(); j++) {
			dataBase.execSQL(insertarPago(pagos.get(j)));
		}
		
		for (int k = 0; k < cobros.size(); k++) {
			dataBase.execSQL(insertarCobro(cobros.get(k)));
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
	
}
