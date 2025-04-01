package src.Playlist;

import src.Utilizador.*;
import src.Musica.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Playlist que reproduz músicas em ordem aleatória
 */
public class PlaylistAleatoria extends PlaylistBase {
    
    public PlaylistAleatoria(String nome, Utilizador criador, boolean publica) {
        super(nome, criador, publica);
    }
    
    @Override
    public void reproduzir() {
        System.out.println("A reproduzir playlist aleatória: " + getNome());
        
        List<Musica> musicasAleatorias = new ArrayList<>(getMusicas());
        Collections.shuffle(musicasAleatorias);
        
        for (Musica musica : musicasAleatorias) {
            System.out.println("\n--- Faixa seguinte (aleatória) ---");
            musica.reproduzir();
        }
    }
    
    @Override
    public String toString() {
        return super.toString() + " [ALEATÓRIA]";
    }
}