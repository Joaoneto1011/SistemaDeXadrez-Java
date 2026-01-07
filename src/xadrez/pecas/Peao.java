package xadrez.pecas;

import jogodetabuleiro.Posicao;
import jogodetabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaDeXadrez;

public class Peao extends PecaDeXadrez {

	public Peao(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public boolean[][] movimentosPossiveis() {
		
		boolean[][] matriz = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Posicao p = new Posicao(0, 0);
		
		if (getCor() == Cor.BRANCO) {
			
			p.setValores(posicao.getLinha() - 1, posicao.getColuna());
			if(getTabuleiro().existePosicao(p) && !getTabuleiro().existePeca(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			
			p.setValores(posicao.getLinha() - 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() - 1, posicao.getColuna());
			if(getTabuleiro().existePosicao(p) && !getTabuleiro().existePeca(p) && getTabuleiro().existePosicao(p2) && !getTabuleiro().existePeca(p2) && getContagemMovimentos() == 0) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			
			p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
			if(getTabuleiro().existePosicao(p) && existeAlgumaPecaAdversaria(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			
			p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
			if(getTabuleiro().existePosicao(p) && existeAlgumaPecaAdversaria(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}	
		}
		else {
			
			p.setValores(posicao.getLinha() + 1, posicao.getColuna());
			if(getTabuleiro().existePosicao(p) && !getTabuleiro().existePeca(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			
			p.setValores(posicao.getLinha() + 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() + 1, posicao.getColuna());
			if(getTabuleiro().existePosicao(p) && !getTabuleiro().existePeca(p) && getTabuleiro().existePosicao(p2) && !getTabuleiro().existePeca(p2) && getContagemMovimentos() == 0) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			
			p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
			if(getTabuleiro().existePosicao(p) && existeAlgumaPecaAdversaria(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			
			p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
			if(getTabuleiro().existePosicao(p) && existeAlgumaPecaAdversaria(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}	
			
		}
		return matriz;
	}
	
	@Override
	public String toString() {
		return "P";
	}

}
