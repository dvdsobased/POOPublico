package src.Playlist;

import src.Utilizador.*;
import src.Musica.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Playlist gerada com base nas preferências do utilizador
 */
public class PlaylistFavoritos extends PlaylistBase {
    private Map<GeneroMusical, Double> preferencias;
    
    public PlaylistFavoritos(String nome, UtilizadorPremium criador, boolean publica) {
        super(nome, criador, publica);
        this.preferencias = new HashMap<>();
    }
    
    /**
     * Gera a playlist com base no histórico de reprodução do utilizador
     */
    public void gerarPlaylistComPreferencias(List<Musica> todasMusicas, Map<Musica, Integer> historicoReproducoes) {
        // Calcular preferências de géneros baseado no histórico
        calcularPreferencias(historicoReproducoes);
        
        // Filtrar músicas pelos géneros preferidos
        List<Musica> musicasFiltradas = todasMusicas.stream()
            .filter(m -> preferencias.containsKey(m.getGenero()) && preferencias.get(m.getGenero()) > 0.1)
            .collect(Collectors.toList());
        
        // Adicionar músicas à playlist (limita a 20 músicas)
        for (int i = 0; i < Math.min(20, musicasFiltradas.size()); i++) {
            adicionarMusica(musicasFiltradas.get(i));
        }
    }
    
    private void calcularPreferencias(Map<Musica, Integer> historicoReproducoes) {
        int totalReproducoes = historicoReproducoes.values().stream().mapToInt(Integer::intValue).sum();
        
        // Criar mapa de contagem por género
        Map<GeneroMusical, Integer> contagemPorGenero = new HashMap<>();
        
        for (Map.Entry<Musica, Integer> entry : historicoReproducoes.entrySet()) {
            GeneroMusical genero = entry.getKey().getGenero();
            int reproducoes = entry.getValue();
            
            contagemPorGenero.put(genero, contagemPorGenero.getOrDefault(genero, 0) + reproducoes);
        }
        
        // Converter contagem para preferências (proporção)
        for (Map.Entry<GeneroMusical, Integer> entry : contagemPorGenero.entrySet()) {
            preferencias.put(entry.getKey(), (double) entry.getValue() / totalReproducoes);
        }
    }
    
    public Map<GeneroMusical, Double> getPreferencias() {
        return new HashMap<>(preferencias);
    }
    
    @Override
    public String toString() {
        return super.toString() + " [FAVORITOS]";
    }
}