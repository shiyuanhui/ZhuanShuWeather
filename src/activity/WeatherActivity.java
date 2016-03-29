package activity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import Utility.JSONUtility;
import Utility.Utility;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shiyuanhui.zhuanshuweather.R;




public class WeatherActivity extends Activity  {
	
	String httpUrl = "http://apis.baidu.com/heweather/weather/free";
	String httpArg = "city=shanghai";
	private LinearLayout weatherInfoLayout;
	/**
	 * ������ʾ��ǰ�¶�
	 */
	private TextView now_temp;
	/**
	 * ������ʾ��ǰ����
	 */
	private TextView now_weather;
	/**
	 * ������ʾ�������
	 */
	private TextView today_maxtemp;
	/**
	 * ������ʾ�������
	 */
	private TextView today_mintemp;
	/**
	 * ������ʾ��������
	 */
	private TextView kongqi_zhiliang;
	/**
	 * ������ʾ������
	 */
	private TextView cityNameText;
	// �л�����
	private Button switchCity;
	// ��������
	private Button refreshWeather;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.show_weather);
		// ��ʼ�����ؼ�
		weatherInfoLayout = (LinearLayout) findViewById(R.id.weather_info_layout);
		cityNameText = (TextView) findViewById(R.id.city_name);
		now_temp = (TextView) findViewById(R.id.now_temp);
		now_weather = (TextView) findViewById(R.id.now_weather);
		today_maxtemp = (TextView) findViewById(R.id.today_maxtemp);
		today_mintemp = (TextView) findViewById(R.id.today_mintemp);
		kongqi_zhiliang = (TextView) findViewById(R.id.kongqi_zhiliang);
		//switchCity = (Button) findViewById(R.id.switch_city);
		//refreshWeather = (Button) findViewById(R.id.refresh_weather);
		//switchCity.setOnClickListener(this);
		//refreshWeather.setOnClickListener(this);
		String countyCode = getIntent().getStringExtra("county_code");
		//httpArg = "cityid=CN101"+countyCode;
		if (!TextUtils.isEmpty(countyCode)) {
			// ���ؼ�����ʱ��ȥ��ѯ����
			//publishText.setText("ͬ����...");
			weatherInfoLayout.setVisibility(View.INVISIBLE);
			cityNameText.setVisibility(View.INVISIBLE);
			JSONUtility.handleWeatherResponse(WeatherActivity.this,request(httpUrl, httpArg));
			showWeather();
		} else {
			// û���ؼ�����ʱ��ֱ����ʾ��������
			showWeather();
		}
	}
/*
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.switch_city:
			//Log.v("MainActivity", "choose");
			Intent intent = new Intent(this, ChooseAreaActivity.class);
			intent.putExtra("from_weather_activity", true);
			startActivity(intent);
			finish();
			break;
		case R.id.refresh_weather:
			publishText.setText("ͬ����...");
			SharedPreferences prefs = PreferenceManager
					.getDefaultSharedPreferences(this);
			String weatherCode = prefs.getString("weather_code", "");
			if (!TextUtils.isEmpty(weatherCode)) {
				queryWeatherInfo(weatherCode);
			}
			break;

		default:
			break;
		}

	}
*/
	
	public static String request(String httpUrl, String httpArg) {
	    BufferedReader reader = null;
	    String result = null;
	    StringBuffer sbf = new StringBuffer();
	    httpUrl = httpUrl + "?" + httpArg;

	    try {
	        URL url = new URL(httpUrl);
	        HttpURLConnection connection = (HttpURLConnection) url
	                .openConnection();
	        connection.setRequestMethod("GET");
	        // ����apikey��HTTP header
	        connection.setRequestProperty("apikey",  "15eb6e7b0ba24e5b4d0e4a69a50e2896");
	        connection.connect();
	        InputStream is = connection.getInputStream();
	        reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	        String strRead = null;
	        while ((strRead = reader.readLine()) != null) {
	            sbf.append(strRead);
	            sbf.append("\r\n");
	        }
	        reader.close();
	        result = sbf.toString();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return result;
	}

	/**
	 * ��SharedPreferences�ļ��ж�ȡ�洢��������Ϣ������ʾ�������ϡ�
	 */
	private void showWeather() {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		cityNameText.setText(prefs.getString("city_name", ""));
		now_temp.setText(prefs.getString("now_temp", ""));
		now_weather.setText(prefs.getString("now_weather", ""));
		today_maxtemp.setText("��������¶ȣ�"+prefs.getString("today_maxtemp", ""));
		today_mintemp.setText("��������¶ȣ�"+prefs.getString("today_mintemp", ""));
		kongqi_zhiliang.setText("����������"+prefs.getString("kongqi_zhiliang", ""));
		weatherInfoLayout.setVisibility(View.VISIBLE);
		cityNameText.setVisibility(View.VISIBLE);
		//�������
		/*Intent intent = new Intent(this,AutoUpdateService.class);
		startService(intent);
		Log.v("MainActivity", "ServiceStart");*/
	}
	
}

