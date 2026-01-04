package xadrez;

import jogodetabuleiro.Peca;
import jogodetabuleiro.Posicao;
import jogodetabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaDeXadrez {
	
	private Tabuleiro tabuleiro;
	
	public PartidaDeXadrez() {
		
		tabuleiro = new Tabuleiro(8, 8);
		configuracaoInicial();
	}
	
	public PecaDeXadrez[][] getPecas() {
		
		PecaDeXadrez[][] matriz = new PecaDeXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		
		for (int i=0; i<tabuleiro.getLinhas(); i++) {
			for(int j=0; j<tabuleiro.getColunas(); j++) {
				matriz[i][j] = (PecaDeXadrez) tabuleiro.peca(i, j);
			}
		}
		
		return matriz;
	}
	
	public boolean[][] movimentosPossiveis(PosicaoXadrez posicaoOrigem) {
		
		Posicao posicao = posicaoOrigem.paraPosicao();
		validandoPosicaoOrigem(posicao);
		return tabuleiro.peca(posicao).movimentosPossiveis();
	}
	
	public PecaDeXadrez execucaoMovimentoXadrez(PosicaoXadrez posicaoOrigem, PosicaoXadrez posicaoDestino) {
		
		Posicao origem = posicaoOrigem.paraPosicao();
		Posicao destino = posicaoDestino.paraPosicao();
		validandoPosicaoOrigem(origem);
		validandoPosicaoDestino(origem, destino);
		Peca pecaCapturada = facaMovimento(origem, destino);
		return (PecaDeXadrez) pecaCapturada;
	}
	
	private Peca facaMovimento(Posicao origem, Posicao destino) {
		
		Peca p = tabuleiro.removePeca(origem);
		Peca pecaCapturada = tabuleiro.removePeca(destino);
		tabuleiro.lugarDaPeca(p, destino);
		return pecaCapturada;
	}
	
	private void validandoPosicaoOrigem(Posicao posicao) {
		
		if (!tabuleiro.existePeca(posicao)) {
			throw new ExcecoesXadrez("Nao existe peca na posicao de origem");
		}
		
		if (!tabuleiro.peca(posicao).existeAlgumaPossibilidadeDeMovimento()) {
			throw new ExcecoesXadrez("Nao existe movimentos possiveis para a peca escolhida.");
		}
	}
	
	private void validandoPosicaoDestino(Posicao origem, Posicao destino) {
		
		if (!tabuleiro.peca(origem).podeMover(destino)) {
			throw new ExcecoesXadrez("A peca escolhida nao pode se mover para a posicao de destino");
		}
	}
	
	private void colocarNovaPeca(char coluna, int linha, PecaDeXadrez peca) {
		
		tabuleiro.lugarDaPeca(peca, new PosicaoXadrez(coluna, linha).paraPosicao());
	}
	
	private void configuracaoInicial() {
		
	    colocarNovaPeca('c', 1, new Torre(tabuleiro, Cor.BRANCO));
	    colocarNovaPeca('c', 2, new Torre(tabuleiro, Cor.BRANCO));
	    colocarNovaPeca('d', 2, new Torre(tabuleiro, Cor.BRANCO));
	    colocarNovaPeca('e', 2, new Torre(tabuleiro, Cor.BRANCO));
	    colocarNovaPeca('e', 1, new Torre(tabuleiro, Cor.BRANCO));
	    colocarNovaPeca('d', 1, new Rei(tabuleiro, Cor.BRANCO));

	    colocarNovaPeca('c', 7, new Torre(tabuleiro, Cor.PRETO));
	    colocarNovaPeca('c', 8, new Torre(tabuleiro, Cor.PRETO));
	    colocarNovaPeca('d', 7, new Torre(tabuleiro, Cor.PRETO));
	    colocarNovaPeca('e', 7, new Torre(tabuleiro, Cor.PRETO));
	    colocarNovaPeca('e', 8, new Torre(tabuleiro, Cor.PRETO));
	    colocarNovaPeca('d', 8, new Rei(tabuleiro, Cor.PRETO));
	}

}
