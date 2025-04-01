package src.Musica;

import java.util.List;

/**
 * Implementação de uma música normal (sem conteúdo explícito ou multimédia)
 */
public class MusicaNormal extends Musica {
    
    public MusicaNormal(String nome, String interprete, String editora, String letra, 
                       List<String> conteudoMusical, GeneroMusical genero, int duracao) {
        super(nome, interprete, editora, letra, conteudoMusical, genero, duracao);
    }
    
    @Override
    public void reproduzir() {
        super.reproduzir();
    }
    
    @Override
    public String toString() {
        return super.toString();
    }
}
