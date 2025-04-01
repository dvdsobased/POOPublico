package src.Playlist;

import src.Utilizador.*;
import src.Musica.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Playlist gerada com base num tempo máximo e género musical
 */
public class PlaylistTempoGenero extends PlaylistBase {
    private int tempoMaximo; // em segundos
    private GeneroMusical genero;
    
    public PlaylistTempoGenero(String nome, UtilizadorPremium criador, boolean publica, 
                            int tempoMaximo, GeneroMusical genero) {
        super(nome, criador, publica);
        this.tempoMaximo = tempoMaximo;
        this.genero = genero;
    }
    
    /**
     * Gera a playlist com músicas do género específico até atingir o tempo máximo
     */
    public void gerarPlaylist(List<Musica> todasMusicas) {
        // Filtrar músicas pelo género
        List<Musica> musicasFiltradas = todasMusicas.stream()
            .filter(m -> m.getGenero() == genero)
            .collect(Collectors.toList());
        
        int tempoAtual = 0;
        
        // Adicionar músicas até atingir o tempo máximo
        for (Musica musica : musicasFiltradas) {
            if (tempoAtual + musica.getDuracao() <= tempoMaximo) {
                adicionarMusica(musica);
                tempoAtual += musica.getDuracao();
            }
        }
    }
    
    public int getTempoMaximo() {
        return tempoMaximo;
    }
    
    public void setTempoMaximo(int tempoMaximo) {
        this.tempoMaximo = tempoMaximo;
    }
    
    public GeneroMusical getGenero() {
        return genero;
    }
    
    public void setGenero(GeneroMusical genero) {
        this.genero = genero;
    }
    
    @Override
    public String toString() {
        return super.toString() + " [TEMPO: " + tempoMaximo/60 + ":" + String.format("%02d", tempoMaximo%60) +
               ", GÉNERO: " + genero + "]";
    }
}