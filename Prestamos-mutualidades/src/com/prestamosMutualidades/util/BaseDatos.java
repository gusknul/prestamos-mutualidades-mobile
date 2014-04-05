package com.prestamosMutualidades.util;


import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.prestamosMutualidades.beans.Cobro;
import com.prestamosMutualidades.beans.Pago;
import com.prestamosMutualidades.beans.Socio;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

@SuppressLint("SimpleDateFormat")
public class BaseDatos extends SQLiteOpenHelper
{
	

	public static final String NOMBRE_BASE_DATOS = "prestamos-mutualidades";
	
	/*Datos para la tabla de socio
	 * 
	 */
	public static final String TABLA_SOCIO = "socio";
	public static final String ID_SOCIO = "idSocio";
	public static final String NOMBRE_SOCIO = "nombreCompleto";
	public static final String DIRECCION_SOCIO = "direccion";
	public static final String TELEFONO_SOCIO = "telefono";
	
	/**
	 * Datos para la tabla de cobros
	 */
	public static final String TABLA_COBRO = "cobro";
	public static final String ID_COBRO = "idCobro";
	public static final String ID_SOCIO_COBRO ="idSocio";
	public static final String ID_MUTUALIDAD_COBRO = "idMutualidad";
	public static final String FECHA_COBRO = "fecha";
	public static final String MONTO_COBRO = "monto";
    public static final String ESTADO_COBRO = "estado";
    public static final String FOLIO_COBRO = "folio";
    public static final String RECARGO_COBRO = "recargo";
    public static final String ATRASO_COBRO = "atraso";
    public static final String ADELANTO_COBRO = "adelanto";
    public static final String NUMERO_SORTEO_COBRO = "sorteo";
	
	
	public static final String TABLA_PAGO = "pago";
	
	public static final String ID_PAGO = "idPago";
	public static final String ID_SOCIO_PAGO = "idSocio";
	public static final String ID_MUTUALIDAD_PAGO = "idMutualidad";
	public static final String FECHA_PAGO = "fecha";
	public static final String MONTO_PAGO = "monto";
	public static final String ESTADO_PAGO = "estado";
	public static final String NUMERO_SORTEO_PAGO = "sorteo";
	public static final String ATRASO_PAGO = "atraso";
	SimpleDateFormat dateFormat;	
	private static int version = 1;
	ArrayList<Object> datos;
	
	public BaseDatos(Context context, ArrayList<Object> datos) 
	{
		super(context, NOMBRE_BASE_DATOS, null, version);
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		this.datos = datos;
		onUpgrade(this.getWritableDatabase(), 1, 2);
		// TODO Auto-generated constructor stub
	}

	public BaseDatos(Context context) {
		// TODO Auto-generated constructor stub
		super(context, NOMBRE_BASE_DATOS, null, version);
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	}

	@Override
	public void onCreate(SQLiteDatabase dataBase) 
	{
		// TODO Auto-generated method stub
		dataBase.execSQL(crearSocio());
		dataBase.execSQL(crearPagos());
		dataBase.execSQL(crearCobros());
		if(datos!=null){
			insertarDatos(dataBase,datos);
		}
		else{
			
		}
	}

	@SuppressWarnings("unchecked")
	private void insertarDatos(SQLiteDatabase dataBase, ArrayList<Object> datos2) {
		// TODO Auto-generated method stub
		ArrayList<Socio> socios = (ArrayList<Socio>) datos2.get(0);
		ArrayList<Pago> pagos = (ArrayList<Pago>) datos2.get(1);
		ArrayList<Cobro> cobros = (ArrayList<Cobro>) datos2.get(2);
			
		
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
				+ ID_SOCIO +" INTEGER PRIMARY KEY,"
				+ NOMBRE_SOCIO + " VARCHAR NOT NULL,"
				+ DIRECCION_SOCIO + " VARCHAR NOT NULL, "
				+ TELEFONO_SOCIO + " VARCHAR NOT NULL"
				+ " ) ; ";
		return query;	
	}
	
