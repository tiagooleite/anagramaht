package ufcg.les.anagrama.activity;

import ufcg.les.anagrama.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SubMenuJogarActivity extends Activity {
	
	private String nomeJogador = "";
	private EditText editText;
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.sub_page_jogar);
	        
	        editText = (EditText) findViewById(R.id.edittext);
	        
	        botaoOkAction();
	        botaoLimparAction();
	        botaoCancelarAction();
	        
	    }

	private void botaoOkAction() {
		Button botaoOk = (Button) findViewById(R.id.confirmar);
		botaoOk.setOnClickListener(botaoOkListener());
		
	}

	private OnClickListener botaoOkListener() {
		return new OnClickListener() {

			public void onClick(View v) {
				
				if (nomeInvalido()) {
					setNomeUsuario("Guest" + getRandon());
				} else {
					setNomeUsuario(editText.getText().toString());
				}
				
				Intent okIntent = new Intent(SubMenuJogarActivity.this,
						JogoActivity.class);
				okIntent.putExtra("nomeJogador", nomeJogador);
				
				startActivity(okIntent);
			}
		};
	}
	

	private void botaoLimparAction() {
		Button botaoLimpar = (Button) findViewById(R.id.limpar);
		botaoLimpar.setOnClickListener(botaoLimparListener());
		
	}

	private OnClickListener botaoLimparListener() {
		return new OnClickListener() {

			public void onClick(View v) {
				editText.setText("");
				setNomeUsuario("");
			}
		};
	}

	private void botaoCancelarAction() {
		Button botaoCancelar = (Button) findViewById(R.id.cancelar);
		botaoCancelar.setOnClickListener(botaoCancelarListener());
		
	}

	private OnClickListener botaoCancelarListener() {
		return new OnClickListener() {

			public void onClick(View v) {
				Intent settingsButton = new Intent(SubMenuJogarActivity.this,
						AnagramaHTActivity.class);
				startActivity(settingsButton);
			}
		};
	}
	
	private boolean nomeInvalido() {
		return editText.getText().toString().trim().equals("");
	}
	
	private String getRandon() {
		int aleatorio = (int) (1+Math.random()*100); 
		return String.valueOf(aleatorio);
	}


	public void setNomeUsuario(String nomeUsuario) {
		this.nomeJogador = nomeUsuario;
	}

	public String getNomeUsuario() {
		return nomeJogador;
	}
	
}
