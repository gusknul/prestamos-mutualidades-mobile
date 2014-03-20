package com.prestamosMutualidades.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.prestamosMutualidades.activities.MainActivity;
import com.prestamosMutualidades.beans.Cobro;
import com.prestamosMutualidades.beans.Pago;
import com.prestamosMutualidades.beans.Socio;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class RecibirDatos extends AsyncTask<Void, Void, String>{
	
	Context context;
	AdapterDAO adapter;
	String ip;
	public RecibirDatos(Context context, String ip) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.ip = ip;
	}
	
	
	String responseGson;
	private final String TAG = "Information";
	
	@Override
	protected String doInBackground(Void... params) {
		    final String url = "http://"+ip+"/datos.json";
		    
		    
		    if (verificaConexion(context)) {
		    	RestTemplate restTemplate = new RestTemplate();
				//restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
		    	restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
				responseGson = restTemplate.getForObject(url, String.class);
				return responseGson;
		    }
		    else{
		    	Toast.makeText(context, "No hay conexion intentelo mas tarde", Toast.LENGTH_SHORT).show();
		    	return null;
		    }
	}

	@Override
    protected void onPostExecute(String datos) {
		Gson gson = new Gson();
		Object[] responseData= gson.fromJson(datos.toString(), Object[].class);
		List<Object> datosRecibidos = Arrays.asList(responseData);
		adapter = new AdapterDAO(context);
		AdapterClass ad = (AdapterClass) context.getApplicationContext();
		ad.setContext(context);
		ad.setDatos(datosRecibidos);
		ad.setAdapter(adapter);
		ad.abrirConexion();
		Toast.makeText(context, "Base de datos cargada con exito", Toast.LENGTH_LONG).show();
    }
	
	public boolean verificaConexion(Context ctx) {
	    boolean bConectado = false;
	    ConnectivityManager connec = (ConnectivityManager) ctx
	            .getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo[] redes = connec.getAllNetworkInfo();
	    for (int i = 0; i < 2; i++) {
	        if (redes[i].getState() == NetworkInfo.State.CONNECTED) {
	            bConectado = true;
	        }
	    }
	    return bConectado;
	}


}
