package com.WYJ.adapter;

import com.WYJ.mynewsapp.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyGridViewAdapter extends BaseAdapter {
	private Context mContext;
	private String[] citys;
	public MyGridViewAdapter(Context context,String[] citys){
		this.mContext=context;
		this.citys=citys;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return citys.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.city_item, null);
			holder = new ViewHolder();
			holder.city = (TextView) convertView.findViewById(R.id.city);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.city.setText(citys[position]);
		return convertView;
	
	}
	
	class ViewHolder {
		TextView city;
	}	

}
