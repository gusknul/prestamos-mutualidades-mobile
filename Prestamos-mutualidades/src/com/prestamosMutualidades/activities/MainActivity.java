package com.prestamosMutualidades.activities;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.http.entity.StringEntity;
import org.json.*;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.loopj.android.http.*;
import com.prestamosMutualidades.beans.*;
import com.prestamosMutualidades.util.*;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;

@SuppressLint("SimpleDateFormat")
public class MainActivity extends Activity {

	EditText ip;
	TextView fechaActual;
	TextView pagosTotales;
	TextView cobrosTotales;
	
	
	private static final String FORMATO_FECHA = "dd/MM/yyyy";
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
		fechaActual.setText("Pendientes del día: " + formatDate.format(date));
		ip.setFocusable(true);
		adapterSocio = new AdapterDAO(this);
		
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
			
			adapterSocio.abrirConexion();
			if(adapterSocio.obtenerTotalCobros() < 0 && adapterSocio.obtenerTotalPagos() < 0){
				Toast.makeText(this, "No hay base de datos, Actulice su base de datos", Toast.LENGTH_SHORT).show();
			}
			else{
			pagosTotales.setText(" Total Pagos: $" + String.valueOf(adapterSocio.obtenerTotalPagos()));
			cobrosTotales.setText(" Total Cobranza: $" + String.valueOf(adapterSocio.obtenerTotalCobros()));}
		
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
		String URL = "http://"+ip.getText().toString()+"/prestamos-mutualidades/server/ws/MobileWS.php";
		
		AdapterDAO adapter = new AdapterDAO(this);
		
		adapter.abrirConexion();
			ArrayList<Pago> pagos = (ArrayList<Pago>) adapter.obtenerPagos();
			ArrayList<Cobro> cobros = (ArrayList<Cobro>)adapter.obtenerCobros();
		adapter.cerrarConexion();
		
			ArrayList<Object> data = new ArrayList<Object>();
				data.add(pagos);
				data.add(cobros);
		
		Gson gson = new Gson();
			String datas = gson.toJson(data);
			if(!ip.getText().toString().equals("")){
				
				
				final JSONObject jsonData = new JSONObject();
				try {
					jsonData.put("accion", "cargar");
					jsonData.put("param", datas);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				StringEntity entity = null;
				try {
					entity = new StringEntity(jsonData.toString());
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				AsyncHttpClient client = new AsyncHttpClient();
				client.setTimeout(60000);//tiempo de espera de la conexion 1 minuto
				client.post(this, URL, entity, "application/json", new AsyncHttpResponseHandler(){
					@Override
					 public void onSuccess(String response){
						
						try {
							JSONObject respuesta = new JSONObject(response);
							boolean success = respuesta.getBoolean("success");
							if(success){
								Toast.makeText(MainActivity.this, "Base de datos enviada con exito", Toast.LENGTH_LONG).show();
							}
							else{
								Toast.makeText(MainActivity.this, "El servidor no responde", Toast.LENGTH_SHORT).show();
							}
							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					 }
					
					@Override
					public void onFailure(int statusCode, Throwable error,
							String content) {
						// TODO Auto-generated method stub
						if(statusCode == 0){
							Toast.makeText(MainActivity.this, "El servidor no responde", Toast.LENGTH_SHORT).show();
						}
							
					}
				});
		
			}
			else{
				Toast.makeText(MainActivity.this, "Ingrese una direccion ip valida", Toast.LENGTH_SHORT).show();
			}
			
	}
	
	public void receiveData(View view){		
		String URL = "http://"+ip.getText().toString()+"/prestamos-mutualidades/server/ws/MobileWS.php"; 
		
		if(!ip.getText().toString().equals("")){
			
			JSONObject data = new JSONObject();
			try {
				data.put("accion", "actualizar");
				data.put("param", new JsonObject());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			StringEntity entity = null;
			try {
				entity = new StringEntity(data.toString());
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			AsyncHttpClient client = new AsyncHttpClient();
			client.post(this, URL, entity, "application/json", new AsyncHttpResponseHandler(){
				@Override
				 public void onSuccess(String response){
					loadData(response);
				 }
				
				@Override
				public void onFailure(int statusCode, Throwable error,
						String content) {
					// TODO Auto-generated method stub
					if(statusCode == 0){
						Toast.makeText(MainActivity.this, "El servidor no responde", Toast.LENGTH_SHORT).show();
					}
						
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

