package src.Utilizador;

/**
 * Plano de subscrição gratuito com funcionalidades limitadas
 */
public class PlanoFree implements PlanoSubscricao {
    
    @Override
    public int calcularPontos(int pontosAtuais) {
        // Utilizadores free recebem 5 pontos por música
        return 5;
    }
    
    @Override
    public boolean podeRetrocederMusica() {
        return false; // Não permite retroceder música
    }
    
    @Override
    public boolean podeCriarPlaylist() {
        return false; // Não permite criar playlists
    }
    
    @Override
    public boolean podeGuardarBiblioteca() {
        return false; // Não permite guardar biblioteca
    }
    
    @Override
    public boolean podeReceberPlaylistsGeradas() {
        return false; // Não permite receber playlists geradas
    }
    
    @Override
    public String getNome() {
        return "Free";
    }
    
    @Override
    public String toString() {
        return "Plano Free";
    }
}
