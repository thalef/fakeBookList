package br.com.thalef.fakebooklist.livro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.thalef.fakebooklist.R;

/**
 * Created by thiagocarvalho on 15/01/17.
 */

public class LivrosAdapter extends ArrayAdapter<Livro> {


    public LivrosAdapter(Context context, List<Livro> livros) {
        super(context, 0, livros);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;

        if (listItemView == null) {

            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.livros_list_item, parent, false);

        }

        Livro livroDaLista = getItem(position);

        TextView tituloTextView = (TextView) listItemView.findViewById(R.id.lista_padrao_titulo_textview);
        TextView autorTextView = (TextView) listItemView.findViewById(R.id.lista_padrao_autor_textview);

        tituloTextView.setText(livroDaLista.getTitulo());
        autorTextView.setText(livroDaLista.getPrimeiroAutor());


        return listItemView;
    }
}
