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
            System.out.println("Utilizador registrado com suc
