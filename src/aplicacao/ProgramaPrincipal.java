package aplicacao;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import xadrez.ExcecoesXadrez;
import xadrez.PartidaDeXadrez;
import xadrez.PecaDeXadrez;
import xadrez.PosicaoXadrez;

public class ProgramaPrincipal {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		PartidaDeXadrez partidaDeXadrez = new PartidaDeXadrez();
		List<PecaDeXadrez> capturadas = new ArrayList<>();

		while (!partidaDeXadrez.getCheckMate()) {
			try {
				InterfaceDoUsuario.limparTela();
				InterfaceDoUsuario.printPartida(partidaDeXadrez, capturadas);
				System.out.println();
				System.out.print("Posicao de origem: ");
				PosicaoXadrez origem = InterfaceDoUsuario.leituraPosicaoXadrez(sc);

				boolean[][] movimentosPossiveis = partidaDeXadrez.movimentosPossiveis(origem);
				InterfaceDoUsuario.limparTela();
				InterfaceDoUsuario.printTabuleiro(partidaDeXadrez.getPecas(), movimentosPossiveis);

				System.out.println();
				System.out.print("Posicao de destino: ");
				PosicaoXadrez destino = InterfaceDoUsuario.leituraPosicaoXadrez(sc);

				PecaDeXadrez pecaCapturada = partidaDeXadrez.execucaoMovimentoXadrez(origem, destino);

				if (pecaCapturada != null) {
					capturadas.add(pecaCapturada);
				}

				if (partidaDeXadrez.getPromocao() != null) {

					System.out.print("Insira a peca para promocao (B/C/T/R): ");
					String tipo = sc.nextLine().toUpperCase();
					while (!tipo.equals("B") && !tipo.equals("C") && !tipo.equals("T") && !tipo.equals("R")) {

						System.out.print("Valor invalido! digite novamente a peca para promocao (B/C/T/R): ");
						tipo = sc.nextLine().toUpperCase();
					}
					partidaDeXadrez.substituirPecaPromovida(tipo);
				}
			} catch (ExcecoesXadrez e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}

		InterfaceDoUsuario.limparTela();
		InterfaceDoUsuario.printPartida(partidaDeXadrez, capturadas);

	}

}
