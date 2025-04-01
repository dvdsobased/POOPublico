package src.Utilizador;

import src.Musica.*;
import src.Playlist.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utilizador com funcionalidades premium
 */
public class UtilizadorPremium extends Utilizador {
    private PlanoSubscricao plano;
    private List<Album> biblioteca;
    private List<Playlist> playlists;
    private Map<Musica, Integer> historicoReproducoes;
    
    public UtilizadorPremium(String nome, String email, String morada, PlanoSubscricao plano) {
        super(nome, email, morada);
        this.plano = plano;
        this.biblioteca = new ArrayList<>();
        this.playlists = new ArrayList<>();
        this.historicoReproducoes = new HashMap<>();
        
        // Utilizadores Premium Top recebem 100 pontos na adesão
        if (plano instanceof PlanoPremiumTop) {
            adicionarPontos(100);
        }
    }
    
    @Override
    public void reproduzirMusica(Musica musica) {
        System.out.println("Utilizador Premium " + getNome() + " está a reproduzir:");
        musica.reproduzir();
        
        // Atualizar histórico de reproduções
        historicoReproducoes.put(musica, historicoReproducoes.getOrDefault(musica, 0) + 1);
        
        // Adicionar pontos ao utilizador
        adicionarPontos(plano.calcularPontos(getPontos()));
    }
    
    /**
     * Adiciona um álbum à biblioteca do utilizador
     */
    public void adicionarAlbum(Album album) {
        if (plano.podeGuardarBiblioteca() && !biblioteca.contains(album)) {
            biblioteca.add(album);
        }
    }
    
    /**
     * Remove um álbum da biblioteca do utilizador
     */
    public void removerAlbum(Album album) {
        biblioteca.remove(album);
    }
    
    /**
     * Reproduz um álbum completo
     */
    public void reproduzirAlbum(Album album) {
        System.out.println("Utilizador Premium " + getNome() + " está a reproduzir álbum:");
        album.reproduzir();
        
        // Atualizar histórico e pontos para cada música
        for (Musica musica : album.getMusicas()) {
            historicoReproducoes.put(musica, historicoReproducoes.getOrDefault(musica, 0) + 1);
            adicionarPontos(plano.calcularPontos(getPontos()));
        }
    }
    
    /**
     * Cria uma nova playlist
     */
    public Playlist criarPlaylist(String nome, boolean publica) {
        if (plano.podeCriarPlaylist()) {
            Playlist novaPlaylist = new PlaylistBase(nome, this, publica);
            playlists.add(novaPlaylist);
            return novaPlaylist;
        }
        return null;
    }
    
    /**
     * Cria uma nova playlist aleatória
     */
    public Playlist criarPlaylistAleatoria(String nome, boolean publica) {
        if (plano.podeCriarPlaylist()) {
            Playlist novaPlaylist = new PlaylistAleatoria(nome, this, publica);
            playlists.add(novaPlaylist);
            return novaPlaylist;
        }
        return null;
    }
    
    /**
     * Adiciona uma playlist existente à biblioteca
     */
    public void adicionarPlaylist(Playlist playlist) {
        if (plano.podeGuardarBiblioteca() && !playlists.contains(playlist)) {
            playlists.add(playlist);
        }
    }
    
    /**
     * Remove uma playlist da biblioteca
     */
    public void removerPlaylist(Playlist playlist) {
        playlists.remove(playlist);
    }
    
    /**
     * Reproduz uma playlist
     */
    public void reproduzirPlaylist(Playlist playlist) {
        System.out.println("Utilizador Premium " + getNome() + " está a reproduzir playlist:");
        playlist.reproduzir();
        
        // Atualizar histórico e pontos para cada música
        for (Musica musica : playlist.getMusicas()) {
            historicoReproducoes.put(musica, historicoReproducoes.getOrDefault(musica, 0) + 1);
            adicionarPontos(plano.calcularPontos(getPontos()));
        }
    }
    
    /**
     * Cria uma playlist de favoritos com base no histórico
     */
    public PlaylistFavoritos criarPlaylistFavoritos(String nome, boolean publica, List<Musica> todasMusicas) {
        if (plano.podeReceberPlaylistsGeradas()) {
            PlaylistFavoritos playlist = new PlaylistFavoritos(nome, this, publica);
            playlist.gerarPlaylistComPreferencias(todasMusicas, historicoReproducoes);
            playlists.add(playlist);
            return playlist;
        }
        return null;
    }
    
    /**
     * Cria uma playlist por tempo e género
     */
    public PlaylistTempoGenero criarPlaylistTempoGenero(String nome, boolean publica, 
                                                      int tempoMaximo, GeneroMusical genero, 
                                                      List<Musica> todasMusicas) {
        if (plano.podeCriarPlaylist()) {
            PlaylistTempoGenero playlist = new PlaylistTempoGenero(nome, this, publica, tempoMaximo, genero);
            playlist.gerarPlaylist(todasMusicas);
            playlists.add(playlist);
            return playlist;
        }
        return null;
    }
    
    /**
     * Cria uma playlist de músicas explícitas
     */
    public Playlist criarPlaylistExplicita(String nome, boolean publica, List<Musica> todasMusicas) {
        if (plano.podeCriarPlaylist()) {
            Playlist playlist = new PlaylistBase(nome, this, publica);
            
            // Adicionar apenas músicas explícitas
            for (Musica musica : todasMusicas) {
                if (musica instanceof MusicaExplicita) {
                    playlist.adicionarMusica(musica);
                }
            }
            
            playlists.add(playlist);
            return playlist;
        }
        return null;
    }
    
    @Override
    public PlanoSubscricao getPlanoSubscricao() {
        return plano;
    }
    
    /**
     * Atualiza o plano de subscrição
     */
    public void setPlanoSubscricao(PlanoSubscricao plano) {
        this.plano = plano;
    }
    
    public List<Album> getBiblioteca() {
        return new ArrayList<>(biblioteca);
    }
    
    public List<Playlist> getPlaylists() {
        return new ArrayList<>(playlists);
    }
    
    public Map<Musica, Integer> getHistoricoReproducoes() {
        return new HashMap<>(historicoReproducoes);
    }
    
    @Override
    public String toString() {
        return super.toString() + " - Utilizador Premium (" + plano.getNome() + ")";
    }
}