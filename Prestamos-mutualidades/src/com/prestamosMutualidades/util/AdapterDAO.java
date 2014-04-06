package com.prestamosMutualidades.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.prestamosMutualidades.beans.Cobro;
import com.prestamosMutualidades.beans.Pago;
import com.prestamosMutualidades.beans.Socio;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.util.SparseArray;


@SuppressLint("SimpleDateFormat")
public class AdapterDAO
{
	
	private Context context;
	private BaseDatos baseDatos;
	private SQLiteDatabase baseDatosSQL;
	private static final String SELECT_ALL = " SELECT * FROM " ;	
	
	public AdapterDAO(Context context)
	{
		this.context = context;
		baseDatos = new BaseDatos(context);
		
	}
	
	public void cargarDatos(ArrayList<Object> datos){
		baseDatos = new BaseDatos(context,datos);
	}
	
	public AdapterDAO abrirConexion()
	{
		baseDatosSQL = baseDatos.getWritableDatabase();
		return this;
	}
	
	
	public void cerrarConexion(){
		baseDatos.close();
	}
	
	public SparseArray<Socio> obtenerSocios(){
		baseDatosSQL = baseDatos.getReadableDatabase();
		Cursor cursor = baseDatosSQL.rawQuery(SELECT_ALL+ BaseDatos.TABLA_SOCIO , null);
		SparseArray<Socio> sociosSparse = new SparseArray<Socio>();
		
		while(cursor.moveToNext()){
			int idSocio = cursor.getInt(cursor.getColumnIndex(BaseDatos.ID_SOCIO));
			String nombreCompleto = cursor.getString(cursor.getColumnIndex(BaseDatos.NOMBRE_SOCIO));
			String direccion = cursor.getString(cursor.getColumnIndex(BaseDatos.DIRECCION_SOCIO));
			String telefono = cursor.getString(cursor.getColumnIndex(BaseDatos.TELEFONO_SOCIO));
			Socio socio = new Socio(); 
			socio.setIdSocio(idSocio);
			socio.setNombreCompleto(nombreCompleto);
			socio.setDireccion(direccion);
			socio.setTelefono(telefono);
			sociosSparse.put(idSocio, socio);
			
		}
		cursor.close();
		cerrarConexion();
		return sociosSparse;
	}
		
	
	public ArrayList<Cobro> obtenerCobros(){
		baseDatosSQL = baseDatos.getReadableDatabase();
		Cursor cursor = baseDatosSQL.rawQuery(SELECT_ALL+ BaseDatos.TABLA_COBRO, null);
		ArrayList<Cobro> cobros = new ArrayList<Cobro>();
		Cobro cobro;
        
		while(cursor.moveToNext()){
			cobro = new Cobro();
			cobro.setIdCobro(cursor.getInt(cursor.getColumnIndex(BaseDatos.ID_COBRO)));
			cobro.setIdSocio(cursor.getInt(cursor.getColumnIndex(BaseDatos.ID_SOCIO_COBRO)));
			cobro.setIdMutualidad(cursor.getInt(cursor.getColumnIndex(BaseDatos.ID_MUTUALIDAD_COBRO)));
			cobro.setFecha(cursor.getString(cursor.getColumnIndex(BaseDatos.FECHA_COBRO)));
			cobro.setMonto(cursor.getDouble(cursor.getColumnIndex(BaseDatos.MONTO_COBRO)));
			cobro.setEstado(cursor.getString(cursor.getColumnIndex(BaseDatos.ESTADO_COBRO)));
			cobro.setFolio(cursor.getInt(cursor.getColumnIndex(BaseDatos.FOLIO_COBRO)));
			cobro.setAtraso(cursor.getInt(cursor.getColumnIndex(BaseDatos.ATRASO_COBRO)));
			cobro.setSorteo(cursor.getInt(cursor.getColumnIndex(BaseDatos.NUMERO_SORTEO_COBRO)));
			cobro.setRecargo(cursor.getDouble(cursor.getColumnIndex(BaseDatos.RECARGO_COBRO)));
			cobro.setAdelanto(cursor.getInt(cursor.getColumnIndex(BaseDatos.ADELANTO_COBRO)));
			boolean flag = true;
			if(!cursor.getString(cursor.getColumnIndex(BaseDatos.APLICA_ATRASOS_RECARGOS)).equals("true")){
				flag = false;
			}
			
			cobro.setAplicaAtrasosRecargos(flag);
			
			cobros.add(cobro);
		}
		
		cursor.close();
		cerrarConexion();
		
		return cobros;
	}
	
