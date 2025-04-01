package src;

import src.Musica.*;
import src.Playlist.*;
import src.Utilizador.*;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Classe principal que gerencia todo o sistema SpotifUM
 */
public class SpotifUM implements Serializable {
    private List<Utilizador> utilizadores;
    private List<Album> albuns;
    private List<Playlist> playlistsPublicas;
    private Map<Musica, Integer> estatisticasReproducao;
    private Map<String, Integer> estatisticasInterprete;
    private Map<GeneroMusical, Integer> estatisticasGenero;
    private Map<Utilizador, Map<LocalDate, Integer>> estatisticasUtilizadorPorDia;
    
    public SpotifUM() {
        this.utilizadores = new ArrayList<>();
        this.albuns = new ArrayList<>();
        this.playlistsPublicas = new ArrayList<>();
        this.estatisticasReproducao = new HashMap<>();
        this.estatisticasInterprete = new HashMap<>();
        this.estatisticasGenero = new HashMap<>();
        this.estatisticasUtilizadorPorDia = new HashMap<>();
    }
    
    // Métodos de gestão de utilizadores
    
    /**
     * Adiciona um novo utilizador ocasional
     */
    public UtilizadorOcasional adicionarUtilizadorOcasional(String nome, String email, String morada) {
        UtilizadorOcasional utilizador = new UtilizadorOcasional(nome, email, morada);
        if (!utilizadores.contains(utilizador)) {
            utilizadores.add(utilizador);
            estatisticasUtilizadorPorDia.put(utilizador, new HashMap<>());
            return utilizador;
        }
        return null;
    }
    
    /**
     * Adiciona um novo utilizador premium
     */
    public UtilizadorPremium adicionarUtilizadorPremium(String nome, String email, String morada, PlanoSubscricao plano) {
        UtilizadorPremium utilizador = new UtilizadorPremium(nome, email, morada, plano);
        if (!utilizadores.contains(utilizador)) {
            utilizadores.add(utilizador);
            estatisticasUtilizadorPorDia.put(utilizador, new HashMap<>());
            return utilizador;
        }
        return null;
    }
    
    /**
     * Remove um utilizador
     */
    public void removerUtilizador(Utilizador utilizador) {
        utilizadores.remove(utilizador);
        estatisticasUtilizadorPorDia.remove(utilizador);
    }
    
    /**
     * Encontra um utilizador pelo email
     */
    public Utilizador encontrarUtilizador(String email) {
        return utilizadores.stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }
    
    // Métodos de gestão de álbuns e músicas
    
    /**
     * Adiciona um novo álbum
     */
    public Album adicionarAlbum(String nome, String interprete, int anoLancamento) {
        Album album = new Album(nome, interprete, anoLancamento);
        if (!albuns.contains(album)) {
            albuns.add(album);
            return album;
        }
        return null;
    }
    
    /**
     * Remove um álbum
     */
    public void removerAlbum(Album album) {
        albuns.remove(album);
    }
    
    /**
     * Adiciona uma música normal a um álbum
     */
    public MusicaNormal adicionarMusicaNormal(Album album, String nome, String interprete, String editora, 
                                            String letra, List<String> conteudoMusical, 
                                            GeneroMusical genero, int duracao) {
        MusicaNormal musica = new MusicaNormal(nome, interprete, editora, letra, conteudoMusical, genero, duracao);
        album.adicionarMusica(musica);
        estatisticasReproducao.put(musica, 0);
        return musica;
    }
    
    /**
     * Adiciona uma música explícita a um álbum
     */
    public MusicaExplicita adicionarMusicaExplicita(Album album, String nome, String interprete, String editora, 
                                                  String letra, List<String> conteudoMusical, 
                                                  GeneroMusical genero, int duracao, String avisoConteudo) {
        MusicaExplicita musica = new MusicaExplicita(nome, interprete, editora, letra, conteudoMusical, 
                                                   genero, duracao, avisoConteudo);
        album.adicionarMusica(musica);
        estatisticasReproducao.put(musica, 0);
        return musica;
    }
    
    /**
     * Adiciona uma música multimédia a um álbum
     */
    public MusicaMultimedia adicionarMusicaMultimedia(Album album, String nome, String interprete, String editora, 
                                                    String letra, List<String> conteudoMusical, 
                                                    GeneroMusical genero, int duracao, String linkVideo) {
        MusicaMultimedia musica = new MusicaMultimedia(nome, interprete, editora, letra, conteudoMusical, 
                                                    genero, duracao, linkVideo);
        album.adicionarMusica(musica);
        estatisticasReproducao.put(musica, 0);
        return musica;
    }
    
