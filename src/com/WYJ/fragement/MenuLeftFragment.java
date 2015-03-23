package com.WYJ.fragement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.WYJ.mynewsapp.R;
import com.WYJ.mynewsapp.Weather;

public class MenuLeftFragment extends Fragment
{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View view=inflater.inflate(R.layout.layout_menu, container, false);
		TextView serchweather=(TextView)view.findViewById(R.id.serchweather);
		serchweather.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(getActivity(),Weather.class);
				startActivity(intent);
			}
			
		});
		return view;
	}
}
