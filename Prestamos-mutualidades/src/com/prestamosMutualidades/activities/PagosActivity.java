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
	MemberAdapter adapter ;
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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pagos, menu);
		return true;
	}
	
	private void cargarLista() {
		    list = adapterSocio.obtenerPagosSocio(adapterSocio.obtenerSocios());
			adapter = new MemberAdapter(this, list);
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
		Integer id = list.get(position).getIdSocio();
		ArrayList<String> pagos = adapterSocio.obtenerDatosPago(id);
			if (pagos!= null) {
				folioSocio.setText("Folio socio: " + pagos.get(0));
				nombreSocio.setText("Nombre: " + pagos.get(1));
				folioMutualista.setText("Folio mutualista: " + pagos.get(6));
				fechaPago.setText("Fecha de pago al socio: " + pagos.get(7));
				monto.setText("Monto: " + pagos.get(8));
				numeroSorteo.setText("# sorte: " + pagos.get(10));
				numeroBloc.setText("# Bloc: " + pagos.get(11));
				recargo.setText("Recargo: " +pagos.get(12));
				atraso.setText("Atraso: " + pagos.get(13));
				registrarPago(id, parent);
	}
			
}
	
	private void registrarPago(int id, final AdapterView<?> parent){
	    final int idSocio = id;
		
		registrarPago.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(adapterSocio.realizarPago(idSocio)){
					Toast.makeText(PagosActivity.this, "Pago realizado" ,Toast.LENGTH_SHORT).show();
					if(idSocio < parent.getChildCount()){
						parent.getChildAt(idSocio).setBackgroundColor(Color.GREEN);
					}
					else{
						return;
					}
					
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