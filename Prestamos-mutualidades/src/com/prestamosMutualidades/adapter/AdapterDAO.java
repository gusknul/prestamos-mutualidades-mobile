package com.prestamosMutualidades.adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.prestamosMutualidades.beans.Cobro;
import com.prestamosMutualidades.beans.Pago;
import com.prestamosMutualidades.beans.Socio;
import com.prestamosMutualidades.dao.BaseDatos;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


@SuppressLint("SimpleDateFormat")
public class AdapterDAO
{
	
	@SuppressWarnings("unused")
	private Context context;
	private BaseDatos baseDatos;
	private SQLiteDatabase baseDatosSQL;
	private static final String FORMATO_FECHA = "dd-MM-yyyy";
	private static final String SELECT_ALL = " SELECT * FROM " ;
	
	public AdapterDAO(Context context)
	{
		// TODO Auto-generated constructor stub
		this.context = context;
		baseDatos = new BaseDatos(context);
	}
	
	public AdapterDAO abrirConexion()
	{
		baseDatosSQL = baseDatos.getWritableDatabase();
		return this;
	}
	
	public void cerrarConexion(){
		baseDatos.close();
	}
	
	public ArrayList<Socio> obtenerSocios(){
		baseDatosSQL = baseDatos.getReadableDatabase();
		Cursor cursor = baseDatosSQL.rawQuery(SELECT_ALL+ BaseDatos.TABLA_SOCIO , null);
		ArrayList<Socio> socios =  new ArrayList<Socio>();
		Socio socio;
		while(cursor.moveToNext()){
			socio = new Socio();
			socio.setIdSocio(cursor.getInt(0));
			socio.setNombreCompleto(cursor.getString(1));
			socio.setDireccion(cursor.getString(2));
			socio.setTelefono(cursor.getString(3));
			socios.add(socio);
		}
		cursor.close();
		baseDatos.close();
		return socios;
	}
	
	public ArrayList<Cobro> obtenerCobros() throws ParseException{
		baseDatosSQL = baseDatos.getReadableDatabase();
		Cursor cursor = baseDatosSQL.rawQuery(SELECT_ALL+ BaseDatos.TABLA_COBRO, null);
		ArrayList<Cobro> cobros = new ArrayList<Cobro>();
		Cobro cobro;
        SimpleDateFormat formatoFecha = new SimpleDateFormat(FORMATO_FECHA);
        Date fecha = null;
		while(cursor.moveToNext()){
			cobro = new Cobro();
			cobro.setIdCobro(cursor.getInt(0));
			cobro.setIdSocio(cursor.getInt(1));
			cobro.setIdMutualidad(cursor.getInt(2));
			fecha = formatoFecha.parse(cursor.getString(3));
			cobro.setDate(fecha);
			cobro.setMonto(cursor.getDouble(4));
			cobro.setEstado(cursor.getString(5));
			cobro.setFolio(cursor.getInt(6));
			cobro.setRecargo(cursor.getDouble(7));
			cobros.add(cobro);
		}
		cursor.close();
		baseDatos.close();
		return cobros;
	}
	
	public ArrayList<Pago> obtenerPagos() throws ParseException{
		baseDatosSQL = baseDatos.getReadableDatabase();
		Cursor cursor = baseDatosSQL.rawQuery(SELECT_ALL+ BaseDatos.TABLA_PAGO, null);
		ArrayList<Pago> pagos = new ArrayList<Pago>();
		Pago pago;
        SimpleDateFormat formatoFecha = new SimpleDateFormat(FORMATO_FECHA);
        Date fecha = null;
		while(cursor.moveToNext()){
			pago = new Pago();
			pago.setIdSocio(cursor.getInt(0));
			pago.setIdMutualidad(cursor.getInt(1));
			fecha = formatoFecha.parse(cursor.getString(2));
			pago.setFecha(fecha);
			pago.setMonto(cursor.getDouble(3));
			pago.setEstado(cursor.getString(4));
			pago.setSorteo(cursor.getInt(5));
			pago.setAtraso(cursor.getInt(6));
			pagos.add(pago);
		}
		cursor.close();
		baseDatos.close();
		return pagos;
	}
	
	
	public Socio getRow(int rowId) {
		baseDatosSQL = baseDatos.getReadableDatabase();
		Socio s = null;
		String where = BaseDatos.ID_SOCIO + "=" + rowId;
		Cursor c = 	baseDatosSQL.query(true, BaseDatos.TABLA_SOCIO, BaseDatos.COLUMNAS_SOCIO, 
						where, null, null, null, null, null);
		if (c != null) {
			if(c.moveToFirst()){
				s = new Socio();
				s.setIdSocio(c.getInt(0));
				s.setNombreCompleto(c.getString(1));
				s.setDireccion(c.getString(1));
				s.setTelefono(c.getString(3));
			}
			return s;
		}
		else{
			return null;
		}
		
	}
	
}
