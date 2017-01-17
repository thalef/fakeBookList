package br.com.thalef.fakebooklist.livro;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by thiagocarvalho on 15/01/17.
 */

public class LivrosLoader extends AsyncTaskLoader<List<Livro>> {

    private static final String TAG = LivrosLoader.class.getName();

    private String mUrlParaCarregar;

    public LivrosLoader(Context context, String url) {
        super(context);
        mUrlParaCarregar = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Livro> loadInBackground() {

        if (mUrlParaCarregar == null) {
            return null;
        }

        List<Livro> livrosRetornados = LivroService.buscaOsLivrosNaAPI(mUrlParaCarregar);

        return livrosRetornados;
    }
}
