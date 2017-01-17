package br.com.thalef.fakebooklist.livro;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.thalef.fakebooklist.R;
import br.com.thalef.fakebooklist.util.InternetStatusService;

import static android.view.View.GONE;

public class LivrosActivity extends AppCompatActivity implements LoaderCallbacks<List<Livro>> {

    private final String TAG = LivrosActivity.class.getSimpleName();
    private static final int LIVROS_LOADER_ID = 1;

    private ListView mLivrosListView;
    private TextView mListaVaziaTextView;
    private LivrosAdapter mLivrosAdapter;
    private ProgressBar mLoadingIndicatorProgressBar;
    private SearchView mLivrosSearchView;
    private InternetStatusService internetStatusService;
    private MenuItem mActionPesquisarLivrosMenuItem;
    private String mTextoInformadoParaBuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livros);

        internetStatusService = new InternetStatusService();
        mTextoInformadoParaBuscar = "";

        configuraViews();

    }

    private void configuraViews() {

        mLivrosListView = (ListView) findViewById(R.id.livros_list_view);
        mListaVaziaTextView = (TextView) findViewById(R.id.livros_lista_vazia_text_view);
        mLoadingIndicatorProgressBar = (ProgressBar) findViewById(R.id.loading_indicator_progress_bar);

        mLivrosListView.setEmptyView(mListaVaziaTextView);

        mLivrosAdapter = new LivrosAdapter(this, new ArrayList<Livro>());
        mLivrosListView.setAdapter(mLivrosAdapter);

        mListaVaziaTextView.setText(R.string.msg_pesquise_os_livros);
        mListaVaziaTextView.setVisibility(View.VISIBLE);

        mLoadingIndicatorProgressBar.setVisibility(GONE);

        if (internetStatusService.isOnline(getBaseContext())) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(LIVROS_LOADER_ID, null, this);
        } else {
            Toast.makeText(getBaseContext(), R.string.msg_sem_internet, Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_livros, menu);

        mActionPesquisarLivrosMenuItem = menu.findItem(R.id.action_livros_pesquisar_item);
        mLivrosSearchView = (SearchView) mActionPesquisarLivrosMenuItem.getActionView();

        mLivrosSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                if (TextUtils.isEmpty(query)) {

                    Toast.makeText(getBaseContext(), R.string.msg_informa_titulo_buscar, Toast.LENGTH_SHORT).show();

                } else {

                    mTextoInformadoParaBuscar = query.trim();
                    limpaCampoListaVazia();
                    tiraFocusCampoBusca();
                    iniciaLivrosLoader();

                }

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        return true;
    }

    private void iniciaLivrosLoader() {

        if (internetStatusService.isOnline(getBaseContext())) {
            LoaderManager loaderManager = getLoaderManager();

            mostraLoadingIndicador();
            mLivrosAdapter.clear();

            if (loaderManager.getLoader(LIVROS_LOADER_ID) != null) {
                loaderManager.restartLoader(LIVROS_LOADER_ID, null, this);

            } else {

                loaderManager.initLoader(LIVROS_LOADER_ID, null, this);
            }

        } else {

            Toast.makeText(getBaseContext(), R.string.msg_sem_internet, Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public Loader<List<Livro>> onCreateLoader(int i, Bundle bundle) {

        return new LivrosLoader(this, LivroService.criaUrlPesquisarPorTexto(mTextoInformadoParaBuscar));
    }

    @Override
    public void onLoaderReset(Loader<List<Livro>> loader) {

        mLivrosAdapter.clear();
    }

    @Override
    public void onLoadFinished(Loader<List<Livro>> loader, List<Livro> livros) {

        escondeLoadingIndicador();

        mLivrosAdapter.clear();

        if (livros != null && !livros.isEmpty()) {

            mLivrosAdapter.addAll(livros);

        } else {

            mListaVaziaTextView.setVisibility(View.VISIBLE);
            mListaVaziaTextView.setText(R.string.livros_nao_encontrados);

        }

    }


    private void tiraFocusCampoBusca() {

        mLivrosSearchView.clearFocus();

    }

    private void mostraLoadingIndicador() {
        mLoadingIndicatorProgressBar.setVisibility(View.VISIBLE);
    }

    private void escondeLoadingIndicador() {
        mLoadingIndicatorProgressBar.setVisibility(View.GONE);
    }

    private void limpaCampoListaVazia() {
        mListaVaziaTextView.setText("");
    }

}
