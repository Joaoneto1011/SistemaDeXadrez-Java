package aplicacao;

import java.util.Scanner;

import xadrez.PartidaDeXadrez;
import xadrez.PecaDeXadrez;
import xadrez.PosicaoXadrez;

public class ProgramaPrincipal {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		PartidaDeXadrez partidaDeXadrez = new PartidaDeXadrez();
		
		while (true) {
			InterfaceDoUsuario.printTabuleiro(partidaDeXadrez.getPecas());
			System.out.println();
			System.out.print("Posicao de origem: ");
			PosicaoXadrez origem = InterfaceDoUsuario.leituraPosicaoXadrez(sc);
			
			System.out.println();
			System.out.print("Posicao de destino: ");
			PosicaoXadrez destino = InterfaceDoUsuario.leituraPosicaoXadrez(sc);
			
			PecaDeXadrez pecaCapturada = partidaDeXadrez.execucaoMovimentoXadrez(origem, destino);
					
		}
		

	}

}
