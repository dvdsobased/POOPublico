package src.Playlist;

import src.Utilizador.*;
import src.Musica.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Implementação básica de uma playlist
 */
public class PlaylistBase implements Playlist {
    private String nome;
    private List<Musica> musicas;
    private boolean publica;
    private Utilizador criador;
    
    public PlaylistBase(String nome, Utilizador criador, boolean publica) {
        this.nome = nome;
        this.criador = criador;
        this.publica = publica;
        this.musicas = new ArrayList<>();
    }
    
    @Override
    public void adicionarMusica(Musica musica) {
        if (!musicas.contains(musica)) {
            musicas.add(musica);
        }
    }
    
    @Override
    public void removerMusica(Musica musica) {
        musicas.remove(musica);
    }
    
    @Override
    public void reproduzir() {
        System.out.println("A reproduzir playlist: " + nome);
        for (Musica musica : musicas) {
            System.out.println("\n--- Faixa seguinte ---");
            musica.reproduzir();
        }
    }
    
    @Override
    public List<Musica> getMusicas() {
        return new ArrayList<>(musicas);
    }
    
    @Override
    public boolean isPublica() {
        return publica;
    }
    
    @Override
    public void setPublica(boolean publica) {
        this.publica = publica;
    }
    
    @Override
    public String getNome() {
        return nome;
    }
    
    @Override
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    @Override
    public Utilizador getCriador() {
        return criador;
    }
    
    @Override
    public int getDuracaoTotal() {
        return musicas.stream().mapToInt(Musica::getDuracao).sum();
    }
    
    @Override
    public String toString() {
        return nome + " - " + musicas.size() + " músicas - " + 
               (publica ? "Pública" : "Privada") + " - Criada por: " + criador.getNome();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        PlaylistBase playlist = (PlaylistBase) obj;
        return nome.equals(playlist.nome) && 
               criador.equals(playlist.criador);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(nome, criador);
    }
}
