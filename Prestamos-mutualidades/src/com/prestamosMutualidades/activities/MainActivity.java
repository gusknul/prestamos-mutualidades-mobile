package com.prestamosMutualidades.activities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.json.*;

import com.google.gson.Gson;
import com.loopj.android.http.*;
import com.prestamosMutualidades.beans.*;
import com.prestamosMutualidades.util.*;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;

@SuppressLint("SimpleDateFormat")
public class MainActivity extends Activity {

	EditText ip;
	TextView fechaActual;
	TextView pagosTotales;
	TextView cobrosTotales;
	
	private static final String FORMATO_FECHA = "dd-MM-yyyy";
	AdapterDAO adapterSocio;
	@Override
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ip = (EditText) findViewById(R.id.direccionIp);
		fechaActual = (TextView) findViewById(R.id.fecha_dia_main);
		pagosTotales = (TextView) findViewById(R.id.totalPagos);
		cobrosTotales = (TextView) findViewById(R.id.totalCobranza);
		SimpleDateFormat formatDate = new SimpleDateFormat(FORMATO_FECHA);
		Date date = new Date();
		fechaActual.setText(formatDate.format(date));
		
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		AdapterDAO adapter = new AdapterDAO(this);
		adapter.abrirConexion();
		pagosTotales.setText(" Total Pagos: $" + String.valueOf(adapter.obtenerTotalPagos()));
		cobrosTotales.setText(" Total Cobranza: $" + String.valueOf(adapter.obtenerTotalCobros()));
	}

	
	public void pagos(View view){
		Intent activity = new Intent(MainActivity.this, PagosActivity.class);
		AdapterDAO adapter = new AdapterDAO(MainActivity.this);
		AdapterClass ad =  (AdapterClass) MainActivity.this.getApplication();
		ad.setContext(MainActivity.this);
		ad.setAdapter(adapter);
		ad.setSocios(adapter.obtenerSocios());
		ad.abrirConexionSinRed();
		startActivity(activity);
	}
	
	
	
	public void cobros(View view){

		Intent activity = new Intent(MainActivity.this, CobrosActivity.class);
		AdapterDAO adapter = new AdapterDAO(MainActivity.this);
		AdapterClass ad =  (AdapterClass) MainActivity.this.getApplication();
		ad.setContext(MainActivity.this);
		ad.setAdapter(adapter);
		ad.setSocios(adapter.obtenerSocios());
		ad.abrirConexionSinRed();
		startActivity(activity);
	}
	
	public void pagosMes(View view){
		Intent intent = new Intent(MainActivity.this,PagosMes.class);
		AdapterDAO adapter = new AdapterDAO(MainActivity.this);
		AdapterClass ad =  (AdapterClass) MainActivity.this.getApplication();
		ad.setContext(MainActivity.this);
		ad.setAdapter(adapter);
		ad.setSocios(adapter.obtenerSocios());
		ad.abrirConexionSinRed();
		startActivity(intent);
		
	}
	
	public void sendDataToServer(View view){
		String  URL = "http://" + ip.getText().toString();
		AdapterDAO adapter = new AdapterDAO(this);
		
		adapter.abrirConexion();
			ArrayList<Socio> socios = (ArrayList<Socio>)adapter.obtenerListaSocios();
			ArrayList<Pago> pagos = (ArrayList<Pago>) adapter.obtenerPagos();
			ArrayList<Cobro> cobros = (ArrayList<Cobro>)adapter.obtenerCobros();
		adapter.cerrarConexion();
		
			ArrayList<Object> data = new ArrayList<Object>();
				data.add(socios);
				data.add(pagos);
				data.add(cobros);
		
		Gson gson = new Gson();
			String datas = gson.toJson(data);
		final RequestParams request = new RequestParams();
			request.put("dataBase", datas);
			
			if(!ip.getText().toString().equals("")){
				AsyncHttpClient client = new AsyncHttpClient();
				client.get(URL, request, new AsyncHttpResponseHandler(){
			
					@Override
					public void onSuccess(String response) {
						Toast.makeText(MainActivity.this, "Base de datos enviada con exito", Toast.LENGTH_LONG).show();
						}
					@Override
					public void onFailure(Throwable error, String content) {
						Toast.makeText(MainActivity.this, "No hay conexion con el servidor verifique su ip y su conexion de red", Toast.LENGTH_SHORT).show();
						}
				});
		
			}
			else{
				Toast.makeText(MainActivity.this, "Ingrese una direccion ip valida", Toast.LENGTH_SHORT).show();
			}
			
	}
	
	public void receiveData(View view){		
		String URL = "http://"+ip.getText().toString()+"/json/data.json"; 
		if(!ip.getText().toString().equals("")){
			AsyncHttpClient client = new AsyncHttpClient();
			client.get(URL, new AsyncHttpResponseHandler(){
				@Override
				public void onSuccess(String response) {
					loadData(response);
					}
				@Override
				public void onFailure(Throwable error, String content) {
					Toast.makeText(MainActivity.this, "No hay conexion con el servidor verifique su ip y su conexion de red", Toast.LENGTH_SHORT).show();
					}
			});
		}
		else{
			Toast.makeText(MainActivity.this, "Ingrese una direccion ip valida", Toast.LENGTH_SHORT).show();
		}
		
		
	}
	
	private void loadData(String jsonArray){
		Gson gson = new Gson();
		ArrayList<Socio> socios = new ArrayList<Socio>();
		ArrayList<Pago> pagos = new ArrayList<Pago>();
		ArrayList<Cobro> cobros = new ArrayList<Cobro>();
		ArrayList<Object> arrayObjetos = new ArrayList<Object>();
		
		try {
			JSONArray objetos = new JSONArray(jsonArray);
			
			JSONArray arraySociosJson = objetos.getJSONArray(0);
			JSONArray arrayPagosJson = objetos.getJSONArray(1);
			JSONArray arrayCobrosJson = objetos.getJSONArray(2);
			
			for(int i = 0; i < arraySociosJson.length() ; i++){
				JSONObject jsonObject = arraySociosJson.getJSONObject(i);
				Socio s = gson.fromJson(jsonObject.toString(), Socio.class);
				socios.add(s);
			}
			
			for(int j = 0; j < arrayPagosJson.length() ; j++){
				JSONObject jsonObject = arrayPagosJson.getJSONObject(j);
				Pago p = gson.fromJson(jsonObject.toString(), Pago.class);
				pagos.add(p);
			}
			
			for(int k = 0; k < arrayCobrosJson.length() ; k++){
				JSONObject jsonObject = arrayCobrosJson.getJSONObject(k);
				Cobro c = gson.fromJson(jsonObject.toString(), Cobro.class);
				cobros.add(c);
			}
			
			arrayObjetos.add(socios);
			arrayObjetos.add(pagos);
			arrayObjetos.add(cobros);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		AdapterDAO adapter = new AdapterDAO(this);
		AdapterClass ad =  (AdapterClass) this.getApplicationContext();
		ad.setContext(this);
		ad.setDatos(arrayObjetos);
		ad.setAdapter(adapter);
		ad.abrirConexion();
		Toast.makeText(this, "Base de datos cargada con exito", Toast.LENGTH_LONG).show();
	}

	
}

