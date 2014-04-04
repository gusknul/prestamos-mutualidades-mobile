package com.prestamosMutualidades.beans;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class PagosMes extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pagos_mes);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pagos_mes, menu);
		return true;
	}

}
