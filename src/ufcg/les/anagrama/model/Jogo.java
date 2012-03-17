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
	
	private static final int PONTO = 50;
	private static final int PALAVRA_ENCONTRADA = 40;
	private static final int PALAVRA_INEXISTENTE = 20;
	
	private String nomeJogador;
	private int pontuacao;
	private Nivel nivel = Nivel.NORMAL;
	private Long tempo;
	
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
	
	public int totalPalavrasRestantes() {
		return this.anagramas.size() - this.anagramasEncontrados.size();
	}
	
	public boolean checarPalavra(String palavra) throws RuntimeException {
		String palavraAChecar = palavra.toLowerCase();
		
		if(anagramasEncontrados.contains(palavraAChecar)) {
			decrementaPontuacao(PALAVRA_ENCONTRADA);
			throw new PalavraJaEncontradaException(palavra);
			
		} else if(!anagramas.contains(palavraAChecar)) {
			decrementaPontuacao(PALAVRA_INEXISTENTE);
			throw new AnagramaNaoExistenteException(palavra);
			
		} else {
			incrementaPontuacao(PONTO);
			return anagramasEncontrados.add(palavraAChecar);
		}
	}
	
	public void incrementaPontuacao(int valor) {
//		if (nivel.equals(Nivel.NORMAL)) {
//			valor += 15;
//		} else if (nivel.equals(Nivel.DIFICIL)) {
//			valor += 30;
//		}
		pontuacao += valor;
	}
	
	public void decrementaPontuacao(int valor) {
		if (pontuacao > 40) {
//			if (nivel.equals(Nivel.NORMAL)) {
//				valor += 15;
//			} else if (nivel.equals(Nivel.DIFICIL)) {
//				valor += 30;
//			}
			pontuacao -= valor;
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
	
//	public int carregaAnagramaTeste() {
//	}
	
	public int carregarNovoAnagrama(){
		this.anagramas = getListaAnagramasAleatoria(
				palavrasDAO.getPalavrasPorNivel(getNivel()));
		
		this.palavraEmbaralhada = getPalavraEmbaralhada(anagramas);
		
		this.anagramasEncontrados = new ArrayList<String>();
		return anagramas.get(0).length();
	}
	
	private String getPalavraEmbaralhada(List<String> anagramas) {
		String palavraEmbaralhada = GeradorStrings.embaralhaPalavra(anagramas.get(0));
		while(getAnagramas().contains(palavraEmbaralhada)) {
			palavraEmbaralhada = GeradorStrings.embaralhaPalavra(anagramas.get(0));
		}
		
		return palavraEmbaralhada;
	}
	
	private List<String> getListaAnagramasAleatoria(List<List<String>> palavrasPorNivel) {
		int tamanho = palavrasPorNivel.size();
		Log.v(LOGS, "Tamanho => " + String.valueOf(tamanho));
		
		Random gerador = new Random();
		int tamanhoAleatorio = gerador.nextInt(tamanho);
		Log.v(LOGS, "Tamanho Aleatorio => " + String.valueOf(tamanhoAleatorio));
		
		return palavrasPorNivel.get(tamanhoAleatorio);		
	}

	public Long getTempo() {
		return tempo;
	}

	public void setTempo(Long tempo) {
		this.tempo = tempo;
	}
}
