package src.Musica;

import java.util.List;

/**
 * Implementação de uma música com conteúdo explícito
 */
public class MusicaExplicita extends Musica {
    private String avisoConteudo;
    
    public MusicaExplicita(String nome, String interprete, String editora, String letra, 
                         List<String> conteudoMusical, GeneroMusical genero, int duracao,
                         String avisoConteudo) {
        super(nome, interprete, editora, letra, conteudoMusical, genero, duracao);
        this.avisoConteudo = avisoConteudo;
    }
    
    @Override
    public void reproduzir() {
        System.out.println("AVISO DE CONTEÚDO EXPLÍCITO: " + avisoConteudo);
        System.out.println("A reproduzir música explícita:");
        super.reproduzir();
    }
    
    public String getAvisoConteudo() {
        return avisoConteudo;
    }
    
    public void setAvisoConteudo(String avisoConteudo) {
        this.avisoConteudo = avisoConteudo;
    }
    
    @Override
    public String toString() {
        return super.toString() + " [EXPLÍCITA]";
    }
}