package com.example.islam.gotomarketdynamicstask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by islam on 18/11/2017.
 */

public class DownLoadUrl {

    public String readUrl(String myUrl) throws IOException {
        String data = "";
        InputStream in = null;
        HttpsURLConnection urlConnection = null;

        try {
            URL url = new URL(myUrl);
            urlConnection = (HttpsURLConnection)url.openConnection();
            urlConnection.connect();

            in = urlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            StringBuffer stringBuffer = new StringBuffer();

            String line = "";
            while ((line = bufferedReader.readLine()) != null){
                stringBuffer.append(line);
            }

            data = stringBuffer.toString();
            bufferedReader.close();
        }catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            in.close();
            urlConnection.disconnect();
        }
        return data;
    }
}
