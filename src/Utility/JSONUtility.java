package Utility;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class JSONUtility {
	/*
	 * 解析服务器返回的JSON数据
	 */
	 static String kongqi_zhiliang = "";
	 static String now_weather = "";
	 static String now_temp = "";
	 static String today_maxtemp = "";
	 static String today_mintemp = "";
	 static String city_name = "";

	public static void handleWeatherResponse(Context context,String response)
	{
		 
		try {
			getAqi(response);
			getBasic(response);
			getDailyForecast(response);
			getNowWeather(response);
		    saveWeatherInfo(context,kongqi_zhiliang,now_weather,now_temp,today_maxtemp,today_mintemp);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	
}

/*
 * 将服务器返回的所有天气信息存储到SharedPreferenced文件中
 * 
 */ 
      public static void saveWeatherInfo(Context context, String kongqi_zhiliang,
			String now_weather, String now_temp, String today_maxtemp, String today_mintemp
			) {
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日", Locale.CHINA);
		SharedPreferences.Editor editor = PreferenceManager
				.getDefaultSharedPreferences(context).edit();
		editor.putBoolean("city_selected", true);
		editor.putString("kongqi_zhiliang", kongqi_zhiliang);
		editor.putString("now_weather", now_weather);
		editor.putString("now_temp", now_temp);
		editor.putString("today_maxtemp", today_maxtemp);
		editor.putString("today_mintemp", today_mintemp);
		editor.commit();
	}




public static void getAqi(String result){
    JSONObject jsonObject= JSON.parseObject(result);
    JSONArray jsonArray= jsonObject.getJSONArray("HeWeather data service 3.0");
    for(int i=0;i<jsonArray.size();i++){
        JSONObject js= (JSONObject) jsonArray.get(i);
        if(js!=null){
            JSONObject jsAqi= js.getJSONObject("aqi");
            if(jsAqi!=null){
                JSONObject aqiCity=jsAqi.getJSONObject("city");
                if(aqiCity!=null){
                	kongqi_zhiliang = aqiCity.getString("qlty");
                    break;
                }
            }
        }
    }
 }

 public static void getBasic(String result){
    JSONObject jsonObject= JSON.parseObject(result);
    JSONArray jsonArray= jsonObject.getJSONArray("HeWeather data service 3.0");
    for(int i=0;i<jsonArray.size();i++){
        JSONObject js= (JSONObject) jsonArray.get(i);
        if(js!=null){
            JSONObject jsBasic=js.getJSONObject("basic");
            if(jsBasic!=null){
                JSONObject update=jsBasic.getJSONObject("update");
                if(update!=null){
                	city_name = jsBasic.getString("city");
                    break;
                }
            }
        }
    }
}
 
 public static void getDailyForecast(String result){
     JSONObject jsonObject= JSON.parseObject(result);
     JSONArray jsonArray= jsonObject.getJSONArray("HeWeather data service 3.0");
     for(int i=0;i<jsonArray.size();i++) {
         JSONObject js = (JSONObject) jsonArray.get(i);
         if (js != null) {
             JSONArray ja=js.getJSONArray("daily_forecast");
             if(ja!=null){
                 for(int j=0;j<1;j++){
                     JSONObject jsDf=ja.getJSONObject(j);
                     if(jsDf!=null){
                         JSONObject tmp=jsDf.getJSONObject("tmp");
                         if(tmp!=null){
                        	 today_maxtemp = tmp.getString("max");
                        	 today_maxtemp = tmp.getString("min");
                         }
                     }
                 }
             }
         }
     }
     
 }
 
 public static void getNowWeather(String result){
     JSONObject jsonObject= JSON.parseObject(result);
     JSONArray jsonArray= jsonObject.getJSONArray("HeWeather data service 3.0");
     for(int i=0;i<jsonArray.size();i++) {
         JSONObject js = (JSONObject) jsonArray.get(i);
         JSONObject now=js.getJSONObject("now");
         if (now != null) {
             JSONObject cond=now.getJSONObject("cond");
             if(cond!=null){
                 now_weather = cond.getString("txt");
                 now_temp = now.getString("tmp");
                 break;
             }
         }
     }
 }

}