package com.prestamosMutualidades.activities;

import java.util.ArrayList;
import com.prestamosMutualidades.beans.R;
import com.prestamosMutualidades.beans.Socio;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MemberAdapter extends ArrayAdapter<Socio>{

	private Context context;
	private ArrayList<Socio> list;
	
	public MemberAdapter(Context context, ArrayList<Socio> list) {
		super(context, R.layout.item_member, list);
		this.context = context;
		this.list = list;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.item_member, parent, false);
		
		TextView textViewIdMember = (TextView) rowView.findViewById(R.id.text_view_id_member);
		TextView textViewNameMember = (TextView) rowView.findViewById(R.id.text_view_name_member);
		TextView textViewAddress = (TextView) rowView.findViewById(R.id.text_view_address);
		TextView textViewPhoneNumber = (TextView) rowView.findViewById(R.id.text_view_phone_number);
		
		Socio socio = list.get(position);
		
		textViewIdMember.setText(String.valueOf(socio.getIdSocio()));
		textViewNameMember.setText(socio.getNombreCompleto());
		textViewAddress.setText(socio.getDireccion());
		textViewPhoneNumber.setText(socio.getTelefono());

		return rowView;
	}
	

}
