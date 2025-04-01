package src.Musica;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstrata que representa uma música no sistema SpotifUM
 */
public abstract class Musica implements Serializable {
    private String nome;
    private String interprete;
    private String editora;
    private String letra;
    private List<String> conteudoMusical;
    private GeneroMusical genero;
    private int duracao; // em segundos
    private int contadorReproducoes;
    
    /**
     * Construtor base para uma música
     */
    public Musica(String nome, String interprete, String editora, String letra, 
                  List<String> conteudoMusical, GeneroMusical genero, int duracao) {
        this.nome = nome;
        this.interprete = interprete;
        this.editora = editora;
        this.letra = letra;
        this.conteudoMusical = new ArrayList<>(conteudoMusical);
        this.genero = genero;
        this.duracao = duracao;
        this.contadorReproducoes = 0;
    }
    
    /**
     * Método para reproduzir a música (simulado)
     */
    public void reproduzir() {
        System.out.println("A reproduzir " + nome + " - " + interprete);
        System.out.println("Letra:");
        System.out.println(letra);
        incrementarReproducoes();
    }
    
    /**
     * Incrementa o contador de reproduções
     */
    public void incrementarReproducoes() {
        this.contadorReproducoes++;
    }
    
    // Getters e Setters
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getInterprete() {
        return interprete;
    }
    
    public void setInterprete(String interprete) {
        this.interprete = interprete;
    }
    
    public String getEditora() {
        return editora;
    }
    
    public void setEditora(String editora) {
        this.editora = editora;
    }
    
    public String getLetra() {
        return letra;
    }
    
    public void setLetra(String letra) {
        this.letra = letra;
    }
    
    public List<String> getConteudoMusical() {
        return new ArrayList<>(conteudoMusical);
    }
    
    public void setConteudoMusical(List<String> conteudoMusical) {
        this.conteudoMusical = new ArrayList<>(conteudoMusical);
    }
    
    public GeneroMusical getGenero() {
        return genero;
    }
    
    public void setGenero(GeneroMusical genero) {
        this.genero = genero;
    }
    
    public int getDuracao() {
        return duracao;
    }
    
    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }
    
    public int getContadorReproducoes() {
        return contadorReproducoes;
    }
    
    @Override
    public String toString() {
        return nome + " - " + interprete + " (" + duracao / 60 + ":" + String.format("%02d", duracao % 60) + ")";
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Musica musica = (Musica) obj;
        return nome.equals(musica.nome) && interprete.equals(musica.interprete);
    }
    
    @Override
    public int hashCode() {
        return 31 * nome.hashCode() + interprete.hashCode();
    }
}