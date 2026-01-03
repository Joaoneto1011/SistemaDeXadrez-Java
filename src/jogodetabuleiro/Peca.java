package jogodetabuleiro;

public abstract class Peca {

	protected Posicao posicao;
	private Tabuleiro tabuleiro;

	public Peca(Tabuleiro tabuleiro) {

		this.tabuleiro = tabuleiro;
	}

	protected Tabuleiro getTabuleiro() {

		return tabuleiro;
	}

	public abstract boolean[][] movimentosPossiveis();

	public boolean podeMover(Posicao posicao) {

		return movimentosPossiveis()[posicao.getLinha()][posicao.getColuna()];
	}

	public boolean existeAlgumaPossibilidadeDeMovimento() {

		boolean[][] matriz = movimentosPossiveis();
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz.length; j++) {
				if (matriz[i][j]) {
					return true;
				}
			}
		}
		return false;
	}

}
