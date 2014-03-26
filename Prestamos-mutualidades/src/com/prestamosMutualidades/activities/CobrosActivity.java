package com.prestamosMutualidades.activities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import com.prestamosMutualidades.beans.R;
import com.prestamosMutualidades.beans.Socio;
import com.prestamosMutualidades.util.AdapterClass;
import com.prestamosMutualidades.util.AdapterDAO;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ShowToast")
public class CobrosActivity extends Activity {
	
	ListView listView;
	TextView fechaDia;
	
	
	TextView folioSocio;
	TextView nombreSocio;
	TextView direccionSocio;
	TextView telefonoSocio;
	
	TextView folioMutualista;
	TextView numeroBloque;
	TextView monto;
	TextView atraso;
	TextView recargo;
	TextView numeroSorteo;
	TextView fechaPagoSocio;	
	
	AdapterDAO adapterSocio;
	ArrayList<Socio> list;
	ArrayAdapter<Socio> adapter;
	
	EditText buscarSocio;
	Button buscar;
	Button registrarCobro;

	@SuppressLint("SimpleDateFormat")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cobros);
		
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date date = new Date();
		
		fechaDia = (TextView) findViewById(R.id.fecha_dia);
		fechaDia.setText(dateFormat.format(date));
		folioSocio = (TextView) findViewById(R.id.folio_socio_cobro);
		nombreSocio = (TextView) findViewById(R.id.nombre_socio_cobro);
		direccionSocio = (TextView) findViewById(R.id.direccion_cobro);
		telefonoSocio = (TextView) findViewById(R.id.telefono_cobro);
		
		folioMutualista = (TextView) findViewById(R.id.folio_mutualista_cobro);
		numeroBloque = (TextView) findViewById(R.id.numero_bloc_cobro);
		monto = (TextView) findViewById(R.id.monto_cobro);
		atraso = (TextView) findViewById(R.id.atraso_cobro);
		recargo = (TextView) findViewById(R.id.recargo_cobro);
		numeroSorteo = (TextView) findViewById(R.id.numero_sorteo_cobro);
		fechaPagoSocio = (TextView) findViewById(R.id.fecha_cobro);
		
		buscarSocio = (EditText) findViewById(R.id.buscar_socio_cobro);
		buscar = (Button) findViewById(R.id.btn_buscar_socio_cobros);
		listView = (ListView) findViewById(R.id.lViewMember);
		registrarCobro = (Button) findViewById(R.id.btn_registrar_cobro_cobros);
		
		AdapterClass cl = (AdapterClass) getApplicationContext();
		adapterSocio = cl.getAdapter();
		
		if(adapterSocio != null){
			registrarEventoClick();
			cargarLista();
			buscarSocio();
		}
		
		else Toast.makeText(this, "No hay datos cargados", Toast.LENGTH_SHORT).show();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cobros, menu);
		return true;
	}
	
	
	private void cargarLista() {
		  list = adapterSocio.obtenerCobrosSocio(adapterSocio.obtenerSocios());
		  adapter = new ArrayAdapter<Socio>(this,android.R.layout.simple_list_item_1, list);
		  listView.setAdapter(adapter);
	}
	
	private void registrarEventoClick() {
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long idInDB){
					setText(position,parent);

				}});
	}
	
	private void setText(int position,AdapterView<?> parent){
		Integer id = list.get(position).getIdSocio();
		ArrayList<String> cobro = adapterSocio.obtenerDatosCobro(id);
			if (cobro!= null) {
					folioSocio.setText("Folio socio: " + cobro.get(0));
					nombreSocio.setText("Nombre: " + cobro.get(1));
					direccionSocio.setText("Direccion: " + cobro.get(2));
					telefonoSocio.setText("Telefono: " + cobro.get(3));
					folioMutualista.setText("Folio mutualista: " +cobro.get(6));
					numeroBloque.setText("# bloc: " + cobro.get(10));
					monto.setText("Monto: " + cobro.get(8));
					atraso.setText("Atraso: " + cobro.get(11));
					recargo.setText("Recargo: " + cobro.get(13) );
					numeroSorteo.setText("# Sorteo: " + cobro.get(12));
					fechaPagoSocio.setText("Fecha de pago al socio: " + cobro.get(7));
					registrarCobro(id,parent);
	}

	}
	
	private void buscarSocio(){
		buscar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String id = buscarSocio.getText().toString();
				int position = Integer.parseInt(id);
				if( position < listView.getChildCount() ){
					listView.getChildAt(position).setFocusable(true);
				}
				else{
					Toast.makeText(CobrosActivity.this, "No existe un socio con ese folio", Toast.LENGTH_SHORT).show();
					return;
				}
				
			}
		});
	}
	
	private void registrarCobro(int id, final AdapterView<?> parent){
			final int idSocio = id;
	    	registrarCobro.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					if(adapterSocio.realizarCobro(idSocio)){
						Toast.makeText(CobrosActivity.this, "Pago realizado" ,Toast.LENGTH_SHORT).show();
						if(idSocio < parent.getChildCount()){
							parent.getChildAt(idSocio).setBackgroundColor(Color.GREEN);
						}
						else{
							return;
						}
					}
					else{
						Toast.makeText(CobrosActivity.this, "no se puede realizar el pago", Toast.LENGTH_SHORT).show();
						}
					}
				});
		}
	
}