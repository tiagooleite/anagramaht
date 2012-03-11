package ufcg.les.anagrama.activity;

import java.util.ArrayList;
import java.util.List;

import ufcg.les.anagrama.R;
import ufcg.les.anagrama.enummeration.Nivel;
import ufcg.les.anagrama.model.Jogo;
import ufcg.les.anagrama.persistence.dao.PalavrasDAO;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class JogoActivity extends Activity {
	
	private static final String LOGS = "logs";
	
	private Jogo jogoAtual; 
	
	private List<List<String>> palavras = new ArrayList<List<String>>();

	private PalavrasDAO palavrasDao = new PalavrasDAO();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.page_jogo);
		
		Intent jogoIntent = getIntent();
		String nomeJogador = jogoIntent.getStringExtra("nomeJogador");
		Nivel nivel = null;
		
		try {
			nivel = (Nivel) jogoIntent.getSerializableExtra("nivel");
		} catch (Exception e) {
			 Log.v(LOGS, "Nivel não escolhido pelo usuário." +
			 		"Setado automaticamente para NORMAL.");
		}
		
		Jogo jogo = new Jogo(nomeJogador, nivel);
		setJogoAtual(jogo);
		
		carregarPalavras(jogoAtual.getNivel());
	}

	private void carregarPalavras(Nivel nivel) {
		palavras = palavrasDao.getPalavrasPorNivel(nivel);
		
	}

	public Jogo getJogoAtual() {
		return jogoAtual;
	}

	public void setJogoAtual(Jogo jogoAtual) {
		this.jogoAtual = jogoAtual;
	}

	public List<List<String>> getPalavras() {
		return palavras;
	}

	public void setPalavras(List<List<String>> palavras) {
		this.palavras = palavras;
	}

}
