package src.Utilizador;

/**
 * Plano de subscrição premium básico
 */
public class PlanoPremiumBase implements PlanoSubscricao {
    
    @Override
    public int calcularPontos(int pontosAtuais) {
        // Utilizadores Premium Base recebem 10 pontos por música
        return 10;
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
        return false; // Não permite receber playlists geradas
    }
    
    @Override
    public String getNome() {
        return "Premium Base";
    }
    
    @Override
    public String toString() {
        return "Plano Premium Base";
    }
}
