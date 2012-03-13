package ufcg.les.anagrama.activity;

import java.util.List;

import ufcg.les.anagrama.R;
import ufcg.les.anagrama.persistence.dao.RankingDAO;
import ufcg.les.anagrama.persistence.dao.Usuario;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class RankingActivity extends Activity {
	
	private List<Usuario> listaUsuarios;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.page_ranking);
		
		
		Intent usuarioIntent = getIntent();
        Usuario usuario = (Usuario) usuarioIntent.getSerializableExtra("usuario");

        RankingDAO rankingDao = (RankingDAO) usuarioIntent.getSerializableExtra("rankingDao");
         
        
        listaUsuarios = rankingDao.getRanking();
        
        if (usuario != null) {
        	rankingDao.addUsuario(usuario);
        }
		
		carregaRanking();
		
		Button botaoVoltar = (Button) findViewById(R.id.voltarRanking);
		botaoVoltar.setOnClickListener(botaoVoltarListener());
		
	}

	private void carregaRanking() {
		TextView primeiroTextView = (TextView) findViewById(R.id.primeiro);
		primeiroTextView.setText("1- " + listaUsuarios.get(4).toString());
		
		TextView segundoTextView = (TextView) findViewById(R.id.segundo);
		segundoTextView.setText("2- " + listaUsuarios.get(3).toString());
		
		TextView terceiroTextView = (TextView) findViewById(R.id.terceiro);
		terceiroTextView.setText("3- " + listaUsuarios.get(2).toString());
		
		TextView quartoTextView = (TextView) findViewById(R.id.quarto);
		quartoTextView.setText("4- " + listaUsuarios.get(1).toString());
		
		TextView quintoTextView = (TextView) findViewById(R.id.quinto);
		quintoTextView.setText("5- " + listaUsuarios.get(0).toString());
	}

	private OnClickListener botaoVoltarListener() {
		return new OnClickListener() {
			
			public void onClick(View v) {
				mudaContexto();
			}

			private void mudaContexto() {
				Intent okIntent = new Intent(RankingActivity.this,
						AnagramaHTActivity.class);
				startActivity(okIntent);
				
			}
		};
	}

}
