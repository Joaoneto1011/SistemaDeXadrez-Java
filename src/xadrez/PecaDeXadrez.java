package xadrez;

import jogodetabuleiro.Peca;
import jogodetabuleiro.Posicao;
import jogodetabuleiro.Tabuleiro;

public abstract class PecaDeXadrez extends Peca {
	
	private Cor cor;
	private int contagemMovimentos;

	public PecaDeXadrez(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}
	
	public Cor getCor() {
		
		return cor;
	}
	
	public int getContagemMovimentos() {
		
		return contagemMovimentos;
	}
	
	public void aumentarContagemMovimentos() {
		contagemMovimentos++;
	}
	
	public void diminuirContagemMovimentos() {
		contagemMovimentos--;
	}
	
	public PosicaoXadrez getPosicaoXadrez() {
		
		return PosicaoXadrez.dePosicao(posicao);
	}
	
	protected boolean existeAlgumaPecaAdversaria(Posicao posicao) {
		
		PecaDeXadrez p = (PecaDeXadrez) getTabuleiro().peca(posicao);
		return p != null && p.getCor() != cor;
	}

}
