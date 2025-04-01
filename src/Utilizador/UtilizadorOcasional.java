package src.Utilizador;

import src.Musica.*;
/**
 * Utilizador com funcionalidades básicas (gratuito)
 */
public class UtilizadorOcasional extends Utilizador {
    private PlanoSubscricao plano;
    
    public UtilizadorOcasional(String nome, String email, String morada) {
        super(nome, email, morada);
        this.plano = new PlanoFree();
    }
    
    @Override
    public void reproduzirMusica(Musica musica) {
        System.out.println("Utilizador " + getNome() + " está a reproduzir:");
        musica.reproduzir();
        
        // Adicionar pontos ao utilizador
        adicionarPontos(plano.calcularPontos(getPontos()));
    }
    
    @Override
    public PlanoSubscricao getPlanoSubscricao() {
        return plano;
    }
    
    @Override
    public String toString() {
        return super.toString() + " - Utilizador Ocasional";
    }
}
