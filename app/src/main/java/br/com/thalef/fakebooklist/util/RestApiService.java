package br.com.thalef.fakebooklist.util;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by thiagocarvalho on 15/01/17.
 */

public final class RestApiService {

    private static final String TAG = RestApiService.class.getSimpleName();
    private static final String HTTP_METHOD_GET = "GET";
    private static final String HTTP_METHOD_POST = "POST";
    private static final String HTTP_METHOD_PUT = "PUT";
    private static final String HTTP_METHOD_DELETE = "DELETE";

    private RestApiService() {
    }

    public static URL criaUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(TAG, "Houve um problema ao montar a URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    public static String executaHttpRequest(URL url) throws IOException {

        String jsonRetornado = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonRetornado;
        }

        HttpURLConnection requisicao = null;
        InputStream inputStream = null;

        try {

            requisicao = (HttpURLConnection) url.openConnection();
            requisicao.setReadTimeout(10000);
            requisicao.setConnectTimeout(15000);
            requisicao.setRequestMethod(HTTP_METHOD_GET);
            requisicao.connect();


            if (requisicao.getResponseCode() == 200) {

                inputStream = requisicao.getInputStream();
                jsonRetornado = converteInputStreamParaStr(inputStream);

            } else {

                Log.e(TAG, "Erro ao tentar conectar, status code retornado: " + requisicao.getResponseCode());

            }

        } catch (IOException e) {

            Log.e(TAG, "Houve um problema ao tentar pegar os dados do JSON.", e);

        } finally {

            if (requisicao != null) {

                requisicao.disconnect();

            }

            if (inputStream != null) {

                inputStream.close();

            }

        }

        return jsonRetornado;
    }

    private static String converteInputStreamParaStr(InputStream inputStream) throws IOException {

        StringBuilder resultado = new StringBuilder();

        if (inputStream != null) {

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String linha = bufferedReader.readLine();

            while (linha != null) {

                resultado.append(linha);
                linha = bufferedReader.readLine();

            }

        }

        return resultado.toString();
    }
}
