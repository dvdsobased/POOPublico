package src.Playlist;

import src.Utilizador.*;
import src.Musica.*;
import java.io.Serializable;
import java.util.List;

/**
 * Interface para diferentes tipos de playlists
 */
public interface Playlist extends Serializable {
    /**
     * Adiciona uma música à playlist
     */
    void adicionarMusica(Musica musica);
    
    /**
     * Remove uma música da playlist
     */
    void removerMusica(Musica musica);
    
    /**
     * Reproduz as músicas da playlist
     */
    void reproduzir();
    
    /**
     * Obtém a lista de músicas da playlist
     */
    List<Musica> getMusicas();
    
    /**
     * Verifica se a playlist é pública
     */
    boolean isPublica();
    
    /**
     * Define se a playlist é pública
     */
    void setPublica(boolean publica);
    
    /**
     * Obtém o nome da playlist
     */
    String getNome();
    
    /**
     * Define o nome da playlist
     */
    void setNome(String nome);
    
    /**
     * Obtém o utilizador criador da playlist
     */
    Utilizador getCriador();
    
    /**
     * Obtém a duração total da playlist em segundos
     */
    int getDuracaoTotal();
}