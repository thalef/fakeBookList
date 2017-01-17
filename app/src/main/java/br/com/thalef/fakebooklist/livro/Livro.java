package br.com.thalef.fakebooklist.livro;

import java.util.ArrayList;

/**
 * Created by thiagocarvalho on 15/01/17.
 */

public class Livro {

    private String id;
    private String titulo;

    /* Se fosse um app real deveria ser convertido a um arraylist com a classe autores e ter mas informacoes*/
    private ArrayList<String> autores = new ArrayList<String>();

    public String getPrimeiroAutor() {
        return autores.size() > 0 ? autores.get(0) : "";
    }

    public ArrayList<String> getAutores() {
        return autores;
    }

    public void setAutores(ArrayList<String> autores) {
        this.autores = autores;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Override
    public String toString() {
        return "Livro{" +
                "autores=" + autores +
                ", id='" + id + '\'' +
                ", titulo='" + titulo + '\'' + '}';
    }
}
