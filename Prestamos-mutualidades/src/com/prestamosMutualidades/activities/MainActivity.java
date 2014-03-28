package com.prestamosMutualidades.activities;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.prestamosMutualidades.beans.R;
import com.prestamosMutualidades.util.AdapterClass;
import com.prestamosMutualidades.util.AdapterDAO;
import com.prestamosMutualidades.util.RecibirDatos;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
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
				AdapterDAO adapter = new AdapterDAO(MainActivity.this);
				AdapterClass ad =  (AdapterClass) MainActivity.this.getApplication();
				ad.setContext(MainActivity.this);
				ad.setAdapter(adapter);
				ad.abrirConexionSinRed();
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
				AdapterDAO adapter = new AdapterDAO(MainActivity.this);
				AdapterClass ad =  (AdapterClass) MainActivity.this.getApplication();
				ad.setContext(MainActivity.this);
				ad.setAdapter(adapter);
				ad.abrirConexionSinRed();
				
				startActivity(activity);
			}
		});
	}
	
	
	private void actualizar(){		
			actualizarDB.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub					
					
					
					if(!ip.getText().toString().equals("")){
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
	

	
}

