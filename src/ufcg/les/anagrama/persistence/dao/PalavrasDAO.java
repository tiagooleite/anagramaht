package ufcg.les.anagrama.persistence.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ufcg.les.anagrama.enummeration.Nivel;

public class PalavrasDAO extends GenericDAOImpl<String> {
	
	private Map<Nivel, List<List<String>>> palavrasPorNivel;
	
	public PalavrasDAO() {
		palavrasPorNivel = new HashMap<Nivel, List<List<String>>>();
		carregarPalavras();
	}

	public List<List<String>> getPalavrasPorNivel(Nivel nivel) {
		return palavrasPorNivel.get(nivel);
	}

	public Map<Nivel, List<List<String>>> getPalavrasPorNivel() {
		return palavrasPorNivel;
	}

	public void setPalavrasPorNivel(Map<Nivel, List<List<String>>> palavrasPorNivel) {
		this.palavrasPorNivel = palavrasPorNivel;
	}
	
	private void carregarPalavras() {
		List<List<String>> listaFacil = new ArrayList<List<String>>();
		List<List<String>> listaNormal = new ArrayList<List<String>>();
		
		ArrayList<String> anagramasAmor = carregaAnagramaAmor();
		ArrayList<String> anagramasPrato = carregaAnagramaPrato();
		
		listaFacil.add(anagramasAmor);
		listaNormal.add(anagramasPrato);
		
		palavrasPorNivel.put(Nivel.FACIL, listaFacil);
		palavrasPorNivel.put(Nivel.NORMAL, listaNormal);
		palavrasPorNivel.put(Nivel.DIFICIL, listaFacil);
	}

	private ArrayList<String> carregaAnagramaPrato() {
		ArrayList<String> anagramasPrato = new ArrayList<String>();
		anagramasPrato.add("trapo");
		anagramasPrato.add("tropa");
		anagramasPrato.add("parto");
		anagramasPrato.add("porta");
		anagramasPrato.add("rapto");
		anagramasPrato.add("topar");
		anagramasPrato.add("prato");
		anagramasPrato.add("optar");
		return anagramasPrato;
	}

	private ArrayList<String> carregaAnagramaAmor() {
		ArrayList<String> anagramasAmor = new ArrayList<String>();
		anagramasAmor.add("amor");
		anagramasAmor.add("roma");
		anagramasAmor.add("mora");
		anagramasAmor.add("ramo");
		return anagramasAmor;
	}
	

}
