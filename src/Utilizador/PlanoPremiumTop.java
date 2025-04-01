package src.Utilizador;

/**
 * Plano de subscrição premium avançado
 */
public class PlanoPremiumTop implements PlanoSubscricao {
    
    @Override
    public int calcularPontos(int pontosAtuais) {
        // Utilizadores Premium Top recebem 2.5% dos pontos já acumulados
        return (int)(pontosAtuais * 0.025);
    }
    
    @Override
    public boolean podeRetrocederMusica() {
        return true; // Permite retroceder música
    }
    
    @Override
    public boolean podeCriarPlaylist() {
        return true; // Permite criar playlists
    }
    
    @Override
    public boolean podeGuardarBiblioteca() {
        return true; // Permite guardar biblioteca
    }
    
    @Override
    public boolean podeReceberPlaylistsGeradas() {
        return true; // Permite receber playlists geradas
    }
    
    @Override
    public String getNome() {
        return "Premium Top";
    }
    
    @Override
    public String toString() {
        return "Plano Premium Top";
    }
}
