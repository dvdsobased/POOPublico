package src.Utilizador;

import src.Musica.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Classe abstrata que representa um utilizador do sistema
 */
public abstract class Utilizador implements Serializable {
    private String nome;
    private String email;
    private String morada;
    private int pontos;
    
    public Utilizador(String nome, String email, String morada) {
        this.nome = nome;
        this.email = email;
        this.morada = morada;
        this.pontos = 0;
    }
    
    /**
     * Adiciona pontos ao utilizador
     */
    public void adicionarPontos(int pontos) {
        this.pontos += pontos;
    }
    
    /**
     * Método para reproduzir uma música
     */
    public abstract void reproduzirMusica(Musica musica);
    
    /**
     * Obtém o plano de subscrição do utilizador
     */
    public abstract PlanoSubscricao getPlanoSubscricao();
    
    // Getters e Setters
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getMorada() {
        return morada;
    }
    
    public void setMorada(String morada) {
        this.morada = morada;
    }
    
    public int getPontos() {
        return pontos;
    }
    
    @Override
    public String toString() {
        return nome + " (" + email + ") - Pontos: " + pontos;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Utilizador utilizador = (Utilizador) obj;
        return email.equals(utilizador.email);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
} 
