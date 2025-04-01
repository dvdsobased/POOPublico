package src.Musica;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Classe que representa um álbum de músicas
 */
public class Album implements Serializable {
    private String nome;
    private String interprete;
    private int anoLancamento;
    private List<Musica> musicas;
    
    public Album(String nome, String interprete, int anoLancamento) {
        this.nome = nome;
        this.interprete = interprete;
        this.anoLancamento = anoLancamento;
        this.musicas = new ArrayList<>();
    }
    
    public void adicionarMusica(Musica musica) {
        if (!musicas.contains(musica)) {
            musicas.add(musica);
        }
    }
    
    public void removerMusica(Musica musica) {
        musicas.remove(musica);
    }
    
    public void reproduzir() {
        System.out.println("A reproduzir álbum: " + nome + " - " + interprete + " (" + anoLancamento + ")");
        for (Musica musica : musicas) {
            System.out.println("\n--- Faixa seguinte ---");
            musica.reproduzir();
        }
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
    
    public int getAnoLancamento() {
        return anoLancamento;
    }
    
    public void setAnoLancamento(int anoLancamento) {
        this.anoLancamento = anoLancamento;
    }
    
    public List<Musica> getMusicas() {
        return new ArrayList<>(musicas);
    }
    
    public int getDuracaoTotal() {
        return musicas.stream().mapToInt(Musica::getDuracao).sum();
    }
    
    @Override
    public String toString() {
        return nome + " - " + interprete + " (" + anoLancamento + ") - " + musicas.size() + " faixas";
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Album album = (Album) obj;
        return nome.equals(album.nome) && 
               interprete.equals(album.interprete) && 
               anoLancamento == album.anoLancamento;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(nome, interprete, anoLancamento);
    }
}
