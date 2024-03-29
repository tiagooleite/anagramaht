package ufcg.les.anagrama.util;

import java.util.LinkedList;
import java.util.Random;

public class GeradorStrings {
	
	public static String embaralhaPalavra(String palavra) {
		int tamanhoPalavra = palavra.length();
		LinkedList<Integer> posicoesAleatorias = getPosicoesAleatorias(tamanhoPalavra);
		
		char[] palavraEmbaralhada = new char[tamanhoPalavra];
		
		for (int i = 0; i < palavraEmbaralhada.length; i++) {
			palavraEmbaralhada[i] = palavra.charAt(posicoesAleatorias.poll());
		}
		return String.copyValueOf(palavraEmbaralhada);
	}
	
	private static LinkedList<Integer> getPosicoesAleatorias(int lenght) {
		Random gerador = new Random();
		LinkedList<Integer> posicoesAleatorias = new LinkedList<Integer>();
		
		for (int i = 0; i < lenght; i++) {
			Integer pos = gerador.nextInt(lenght);
			while (posicoesAleatorias.contains(pos)) {
				pos = gerador.nextInt(lenght);
			}
			posicoesAleatorias.addLast(pos);
		}
		return posicoesAleatorias;
	}
}