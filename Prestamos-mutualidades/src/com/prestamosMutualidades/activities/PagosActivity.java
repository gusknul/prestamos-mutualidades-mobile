package com.prestamosMutualidades.activities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.prestamosMutualidades.beans.Cobro;
import com.prestamosMutualidades.beans.Pago;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("SimpleDateFormat")
public class PagosActivity extends Activity {

	
	TextView fechaDia;
	TextView folioSocio;
	TextView nombreSocio;
	TextView folioMutualista;
	TextView numeroBloc;
	TextView monto;
	TextView atraso;
	TextView recargo;
	TextView numeroSorteo;
	TextView fechaPago;
	AdapterDAO adapterSocio;
	ArrayList<Socio> list;
	ArrayList<View> view;
	ListView listView;
	Button registrarPago;
	EditText editTextBuscar;
	PagosAdapter adapter ;
	SparseArray<Integer> data;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date date = new Date();
		setContentView(R.layout.activity_pagos);
		fechaDia = (TextView) findViewById(R.id.fecha_dia_pagos);
		folioSocio  = (TextView) findViewById(R.id.folio_socio_pago);
		nombreSocio  = (TextView) findViewById(R.id.nombre_socio_pago);
		folioMutualista  = (TextView) findViewById(R.id.folio_mutualista_pago);
		numeroBloc  = (TextView) findViewById(R.id.numero_bloc_pago);
		monto  = (TextView) findViewById(R.id.monto_pago);
		atraso  = (TextView) findViewById(R.id.atraso_pago);
		recargo  = (TextView) findViewById(R.id.recargo_pago);
		numeroSorteo = (TextView) findViewById(R.id.numero_sorteo_pago);
		fechaPago  = (TextView) findViewById(R.id.fecha_pago);
		fechaDia.setText(dateFormat.format(date));
		registrarPago = (Button)findViewById(R.id.btn_registrar_cobro_pagos);
		editTextBuscar = (EditText) findViewById(R.id.buscarSocioPago);
		
		AdapterClass cl = (AdapterClass) getApplication();
		adapterSocio = cl.getAdapter();
		
		if(adapterSocio != null){
			registrarEventoClick();
			cargarLista();
			generateSearch();
		}
		
		else Toast.makeText(this, "No hay datos cargados", Toast.LENGTH_SHORT).show();;
		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	private void cargarLista() {
		adapter = new PagosAdapter(this, adapterSocio.obtenerPagos(), adapterSocio.obtenerSocios());
		listView = (ListView) findViewById(R.id.list_view_pay_member);
		listView.setAdapter(adapter);
	}
	
	private void registrarEventoClick() {
		ListView myList = (ListView) findViewById(R.id.list_view_pay_member);
		myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@SuppressLint("NewApi")
			@Override
			public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long idInDB){
					setText(position , parent);
					
				}});
	}
	
	
	private void setText(int position,AdapterView<?> parent){
			if (adapter.getItem(position) != null) {
				Pago pago = adapter.getItem(position);
				folioSocio.setText("Folio socio: " + String.valueOf(adapterSocio.obtenerSocios().get(pago.getIdSocio()).getIdSocio()));
				nombreSocio.setText("Nombre: " + adapterSocio.obtenerSocios().get(pago.getIdSocio()).getNombreCompleto());
				folioMutualista.setText("Folio mutualista: " + pago.getIdMutualidad());
				fechaPago.setText("Fecha de pago al socio: " + pago.getFecha());
				monto.setText("Monto: " + pago.getMonto());
				numeroSorteo.setText("# sorte: " + pago.getSorteo());
				numeroBloc.setText("# Bloc: " + pago.getNumeroBloc());
				recargo.setText("Recargo: " +pago.getRecargo());
				atraso.setText("Atraso: " + pago.getAtraso());
				registrarPago(position, parent);
	}
			
}
	
	private void registrarPago(final int position, final AdapterView<?> parent){
		
		registrarPago.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(adapterSocio.realizarPago(adapter.getItem(position).getIdSocio())){
					Toast.makeText(PagosActivity.this, "Pago realizado" ,Toast.LENGTH_SHORT).show();
					Pago pago = adapter.getItem(position);
					pago.setEstado("completado");
					adapter = new PagosAdapter(PagosActivity.this, adapter.getList(), adapterSocio.obtenerSocios());
					listView.setAdapter(adapter);
					
				}
				else{
					Toast.makeText(PagosActivity.this, "no se puede realizar el pago", Toast.LENGTH_SHORT).show();
					}
				}
			
		});
	}

	

	public void searchMember(View view){
		
		if(!editTextBuscar.getText().toString().equals("")){
			int position = Integer.parseInt(editTextBuscar.getText().toString());
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