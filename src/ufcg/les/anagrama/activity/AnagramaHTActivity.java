package ufcg.les.anagrama.activity;

import ufcg.les.anagrama.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AnagramaHTActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        botaoJogarAction();
        
        
        botaoAjudaAction();
        botaoSairAction();
    }


	private void botaoJogarAction() {
		Button botaoJogar = (Button) findViewById(R.id.jogar);
        botaoJogar.setOnClickListener(botaoJogarListener());
	}


	private OnClickListener botaoJogarListener() {
		return new OnClickListener() {

			public void onClick(View v) {
				Intent settingsButton = new Intent(AnagramaHTActivity.this,
						SubMenuJogarActivity.class);
				startActivity(settingsButton);

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


	private OnClickListener botaoAjudaListener() {
		return new OnClickListener() {
			
			public void onClick(View v) {
				  Intent settingsButton = new Intent(AnagramaHTActivity.this, TesteActivity.class);
                  startActivity(settingsButton);
				
			}
		};
	}
	
	public void onDestroy() {
		super.onDestroy();
		System.exit(0);
	}


	private OnClickListener botaoSairListener() {
		return new OnClickListener() {
			
			public void onClick(View v) {
				onDestroy();
			}
		};
	}
}