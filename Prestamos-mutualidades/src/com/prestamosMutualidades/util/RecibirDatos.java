package com.prestamosMutualidades.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
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
	String responseGson;
	private final String TAG = "Information";
	
	public RecibirDatos(Context context, String ip) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.ip = ip;
	}
	
	
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
		ArrayList<Socio> socios = new ArrayList<Socio>();
		ArrayList<Pago> pagos = new ArrayList<Pago>();
		ArrayList<Cobro> cobros = new ArrayList<Cobro>();
		ArrayList<Object> arrayObjetos = new ArrayList<Object>();
		
		try {
			JSONArray objetos = new JSONArray(datos);
			
			JSONArray arraySociosJson = objetos.getJSONArray(0);
			JSONArray arrayPagosJson = objetos.getJSONArray(1);
			JSONArray arrayCobrosJson = objetos.getJSONArray(2);
			
			for(int i = 0; i < arraySociosJson.length() ; i++){
				JSONObject jsonObject = arraySociosJson.getJSONObject(i);
				Socio s = gson.fromJson(jsonObject.toString(), Socio.class);
				socios.add(s);
			}
			
			for(int j = 0; j < arrayPagosJson.length() ; j++){
				JSONObject jsonObject = arrayPagosJson.getJSONObject(j);
				Pago p = gson.fromJson(jsonObject.toString(), Pago.class);
				pagos.add(p);
			}
			
			for(int k = 0; k < arrayCobrosJson.length() ; k++){
				JSONObject jsonObject = arrayCobrosJson.getJSONObject(k);
				Cobro c = gson.fromJson(jsonObject.toString(), Cobro.class);
				cobros.add(c);
			}
			
			arrayObjetos.add(socios);
			arrayObjetos.add(pagos);
			arrayObjetos.add(cobros);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		adapter = new AdapterDAO(context);
		AdapterClass ad =  (AdapterClass) context.getApplicationContext();
		ad.setContext(context);
		ad.setDatos(arrayObjetos);
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
