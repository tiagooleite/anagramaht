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
		List<List<String>> listaDificil = new ArrayList<List<String>>();
		
		ArrayList<String> anagramasAmor = carregaAnagramaAmor();
		ArrayList<String> anagramasRato = carregaAnagramaRato();
		ArrayList<String> anagramasFio = carregaAnagramaFio();
		
		ArrayList<String> anagramasPrato = carregaAnagramaPrato();
		ArrayList<String> anagramasMorto = carregaAnagramaMorto();
		ArrayList<String> anagramasOlhar = carregaAnagramaOlhar();
		ArrayList<String> anagramasBolero = carregaAnagramaBolero();
		ArrayList<String> anagramasRancor = carregaAnagramaRancor();
		ArrayList<String> anagramasSacar = carregaAnagramaSacar();
		
		ArrayList<String> anagramasGerador = carregaAnagramaGerador();
		ArrayList<String> anagramasRasteira = carregaAnagramaRasteira();
		ArrayList<String> anagramasEstragar = carregaAnagramaEstragar();
		
		listaFacil.add(anagramasAmor);
		listaFacil.add(anagramasRato);
		listaFacil.add(anagramasFio);
		
		listaNormal.add(anagramasPrato);
		listaNormal.add(anagramasMorto);
		listaNormal.add(anagramasOlhar);
		listaNormal.add(anagramasBolero);
		listaNormal.add(anagramasRancor);
		listaNormal.add(anagramasSacar);
		
		listaDificil.add(anagramasGerador);
		listaDificil.add(anagramasRasteira);
		listaDificil.add(anagramasEstragar);
		
		palavrasPorNivel.put(Nivel.FACIL, listaFacil); // 3 e 4 letras
		palavrasPorNivel.put(Nivel.NORMAL, listaNormal); // 5 e 6 letras
		palavrasPorNivel.put(Nivel.DIFICIL, listaDificil); // >= 7 letras
	}
	
	private ArrayList<String> carregaAnagramaGerador() {
		ArrayList<String> anagramasGerador = new ArrayList<String>();
		anagramasGerador.add("gerador");
		anagramasGerador.add("regador");
		anagramasGerador.add("regrado");
		return anagramasGerador;
	}
	
	private ArrayList<String> carregaAnagramaRasteira() {
		ArrayList<String> anagramasRasteira = new ArrayList<String>();
		anagramasRasteira.add("rasteira");
		anagramasRasteira.add("traseira");
		return anagramasRasteira;
	}
	
	private ArrayList<String> carregaAnagramaEstragar() {
		ArrayList<String> anagramasRasteira = new ArrayList<String>();
		anagramasRasteira.add("estragar");
		anagramasRasteira.add("resgatar");
		return anagramasRasteira;
	}
	
	private ArrayList<String> carregaAnagramaMorto() {
		ArrayList<String> anagramasMorto = new ArrayList<String>();
		anagramasMorto.add("morto");
		anagramasMorto.add("motor");
		return anagramasMorto;
	}
	
	private ArrayList<String> carregaAnagramaSacar() {
		ArrayList<String> anagramasMorto = new ArrayList<String>();
		anagramasMorto.add("sacar");
		anagramasMorto.add("casar");
		anagramasMorto.add("sacra");
		return anagramasMorto;
	}
	
	private ArrayList<String> carregaAnagramaBolero() {
		ArrayList<String> anagramasBolero = new ArrayList<String>();
		anagramasBolero.add("rebolo");
		anagramasBolero.add("bolero");
		return anagramasBolero;
	}
	
	private ArrayList<String> carregaAnagramaRancor() {
		ArrayList<String> anagramasBolero = new ArrayList<String>();
		anagramasBolero.add("rancor");
		anagramasBolero.add("roncar");
		return anagramasBolero;
	}
	
	private ArrayList<String> carregaAnagramaOlhar() {
		ArrayList<String> anagramasOlhar = new ArrayList<String>();
		anagramasOlhar.add("olhar");
		anagramasOlhar.add("ralho");
		anagramasOlhar.add("rolha");
		return anagramasOlhar;
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
	
	private ArrayList<String> carregaAnagramaRato() {
		ArrayList<String> anagramasRato = new ArrayList<String>();
		anagramasRato.add("rato");
		anagramasRato.add("rota");
		anagramasRato.add("toar");
		anagramasRato.add("tora");
		return anagramasRato;
	}
	
	private ArrayList<String> carregaAnagramaFio() {
		ArrayList<String> anagramasFio = new ArrayList<String>();
		anagramasFio.add("fio");
		anagramasFio.add("foi");
		return anagramasFio;
	}
}
