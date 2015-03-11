package com.WYJ.mynewsapp;

import com.WYJ.animation.MyAnimation;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class NewsDetailsActivity extends ActionBarActivity {
	private WebView webView;
	private String html;
	ImageButton imgbtn;
	private RelativeLayout relativeLayout;
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if(msg.arg1==1){
				relativeLayout.setVisibility(View.GONE);
				webView.getSettings().setBlockNetworkImage(false);
				webView.getSettings().setJavaScriptEnabled(false);
				webView.setVisibility(View.VISIBLE);
				webView.loadDataWithBaseURL(null, html.replace("data-url", "src").replace("class=\"lazy-load\"","").replace("本日点击排行榜", "").replace("查看原图", ""), "text/html", "utf-8",null);
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_news_details);
		String url = getIntent().getStringExtra("url");
		webView = (WebView)findViewById(R.id.news_details_webview);
		webView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.addJavascriptInterface(new InJavaScriptLocalObj(), "local_obj");
		webView.setWebViewClient(new MyWebViewClient());
		webView.getSettings().setBlockNetworkImage(true);
		webView.loadUrl("http://m.baidu.com/news?tn=bdbody&src="+url+"&pu=sz@1320_2001,usm@4,ta@iphone_1_4.3_3_533&bd_page_type=1");
		relativeLayout = (RelativeLayout)findViewById(R.id.activity_news_details_relative_is_loading);
		imgbtn=(ImageButton)findViewById(R.id.imgbtn);
		imgbtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
			
		});
		WindowManager windowManager = (WindowManager)
				getSystemService(WINDOW_SERVICE);
		Display display = windowManager.getDefaultDisplay();
		DisplayMetrics metrice = new DisplayMetrics();
		// 获取屏幕的宽和高
		display.getMetrics(metrice);
		webView.setAnimation(new MyAnimation(metrice.xdpi / 2
				, metrice.ydpi / 2, 3500));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.news_details, menu);
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

	final class InJavaScriptLocalObj{
		private boolean isLoaded = false;
		@JavascriptInterface
		public void showSource(String html) {
			if(!isLoaded){
				Document document = Jsoup.parse(html);
				Elements elements = document.getElementsByClass("page-view-article");
				NewsDetailsActivity.this.html = document.head()+elements.toString();
				Message message = new Message();
				message.arg1 = 1;
				handler.sendMessage(message);
				isLoaded = true;
			}
		}
	}

	final class MyWebViewClient extends WebViewClient{ 
		public boolean shouldOverrideUrlLoading(WebView view, String url) {  
			view.loadUrl(url);  
			return true;  
		} 
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			Log.d("WebView","onPageStarted");
			super.onPageStarted(view, url, favicon);
		}   
		public void onPageFinished(WebView view, String url) {
			Log.d("WebView","onPageFinished ");
			view.loadUrl("javascript:window.local_obj.showSource('<head>'+" +
					"document.getElementsByTagName('html')[0].innerHTML+'</head>');");
			super.onPageFinished(view, url);
		}
	}
}
