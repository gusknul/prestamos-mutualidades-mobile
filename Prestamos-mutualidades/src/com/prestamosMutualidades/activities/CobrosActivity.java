package com.prestamosMutualidades.activities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.prestamosMutualidades.beans.Cobro;
import com.prestamosMutualidades.beans.R;
import com.prestamosMutualidades.beans.Socio;
import com.prestamosMutualidades.util.AdapterClass;
import com.prestamosMutualidades.util.AdapterDAO;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.util.SparseArray;
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
	
	EditText buscarSocio;
	EditText adelantos;
	Button buscar;
	Button registrarCobro;
	CobrosAdapter adapter;
	SparseArray<Integer> data;
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
		registrarCobro = (Button) findViewById(R.id.btn_registrar_cobro_cobros);
		adelantos = (EditText)findViewById(R.id.edit_text_adelanto);
		
		AdapterClass cl = (AdapterClass) getApplicationContext();
		adapterSocio = cl.getAdapter();
		
		if(adapterSocio != null){
			cargarLista();
			registrarEventoClick();
			generateSearch();
		}
		
		else Toast.makeText(this, "No hay datos cargados", Toast.LENGTH_SHORT).show();
	}

	private void cargarLista() {
		adapter = new CobrosAdapter(this, adapterSocio.obtenerCobros(), adapterSocio.obtenerSocios());
		listView = (ListView) findViewById(R.id.list_view_payment_member);
		listView.setAdapter(adapter);
	}
	
	private void registrarEventoClick() {
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long idInDB){
				setText(position,parent);
				}});
	}
	
	private void setText(int position,AdapterView<?> parent){
		
			if (adapter.getItem(position) != null) {
				Cobro cobro = adapter.getItem(position);
					folioSocio.setText("Folio socio: " + String.valueOf(adapterSocio.obtenerSocios().get(cobro.getIdSocio()).getIdSocio()));
					nombreSocio.setText("Nombre: " + adapterSocio.obtenerSocios().get(cobro.getIdSocio()).getNombreCompleto());
					direccionSocio.setText("Direccion: " + adapterSocio.obtenerSocios().get(cobro.getIdSocio()).getDireccion());
					telefonoSocio.setText("Telefono: " + adapterSocio.obtenerSocios().get(cobro.getIdSocio()).getTelefono());
					folioMutualista.setText("Folio mutualista: " + String.valueOf(cobro.getIdMutualidad()));
					numeroBloque.setText("# bloc: " + String.valueOf(cobro.getFolio()));
					monto.setText("Monto: " + String.valueOf(cobro.getMonto()));
					atraso.setText("Atraso: " + String.valueOf(cobro.getAtraso()));
					recargo.setText("Recargo: " + String.valueOf(cobro.getRecargo()));
					numeroSorteo.setText("# Sorteo: " + String.valueOf(cobro.getNumeroSorteo()));
					fechaPagoSocio.setText("Fecha de pago al socio: " + cobro.getDate());
					registrarCobro(position,parent);
	}
	}
	
	private void registrarCobro(final int position, final AdapterView<?> parent){
	    	registrarCobro.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					if(adapterSocio.realizarCobro(adapter.getItem(position).getIdSocio(),Integer.parseInt(adelantos.getText().toString()))){
						Toast.makeText(CobrosActivity.this, "Cobro realizado" ,Toast.LENGTH_SHORT).show();
						Cobro cobro = adapter.getItem(position);
						cobro.setEstado("completado");
						adapter = new CobrosAdapter(CobrosActivity.this, adapter.getList(), adapterSocio.obtenerSocios());
						listView.setAdapter(adapter);

					}
					else{
						Toast.makeText(CobrosActivity.this, "no se puede realizar el pago", Toast.LENGTH_SHORT).show();
						}
					}
				});
		}
	
	
	public void searchMember(View view){
		if(!buscarSocio.getText().toString().equals("")){
			int position = Integer.parseInt(buscarSocio.getText().toString());			
			if( data.indexOfKey(position) >= 0){
				listView.smoothScrollToPosition(data.get(position));
			}
			else{
				Toast.makeText(this, "No existe un usuario con ese ID, intente de nuevo", Toast.LENGTH_SHORT).show();
			}
		}
		else{
			Toast.makeText(this, "Debe ingresar un id para iniciar la busqueda", Toast.LENGTH_SHORT).show();
		}
	}

	
	private void generateSearch(){
		
		data = new SparseArray<Integer>();
		for(int i = 0; i < adapter.getCount(); i++ ){
				data.put(adapter.getItem(i).getIdSocio() ,i);
		}	
	}
	
}