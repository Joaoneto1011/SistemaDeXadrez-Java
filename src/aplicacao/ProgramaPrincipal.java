package aplicacao;

import xadrez.PartidaDeXadrez;

public class ProgramaPrincipal {

	public static void main(String[] args) {
		
		PartidaDeXadrez partidaDeXadrez = new PartidaDeXadrez();
		
		InterfaceDoUsuario.printTabuleiro(partidaDeXadrez.getPecas());
		
		

	}

}
