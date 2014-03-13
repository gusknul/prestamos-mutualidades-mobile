package com.prestamosMutualidades.activities;

import com.prestamosMutualidades.adapter.AdapterDAO;
import com.prestamosMutualidades.beans.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	Intent activity;
	Button cobranza;
	Button pagos;
	
	AdapterDAO adapterSocio;
	@Override
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		pagos = (Button)findViewById(R.id.pagos);
		cobranza = (Button) findViewById(R.id.cobros);
		pagos();
		cobros();
		
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void pagos(){
		
		pagos.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				activity = new Intent(MainActivity.this, PagosActivity.class);
				startActivity(activity);
			}
		});
	}
	
	
	public void cobros(){
		
		cobranza.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				activity = new Intent(MainActivity.this, CobrosActivity.class);
				startActivity(activity);
			}
		});
	}

}