	public String crearPagos()
	{
		String query = " CREATE TABLE " + TABLA_PAGO + " ( "
				+ ID_PAGO + " INTEGER PRIMARY KEY, "
				+ ID_SOCIO_PAGO + " INTEGER,"
				+ ID_MUTUALIDAD_PAGO + " INTEGER ,"
				+ FECHA_PAGO + " DATE ,"
				+ MONTO_PAGO + " DOUBLE ,"
				+ ESTADO_PAGO + " VARCHAR,"
				+ NUMERO_SORTEO_PAGO + " INTEGER ,"
				+ ATRASO_PAGO + " INTEGER "
				+ " ) ; ";
		return query;	
	}
	
	private String crearCobros(){
		String query = "CREATE TABLE " + TABLA_COBRO + " ( "
				+ ID_COBRO + " INTEGER PRIMARY KEY, "
				+ ID_SOCIO_COBRO + " INTEGER , "
				+ ID_MUTUALIDAD_COBRO + " INTEGER , "
				+ FECHA_COBRO + " DATE , "
				+ MONTO_COBRO + " DOUBLE , "
				+ ESTADO_COBRO + " VARCHAR , "
				+ FOLIO_COBRO + " INTEGER , "
				+ ATRASO_COBRO + " INTEGER , "
				+ NUMERO_SORTEO_COBRO + " INTEGER , "
				+ RECARGO_COBRO + " DOUBLE, "
				+ ADELANTO_COBRO + " INTEGER "
				+ " ) ; ";
		return query;
	}
	
	public String insertarSocio(Socio s){
		String query = null;
		query = " INSERT INTO " + TABLA_SOCIO
				+ " (idSocio, nombreCompleto , direccion , telefono ) " 
				+ " VALUES ( "
				+ " '" + s.getIdSocio() + "' , "
				+ " '" + s.getNombreCompleto() + "' , "
				+ " '" +  s.getDireccion() + "' , "
				+ " '" + s.getTelefono()  + "' "
				+ ")";
		return query;
	}
	
	public String insertarPago(Pago pago){
		String query = null;
		
		query = " INSERT INTO " + TABLA_PAGO
				+ " (idPago, idSocio , idMutualidad , fecha , monto , estado , sorteo , atraso ) " 
				+ " VALUES ( " 
				+ " '" +  pago.getIdPago() + "' , "
				+ " '" +  pago.getIdSocio() + "' , "
				+ " '" +  pago.getIdMutualidad() + "' , "
				+ " '" +  pago.getFecha() + "' , "
				+ " '" +  pago.getMonto() + "' , "
				+ " '" +  pago.getEstado() + "' , "
				+ " '" +  pago.getSorteo() + "' , "
				+ " '" +  pago.getAtraso()  + "' "
				+ ")";
		return query;
	}
	
	public String insertarCobro(Cobro cobro){
		String query = null;
		
		query = " INSERT INTO " + TABLA_COBRO
				+ " ( idCobro, idSocio , idMutualidad , fecha , monto , estado , folio , atraso, sorteo , recargo ) " 
				+ " VALUES ( " 
				+ " '" +  cobro.getIdCobro() + "' , "
				+ " '" +  cobro.getIdSocio() + "' , "
				+ " '" +  cobro.getIdMutualidad() + "' , "
				+ " '" +  cobro.getDate() + "' , "
				+ " '" +  cobro.getMonto() + "' , "
				+ " '" +  cobro.getEstado() + "' , "
				+ " '" +  cobro.getFolio() + "' , "
				+ " '" +  cobro.getAtraso() + "' , "
				+ " '" +  cobro.getSorteo() + "' , "
				+ " '" +  cobro.getRecargo()  + "' "
				+ ")";
		return query;
	}
	
}
