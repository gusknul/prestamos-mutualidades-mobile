package com.prestamosMutualidades.activities;

import java.util.ArrayList;

import com.prestamosMutualidades.beans.Cobro;
import com.prestamosMutualidades.beans.R;
import com.prestamosMutualidades.beans.Socio;

import android.content.Context;
import android.graphics.Color;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CobrosAdapter extends ArrayAdapter<Cobro>{
	private Context context;
	private ArrayList<Cobro> list;
	private SparseArray<Socio> socios;
	
	public CobrosAdapter(Context context, ArrayList<Cobro> list,SparseArray<Socio> socios) {
		super(context, R.layout.item_member, list);
		this.context = context;
		this.list = list;
		this.socios = socios;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.item_member, parent, false);
		
		TextView textViewIdMember = (TextView) rowView.findViewById(R.id.text_view_id_member);
		TextView textViewNameMember = (TextView) rowView.findViewById(R.id.text_view_name_member);
		TextView textViewAddress = (TextView) rowView.findViewById(R.id.text_view_address);
		TextView textViewPhoneNumber = (TextView) rowView.findViewById(R.id.text_view_phone_number);
		
		
		Socio socio = socios.get(list.get(position).getIdSocio());
		
		if (list.get(position).getEstado().equals("completado"))
			rowView.setBackgroundColor(Color.parseColor("#468847"));
		else 
			rowView.setBackgroundColor(Color.parseColor("#3A87AD"));
		
		textViewIdMember.setText(String.valueOf(socio.getIdSocio()));
		textViewNameMember.setText(socio.getNombreCompleto());
		textViewAddress.setText(socio.getDireccion());
		textViewPhoneNumber.setText(socio.getTelefono());
		
		
		
		return rowView;
	}

}
