package com.prestamosMutualidades.activities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import com.prestamosMutualidades.beans.Pago;
import com.prestamosMutualidades.beans.R;
import com.prestamosMutualidades.beans.R.layout;
import com.prestamosMutualidades.beans.R.menu;
import com.prestamosMutualidades.util.AdapterClass;
import com.prestamosMutualidades.util.AdapterDAO;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;
import android.widget.Toast;

public class PagosMes extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pagos_mes);
		
		AdapterClass cl = (AdapterClass) getApplicationContext();
		AdapterDAO adapterSocio = cl.getAdapter();
		
		if(adapterSocio != null){
			ArrayList<Pago> pagos =adapterSocio.obtenerPagos(); 
			for(int i = 0 ; i< pagos.size();i++){
				Date date = new Date();
				Date fechaPago = null;
				int mesPago = date.getMonth();
				SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
				String format = formatoFecha.format(date);
				try {
					fechaPago = formatoFecha.parse(pagos.get(i).getFecha());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(fechaPago.getMonth() != mesPago){
					pagos.remove(i);
				}
				else{
					continue;
				}
				
			}
			PagosMesAdapter adapter = new PagosMesAdapter(this, pagos, adapterSocio.obtenerSocios());
			ListView listView = (ListView) findViewById(R.id.list_view_pagos_mes);
			listView.setAdapter(adapter);
		}
		
		else Toast.makeText(this, "No hay datos cargados", Toast.LENGTH_SHORT).show();
		
	}

}
