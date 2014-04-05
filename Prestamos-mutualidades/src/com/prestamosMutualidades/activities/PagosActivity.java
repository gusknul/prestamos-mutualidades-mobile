package com.prestamosMutualidades.activities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		setContentView(R.layout.activity_pagos);
		fechaDia = (TextView) findViewById(R.id.fecha_dia_pagos);
		folioSocio  = (TextView) findViewById(R.id.folio_socio_pago);
		nombreSocio  = (TextView) findViewById(R.id.nombre_socio_pago);
		folioMutualista  = (TextView) findViewById(R.id.folio_mutualista_pago);
		monto  = (TextView) findViewById(R.id.monto_pago);
		atraso  = (TextView) findViewById(R.id.atraso_pago);
		numeroSorteo = (TextView) findViewById(R.id.numero_sorteo_pago);
		fechaPago  = (TextView) findViewById(R.id.fecha_pago);
		fechaDia.setText("Pagos del día " + dateFormat.format(date));
		registrarPago = (Button)findViewById(R.id.btn_registrar_cobro_pagos);
		editTextBuscar = (EditText) findViewById(R.id.buscarSocioPago);
		
		AdapterClass cl = (AdapterClass) getApplication();
		adapterSocio = cl.getAdapter();
		
		if(adapterSocio != null){
			registrarEventoClick();
			try {
				cargarLista();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			generateSearch();
		}
		
		else Toast.makeText(this, "No hay datos cargados", Toast.LENGTH_SHORT).show();;
		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	private void cargarLista() throws ParseException {
		ArrayList<Pago> pagos = adapterSocio.obtenerPagos();
		Iterator<Pago> itr = pagos.iterator();
		while (itr.hasNext()) {
			Pago pago = itr.next();
			if (!comparaFechas(pago.getFecha())) {
				itr.remove();
			}
		}
		
		adapter = new PagosAdapter(this, pagos , adapterSocio.obtenerSocios());
		listView = (ListView) findViewById(R.id.list_view_pay_member);
		listView.setAdapter(adapter);
	}
	
	public boolean comparaFechas(String fechaComparar) throws ParseException{
		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		String fechaActual = formatoFecha.format(date);
		if (fechaComparar.equals(fechaActual)){
			return true;
		}
		else{
		return false;
		}
	}
	
	private void registrarEventoClick() {
		ListView myList = (ListView) findViewById(R.id.list_view_pay_member);
		myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@SuppressLint("NewApi")
			@Override
			public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long idInDB){
					setText(position , parent);
					if(adapter.getItem(position).getEstado().equals("completado")){
						registrarPago.setEnabled(false);
					}
					else{
						registrarPago.setEnabled(true);
					}
				}});
	}
	
	
	private void setText(int position,AdapterView<?> parent){
			if (adapter.getItem(position) != null) {
				Pago pago = adapter.getItem(position);
				folioSocio.setText("Folio socio: " + String.valueOf(adapterSocio.obtenerSocios().get(pago.getIdSocio()).getIdSocio()));
				nombreSocio.setText("Nombre: " + adapterSocio.obtenerSocios().get(pago.getIdSocio()).getNombreCompleto());
				folioMutualista.setText("Folio mutualista: " + pago.getIdMutualidad());
				fechaPago.setText("Fecha de pago al socio: " + pago.getFecha());
				monto.setText("Monto: $" + pago.getMonto());
				numeroSorteo.setText("# sorteo: " + pago.getSorteo());
				atraso.setText("Atraso: " + pago.getAtraso() + " dias");
				registrarPago(position, parent);
	}
			
}
	
	private void registrarPago(final int position, final AdapterView<?> parent){
		
		registrarPago.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(adapterSocio.realizarPago(adapter.getItem(position).getIdPago())){
					Toast.makeText(PagosActivity.this, "Pago realizado" ,Toast.LENGTH_SHORT).show();
					Pago pago = adapter.getItem(position);
					pago.setEstado("completado");
					adapter = new PagosAdapter(PagosActivity.this, adapter.getList(), adapterSocio.obtenerSocios());
					listView.setAdapter(adapter);
					registrarPago.setEnabled(false);
					
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