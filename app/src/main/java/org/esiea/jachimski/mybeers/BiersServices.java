package org.esiea.jachimski.mybeers;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class BiersServices extends IntentService {
    private static final String ACTION_Get_Json = "com.example.jachimski.mybeers.action.Get_Json";
    public static final String TAG = "TAG";

    public static void startActionGetJson(Context context) {
        Intent intent = new Intent(context, BiersServices.class);
        intent.setAction(ACTION_Get_Json);

        context.startService(intent);
    }

    public BiersServices() {
        super("DownloadJSON");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_Get_Json.equals(action)) {
                handleActionGetJson();
            }
        }
    }

    private void copyInputStreamToFile(InputStream inputStream, File file) {
        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while((len=inputStream.read(buf))>0) {
                out.write(buf,0,len);
            }
            out.close();
            inputStream.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void handleActionGetJson() {
        Log.d(TAG, "Thread service name:" + Thread.currentThread().getName());
        URL url = null;
        try {
            url = new URL("http://binouze.fabrigli.fr/bieres.json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            if(HttpURLConnection.HTTP_OK == conn.getResponseCode()){
                copyInputStreamToFile(conn.getInputStream(), new File(getCacheDir(), "bieres.json"));
                Log.d(TAG, "Bieres json downloaded !");

                Intent i = new Intent("BEERS_UPDATE");
                sendBroadcast(i);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}