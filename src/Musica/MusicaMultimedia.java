package src.Musica;

import java.util.List;

/**
 * Implementação de uma música com conteúdo multimédia
 */
public class MusicaMultimedia extends Musica {
    private String linkVideo;
    
    public MusicaMultimedia(String nome, String interprete, String editora, String letra, 
                           List<String> conteudoMusical, GeneroMusical genero, int duracao,
                           String linkVideo) {
        super(nome, interprete, editora, letra, conteudoMusical, genero, duracao);
        this.linkVideo = linkVideo;
    }
    
    @Override
    public void reproduzir() {
        System.out.println("A reproduzir música multimédia:");
        System.out.println("Link do vídeo: " + linkVideo);
        super.reproduzir();
    }
    
    public String getLinkVideo() {
        return linkVideo;
    }
    
    public void setLinkVideo(String linkVideo) {
        this.linkVideo = linkVideo;
    }
    
    @Override
    public String toString() {
        return super.toString() + " [VÍDEO]";
    }
}
