package com.WYJ.mynewsapp;

import java.util.List;

import com.WYJ.util.GetNewsListService;
import com.WYJ.util.DBNewsListManage;
import com.WYJ.adapter.MainListViewAdapter;
import com.WYJ.domain.News;
import com.nineoldandroids.view.ViewHelper;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.os.Build;

public class MainActivity extends ActionBarActivity {
	private DrawerLayout mDrawerLayout;
	


	private ListView listView;
	private MainListViewAdapter adapter;
	private List<News> newss;
	//��ǰҳ��
	private int pageNow = 0;
	//�ж��Ƿ����ڼ��ظ���
	private boolean isLoading = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		initView();
		initEvents();
		
		newss = new DBNewsListManage().getNewss(this);//�����ݿ��ȡ�����б�
		adapter = new MainListViewAdapter(newss, this);
		listView.addFooterView(View.inflate(this, R.layout.foot, null));
		listView.setAdapter(adapter);
		listView.setOnScrollListener(new MyScrollListener());
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(position!=0&&position!=newss.size()+1){
					Intent intent = new Intent(MainActivity.this,NewsDetailsActivity.class);
					intent.putExtra("url", newss.get(position-1).getUrl());
					startActivity(intent);
				}
			}
		});
		new MyAsyncTask().execute("");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public class MyAsyncTask extends AsyncTask<String, String, List<News>>{
		@Override
		protected List<News> doInBackground(String... params) {
			List<News> tempNews = new GetNewsListService().getNews(pageNow);
			return tempNews;
		}
		@Override//onPostExecute��������doBackgroundִ�к���UI�̵߳���
		protected void onPostExecute(List<News> tempNews) {
			//�ж��Ƿ��ǵ�һ�μ��أ��ǵĻ��������ݿ���ȡ�������滻�ɴ������ȡ�����ݣ������ʾ������һҳ������һҳ���ݼ��뵱ǰ��������
			if(pageNow!=0){ //�����ǵ�һ�μ���
			    newss.addAll(tempNews);
			}else{
				newss = tempNews;
				//����һҳ���ݼ������ݿ�
				new DBNewsListManage().addNewsList(tempNews, MainActivity.this);
			}
			if(newss!=null){
				adapter.setNews(newss);
				adapter.notifyDataSetChanged();//��̬����listview
			}
			isLoading=false;
		}
	}
	

	public class MyScrollListener implements OnScrollListener{

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
		
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
//			System.out.println(firstVisibleItem+"   "+visibleItemCount+"    "+totalItemCount);
			if(firstVisibleItem+visibleItemCount==totalItemCount&&isLoading==false){
				isLoading=true;
				pageNow++;
				new MyAsyncTask().execute("");
			}
		}
		
	}
	protected void dialog() {
		  AlertDialog.Builder builder = new Builder(MainActivity.this);
		  builder.setMessage("ȷ���˳���");

		  builder.setTitle("��ʾ");

		  builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {

		   @Override
		   public void onClick(DialogInterface dialog, int which) {
		    dialog.dismiss();
		  //  HomePageActivity.this.finish();
		  android.os.Process.killProcess(android.os.Process.myPid());//�˳�����
		   }
		  });

		  builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {

		   @Override
		   public void onClick(DialogInterface dialog, int which) {
		    dialog.dismiss();
		   }
		  });

		  builder.create().show();
		 } 
	
	public void OpenRightMenu(View view)
	{
		mDrawerLayout.openDrawer(Gravity.RIGHT);
		mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED,
				Gravity.RIGHT);
	}
	private void initView()
	{
		mDrawerLayout = (DrawerLayout) findViewById(R.id.id_drawerLayout);
		//mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED,
				//Gravity.RIGHT);
		listView = (ListView)findViewById(R.id.main_listview);
	}
	private void initEvents()
	{
		mDrawerLayout.setDrawerListener(new DrawerListener()
		{
			@Override
			public void onDrawerStateChanged(int newState)
			{
			}

			@Override
			public void onDrawerSlide(View drawerView, float slideOffset)
			{
				View mContent = mDrawerLayout.getChildAt(0);
				View mMenu = drawerView;
				float scale = 1 - slideOffset;
				float rightScale = 0.8f + scale * 0.2f;

				if (drawerView.getTag().equals("LEFT"))
				{

					float leftScale = 1 - 0.3f * scale;

					ViewHelper.setScaleX(mMenu, leftScale);
					ViewHelper.setScaleY(mMenu, leftScale);
					ViewHelper.setAlpha(mMenu, 0.6f + 0.4f * (1 - scale));
					ViewHelper.setTranslationX(mContent,
							mMenu.getMeasuredWidth() * (1 - scale));
					ViewHelper.setPivotX(mContent, 0);
					ViewHelper.setPivotY(mContent,
							mContent.getMeasuredHeight() / 2);
					mContent.invalidate();
					ViewHelper.setScaleX(mContent, rightScale);
					ViewHelper.setScaleY(mContent, rightScale);
				} else
				{
					ViewHelper.setTranslationX(mContent,
							-mMenu.getMeasuredWidth() * slideOffset);
					ViewHelper.setPivotX(mContent, mContent.getMeasuredWidth());
					ViewHelper.setPivotY(mContent,
							mContent.getMeasuredHeight() / 2);
					mContent.invalidate();
					ViewHelper.setScaleX(mContent, rightScale);
					ViewHelper.setScaleY(mContent, rightScale);
				}

			}

			@Override
			public void onDrawerOpened(View drawerView)
			{
			}

			@Override
			public void onDrawerClosed(View drawerView)
			{
				//mDrawerLayout.setDrawerLockMode(
						//DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);
			}
		});
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			//�˳�
			dialog();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
