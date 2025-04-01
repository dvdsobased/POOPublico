package src.Utilizador;

import java.io.Serializable;

/**
 * Interface para diferentes planos de subscrição
 */
public interface PlanoSubscricao extends Serializable {
    /**
     * Calcula os pontos ganhos por reproduzir uma música
     */
    int calcularPontos(int pontosAtuais);
    
    /**
     * Verifica se o plano permite retroceder música
     */
    boolean podeRetrocederMusica();
    
    /**
     * Verifica se o plano permite criar playlists
     */
    boolean podeCriarPlaylist();
    
    /**
     * Verifica se o plano permite guardar biblioteca
     */
    boolean podeGuardarBiblioteca();
    
    /**
     * Verifica se o plano permite receber playlists geradas
     */
    boolean podeReceberPlaylistsGeradas();
    
    /**
     * Obtém o nome do plano
     */
    String getNome();
}
