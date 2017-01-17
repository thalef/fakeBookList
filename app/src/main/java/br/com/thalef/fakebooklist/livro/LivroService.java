package br.com.thalef.fakebooklist.livro;

/**
 * Created by thiagocarvalho on 15/01/17.
 */

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import br.com.thalef.fakebooklist.util.RestApiService;


public final class LivroService {

    private static final String TAG = LivroService.class.getSimpleName();
    private static final String GOOGLE_BOOK_API_BASE_REQUEST_URL = "https://www.googleapis.com/books/v1/volumes";

    private LivroService() {
    }


    public static String criaUrlPesquisarPorTexto(String textoDigitado) {

        Uri baseUri = Uri.parse(GOOGLE_BOOK_API_BASE_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("q", textoDigitado);
        uriBuilder.appendQueryParameter("maxResults", "10"); // melhorar a performance da demonstracao

        return uriBuilder.toString();
    }


    public static List<Livro> buscaOsLivrosNaAPI(String requestUrl) {

        URL url = RestApiService.criaUrl(requestUrl);

        String jsonResponse = null;

        try {
            jsonResponse = RestApiService.executaHttpRequest(url);
        } catch (IOException e) {
            Log.e(TAG, "Houve um problema ao tentar realizar a requisição", e);
        }

        List<Livro> livrosConvertidos = converteDadosJsonParaObject(jsonResponse);

        return livrosConvertidos;
    }


    private static List<Livro> converteDadosJsonParaObject(String jsonRetornado) {

        if (TextUtils.isEmpty(jsonRetornado)) {
            return null;
        }

        List<Livro> livrosRetornados = new ArrayList<>();

        try {

            JSONObject baseJsonResponse = new JSONObject(jsonRetornado);

            JSONArray livrosArray = baseJsonResponse.getJSONArray("items");

            int livrosArraySize = livrosArray.length();

            for (int i = 0; i < livrosArraySize; i++) {


                Livro tempLivro = new Livro();

                JSONObject livroJson = livrosArray.getJSONObject(i);
                JSONObject dadosDoLivroJson = livroJson.getJSONObject("volumeInfo");

                tempLivro.setId(livroJson.getString("id"));
                tempLivro.setTitulo(dadosDoLivroJson.getString("title"));

                JSONArray autoresArray = dadosDoLivroJson.optJSONArray("authors");

                if (autoresArray != null && autoresArray.length() > 0) {

                    tempLivro.getAutores().clear();

                    for (int a = 0; a < autoresArray.length(); a++) {

                        tempLivro.getAutores().add(autoresArray.getString(a));
                    }

                }

                livrosRetornados.add(tempLivro);
            }

        } catch (JSONException e) {
            Log.e(TAG, "Houve um problema ao tentar converter o JSON uma lista de objetos do tipo livro", e);
        }

        return livrosRetornados;
    }

}
