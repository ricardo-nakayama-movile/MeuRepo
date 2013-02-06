package com.example.androidjsontest.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.util.Log;

/**
 * @author Ronaldo R. Nascimento (ronaldo.ronie@movile.com)
 */
public class HttpUtil {

    private static final int DEFAULT_TIMEOUT = 15;
// Classe que pega uma url e converte seu conteudo em string
    public static String doHttpGet(String url) throws Exception {
        return doHttpGet(url, DEFAULT_TIMEOUT);
    }

    public static String doHttpGet(String urlParam, Integer timeout) {

        // Send data
    	// TODO for a better performance, it's possible to make a inputstream to string conversion, saving space
    	
        URL url;
        HttpURLConnection conn = null;
        BufferedReader br;
        StringBuilder resp = new StringBuilder();
        String respStr = null;
        try {
            url = new URL(urlParam);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setDefaultUseCaches(false);
            conn.setInstanceFollowRedirects(true);
            conn.setConnectTimeout(timeout * 1000);
            conn.setReadTimeout(timeout * 1000);
            conn.setUseCaches(false);
                                    
            br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "iso-8859-1"));            

            String line;

            while ((line = br.readLine()) != null) {
                resp.append(line);
            }

            br.close();

            respStr = resp.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            if(e.getMessage().contains("403")){
                System.out.println("[ERROR] 403 Forbidden");
            }
            else{
                Log.e("Erro ao conectar", e.getMessage());
                e.printStackTrace();
            }
        }
        
        return respStr;
    }
}