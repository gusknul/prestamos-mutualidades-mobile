package com.prestamosMutualidades.activities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.json.JSONObject;
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
import com.prestamosMutualidades.beans.R;
import com.prestamosMutualidades.beans.Socio;
import com.prestamosMutualidades.util.AdapterClass;
import com.prestamosMutualidades.util.AdapterDAO;
import com.prestamosMutualidades.util.RecibirDatos;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("SimpleDateFormat")
public class MainActivity extends Activity {

	Intent activity;
	
	Button cobranza;
	Button pagos;
	Button actualizarDB;
	Button cargarDBS;
	EditText ip;
	TextView fechaActual;
	
	private static final String FORMATO_FECHA = "dd-MM-yyyy";
	AdapterDAO adapterSocio;
	@Override
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initComponents();
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void initComponents(){
		pagos = (Button)findViewById(R.id.pagos);
		cobranza = (Button) findViewById(R.id.cobros);
		actualizarDB = (Button) findViewById(R.id.actualizar);
		cargarDBS = (Button) findViewById(R.id.cargarDatos);
		ip = (EditText) findViewById(R.id.direccionIp);
		fechaActual = (TextView) findViewById(R.id.fecha_dia_main);
		
		SimpleDateFormat formatDate = new SimpleDateFormat(FORMATO_FECHA);
		Date date = new Date();
		fechaActual.setText(formatDate.format(date));		
		pagos();
		cobros();
		actualizar();
		cargarDatosServidor();
	}
	
	
	private void pagos(){
		
		pagos.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				activity = new Intent(MainActivity.this, PagosActivity.class);
				startActivity(activity);
			}
		});
	}
	
	
	
	private void cobros(){
		
		cobranza.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				activity = new Intent(MainActivity.this, CobrosActivity.class);
				startActivity(activity);
			}
		});
	}
	
	
	private void actualizar(){
		final String direccionIp = ip.getText().toString();
		Log.i("ip", direccionIp);
		
			actualizarDB.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub					
					if(direccionIp != ""){
						RecibirDatos recibir = new RecibirDatos(MainActivity.this,ip.getText().toString());
						recibir.execute();
					}
					else{
						Toast.makeText(MainActivity.this, "Ingrese una direccion ip valida", Toast.LENGTH_SHORT).show();
					}
				}
			});
	}
	
	private void cargarDatosServidor(){
		cargarDBS.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
//	private class EnviarDatos extends AsyncTask<Void, Void , Void>{
//
//		@Override
//		protected Void doInBackground(Void... params) {
//			// TODO Auto-generated method stub
//			
//			Gson gson = new Gson();
//			ArrayList<Socio> socios = adapterSocio.obtenerListaSocios();
//			ArrayList<Pago> pagos = adapterSocio.obtenerPagos();
//			ArrayList<Cobro> cobros = adapterSocio.obtenerCobros();
//			
//			
//			ArrayList<Object> o = new ArrayList<Object>();
//			o.add(socios);
//			o.add(pagos);
//			o.add(cobros);
//			String url = "http://127.0.0.1";
//			String message = gson.toJson(o);
//			
//			HttpHeaders requestHeaders = new HttpHeaders();
//			requestHeaders.setContentType(MediaType.APPLICATION_JSON);
//			HttpEntity<ArrayList<Object>> requestEntity = new HttpEntity<ArrayList<Object>>(o, requestHeaders);
//			RestTemplate restTemplate = new RestTemplate();
//			restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
//			restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
//			ResponseEntity<String> responseEntity = restTemplate.exchange(message, HttpMethod.POST, requestEntity, String.class);
//			String result = responseEntity.getBody();
//			return null;
//		}
//		
//	}
	
}
