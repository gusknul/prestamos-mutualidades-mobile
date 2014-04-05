package com.prestamosMutualidades.activities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.prestamosMutualidades.beans.Pago;
import com.prestamosMutualidades.beans.R;
import com.prestamosMutualidades.beans.Socio;

public class PagosMesAdapter extends ArrayAdapter<Pago> {

	private Context context;
	private ArrayList<Pago> list;
	SparseArray<Socio> socios;

	public PagosMesAdapter(Context context, ArrayList<Pago> list,
			SparseArray<Socio> socios) {
		super(context, R.layout.item_pagos, list);
		this.context = context;
		this.list = list;
		this.socios = socios;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.item_pagos, parent, false);

		TextView textViewIdMember = (TextView) rowView
				.findViewById(R.id.text_view_id_member_pagos);
		TextView textViewNameMember = (TextView) rowView
				.findViewById(R.id.text_view_name_member_pagos);
		TextView textViewMutualidad = (TextView) rowView
				.findViewById(R.id.text_id_mutualidad_pagos);
		TextView textViewFechaPago = (TextView) rowView
				.findViewById(R.id.text_view_fecha_pago);

		Socio socio = socios.get(list.get(position).getIdSocio());
		if (list.get(position).getEstado().equals("completado"))
			rowView.setBackgroundColor(Color.parseColor("#F89406"));
		else 
			rowView.setBackgroundColor(Color.parseColor("#999999"));
		textViewIdMember.setText("Folio Socio: "
				+ String.valueOf(socio.getIdSocio()));
		textViewNameMember
				.setText("Nombre Socio: " + socio.getNombreCompleto());
		textViewMutualidad.setText("Folio mutualidad: "
				+ String.valueOf(list.get(position).getIdMutualidad()));
		textViewFechaPago.setText("Fecha de pago: "
				+ list.get(position).getFecha());

		return rowView;
	}
}