	public ArrayList<Pago> obtenerPagos(){
		baseDatosSQL = baseDatos.getReadableDatabase();
		Cursor cursor = baseDatosSQL.rawQuery(SELECT_ALL+ BaseDatos.TABLA_PAGO, null);
		ArrayList<Pago> pagos = new ArrayList<Pago>();
		Pago pago;
		
		while(cursor.moveToNext()){
			pago = new Pago();
			pago.setIdPago(cursor.getInt(cursor.getColumnIndex(BaseDatos.ID_PAGO)));
			pago.setIdSocio(cursor.getInt(cursor.getColumnIndex(BaseDatos.ID_SOCIO_PAGO)));
			pago.setIdMutualidad(cursor.getInt(cursor.getColumnIndex(BaseDatos.ID_MUTUALIDAD_PAGO)));
			pago.setFecha(cursor.getString(cursor.getColumnIndex(BaseDatos.FECHA_PAGO)));
			pago.setMonto(cursor.getDouble(cursor.getColumnIndex(BaseDatos.MONTO_PAGO)));
			pago.setEstado(cursor.getString(cursor.getColumnIndex(BaseDatos.ESTADO_PAGO)));
			pago.setSorteo(cursor.getInt(cursor.getColumnIndex(BaseDatos.NUMERO_SORTEO_PAGO)));
			pago.setAtraso(cursor.getInt(cursor.getColumnIndex(BaseDatos.ATRASO_PAGO)));
			
			pagos.add(pago);
		}
		
		cursor.close();
		cerrarConexion();
		
		return pagos;
	}

	
	public boolean realizarPago(int idPago){
		baseDatosSQL = baseDatos.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(BaseDatos.ESTADO_PAGO, "completado");
		int cant = baseDatosSQL.update(BaseDatos.TABLA_PAGO, values, BaseDatos.ID_PAGO + "=" + idPago, null);
		
		if(cant==1){
			baseDatosSQL.close();
			return true;
		}
		else{
			baseDatosSQL.close();
			return false;
		}
		
	}
	
	
	public boolean realizarCobro(int idCobro , int totalAdelantos, String aplicaAtrasosRecargos){
		baseDatosSQL = baseDatos.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(BaseDatos.ESTADO_COBRO, "completado");
		values.put(BaseDatos.ADELANTO_COBRO, totalAdelantos);
		values.put(BaseDatos.APLICA_ATRASOS_RECARGOS, aplicaAtrasosRecargos);
		
		int cant = baseDatosSQL.update(BaseDatos.TABLA_COBRO, values, BaseDatos.ID_COBRO + "=" + idCobro, null);
		
		if(cant==1){
			baseDatosSQL.close();
			return true;
		}
		else{
			baseDatosSQL.close();
			return false;
		}
		
	}
	
	public float obtenerTotalPagos(){
		baseDatosSQL = baseDatos.getReadableDatabase();
		String query = "SELECT total ( monto ) from "+ BaseDatos.TABLA_PAGO + " where estado = 'completado' ;";		
		Cursor cursor = baseDatosSQL.rawQuery(query, null);
		float total = 0;
		while(cursor.moveToNext()){
			 total = total + cursor.getFloat(0);
		}
		return total;
	}
	
	public float obtenerTotalCobros(){
		baseDatosSQL = baseDatos.getReadableDatabase();
		String query = "SELECT total ( monto ) from "+ BaseDatos.TABLA_COBRO + " where estado = 'completado' ;";		
		Cursor cursor = baseDatosSQL.rawQuery(query, null);
		float total = 0;
		while(cursor.moveToNext()){
			 total = total + cursor.getFloat(0);
		}
		return total;
	}

}