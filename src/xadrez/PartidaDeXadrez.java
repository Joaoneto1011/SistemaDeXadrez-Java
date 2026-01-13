package xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jogodetabuleiro.Peca;
import jogodetabuleiro.Posicao;
import jogodetabuleiro.Tabuleiro;
import xadrez.pecas.Bispo;
import xadrez.pecas.Cavalo;
import xadrez.pecas.Peao;
import xadrez.pecas.Rainha;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaDeXadrez {

	private int turno;
	private Cor jogadorAtual;
	private Tabuleiro tabuleiro;
	private boolean check;
	private boolean checkMate;
	private PecaDeXadrez pecaVulneravelEnPassant;
	private PecaDeXadrez promocao;

	private List<Peca> pecasNoTabuleiro = new ArrayList<>();
	private List<Peca> pecasCapturadas = new ArrayList<>();

	public PartidaDeXadrez() {

		tabuleiro = new Tabuleiro(8, 8);
		turno = 1;
		jogadorAtual = Cor.BRANCO;
		configuracaoInicial();
	}

	public int getTurno() {

		return turno;
	}

	public Cor getJogadorAtual() {

		return jogadorAtual;
	}

	public boolean getCheck() {

		return check;
	}

	public boolean getCheckMate() {

		return checkMate;
	}

	public PecaDeXadrez getPecaVulneravelEnPassant() {

		return pecaVulneravelEnPassant;
	}

	public PecaDeXadrez getPromocao() {

		return promocao;
	}

	public PecaDeXadrez[][] getPecas() {

		PecaDeXadrez[][] matriz = new PecaDeXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];

		for (int i = 0; i < tabuleiro.getLinhas(); i++) {
			for (int j = 0; j < tabuleiro.getColunas(); j++) {
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

		if (testandoCheck(jogadorAtual)) {
			desfazerMovimento(origem, destino, pecaCapturada);
			throw new ExcecoesXadrez("Voce nao pode se colocar em Check");
		}

		PecaDeXadrez pecaMovida = (PecaDeXadrez) tabuleiro.peca(destino);

		// movimento especial promocao
		promocao = null;
		if (pecaMovida instanceof Peao) {

			if (pecaMovida.getCor() == Cor.BRANCO && destino.getLinha() == 0
					|| pecaMovida.getCor() == Cor.PRETO && destino.getLinha() == 7) {

				promocao = (PecaDeXadrez) tabuleiro.peca(destino);
				promocao = substituirPecaPromovida("R");
			}
		}

		check = (testandoCheck(oponente(jogadorAtual))) ? true : false;

		if (testandoCheckMate(oponente(jogadorAtual))) {
			checkMate = true;
		} else {
			proximaVez();
		}

		// movimento especial en passant
		if (pecaMovida instanceof Peao
				&& (destino.getLinha() == origem.getLinha() - 2 || destino.getLinha() == origem.getLinha() + 2)) {

			pecaVulneravelEnPassant = pecaMovida;
		} else {

			pecaVulneravelEnPassant = null;
		}

		return (PecaDeXadrez) pecaCapturada;
	}

	public PecaDeXadrez substituirPecaPromovida(String tipo) {

		if (promocao == null) {
			throw new IllegalStateException("Nao a peca para ser promovida");
		}

		if (!tipo.equals("B") && !tipo.equals("C") && !tipo.equals("T") && !tipo.equals("R")) {

			return promocao;
		}

		Posicao pos = promocao.getPosicaoXadrez().paraPosicao();
		Peca p = tabuleiro.removePeca(pos);
		pecasNoTabuleiro.remove(p);

		PecaDeXadrez novaPeca = novaPeca(tipo, promocao.getCor());
		tabuleiro.lugarDaPeca(novaPeca, pos);
		pecasNoTabuleiro.add(novaPeca);

		return novaPeca;
	}

	private PecaDeXadrez novaPeca(String tipo, Cor cor) {

		if (tipo.equals("B"))
			return new Bispo(tabuleiro, cor);
		if (tipo.equals("C"))
			return new Cavalo(tabuleiro, cor);
		if (tipo.equals("R"))
			return new Rainha(tabuleiro, cor);
		return new Torre(tabuleiro, cor);
	}

	private Peca facaMovimento(Posicao origem, Posicao destino) {

		PecaDeXadrez p = (PecaDeXadrez) tabuleiro.removePeca(origem);
		p.aumentarContagemMovimentos();
		Peca pecaCapturada = tabuleiro.removePeca(destino);
		tabuleiro.lugarDaPeca(p, destino);

		if (pecaCapturada != null) {
			pecasNoTabuleiro.remove(pecaCapturada);
			pecasCapturadas.add(pecaCapturada);
		}

		// movimento especial Roque (pequeno)

		if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
			Posicao posicaoOrigemTorre = new Posicao(origem.getLinha(), origem.getColuna() + 3);
			Posicao posicaoDestinoTorre = new Posicao(origem.getLinha(), origem.getColuna() + 1);
			PecaDeXadrez torre = (PecaDeXadrez) tabuleiro.removePeca(posicaoOrigemTorre);
			tabuleiro.lugarDaPeca(torre, posicaoDestinoTorre);
			torre.aumentarContagemMovimentos();
		}

		// movimento especial Roque (grande)

		if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
			Posicao posicaoOrigemTorre = new Posicao(origem.getLinha(), origem.getColuna() - 4);
			Posicao posicaoDestinoTorre = new Posicao(origem.getLinha(), origem.getColuna() - 1);
			PecaDeXadrez torre = (PecaDeXadrez) tabuleiro.removePeca(posicaoOrigemTorre);
			tabuleiro.lugarDaPeca(torre, posicaoDestinoTorre);
			torre.aumentarContagemMovimentos();
		}

		// movimento especial en passant
		if (p instanceof Peao) {

			if (origem.getColuna() != destino.getColuna() && pecaCapturada == null) {

				Posicao posicaoPeao;

				if (p.getCor() == Cor.BRANCO) {

					posicaoPeao = new Posicao(destino.getLinha() + 1, destino.getColuna());
				} else {
					posicaoPeao = new Posicao(destino.getLinha() - 1, destino.getColuna());
				}
				pecaCapturada = tabuleiro.removePeca(posicaoPeao);
				pecasCapturadas.add(pecaCapturada);
				pecasNoTabuleiro.remove(pecaCapturada);
			}
		}

		return pecaCapturada;
	}

	public void desfazerMovimento(Posicao origem, Posicao destino, Peca pecaCapturada) {

		PecaDeXadrez p = (PecaDeXadrez) tabuleiro.removePeca(destino);
		p.diminuirContagemMovimentos();
		tabuleiro.lugarDaPeca(p, origem);

		if (pecaCapturada != null) {
			tabuleiro.lugarDaPeca(pecaCapturada, destino);
			pecasCapturadas.remove(pecaCapturada);
			pecasNoTabuleiro.add(pecaCapturada);
		}

		// movimento especial Roque (pequeno)

		if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
			Posicao posicaoOrigemTorre = new Posicao(origem.getLinha(), origem.getColuna() + 3);
			Posicao posicaoDestinoTorre = new Posicao(origem.getLinha(), origem.getColuna() + 1);
			PecaDeXadrez torre = (PecaDeXadrez) tabuleiro.removePeca(posicaoDestinoTorre);
			tabuleiro.lugarDaPeca(torre, posicaoOrigemTorre);
			torre.diminuirContagemMovimentos();
			;
		}

		// movimento especial Roque (grande)

		if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
			Posicao posicaoOrigemTorre = new Posicao(origem.getLinha(), origem.getColuna() - 4);
			Posicao posicaoDestinoTorre = new Posicao(origem.getLinha(), origem.getColuna() - 1);
			PecaDeXadrez torre = (PecaDeXadrez) tabuleiro.removePeca(posicaoDestinoTorre);
			tabuleiro.lugarDaPeca(torre, posicaoOrigemTorre);
			torre.diminuirContagemMovimentos();
		}

		// movimento especial en passant
		if (p instanceof Peao) {

			if (origem.getColuna() != destino.getColuna() && pecaCapturada == pecaVulneravelEnPassant) {

				PecaDeXadrez peao = (PecaDeXadrez) tabuleiro.removePeca(destino);
				Posicao posicaoPeao;

				if (p.getCor() == Cor.BRANCO) {

					posicaoPeao = new Posicao(3, destino.getColuna());
				} else {
					posicaoPeao = new Posicao(4, destino.getColuna());
				}

				tabuleiro.lugarDaPeca(peao, posicaoPeao);
			}
		}
	}

	private void validandoPosicaoOrigem(Posicao posicao) {

		if (!tabuleiro.existePeca(posicao)) {
			throw new ExcecoesXadrez("Nao existe peca na posicao de origem");
		}

		if (jogadorAtual != ((PecaDeXadrez) tabuleiro.peca(posicao)).getCor()) {
			throw new ExcecoesXadrez("A peca escolhida nao e sua");
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

	private void proximaVez() {

		turno++;
		jogadorAtual = (jogadorAtual == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
	}

	private Cor oponente(Cor cor) {

		return (cor == Cor.BRANCO) ? cor.PRETO : Cor.BRANCO;
	}

	private PecaDeXadrez rei(Cor cor) {

		List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((PecaDeXadrez) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Peca p : list) {

			if (p instanceof Rei) {
				return (PecaDeXadrez) p;
			}
		}

		throw new IllegalStateException("Nao existe o Rei da cor " + cor + "no tabuleiro");
	}

	private boolean testandoCheck(Cor cor) {

		Posicao posicaoRei = rei(cor).getPosicaoXadrez().paraPosicao();
		List<Peca> pecasOponente = pecasNoTabuleiro.stream().filter(x -> ((PecaDeXadrez) x).getCor() == oponente(cor))
				.collect(Collectors.toList());
		for (Peca p : pecasOponente) {
			boolean[][] matriz = p.movimentosPossiveis();
			if (matriz[posicaoRei.getLinha()][posicaoRei.getColuna()]) {
				return true;
			}
		}

		return false;
	}

	public boolean testandoCheckMate(Cor cor) {

		if (!testandoCheck(cor)) {
			return false;
		}

		List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((PecaDeXadrez) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Peca p : list) {

			boolean[][] matriz = p.movimentosPossiveis();
			for (int i = 0; i < tabuleiro.getLinhas(); i++) {
				for (int j = 0; j < tabuleiro.getColunas(); j++) {
					if (matriz[i][j]) {
						Posicao origem = ((PecaDeXadrez) p).getPosicaoXadrez().paraPosicao();
						Posicao destino = new Posicao(i, j);
						Peca pecaCapturada = facaMovimento(origem, destino);
						boolean testandoCheck = testandoCheck(cor);
						desfazerMovimento(origem, destino, pecaCapturada);
						if (!testandoCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	private void colocarNovaPeca(char coluna, int linha, PecaDeXadrez peca) {

		tabuleiro.lugarDaPeca(peca, new PosicaoXadrez(coluna, linha).paraPosicao());
		pecasNoTabuleiro.add(peca);
	}

	private void configuracaoInicial() {

		colocarNovaPeca('a', 1, new Torre(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('b', 1, new Cavalo(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('c', 1, new Bispo(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('d', 1, new Rainha(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('e', 1, new Rei(tabuleiro, Cor.BRANCO, this));
		colocarNovaPeca('f', 1, new Bispo(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('g', 1, new Cavalo(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('h', 1, new Torre(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('a', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		colocarNovaPeca('b', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		colocarNovaPeca('c', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		colocarNovaPeca('d', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		colocarNovaPeca('e', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		colocarNovaPeca('f', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		colocarNovaPeca('g', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		colocarNovaPeca('h', 2, new Peao(tabuleiro, Cor.BRANCO, this));

		colocarNovaPeca('a', 8, new Torre(tabuleiro, Cor.PRETO));
		colocarNovaPeca('b', 8, new Cavalo(tabuleiro, Cor.PRETO));
		colocarNovaPeca('c', 8, new Bispo(tabuleiro, Cor.PRETO));
		colocarNovaPeca('d', 8, new Rainha(tabuleiro, Cor.PRETO));
		colocarNovaPeca('e', 8, new Rei(tabuleiro, Cor.PRETO, this));
		colocarNovaPeca('f', 8, new Bispo(tabuleiro, Cor.PRETO));
		colocarNovaPeca('g', 8, new Cavalo(tabuleiro, Cor.PRETO));
		colocarNovaPeca('h', 8, new Torre(tabuleiro, Cor.PRETO));
		colocarNovaPeca('a', 7, new Peao(tabuleiro, Cor.PRETO, this));
		colocarNovaPeca('b', 7, new Peao(tabuleiro, Cor.PRETO, this));
		colocarNovaPeca('c', 7, new Peao(tabuleiro, Cor.PRETO, this));
		colocarNovaPeca('d', 7, new Peao(tabuleiro, Cor.PRETO, this));
		colocarNovaPeca('e', 7, new Peao(tabuleiro, Cor.PRETO, this));
		colocarNovaPeca('f', 7, new Peao(tabuleiro, Cor.PRETO, this));
		colocarNovaPeca('g', 7, new Peao(tabuleiro, Cor.PRETO, this));
		colocarNovaPeca('h', 7, new Peao(tabuleiro, Cor.PRETO, this));
	}

}
