package com.realm.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class BgWork extends AsyncTask<Void,Void,Void> {
    Context context;
    public BgWork(Context context) {
        this.context=context;
    }

    String URL = "https://www.fatsecret.com.tr/kaloriler-beslenme/genel/mercimek-%C3%87orbas%C4%B1";
    String title="No title";
    String calories="calories";
    String serving="serving_size";
    String fat="fat";
    String carbonhydrate="carb";
    String protein="pro";
    //ProgressDialog dialog;
    @Override
    protected Void doInBackground(Void... voids) {
        //dialog = new ProgressDialog(context);
        //dialog.setTitle(title);
        //dialog.setIndeterminate(false);
        //dialog.show();

        try {
            Document doc = Jsoup.connect(URL).get();
            title = doc.select("body").select("h1").text();
            calories = doc.select("td.borderBottom.smallText").select("a").get(0).text();
            serving = doc.select("td.borderBottom").select("span").text();
        }catch (Exception e){
            Log.i("LogData",e.toString());
        }

        //dialog.dismiss();
        Log.i("LogData",title+"-"+calories+"-"+serving);
        return null;
    }
}