    /**
     * Encontra um álbum pelo nome e intérprete
     */
    public Album encontrarAlbum(String nome, String interprete) {
        return albuns.stream()
                .filter(a -> a.getNome().equals(nome) && a.getInterprete().equals(interprete))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Obtém todas as músicas do sistema
     */
    public List<Musica> getTodasMusicas() {
        List<Musica> todasMusicas = new ArrayList<>();
        for (Album album : albuns) {
            todasMusicas.addAll(album.getMusicas());
        }
        return todasMusicas;
    }
    
    // Métodos de gestão de playlists
    
    /**
     * Adiciona uma playlist pública
     */
    public void adicionarPlaylistPublica(Playlist playlist) {
        if (playlist.isPublica() && !playlistsPublicas.contains(playlist)) {
            playlistsPublicas.add(playlist);
        }
    }
    
    /**
     * Remove uma playlist pública
     */
    public void removerPlaylistPublica(Playlist playlist) {
        playlistsPublicas.remove(playlist);
    }
    
    // Métodos de reprodução e estatísticas
    
    /**
     * Reproduz uma música para um utilizador e atualiza estatísticas
     */
    public void reproduzirMusica(Utilizador utilizador, Musica musica) {
        utilizador.reproduzirMusica(musica);
        
        // Atualizar estatísticas
        estatisticasReproducao.put(musica, estatisticasReproducao.getOrDefault(musica, 0) + 1);
        estatisticasInterprete.put(musica.getInterprete(), 
                estatisticasInterprete.getOrDefault(musica.getInterprete(), 0) + 1);
        estatisticasGenero.put(musica.getGenero(), 
                estatisticasGenero.getOrDefault(musica.getGenero(), 0) + 1);
        
        // Atualizar estatísticas diárias do utilizador
        LocalDate hoje = LocalDate.now();
        Map<LocalDate, Integer> estatisticasDiarias = estatisticasUtilizadorPorDia.get(utilizador);
        estatisticasDiarias.put(hoje, estatisticasDiarias.getOrDefault(hoje, 0) + 1);
    }
    
    // Métodos para obter estatísticas
    
    /**
     * Obtém a música mais reproduzida
     */
    public Musica getMusicaMaisReproduzida() {
        return estatisticasReproducao.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }
    
    /**
     * Obtém o intérprete mais escutado
     */
    public String getInterpreteMaisEscutado() {
        return estatisticasInterprete.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }
    
    /**
     * Obtém o utilizador que mais músicas ouviu desde sempre
     */
    public Utilizador getUtilizadorMaisMusicasOuvidas() {
        Map<Utilizador, Integer> totalPorUtilizador = new HashMap<>();
        
        for (Map.Entry<Utilizador, Map<LocalDate, Integer>> entry : estatisticasUtilizadorPorDia.entrySet()) {
            Utilizador utilizador = entry.getKey();
            int total = entry.getValue().values().stream().mapToInt(Integer::intValue).sum();
            totalPorUtilizador.put(utilizador, total);
        }
        
        return totalPorUtilizador.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }
    
    /**
     * Obtém o utilizador que mais músicas ouviu num período específico
     */
    public Utilizador getUtilizadorMaisMusicasOuvidasPeriodo(LocalDate inicio, LocalDate fim) {
        Map<Utilizador, Integer> totalPorUtilizador = new HashMap<>();
        
        for (Map.Entry<Utilizador, Map<LocalDate, Integer>> entry : estatisticasUtilizadorPorDia.entrySet()) {
            Utilizador utilizador = entry.getKey();
            Map<LocalDate, Integer> estatisticasDiarias = entry.getValue();
            
            int total = estatisticasDiarias.entrySet().stream()
                    .filter(e -> !e.getKey().isBefore(inicio) && !e.getKey().isAfter(fim))
                    .mapToInt(Map.Entry::getValue)
                    .sum();
            
            totalPorUtilizador.put(utilizador, total);
        }
        
        return totalPorUtilizador.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }
    
    /**
     * Obtém o utilizador com mais pontos
     */
    public Utilizador getUtilizadorMaisPontos() {
        return utilizadores.stream()
                .max(Comparator.comparingInt(Utilizador::getPontos))
                .orElse(null);
    }
    
    /**
     * Obtém o género musical mais reproduzido
     */
    public GeneroMusical getGeneroMaisReproduzido() {
        return estatisticasGenero.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }
    
    /**
     * Obtém o número de playlists públicas
     */
    public int getNumeroPlaylistsPublicas() {
        return playlistsPublicas.size();
    }
    
    /**
     * Obtém o utilizador com mais playlists
     */
    public Utilizador getUtilizadorMaisPlaylists() {
        Map<Utilizador, Integer> playlistsPorUtilizador = new HashMap<>();
        
        for (Utilizador u : utilizadores) {
            if (u instanceof UtilizadorPremium) {
                UtilizadorPremium up = (UtilizadorPremium) u;
                playlistsPorUtilizador.put(u, up.getPlaylists().size());
            } else {
                playlistsPorUtilizador.put(u, 0);
            }
        }
        
        return playlistsPorUtilizador.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }
    
    // Getters
    
    public List<Utilizador> getUtilizadores() {
        return new ArrayList<>(utilizadores);
    }
    
    public List<Album> getAlbuns() {
        return new ArrayList<>(albuns);
    }
    
    public List<Playlist> getPlaylistsPublicas() {
        return new ArrayList<>(playlistsPublicas);
    }
    
    // Métodos para salvar e carregar o estado
    
    /**
     * Salva o estado atual do sistema em um arquivo
     */
    public void salvarEstado(String nomeArquivo) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nomeArquivo))) {
            oos.writeObject(this);
            System.out.println("Estado do sistema salvo com sucesso em " + nomeArquivo);
        }
    }
    
    /**
     * Carrega o estado do sistema a partir de um arquivo
     */
    public static SpotifUM carregarEstado(String nomeArquivo) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nomeArquivo))) {
            SpotifUM sistema = (SpotifUM) ois.readObject();
            System.out.println("Estado do sistema carregado com sucesso de " + nomeArquivo);
            return sistema;
        }
    }
}