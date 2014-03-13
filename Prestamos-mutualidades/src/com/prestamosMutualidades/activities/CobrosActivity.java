package com.prestamosMutualidades.activities;

import java.util.ArrayList;
import java.util.List;

import com.prestamosMutualidades.adapter.AdapterDAO;
import com.prestamosMutualidades.beans.R;
import com.prestamosMutualidades.beans.Socio;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class CobrosActivity extends Activity {
	
	ListView listView;
	TextView folioSocio;
	TextView nombreSocio;
	TextView direccionSocio;
	TextView telefonoSocio;
	AdapterDAO adapterSocio;
	ArrayList<Socio> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cobros);
		
		folioSocio = (TextView) findViewById(R.id.folio_socio);
		nombreSocio = (TextView) findViewById(R.id.nombre_socio);
		direccionSocio = (TextView) findViewById(R.id.direccion);
		telefonoSocio = (TextView) findViewById(R.id.telefono);
		abrirConexion();
		registrarEventoClick();
		cargarLista();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cobros, menu);
		return true;
	}
	
	private void abrirConexion() {
		adapterSocio = new AdapterDAO(this);
		adapterSocio.abrirConexion();
	}
	
	@SuppressWarnings("unchecked")
	private void cargarLista() {
		  list = ((ArrayList<Socio>)adapterSocio.obtenerSocios());
		
		  ArrayAdapter<Socio> adapter = new ArrayAdapter<Socio>(this,android.R.layout.simple_list_item_1, list);
		  
		  listView = (ListView) findViewById(R.id.lViewMember);
		  
		  listView.setAdapter(adapter);
	}
	
	private void registrarEventoClick() {
		ListView myList = (ListView) findViewById(R.id.lViewMember);
		myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View viewClicked, 
					int position, long idInDB) {
				//lo que sucede cuando se presiona sobre un socio
				Integer id = list.get(position).getIdSocio();
				
				Socio socio = adapterSocio.getRow(id);
				
				if (socio!= null) {
					folioSocio.setText("Folio socio: " + String.valueOf(socio.getIdSocio()));
					nombreSocio.setText("Nombre: " + socio.getNombreCompleto());
					direccionSocio.setText("Direccion: " + socio.getDireccion());
					telefonoSocio.setText("Telefono: " + socio.getTelefono());
				}
				
			}
		});
	}

}
