package com.prestamosMutualidades.activities;

import com.prestamosMutualidades.beans.R;
import com.prestamosMutualidades.beans.R.layout;
import com.prestamosMutualidades.beans.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Cobros extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cobros);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cobros, menu);
		return true;
	}

}
