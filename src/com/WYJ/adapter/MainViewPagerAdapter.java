package com.WYJ.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MainViewPagerAdapter extends PagerAdapter{ 
	List<ImageView> imageViews;
	
	public MainViewPagerAdapter(List<ImageView> imageViews){
		this.imageViews=imageViews;
	}
	
	@Override
	public int getCount() {
		return imageViews.size();
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(imageViews.get(position));
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		container.addView(imageViews.get(position));
		return imageViews.get(position);
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0==arg1;
	}

}
