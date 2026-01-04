package aplicacao;

import java.util.InputMismatchException;
import java.util.Scanner;

import xadrez.ExcecoesXadrez;
import xadrez.PartidaDeXadrez;
import xadrez.PecaDeXadrez;
import xadrez.PosicaoXadrez;

public class ProgramaPrincipal {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		PartidaDeXadrez partidaDeXadrez = new PartidaDeXadrez();
		
		while (true) {
			try {
				InterfaceDoUsuario.limparTela();
				InterfaceDoUsuario.printPartida(partidaDeXadrez);
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
			}
			catch (ExcecoesXadrez e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
			catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
		

	}

}
