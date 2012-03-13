package ufcg.les.anagrama.activity;

import java.io.Serializable;

import ufcg.les.anagrama.R;
import ufcg.les.anagrama.enummeration.Nivel;
import ufcg.les.anagrama.persistence.dao.RankingDAO;
import ufcg.les.anagrama.persistence.dao.Usuario;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AnagramaHTActivity extends Activity implements Serializable {
	
	private static final long serialVersionUID = -5308668553694718836L;
	
	private RankingDAO rankingDAO = new RankingDAO(this);
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Intent nivelIntent = getIntent();
        Nivel nivel = (Nivel) nivelIntent.getSerializableExtra("nivel");
        
        Intent usuarioIntent = getIntent();
        Usuario usuario = (Usuario) usuarioIntent.getSerializableExtra("usuario");
        
        botaoJogarAction(nivel);
        botaoRankingAction(usuario, rankingDAO);
        botaoOpcoesAction();
        botaoAjudaAction();
        botaoSairAction();
    }
    


	private void botaoRankingAction(Usuario usuario, RankingDAO rankingDAO) {
		Button botaoRanking = (Button) findViewById(R.id.ranking);
        botaoRanking.setOnClickListener(botaoRankingListener(usuario, rankingDAO));
		
	}


	private OnClickListener botaoRankingListener(final Usuario usuario,
			final RankingDAO rankingDAO) {
		return new OnClickListener() {

			public void onClick(View v) {
				Intent settingsButton = new Intent(AnagramaHTActivity.this,
						RankingActivity.class);
				settingsButton.putExtra("usuario", usuario);
				settingsButton.putExtra("rankingDao", rankingDAO);
				startActivity(settingsButton);
				finish();
			}
		};
	}


	private void botaoOpcoesAction() {
		Button botaoOpcoes = (Button) findViewById(R.id.opcoes);
        botaoOpcoes.setOnClickListener(botaoOpcoesListener());
		
	}


	private OnClickListener botaoOpcoesListener() {
		return new OnClickListener() {

			public void onClick(View v) {
				Intent settingsButton = new Intent(AnagramaHTActivity.this,
						OpcoesActivity.class);
				startActivity(settingsButton);
				finish();
			}
		};
	}


	private void botaoJogarAction(Nivel nivel) {
		Button botaoJogar = (Button) findViewById(R.id.jogar);
        botaoJogar.setOnClickListener(botaoJogarListener(nivel));
	}


	private OnClickListener botaoJogarListener(final Nivel nivel) {
		return new OnClickListener() {

			public void onClick(View v) {
				Intent settingsButton = new Intent(AnagramaHTActivity.this,
						SubMenuJogarActivity.class);
				settingsButton.putExtra("nivel", nivel);
				startActivity(settingsButton);
				//finish();
			}
		};
	}


	private void botaoAjudaAction() {
		Button botaoAjuda = (Button) findViewById(R.id.ajuda);
        botaoAjuda.setOnClickListener(botaoAjudaListener());
	}


	private void botaoSairAction() {
		Button botaoSair = (Button) findViewById(R.id.sair);
        botaoSair.setOnClickListener(botaoSairListener());
	}
	
	private OnClickListener botaoSairListener() {
		return new OnClickListener() { 
			
			public void onClick(View v) {
				finish();
			}
		};
	}


	private OnClickListener botaoAjudaListener() {
		return new OnClickListener() {
			
			public void onClick(View v) {
				  Intent settingsButton = new Intent(AnagramaHTActivity.this,
						  AjudaActivity.class);
                  startActivity(settingsButton);
			}
		};
	}
	
	
}