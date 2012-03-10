package ufcg.les.anagrama.persistence.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ufcg.les.anagrama.enummeration.Nivel;

public class PalavrasDAO extends GenericDAOImpl<String> {
	
	private Map<Nivel, List<List<String>>> palavrasPorNivel =
			new HashMap<Nivel, List<List<String>>>();
	
	public PalavrasDAO() {
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
		
		ArrayList<String> anagramasAmor = carregaAnagramaAmor();
		
		listaFacil.add(anagramasAmor);
		
		palavrasPorNivel.put(Nivel.NORMAL, listaFacil);
		
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
