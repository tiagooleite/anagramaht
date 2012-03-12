package ufcg.les.anagrama.activity;

import ufcg.les.anagrama.R;
import ufcg.les.anagrama.enummeration.Nivel;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AnagramaHTActivity extends Activity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Intent nivelIntent = getIntent();
        Nivel nivel = (Nivel) nivelIntent.getSerializableExtra("nivel");
        
        botaoJogarAction(nivel);
        botaoOpcoesAction();
        botaoAjudaAction();
        botaoSairAction();
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