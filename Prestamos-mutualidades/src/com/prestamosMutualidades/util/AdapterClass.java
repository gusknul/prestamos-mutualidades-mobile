package com.prestamosMutualidades.util;

import java.util.ArrayList;
import java.util.List;

import android.app.Application;
import android.content.Context;

public class AdapterClass extends Application{
	
	private AdapterDAO adapter;
	private BaseDatos baseDatos;
	private Context context;
	private ArrayList<Object> datos;
	
	public AdapterClass(Context context){
		this.context = context;
	}
	
	public AdapterClass(){
	}
	
	public void abrirConexion(){
		adapter.cargarDatos(datos);
		adapter.abrirConexion();
	}
	
	public AdapterDAO getAdapter() {
		return adapter;
	}

	public void setAdapter(AdapterDAO adapter) {
		this.adapter = adapter;
	}

	public BaseDatos getBaseDatos() {
		return baseDatos;
	}

	public void setBaseDatos(BaseDatos baseDatos) {
		this.baseDatos = baseDatos;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}
	
	public ArrayList<Object> getDatos(){
		return datos;
	}
	
	public void setDatos(ArrayList<Object> datos){
		this.datos = datos;
	}
	
}