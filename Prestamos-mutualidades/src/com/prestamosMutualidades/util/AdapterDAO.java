package com.prestamosMutualidades.util;

import java.util.ArrayList;
import com.prestamosMutualidades.beans.Cobro;
import com.prestamosMutualidades.beans.Pago;
import com.prestamosMutualidades.beans.Socio;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
			int idSocio = cursor.getInt(cursor.getColumnIndex("id"));
			String nombreCompleto = cursor.getString(cursor.getColumnIndex("nombre"));
			String direccion = cursor.getString(cursor.getColumnIndex("direccion"));
			String telefono = cursor.getString(cursor.getColumnIndex("telefono"));
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
	
	
	public ArrayList<Socio> obtenerListaSocios(){
		baseDatosSQL = baseDatos.getReadableDatabase();
		Cursor cursor = baseDatosSQL.rawQuery(SELECT_ALL + BaseDatos.TABLA_SOCIO , null);
		Socio socio;
		ArrayList<Socio> socios = new ArrayList<Socio>();
		while(cursor.moveToNext()){
			socio = new Socio(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));
			socios.add(socio);
			
		}
		cursor.close();
		cerrarConexion();
		return socios;
	}
	
	
	public ArrayList<Cobro> obtenerCobros(){
		baseDatosSQL = baseDatos.getReadableDatabase();
		Cursor cursor = baseDatosSQL.rawQuery(SELECT_ALL+ BaseDatos.TABLA_COBRO, null);
		ArrayList<Cobro> cobros = new ArrayList<Cobro>();
		Cobro cobro;
        
		while(cursor.moveToNext()){
			cobro = new Cobro();
			cobro.setIdCobro(cursor.getInt(0));
			cobro.setIdSocio(cursor.getInt(1));
			cobro.setIdMutualidad(cursor.getInt(2));
			cobro.setDate(cursor.getString(3));
			cobro.setMonto(cursor.getDouble(4));
			cobro.setEstado(cursor.getString(5));
			cobro.setFolio(cursor.getInt(6));
			cobro.setAtraso(cursor.getInt(7));
			cobro.setNumeroSorteo(cursor.getInt(8));
			cobro.setRecargo(cursor.getDouble(9));
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
			pago.setIdPago(cursor.getInt(0));
			pago.setIdSocio(cursor.getInt(1));
			pago.setIdMutualidad(cursor.getInt(2));
			pago.setFecha(cursor.getString(3));
			pago.setMonto(cursor.getDouble(4));
			pago.setEstado(cursor.getString(5));
			pago.setSorteo(cursor.getInt(6));
			pago.setNumeroBloc(cursor.getInt(7));
			pago.setRecargo(cursor.getInt(8));
			pago.setAtraso(cursor.getInt(9));
			
			pagos.add(pago);
		}
		
		cursor.close();
		cerrarConexion();
		
		return pagos;
	}
		
	
	
	public Socio getMember( int position ){
		Socio s = null;
		baseDatosSQL = baseDatos.getReadableDatabase();
		String query = "SELECT * FROM socio where id = " + position;		
		Cursor cursor = baseDatosSQL.rawQuery(query, null);
		
		if(cursor != null) {
			s = new Socio();
			s.setIdSocio(cursor.getInt(0));
			s.setNombreCompleto(cursor.getString(1));
			s.setDireccion(cursor.getString(2));
			s.setTelefono(cursor.getString(3));
		}
		cerrarConexion();
		
		return s;
	}
	
	
	
	public boolean realizarPago(int idSocio){
		baseDatosSQL = baseDatos.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(BaseDatos.ESTADO_PAGO, "completado");
		int cant = baseDatosSQL.update(BaseDatos.TABLA_PAGO, values, BaseDatos.ID_SOCIO_PAGO + "=" + idSocio, null);
		
		if(cant==1){
			baseDatosSQL.close();
			return true;
		}
		else{
			baseDatosSQL.close();
			return false;
		}
		
	}
	
	
	public boolean realizarCobro(int idSocio){
		baseDatosSQL = baseDatos.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(BaseDatos.ESTADO_COBRO, "completado");
		int cant = baseDatosSQL.update(BaseDatos.TABLA_COBRO, values, BaseDatos.ID_SOCIO_COBRO + "=" + idSocio, null);
		
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