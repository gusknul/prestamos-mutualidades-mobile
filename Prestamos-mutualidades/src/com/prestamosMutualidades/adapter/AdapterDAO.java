package com.prestamosMutualidades.adapter;

import java.util.ArrayList;
import java.util.Collections;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.prestamosMutualidades.beans.Cobro;
import com.prestamosMutualidades.beans.Pago;
import com.prestamosMutualidades.beans.Socio;
import com.prestamosMutualidades.dao.BaseDatos;

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
	
	@SuppressWarnings("unused")
	private Context context;
	private BaseDatos baseDatos;
	private SQLiteDatabase baseDatosSQL;
	private static final String SELECT_ALL = " SELECT * FROM " ;
	private static final String URL = "";
	
	
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
	
	public SparseArray<Socio> obtenerSocios(){
		baseDatosSQL = baseDatos.getReadableDatabase();
		Cursor cursor = baseDatosSQL.rawQuery(SELECT_ALL+ BaseDatos.TABLA_SOCIO , null);
		Socio socio;
		SparseArray<Socio> sociosSparse = new SparseArray<Socio>();
		
		while(cursor.moveToNext()){
			socio = new Socio();
			socio.setIdSocio(cursor.getInt(0));
			socio.setNombreCompleto(cursor.getString(1));
			socio.setDireccion(cursor.getString(2));
			socio.setTelefono(cursor.getString(3));
			sociosSparse.put(cursor.getInt(0), socio);
			
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
	
	public ArrayList<Socio> obtenerCobrosSocio(SparseArray<Socio> sparseArray){
		baseDatosSQL = baseDatos.getReadableDatabase();
		ArrayList<Socio> s = new ArrayList<Socio>();
		String query = " SELECT id_socio FROM cobro ";
		Cursor cursor = baseDatosSQL.rawQuery(query, null);
		while(cursor.moveToNext()){
			
				s.add(sparseArray.get(cursor.getInt(0)));
			
		}
		cursor.close();
		cerrarConexion();
		return s;
	}
	
	public ArrayList<Socio> obtenerPagosSocio(SparseArray<Socio> sparseArray){
		baseDatosSQL = baseDatos.getReadableDatabase();
		ArrayList<Socio> s = new ArrayList<Socio>();
		String query = " SELECT id_socio FROM pago ";
		Cursor cursor = baseDatosSQL.rawQuery(query, null);
		while(cursor.moveToNext()){
			
				s.add(sparseArray.get(cursor.getInt(0)));
			
		}
		cursor.close();
		cerrarConexion();
		return s;
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
		
	
	public ArrayList<String> obtenerDatosPago(int id){
		baseDatosSQL = baseDatos.getReadableDatabase();
		ArrayList<String> a = new ArrayList<String>();
		
		String query = "SELECT * FROM socio , pago where pago.id_socio = " + id;		
		Cursor cursor = baseDatosSQL.rawQuery(query, null);
		if(cursor!= null){
			if(cursor.move(id)){
				a.add(cursor.getString(0));
				a.add(cursor.getString(1));
				a.add(cursor.getString(2));
				a.add(cursor.getString(3));
				a.add(cursor.getString(4));
				a.add(cursor.getString(5));
				a.add(cursor.getString(6));
				a.add(cursor.getString(7));
				a.add(cursor.getString(8));
				a.add(cursor.getString(9));
				a.add(cursor.getString(10));
				a.add(cursor.getString(11));
				a.add(cursor.getString(12));
				a.add(cursor.getString(13));
				
			}
		}
		cerrarConexion();
		return a;
	}
	
	
	public ArrayList<String> obtenerDatosCobro(int id){
		baseDatosSQL = baseDatos.getReadableDatabase();
		ArrayList<String> a = new ArrayList<String>();
		
		String query = "SELECT * FROM socio , cobro where cobro.id_socio = " + id;		
		Cursor cursor = baseDatosSQL.rawQuery(query, null);
		if(cursor!= null){
			if(cursor.move(id)){
				a.add(cursor.getString(0));
				a.add(cursor.getString(1));
				a.add(cursor.getString(2));
				a.add(cursor.getString(3));
				a.add(cursor.getString(4));
				a.add(cursor.getString(5));
				a.add(cursor.getString(6));
				a.add(cursor.getString(7));
				a.add(cursor.getString(8));
				a.add(cursor.getString(9));
				a.add(cursor.getString(10));
				a.add(cursor.getString(11));
				a.add(cursor.getString(12));
				a.add(cursor.getString(13));
				
			}
		}
		
		
		cerrarConexion();

		return a;
	}
	
	
	
	public boolean realizarPago(int idSocio){
		baseDatosSQL = baseDatos.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(BaseDatos.ESTADO_PAGO, "cobrado");
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
		values.put(BaseDatos.ESTADO_COBRO, "cobrado");
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
	
	public void enviarDatos(){
		
		RestTemplate template = new RestTemplate();
		Gson gson = new Gson();
		
		ArrayList<Socio> socios = obtenerListaSocios();
		ArrayList<Pago> pagos = obtenerPagos();
		ArrayList<Cobro> cobros = obtenerCobros();
		
		ArrayList<Object> o = new ArrayList<Object>();
		o.add(socios);
		o.add(pagos);
		o.add(cobros);
		
		String message = gson.toJson(o);
		Log.i("mensaje", message);
		
		HttpHeaders requestHeaders = new HttpHeaders();
		HttpEntity<Object> requestEntity = new HttpEntity<Object>(o, requestHeaders);
		
		template.getMessageConverters().add(new GsonHttpMessageConverter());
		template.getMessageConverters().add(new StringHttpMessageConverter());
		ResponseEntity<Object> responseEntity = template.exchange(URL + message , HttpMethod.GET, requestEntity, Object.class);
		Object result = responseEntity.getBody();
		if(result != null){
			
		}
		else{
			return;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public void recibirDatos(){
		String URL = "http://127.0.0.1:8000/"; 
		Gson gson = new Gson();
		Socio s = new Socio();
		s.setIdSocio(1);
		s.setNombreCompleto("Gustavo Canul");
		s.setDireccion("Santa isabel");
		s.setTelefono("9991919191");
		String ss = gson.toJson(s);
				
		//HttpHeaders requestHeaders = new HttpHeaders();
		//requestHeaders.setAccept(Collections.singletonList(new MediaType("application","json")));
		//HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
		Socio[] sss = restTemplate.getForObject(URL, Socio[].class);
		//ResponseEntity<Socio> responseEntity = restTemplate.exchange(URL+ss, HttpMethod.GET, requestEntity, Socio.class);
		//Socio events = responseEntity.getBody();
		Log.i("socios", sss.toString());	
		
	}



	@SuppressWarnings({ "unchecked" })
	private void loadObjects(ArrayList<Object> object) {
		// TODO Auto-generated method stub
		
		ArrayList<Socio> socios = new ArrayList<Socio>();
		ArrayList<Cobro> cobros = new ArrayList<Cobro>();
		ArrayList<Pago> pagos = new ArrayList<Pago>();
		
		for( int i = 0 ; i < object.size() ; i++){
			socios = (ArrayList<Socio>) object.get(0) ;
			cobros = (ArrayList<Cobro>) object.get(1) ;
			pagos = (ArrayList<Pago>) object.get(2) ;
		}
		
		for( int i = 0 ; i < socios.size() ; i++ ){
			baseDatos.insertarSocio(socios.get(i));
		}
		
		for(int i = 0; i < cobros.size() ; i ++){
			baseDatos.insertarCobro(cobros.get(i));
		}
		
		for (int i = 0; i<pagos.size() ; i ++){
			baseDatos.insertarPago(pagos.get(i));
		}
	}
	
	
}
