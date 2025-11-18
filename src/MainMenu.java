package src;

import src.Musica.*;
import src.Playlist.*;
import src.Utilizador.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Classe responsável pela interface com o utilizador (menu)
 */
public class MainMenu {
    private static final Scanner scanner = new Scanner(System.in);
    private static SpotifUM sistema;
    private static Utilizador utilizadorAtual;
    
    public static void main(String[] args) {
        inicializarSistema();
        
        boolean sair = false;
        while (!sair) {
            mostrarMenuPrincipal();
            int opcao = lerOpcao();
            
            switch (opcao) {
                case 1:
                    // Login de utilizador
                    fazerLogin();
                    break;
                case 2:
                    // Registrar novo utilizador
                    registrarUtilizador();
                    break;
                case 3:
                    // Gestão de álbuns e músicas
                    if (verificarAdmin()) {
                        menuGestaoAlbuns();
                    }
                    break;
                case 4:
                    // Estatísticas
                    menuEstatisticas();
                    break;
                case 5:
                    // Salvar estado
                    salvarEstado();
                    break;
                case 6:
                    // Carregar estado
                    carregarEstado();
                    break;
                case 0:
                    sair = true;
                    System.out.println("A sair do SpotifUM...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }
    
    private static void inicializarSistema() {
        sistema = new SpotifUM();
        System.out.println("Sistema SpotifUM iniciado.");
        
        // Perguntar se deseja carregar dados
        System.out.print("Deseja carregar um estado salvo anteriormente? (S/N): ");
        String resposta = scanner.nextLine().trim().toUpperCase();
        
        if (resposta.equals("S")) {
            carregarEstado();
        } else {
            // Criar alguns dados de exemplo para facilitar testes
            criarDadosExemplo();
        }
    }
    
    private static void criarDadosExemplo() {
        // Criar utilizadores
        PlanoSubscricao planoFree = new PlanoFree();
        PlanoSubscricao planoBase = new PlanoPremiumBase();
        PlanoSubscricao planoTop = new PlanoPremiumTop();
        
        sistema.adicionarUtilizadorOcasional("João Silva", "joao@email.com", "Rua A, 123");
        sistema.adicionarUtilizadorPremium("Maria Santos", "maria@email.com", "Rua B, 456", planoBase);
        sistema.adicionarUtilizadorPremium("Carlos Oliveira", "carlos@email.com", "Rua C, 789", planoTop);
        
        // Criar álbuns e músicas
        Album album1 = sistema.adicionarAlbum("Grandes Êxitos", "Vários Artistas", 2023);
        Album album2 = sistema.adicionarAlbum("Rock Portugal", "Banda Portuguesa", 2022);
        
        List<String> notasMusicais = Arrays.asList("Dó", "Ré", "Mi", "Fá", "Sol");
        
        sistema.adicionarMusicaNormal(album1, "Canção Pop", "Artista A", "Editora XYZ", 
                "Letra da canção pop...", notasMusicais, GeneroMusical.POP, 180);
        
        sistema.adicionarMusicaExplicita(album1, "Rock Pesado", "Artista B", "Editora ABC", 
                "Letra da música de rock...", notasMusicais, GeneroMusical.ROCK, 240, 
                "Contém linguagem imprópria");
        
        sistema.adicionarMusicaMultimedia(album2, "Vídeo Clipe", "Banda Portuguesa", "Editora Nacional", 
                "Letra da música com vídeo...", notasMusicais, GeneroMusical.FOLK, 210, 
                "http://exemplo.com/video");
        
        sistema.adicionarMusicaNormal(album2, "Fado Novo", "Fadista Moderna", "Editora Fado", 
                "Letra do fado...", notasMusicais, GeneroMusical.FADO, 195);
        
        System.out.println("Dados de exemplo criados com sucesso!");
    }
    
    private static void mostrarMenuPrincipal() {
        System.out.println("\n===== SpotifUM - Menu Principal =====");
        System.out.println("1. Login de utilizador");
        System.out.println("2. Registrar novo utilizador");
        System.out.println("3. Gestão de álbuns e músicas (Admin)");
        System.out.println("4. Estatísticas do sistema");
        System.out.println("5. Salvar estado do sistema");
        System.out.println("6. Carregar estado do sistema");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }
    
    private static void fazerLogin() {
        System.out.println("\n===== Login =====");
        System.out.print("Email: ");
        String email = scanner.nextLine();
        
        Utilizador utilizador = sistema.encontrarUtilizador(email);
        if (utilizador != null) {
            utilizadorAtual = utilizador;
            System.out.println("Login efetuado com sucesso como " + utilizador.getNome());
            menuUtilizador();
        } else {
            System.out.println("Utilizador não encontrado!");
        }
    }
    
    private static void registrarUtilizador() {
        System.out.println("\n===== Registrar Novo Utilizador =====");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Morada: ");
        String morada = scanner.nextLine();
        
        System.out.println("Selecione o tipo de plano:");
        System.out.println("1. Free (Gratuito)");
        System.out.println("2. Premium Base");
        System.out.println("3. Premium Top");
        System.out.print("Opção: ");
        int opcao = lerOpcao();
        
        Utilizador novoUtilizador = null;
        
        switch (opcao) {
            case 1:
                novoUtilizador = sistema.adicionarUtilizadorOcasional(nome, email, morada);
                break;
            case 2:
                novoUtilizador = sistema.adicionarUtilizadorPremium(nome, email, morada, new PlanoPremiumBase());
                break;
            case 3:
                novoUtilizador = sistema.adicionarUtilizadorPremium(nome, email, morada, new PlanoPremiumTop());
                break;
            default:
                System.out.println("Opção inválida! Utilizador registrado como Free.");
                novoUtilizador = sistema.adicionarUtilizadorOcasional(nome, email, morada);
        }
        
        if (novoUtilizador != null) {
            System.out.println("Utilizador registrado com sucesso!");
        } else {
            System.out.println("Erro ao registrar utilizador!");
        }
    }

    private static int lerOpcao() {
        try {
            String input = scanner.nextLine();
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static boolean verificarAdmin() {
        System.out.println("Funcionalidade de administração disponível.");
        return true;
    }

    private static void menuUtilizador() {
        boolean sair = false;
        while (!sair) {
            System.out.println("\n===== Menu do Utilizador: " + utilizadorAtual.getNome() + " =====");
            System.out.println("Plano: " + utilizadorAtual.getPlanoSubscricao().getNome());
            System.out.println("Pontos: " + utilizadorAtual.getPontos());
            System.out.println("\n1. Reproduzir música");
            System.out.println("2. Reproduzir álbum");
            System.out.println("3. Reproduzir playlist pública");

            if (utilizadorAtual instanceof UtilizadorPremium) {
                System.out.println("4. Criar playlist base");
                System.out.println("5. Criar playlist aleatória");
                System.out.println("6. Gerar playlist de favoritos");
                System.out.println("7. Gerar playlist por tempo e género");
                System.out.println("8. Gerar playlist de músicas explícitas");
                System.out.println("9. Ver minha biblioteca");
                System.out.println("10. Adicionar álbum à biblioteca");
                System.out.println("11. Ver minhas playlists");
            }

            System.out.println("0. Logout");
            System.out.print("Escolha uma opção: ");

            int opcao = lerOpcao();

            switch (opcao) {
                case 1:
                    reproduzirMusica();
                    break;
                case 2:
                    reproduzirAlbum();
                    break;
                case 3:
                    reproduzirPlaylistPublica();
                    break;
                case 4:
                    if (utilizadorAtual instanceof UtilizadorPremium) {
                        criarPlaylistBase();
                    } else {
                        System.out.println("Funcionalidade disponível apenas para utilizadores Premium!");
                    }
                    break;
                case 5:
                    if (utilizadorAtual instanceof UtilizadorPremium) {
                        criarPlaylistAleatoria();
                    } else {
                        System.out.println("Funcionalidade disponível apenas para utilizadores Premium!");
                    }
                    break;
                case 6:
                    if (utilizadorAtual instanceof UtilizadorPremium) {
                        gerarPlaylistFavoritos();
                    } else {
                        System.out.println("Funcionalidade disponível apenas para utilizadores Premium!");
                    }
                    break;
                case 7:
                    if (utilizadorAtual instanceof UtilizadorPremium) {
                        gerarPlaylistTempoGenero();
                    } else {
                        System.out.println("Funcionalidade disponível apenas para utilizadores Premium!");
                    }
                    break;
                case 8:
                    if (utilizadorAtual instanceof UtilizadorPremium) {
                        gerarPlaylistExplicitas();
                    } else {
                        System.out.println("Funcionalidade disponível apenas para utilizadores Premium!");
                    }
                    break;
                case 9:
                    if (utilizadorAtual instanceof UtilizadorPremium) {
                        verBiblioteca();
                    } else {
                        System.out.println("Funcionalidade disponível apenas para utilizadores Premium!");
                    }
                    break;
                case 10:
                    if (utilizadorAtual instanceof UtilizadorPremium) {
                        adicionarAlbumBiblioteca();
                    } else {
                        System.out.println("Funcionalidade disponível apenas para utilizadores Premium!");
                    }
                    break;
                case 11:
                    if (utilizadorAtual instanceof UtilizadorPremium) {
                        verPlaylists();
                    } else {
                        System.out.println("Funcionalidade disponível apenas para utilizadores Premium!");
                    }
                    break;
                case 0:
                    sair = true;
                    utilizadorAtual = null;
                    System.out.println("Logout efetuado com sucesso!");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private static void reproduzirMusica() {
        List<Album> albuns = sistema.getAlbuns();
        if (albuns.isEmpty()) {
            System.out.println("Nenhum álbum disponível!");
            return;
        }

        System.out.println("\n===== Álbuns Disponíveis =====");
        for (int i = 0; i < albuns.size(); i++) {
            Album album = albuns.get(i);
            System.out.println((i + 1) + ". " + album.getNome() + " - " + album.getInterprete());
        }

        System.out.print("Escolha um álbum: ");
        int albumIdx = lerOpcao() - 1;

        if (albumIdx < 0 || albumIdx >= albuns.size()) {
            System.out.println("Álbum inválido!");
            return;
        }

        Album albumEscolhido = albuns.get(albumIdx);
        List<Musica> musicas = albumEscolhido.getMusicas();

        if (musicas.isEmpty()) {
            System.out.println("Este álbum não tem músicas!");
            return;
        }

        System.out.println("\n===== Músicas do álbum " + albumEscolhido.getNome() + " =====");
        for (int i = 0; i < musicas.size(); i++) {
            Musica musica = musicas.get(i);
            String tipo = "";
            if (musica instanceof MusicaExplicita) {
                tipo = " [EXPLÍCITA]";
            } else if (musica instanceof MusicaMultimedia) {
                tipo = " [MULTIMÉDIA]";
            }
            System.out.println((i + 1) + ". " + musica.getNome() + " - " + musica.getInterprete() + tipo);
        }

        System.out.print("Escolha uma música: ");
        int musicaIdx = lerOpcao() - 1;

        if (musicaIdx < 0 || musicaIdx >= musicas.size()) {
            System.out.println("Música inválida!");
            return;
        }

        Musica musicaEscolhida = musicas.get(musicaIdx);
        sistema.reproduzirMusica(utilizadorAtual, musicaEscolhida);
        System.out.println("\nPontos atuais: " + utilizadorAtual.getPontos());
    }

    private static void reproduzirAlbum() {
        List<Album> albuns = sistema.getAlbuns();
        if (albuns.isEmpty()) {
            System.out.println("Nenhum álbum disponível!");
            return;
        }

        System.out.println("\n===== Álbuns Disponíveis =====");
        for (int i = 0; i < albuns.size(); i++) {
            Album album = albuns.get(i);
            System.out.println((i + 1) + ". " + album.getNome() + " - " + album.getInterprete() +
                    " (" + album.getMusicas().size() + " músicas)");
        }

        System.out.print("Escolha um álbum: ");
        int albumIdx = lerOpcao() - 1;

        if (albumIdx < 0 || albumIdx >= albuns.size()) {
            System.out.println("Álbum inválido!");
            return;
        }

        Album albumEscolhido = albuns.get(albumIdx);

        if (utilizadorAtual instanceof UtilizadorPremium) {
            ((UtilizadorPremium) utilizadorAtual).reproduzirAlbum(albumEscolhido);
        } else {
            // Utilizadores Free não podem escolher álbuns, mas podemos reproduzir as músicas
            for (Musica musica : albumEscolhido.getMusicas()) {
                sistema.reproduzirMusica(utilizadorAtual, musica);
            }
        }
        System.out.println("\nPontos atuais: " + utilizadorAtual.getPontos());
    }

    private static void reproduzirPlaylistPublica() {
        List<Playlist> playlistsPublicas = sistema.getPlaylistsPublicas();
        if (playlistsPublicas.isEmpty()) {
            System.out.println("Nenhuma playlist pública disponível!");
            return;
        }

        System.out.println("\n===== Playlists Públicas =====");
        for (int i = 0; i < playlistsPublicas.size(); i++) {
            Playlist playlist = playlistsPublicas.get(i);
            System.out.println((i + 1) + ". " + playlist.getNome() + " (criada por: " +
                    playlist.getCriador() + ", " + playlist.getMusicas().size() + " músicas)");
        }

        System.out.print("Escolha uma playlist: ");
        int playlistIdx = lerOpcao() - 1;

        if (playlistIdx < 0 || playlistIdx >= playlistsPublicas.size()) {
            System.out.println("Playlist inválida!");
            return;
        }

        Playlist playlistEscolhida = playlistsPublicas.get(playlistIdx);

        if (utilizadorAtual instanceof UtilizadorPremium) {
            ((UtilizadorPremium) utilizadorAtual).reproduzirPlaylist(playlistEscolhida);
        } else {
            // Utilizadores Free reproduzem as músicas
            for (Musica musica : playlistEscolhida.getMusicas()) {
                sistema.reproduzirMusica(utilizadorAtual, musica);
            }
        }
        System.out.println("\nPontos atuais: " + utilizadorAtual.getPontos());
    }

    private static void criarPlaylistBase() {
        UtilizadorPremium userPremium = (UtilizadorPremium) utilizadorAtual;

        System.out.print("\nNome da playlist: ");
        String nome = scanner.nextLine();

        Playlist novaPlaylist = userPremium.criarPlaylist(nome, false);

        // Adicionar músicas à playlist
        adicionarMusicasPlaylist(novaPlaylist);

        System.out.print("Tornar esta playlist pública? (S/N): ");
        String resposta = scanner.nextLine().trim().toUpperCase();
        if (resposta.equals("S")) {
            novaPlaylist.setPublica(true);
            sistema.adicionarPlaylistPublica(novaPlaylist);
            System.out.println("Playlist criada e tornada pública!");
        } else {
            System.out.println("Playlist criada!");
        }
    }

    private static void criarPlaylistAleatoria() {
        UtilizadorPremium userPremium = (UtilizadorPremium) utilizadorAtual;

        System.out.print("\nNome da playlist: ");
        String nome = scanner.nextLine();

        Playlist novaPlaylist = userPremium.criarPlaylistAleatoria(nome, false);

        // Adicionar músicas à playlist
        adicionarMusicasPlaylist(novaPlaylist);

        System.out.print("Tornar esta playlist pública? (S/N): ");
        String resposta = scanner.nextLine().trim().toUpperCase();
        if (resposta.equals("S")) {
            novaPlaylist.setPublica(true);
            sistema.adicionarPlaylistPublica(novaPlaylist);
            System.out.println("Playlist aleatória criada e tornada pública!");
        } else {
            System.out.println("Playlist aleatória criada!");
        }
    }

    private static void adicionarMusicasPlaylist(Playlist playlist) {
        boolean continuar = true;
        while (continuar) {
            List<Album> albuns = sistema.getAlbuns();
            if (albuns.isEmpty()) {
                System.out.println("Nenhum álbum disponível!");
                return;
            }

            System.out.println("\n===== Adicionar Músicas à Playlist =====");
            for (int i = 0; i < albuns.size(); i++) {
                Album album = albuns.get(i);
                System.out.println((i + 1) + ". " + album.getNome() + " - " + album.getInterprete());
            }

            System.out.print("Escolha um álbum (0 para terminar): ");
            int albumIdx = lerOpcao() - 1;

            if (albumIdx == -1) {
                continuar = false;
                continue;
            }

            if (albumIdx < 0 || albumIdx >= albuns.size()) {
                System.out.println("Álbum inválido!");
                continue;
            }

            Album albumEscolhido = albuns.get(albumIdx);
            List<Musica> musicas = albumEscolhido.getMusicas();

            if (musicas.isEmpty()) {
                System.out.println("Este álbum não tem músicas!");
                continue;
            }

            System.out.println("\n===== Músicas do álbum " + albumEscolhido.getNome() + " =====");
            for (int i = 0; i < musicas.size(); i++) {
                Musica musica = musicas.get(i);
                System.out.println((i + 1) + ". " + musica.getNome() + " - " + musica.getInterprete());
            }

            System.out.print("Escolha uma música: ");
            int musicaIdx = lerOpcao() - 1;

            if (musicaIdx < 0 || musicaIdx >= musicas.size()) {
                System.out.println("Música inválida!");
                continue;
            }

            Musica musicaEscolhida = musicas.get(musicaIdx);
            playlist.adicionarMusica(musicaEscolhida);
            System.out.println("Música adicionada à playlist!");

            System.out.print("Adicionar mais músicas? (S/N): ");
            String resposta = scanner.nextLine().trim().toUpperCase();
            if (!resposta.equals("S")) {
                continuar = false;
            }
        }
    }

    private static void gerarPlaylistFavoritos() {
        UtilizadorPremium userPremium = (UtilizadorPremium) utilizadorAtual;

        if (!userPremium.getPlanoSubscricao().podeReceberPlaylistsGeradas()) {
            System.out.println("Esta funcionalidade está disponível apenas para utilizadores Premium Top!");
            return;
        }

        Playlist playlistFavoritos = userPremium.criarPlaylistFavoritos("Meus Favoritos", false, sistema.getTodasMusicas());

        if (playlistFavoritos == null || playlistFavoritos.getMusicas().isEmpty()) {
            System.out.println("Não foi possível gerar playlist de favoritos. Ouça mais músicas primeiro!");
            return;
        }

        System.out.println("\n===== Playlist de Favoritos Gerada =====");
        System.out.println("Nome: " + playlistFavoritos.getNome());
        System.out.println("Músicas: " + playlistFavoritos.getMusicas().size());
        System.out.println("\nReproduzindo playlist de favoritos...\n");

        userPremium.reproduzirPlaylist(playlistFavoritos);
        System.out.println("\nPontos atuais: " + utilizadorAtual.getPontos());
    }

    private static void gerarPlaylistTempoGenero() {
        UtilizadorPremium userPremium = (UtilizadorPremium) utilizadorAtual;

        System.out.println("\n===== Gerar Playlist por Tempo e Género =====");
        System.out.println("Géneros disponíveis:");
        GeneroMusical[] generos = GeneroMusical.values();
        for (int i = 0; i < generos.length; i++) {
            System.out.println((i + 1) + ". " + generos[i]);
        }

        System.out.print("Escolha um género: ");
        int generoIdx = lerOpcao() - 1;

        if (generoIdx < 0 || generoIdx >= generos.length) {
            System.out.println("Género inválido!");
            return;
        }

        GeneroMusical generoEscolhido = generos[generoIdx];

        System.out.print("Tempo máximo em segundos: ");
        int tempoMaximo = lerOpcao();

        if (tempoMaximo <= 0) {
            System.out.println("Tempo inválido!");
            return;
        }

        Playlist playlistGerada = userPremium.criarPlaylistTempoGenero(
            "Playlist " + generoEscolhido, false, tempoMaximo, generoEscolhido, sistema.getTodasMusicas());

        if (playlistGerada == null || playlistGerada.getMusicas().isEmpty()) {
            System.out.println("Não foi possível gerar playlist com os critérios especificados!");
            return;
        }

        System.out.println("\n===== Playlist Gerada =====");
        System.out.println("Nome: " + playlistGerada.getNome());
        System.out.println("Músicas: " + playlistGerada.getMusicas().size());
        System.out.println("Duração total: " + playlistGerada.getDuracaoTotal() + " segundos");

        System.out.print("\nReproduzir esta playlist? (S/N): ");
        String resposta = scanner.nextLine().trim().toUpperCase();
        if (resposta.equals("S")) {
            userPremium.reproduzirPlaylist(playlistGerada);
            System.out.println("\nPontos atuais: " + utilizadorAtual.getPontos());
        }
    }

    private static void gerarPlaylistExplicitas() {
        UtilizadorPremium userPremium = (UtilizadorPremium) utilizadorAtual;

        Playlist playlistExplicitas = userPremium.criarPlaylistExplicita("Músicas Explícitas", false, sistema.getTodasMusicas());

        if (playlistExplicitas == null || playlistExplicitas.getMusicas().isEmpty()) {
            System.out.println("Não existem músicas explícitas disponíveis!");
            return;
        }

        System.out.println("\n===== Playlist de Músicas Explícitas Gerada =====");
        System.out.println("Nome: " + playlistExplicitas.getNome());
        System.out.println("Músicas: " + playlistExplicitas.getMusicas().size());

        System.out.print("\nReproduzir esta playlist? (S/N): ");
        String resposta = scanner.nextLine().trim().toUpperCase();
        if (resposta.equals("S")) {
            userPremium.reproduzirPlaylist(playlistExplicitas);
            System.out.println("\nPontos atuais: " + utilizadorAtual.getPontos());
        }
    }

    private static void verBiblioteca() {
        UtilizadorPremium userPremium = (UtilizadorPremium) utilizadorAtual;
        List<Album> biblioteca = userPremium.getBiblioteca();

        if (biblioteca.isEmpty()) {
            System.out.println("\nSua biblioteca está vazia!");
            return;
        }

        System.out.println("\n===== Minha Biblioteca =====");
        for (int i = 0; i < biblioteca.size(); i++) {
            Album album = biblioteca.get(i);
            System.out.println((i + 1) + ". " + album.getNome() + " - " + album.getInterprete() +
                    " (" + album.getMusicas().size() + " músicas)");
        }
    }

    private static void adicionarAlbumBiblioteca() {
        UtilizadorPremium userPremium = (UtilizadorPremium) utilizadorAtual;

        List<Album> albuns = sistema.getAlbuns();
        if (albuns.isEmpty()) {
            System.out.println("Nenhum álbum disponível!");
            return;
        }

        System.out.println("\n===== Álbuns Disponíveis =====");
        for (int i = 0; i < albuns.size(); i++) {
            Album album = albuns.get(i);
            System.out.println((i + 1) + ". " + album.getNome() + " - " + album.getInterprete());
        }

        System.out.print("Escolha um álbum: ");
        int albumIdx = lerOpcao() - 1;

        if (albumIdx < 0 || albumIdx >= albuns.size()) {
            System.out.println("Álbum inválido!");
            return;
        }

        Album albumEscolhido = albuns.get(albumIdx);
        userPremium.adicionarAlbum(albumEscolhido);
        System.out.println("Álbum adicionado à biblioteca!");
    }

    private static void verPlaylists() {
        UtilizadorPremium userPremium = (UtilizadorPremium) utilizadorAtual;
        List<Playlist> playlists = userPremium.getPlaylists();

        if (playlists.isEmpty()) {
            System.out.println("\nVocê não tem playlists!");
            return;
        }

        System.out.println("\n===== Minhas Playlists =====");
        for (int i = 0; i < playlists.size(); i++) {
            Playlist playlist = playlists.get(i);
            String tipo = playlist.isPublica() ? "[PÚBLICA]" : "[PRIVADA]";
            System.out.println((i + 1) + ". " + playlist.getNome() + " " + tipo +
                    " (" + playlist.getMusicas().size() + " músicas)");
        }
    }

    private static void menuGestaoAlbuns() {
        boolean sair = false;
        while (!sair) {
            System.out.println("\n===== Gestão de Álbuns e Músicas =====");
            System.out.println("1. Criar novo álbum");
            System.out.println("2. Adicionar música a álbum");
            System.out.println("3. Listar todos os álbuns");
            System.out.println("4. Remover álbum");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            int opcao = lerOpcao();

            switch (opcao) {
                case 1:
                    criarAlbum();
                    break;
                case 2:
                    adicionarMusicaAlbum();
                    break;
                case 3:
                    listarAlbuns();
                    break;
                case 4:
                    removerAlbum();
                    break;
                case 0:
                    sair = true;
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private static void criarAlbum() {
        System.out.println("\n===== Criar Novo Álbum =====");
        System.out.print("Nome do álbum: ");
        String nome = scanner.nextLine();
        System.out.print("Intérprete: ");
        String interprete = scanner.nextLine();
        System.out.print("Ano de lançamento: ");
        int ano = lerOpcao();

        Album novoAlbum = sistema.adicionarAlbum(nome, interprete, ano);
        if (novoAlbum != null) {
            System.out.println("Álbum criado com sucesso!");
        } else {
            System.out.println("Erro ao criar álbum!");
        }
    }

    private static void adicionarMusicaAlbum() {
        List<Album> albuns = sistema.getAlbuns();
        if (albuns.isEmpty()) {
            System.out.println("Nenhum álbum disponível! Crie um álbum primeiro.");
            return;
        }

        System.out.println("\n===== Adicionar Música a Álbum =====");
        System.out.println("Álbuns disponíveis:");
        for (int i = 0; i < albuns.size(); i++) {
            Album album = albuns.get(i);
            System.out.println((i + 1) + ". " + album.getNome() + " - " + album.getInterprete());
        }

        System.out.print("Escolha um álbum: ");
        int albumIdx = lerOpcao() - 1;

        if (albumIdx < 0 || albumIdx >= albuns.size()) {
            System.out.println("Álbum inválido!");
            return;
        }

        Album albumEscolhido = albuns.get(albumIdx);

        System.out.println("\nTipo de música:");
        System.out.println("1. Normal");
        System.out.println("2. Explícita");
        System.out.println("3. Multimédia (com vídeo)");
        System.out.print("Escolha o tipo: ");
        int tipo = lerOpcao();

        System.out.print("Nome da música: ");
        String nome = scanner.nextLine();
        System.out.print("Intérprete: ");
        String interprete = scanner.nextLine();
        System.out.print("Editora: ");
        String editora = scanner.nextLine();
        System.out.print("Letra: ");
        String letra = scanner.nextLine();

        System.out.println("Género musical:");
        GeneroMusical[] generos = GeneroMusical.values();
        for (int i = 0; i < generos.length; i++) {
            System.out.println((i + 1) + ". " + generos[i]);
        }
        System.out.print("Escolha um género: ");
        int generoIdx = lerOpcao() - 1;

        if (generoIdx < 0 || generoIdx >= generos.length) {
            System.out.println("Género inválido!");
            return;
        }

        GeneroMusical genero = generos[generoIdx];

        System.out.print("Duração em segundos: ");
        int duracao = lerOpcao();

        // Notas musicais simples
        List<String> notas = Arrays.asList("Dó", "Ré", "Mi", "Fá", "Sol", "Lá", "Si");

        switch (tipo) {
            case 1:
                sistema.adicionarMusicaNormal(albumEscolhido, nome, interprete, editora, letra, notas, genero, duracao);
                System.out.println("Música normal adicionada com sucesso!");
                break;
            case 2:
                System.out.print("Aviso de conteúdo explícito: ");
                String aviso = scanner.nextLine();
                sistema.adicionarMusicaExplicita(albumEscolhido, nome, interprete, editora, letra, notas, genero, duracao, aviso);
                System.out.println("Música explícita adicionada com sucesso!");
                break;
            case 3:
                System.out.print("Link do vídeo: ");
                String link = scanner.nextLine();
                sistema.adicionarMusicaMultimedia(albumEscolhido, nome, interprete, editora, letra, notas, genero, duracao, link);
                System.out.println("Música multimédia adicionada com sucesso!");
                break;
            default:
                System.out.println("Tipo inválido!");
        }
    }

    private static void listarAlbuns() {
        List<Album> albuns = sistema.getAlbuns();
        if (albuns.isEmpty()) {
            System.out.println("\nNenhum álbum cadastrado!");
            return;
        }

        System.out.println("\n===== Álbuns Cadastrados =====");
        for (int i = 0; i < albuns.size(); i++) {
            Album album = albuns.get(i);
            System.out.println("\n" + (i + 1) + ". " + album.getNome());
            System.out.println("   Intérprete: " + album.getInterprete());
            System.out.println("   Ano: " + album.getAnoLancamento());
            System.out.println("   Músicas: " + album.getMusicas().size());

            if (!album.getMusicas().isEmpty()) {
                System.out.println("   Lista de músicas:");
                for (Musica musica : album.getMusicas()) {
                    String tipo = "";
                    if (musica instanceof MusicaExplicita) {
                        tipo = " [EXPLÍCITA]";
                    } else if (musica instanceof MusicaMultimedia) {
                        tipo = " [MULTIMÉDIA]";
                    }
                    System.out.println("      - " + musica.getNome() + tipo + " (" + musica.getDuracao() + "s)");
                }
            }
        }
    }

    private static void removerAlbum() {
        List<Album> albuns = sistema.getAlbuns();
        if (albuns.isEmpty()) {
            System.out.println("Nenhum álbum disponível!");
            return;
        }

        System.out.println("\n===== Remover Álbum =====");
        for (int i = 0; i < albuns.size(); i++) {
            Album album = albuns.get(i);
            System.out.println((i + 1) + ". " + album.getNome() + " - " + album.getInterprete());
        }

        System.out.print("Escolha um álbum para remover: ");
        int albumIdx = lerOpcao() - 1;

        if (albumIdx < 0 || albumIdx >= albuns.size()) {
            System.out.println("Álbum inválido!");
            return;
        }

        Album albumRemover = albuns.get(albumIdx);
        sistema.removerAlbum(albumRemover);
        System.out.println("Álbum removido com sucesso!");
    }

    private static void menuEstatisticas() {
        System.out.println("\n===== Estatísticas do Sistema =====");

        // 1. Música mais reproduzida
        Musica musicaMaisReproduzida = sistema.getMusicaMaisReproduzida();
        if (musicaMaisReproduzida != null) {
            System.out.println("\n1. Música mais reproduzida:");
            System.out.println("   " + musicaMaisReproduzida.getNome() + " - " +
                    musicaMaisReproduzida.getInterprete() +
                    " (" + musicaMaisReproduzida.getContadorReproducoes() + " reproduções)");
        } else {
            System.out.println("\n1. Música mais reproduzida: Nenhuma música foi reproduzida ainda");
        }

        // 2. Intérprete mais escutado
        String interpreteMaisEscutado = sistema.getInterpreteMaisEscutado();
        if (interpreteMaisEscutado != null) {
            System.out.println("\n2. Intérprete mais escutado:");
            System.out.println("   " + interpreteMaisEscutado);
        } else {
            System.out.println("\n2. Intérprete mais escutado: Nenhum intérprete registrado");
        }

        // 3. Utilizador que mais músicas ouviu (desde sempre)
        Utilizador userMaisMusicasTotal = sistema.getUtilizadorMaisMusicasOuvidas();
        if (userMaisMusicasTotal != null) {
            System.out.println("\n3. Utilizador que mais músicas ouviu (total):");
            System.out.println("   " + userMaisMusicasTotal.getNome() + " - " + userMaisMusicasTotal.getEmail());
        } else {
            System.out.println("\n3. Utilizador que mais músicas ouviu: Nenhum utilizador registrado");
        }

        // 3b. Utilizador que mais músicas ouviu em período
        System.out.println("\nDeseja consultar por período específico? (S/N): ");
        String resposta = scanner.nextLine().trim().toUpperCase();
        if (resposta.equals("S")) {
            System.out.print("Data inicial (dd/MM/yyyy): ");
            String dataInicialStr = scanner.nextLine();
            System.out.print("Data final (dd/MM/yyyy): ");
            String dataFinalStr = scanner.nextLine();

            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate dataInicial = LocalDate.parse(dataInicialStr, formatter);
                LocalDate dataFinal = LocalDate.parse(dataFinalStr, formatter);

                Utilizador userMaisMusicasPeriodo = sistema.getUtilizadorMaisMusicasOuvidasPeriodo(dataInicial, dataFinal);
                if (userMaisMusicasPeriodo != null) {
                    System.out.println("   Utilizador que mais músicas ouviu no período:");
                    System.out.println("   " + userMaisMusicasPeriodo.getNome() + " - " + userMaisMusicasPeriodo.getEmail());
                } else {
                    System.out.println("   Nenhum utilizador ouviu músicas no período especificado");
                }
            } catch (DateTimeParseException e) {
                System.out.println("   Formato de data inválido!");
            }
        }

        // 4. Utilizador com mais pontos
        Utilizador userMaisPontos = sistema.getUtilizadorMaisPontos();
        if (userMaisPontos != null) {
            System.out.println("\n4. Utilizador com mais pontos:");
            System.out.println("   " + userMaisPontos.getNome() + " - " +
                    userMaisPontos.getPontos() + " pontos");
        } else {
            System.out.println("\n4. Utilizador com mais pontos: Nenhum utilizador registrado");
        }

        // 5. Tipo de música mais reproduzida
        GeneroMusical generoMaisReproduzido = sistema.getGeneroMaisReproduzido();
        if (generoMaisReproduzido != null) {
            System.out.println("\n5. Tipo de música mais reproduzida:");
            System.out.println("   " + generoMaisReproduzido);
        } else {
            System.out.println("\n5. Tipo de música mais reproduzida: Nenhuma música foi reproduzida ainda");
        }

        // 6. Quantas playlists públicas existem
        int numPlaylistsPublicas = sistema.getNumeroPlaylistsPublicas();
        System.out.println("\n6. Número de playlists públicas: " + numPlaylistsPublicas);

        // 7. Utilizador com mais playlists
        Utilizador userMaisPlaylists = sistema.getUtilizadorMaisPlaylists();
        if (userMaisPlaylists != null) {
            System.out.println("\n7. Utilizador com mais playlists:");
            if (userMaisPlaylists instanceof UtilizadorPremium) {
                UtilizadorPremium userPremium = (UtilizadorPremium) userMaisPlaylists;
                System.out.println("   " + userMaisPlaylists.getNome() + " - " +
                        userPremium.getPlaylists().size() + " playlists");
            }
        } else {
            System.out.println("\n7. Utilizador com mais playlists: Nenhum utilizador premium registrado");
        }

        System.out.println("\n" + "=".repeat(40));
    }

    private static void salvarEstado() {
        System.out.print("\nNome do ficheiro para salvar (sem extensão): ");
        String nomeFicheiro = scanner.nextLine();

        try {
            sistema.salvarEstado(nomeFicheiro + ".dat");
            System.out.println("Estado salvo com sucesso em " + nomeFicheiro + ".dat");
        } catch (IOException e) {
            System.out.println("Erro ao salvar estado: " + e.getMessage());
        }
    }

    private static void carregarEstado() {
        System.out.print("\nNome do ficheiro para carregar (sem extensão): ");
        String nomeFicheiro = scanner.nextLine();

        try {
            sistema = SpotifUM.carregarEstado(nomeFicheiro + ".dat");
            System.out.println("Estado carregado com sucesso de " + nomeFicheiro + ".dat");
            System.out.println("Utilizadores: " + sistema.getUtilizadores().size());
            System.out.println("Álbuns: " + sistema.getAlbuns().size());
            System.out.println("Playlists públicas: " + sistema.getPlaylistsPublicas().size());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar estado: " + e.getMessage());
            System.out.println("Continuando com sistema vazio.");
            sistema = new SpotifUM();
        }
    }
}
