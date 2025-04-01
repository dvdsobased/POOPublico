package src.Musica;
/**
 * Enumeração dos géneros musicais disponíveis no sistema
 */
public enum GeneroMusical {
    POP, ROCK, CLASSICA, JAZZ, HIPHOP, ELECTRONICA, FADO, METAL, REGGAE, BLUES, FOLK;
    
    @Override
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
}