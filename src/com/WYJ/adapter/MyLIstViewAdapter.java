package com.WYJ.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import com.WYJ.mynewsapp.R;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyLIstViewAdapter extends BaseAdapter {

	private Context mContext;
	private ArrayList<HashMap<String, Object>> list;
	public MyLIstViewAdapter(Context mContext,ArrayList<HashMap<String, Object>> list){
		this.mContext = mContext;
		this.list=list;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
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
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.weather_forecast_item, null);
			holder = new ViewHolder();
			holder.date = (TextView) convertView
					.findViewById(R.id.weather_forecast_date);
			holder.img = (ImageView) convertView
					.findViewById(R.id.weather_forecast_img);
			holder.weather = (TextView) convertView
					.findViewById(R.id.weather_forecast_weather);
			holder.temperature = (TextView) convertView
					.findViewById(R.id.weather_forecast_temperature);
			holder.wind = (TextView) convertView
					.findViewById(R.id.weather_forecast_wind);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		//获取字体
		Typeface face = Typeface.createFromAsset(mContext.getAssets(),
				"fonts/fangzhenglantingxianhe_GBK.ttf");
		holder.date.setText(list.get(position).get("date").toString());
		holder.img.setImageResource((Integer) list.get(position).get(
				"img"));
		holder.weather.setText(list.get(position).get("weather")
				.toString());
		holder.temperature.setText(list.get(position)
				.get("temperature").toString());
		holder.temperature.setTypeface(face);//设置字体
		holder.wind.setText(list.get(position).get("wind").toString());
		return convertView;
	
	}
	
	class ViewHolder {
		TextView date;
		ImageView img;
		TextView weather;
		TextView temperature;
		TextView wind;
	}

}
