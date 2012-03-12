package ufcg.les.anagrama.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.util.Log;

import ufcg.les.anagrama.enummeration.Nivel;
import ufcg.les.anagrama.exceptions.AnagramaNaoExistenteException;
import ufcg.les.anagrama.exceptions.PalavraJaEncontradaException;
import ufcg.les.anagrama.persistence.dao.PalavrasDAO;
import ufcg.les.anagrama.util.ContabilizaPontos;
import ufcg.les.anagrama.util.GeradorStrings;

public class Jogo {
	
	private static final String LOGS = "logs";
	
	private String nomeJogador;
	private int pontuacao;
	private Nivel nivel = Nivel.NORMAL;
	
	private PalavrasDAO palavrasDAO;
	private String palavraEmbaralhada;
	private List<String> anagramas;
	private List<String> anagramasEncontrados;
	
	public Jogo(String nomeJogador, Nivel nivel) {
		setNivel(nivel);
		setNomeJogador(nomeJogador);
		this.palavrasDAO = new PalavrasDAO();
		this.anagramas = new ArrayList<String>();
	}

	public String getNomeJogador() {
		return nomeJogador;
	}

	public void setNomeJogador(String nomeJogador) {
		this.nomeJogador = nomeJogador;
	}

	public int getPontuacao() {
		return pontuacao;
	}

	public void setPontuacao(int pontuacao) {
		this.pontuacao = pontuacao;
	}

	public Nivel getNivel() {
		return nivel;
	}

	public void setNivel(Nivel nivel) {
		if (nivel != null) {
			this.nivel = nivel;
		}
	}
	
	public int getPontuacaoTotal() {
		return ContabilizaPontos.contabilizaPontuacaoTotal(this.anagramasEncontrados);
	}
	
	public boolean jogoTerminou() {
		return this.anagramasEncontrados.size() >= this.anagramas.size();
	}
	
	public boolean checarPalavra(String palavra) throws RuntimeException {
		String palavraAChecar = palavra.toLowerCase();
		
		if(anagramasEncontrados.contains(palavraAChecar)) {
			throw new PalavraJaEncontradaException(palavra);
			
		} else if(!anagramas.contains(palavraAChecar)) {
			throw new AnagramaNaoExistenteException(palavra);
			
		} else {
			return anagramasEncontrados.add(palavraAChecar);
		}
	}
	
	public boolean checarPalavra(char[] palavra) {
		return checarPalavra(String.copyValueOf(palavra));
	}
	
	public String getPalavraEmbaralhada() {
		return this.palavraEmbaralhada;
	}
	
	public List<String> getAnagramas() {
		return this.anagramas;
	}
	
	public int carregarNovoAnagrama(){
		this.anagramas = getListaAnagramasAleatoria(
				palavrasDAO.getPalavrasPorNivel(getNivel()));
		Log.v(LOGS, "Tamanho da lista Anagramas" + String.valueOf(anagramas.size()));
		
		this.palavraEmbaralhada = getPalavraEmbaralhada(anagramas);
		Log.v(LOGS, "Palavra embaralhada" + palavraEmbaralhada);
		
		this.anagramasEncontrados = new ArrayList<String>();
		return anagramas.get(0).length();
	}
	
	private String getPalavraEmbaralhada(List<String> anagramas) {
		return GeradorStrings.embaralhaPalavra(anagramas.get(0));
	}
	
	private List<String> getListaAnagramasAleatoria(List<List<String>> palavrasPorNivel) {
		int tamanho = palavrasPorNivel.size();
		Log.v(LOGS, "Tamanho => " + String.valueOf(tamanho));
		
		Random gerador = new Random();
		int tamanhoAleatorio = gerador.nextInt(tamanho);
		Log.v(LOGS, "Tamanho Aleatorio => " + String.valueOf(tamanhoAleatorio));
		
		return palavrasPorNivel.get(tamanhoAleatorio);		
	}
}
