package xadrez.pecas;

import jogodetabuleiro.Posicao;
import jogodetabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidaDeXadrez;
import xadrez.PecaDeXadrez;

public class Peao extends PecaDeXadrez {

	private PartidaDeXadrez partidaDeXadrez;

	public Peao(Tabuleiro tabuleiro, Cor cor, PartidaDeXadrez partidaDeXadrez) {
		super(tabuleiro, cor);
		this.partidaDeXadrez = partidaDeXadrez;
	}

	@Override
	public boolean[][] movimentosPossiveis() {

		boolean[][] matriz = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

		Posicao p = new Posicao(0, 0);

		if (getCor() == Cor.BRANCO) {

			p.setValores(posicao.getLinha() - 1, posicao.getColuna());
			if (getTabuleiro().existePosicao(p) && !getTabuleiro().existePeca(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}

			p.setValores(posicao.getLinha() - 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() - 1, posicao.getColuna());
			if (getTabuleiro().existePosicao(p) && !getTabuleiro().existePeca(p) && getTabuleiro().existePosicao(p2)
					&& !getTabuleiro().existePeca(p2) && getContagemMovimentos() == 0) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}

			p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
			if (getTabuleiro().existePosicao(p) && existeAlgumaPecaAdversaria(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}

			p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
			if (getTabuleiro().existePosicao(p) && existeAlgumaPecaAdversaria(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}

			// movimento especial en passant pecas brancas
			if (posicao.getLinha() == 3) {
				Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
				if (getTabuleiro().existePosicao(esquerda) && existeAlgumaPecaAdversaria(esquerda)
						&& getTabuleiro().peca(esquerda) == partidaDeXadrez.getPecaVulneravelEnPassant()) {

					matriz[esquerda.getLinha() - 1][esquerda.getColuna()] = true;
				}

				Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
				if (getTabuleiro().existePosicao(direita) && existeAlgumaPecaAdversaria(direita)
						&& getTabuleiro().peca(direita) == partidaDeXadrez.getPecaVulneravelEnPassant()) {

					matriz[direita.getLinha() - 1][direita.getColuna()] = true;
				}
			}
		} else {

			p.setValores(posicao.getLinha() + 1, posicao.getColuna());
			if (getTabuleiro().existePosicao(p) && !getTabuleiro().existePeca(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}

			p.setValores(posicao.getLinha() + 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() + 1, posicao.getColuna());
			if (getTabuleiro().existePosicao(p) && !getTabuleiro().existePeca(p) && getTabuleiro().existePosicao(p2)
					&& !getTabuleiro().existePeca(p2) && getContagemMovimentos() == 0) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}

			p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
			if (getTabuleiro().existePosicao(p) && existeAlgumaPecaAdversaria(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}

			p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
			if (getTabuleiro().existePosicao(p) && existeAlgumaPecaAdversaria(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}

			// movimento especial en passant pecas pretas
			if (posicao.getLinha() == 4) {
				Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
				if (getTabuleiro().existePosicao(esquerda) && existeAlgumaPecaAdversaria(esquerda)
						&& getTabuleiro().peca(esquerda) == partidaDeXadrez.getPecaVulneravelEnPassant()) {

					matriz[esquerda.getLinha() + 1][esquerda.getColuna()] = true;
				}

				Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
				if (getTabuleiro().existePosicao(direita) && existeAlgumaPecaAdversaria(direita)
						&& getTabuleiro().peca(direita) == partidaDeXadrez.getPecaVulneravelEnPassant()) {

					matriz[direita.getLinha() + 1][direita.getColuna()] = true;
				}
			}

		}
		return matriz;
	}

	@Override
	public String toString() {
		return "P";
	}

}
