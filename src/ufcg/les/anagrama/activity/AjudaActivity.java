package ufcg.les.anagrama.activity;

import ufcg.les.anagrama.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class AjudaActivity extends Activity {
	private final static String TEXTO_JOGAR = "Jogar: No menu jogar você iniciará o " +
			"jogo após inserir seu nome, será apresentada uma palavra embaralhada e o " +
			"jogador terá que entrar com os anagramas dessa palavra. Cada palavra encontrada " +
			"vale 50 pontos e será listada na tela, ao tentar enviar uma palavra já encontrada " +
			"40 pontos serão diminuidos do total.\n";
	
	private final static String TEXTO_RANKING = "Ranking: No menu ranking é possível ver " +
			"a listagem dos 5 jogadores com melhor pontuação.\n";
	
	private final static String TEXTO_OPCOES = "Opções: No menu opções é possível escolher " +
			"o nível dos anagramas, que pode ser fácil, médio ou difícil.\n";
	
	private final static String TEXTO_SAIR = "Sair: Para sair, escolha sair no menu principal.\n";
	
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.page_ajuda);
	        
	        TextView professorTextView = (TextView) findViewById(R.id.professor);
	        professorTextView.setText(TEXTO_JOGAR);
			
	        TextView desenvolvedoresTextView = (TextView) findViewById(R.id.alunos);
	        desenvolvedoresTextView.setText(TEXTO_RANKING);
			
			TextView ajudaTextView = (TextView) findViewById(R.id.ajuda);
			ajudaTextView.setText(TEXTO_OPCOES);
			
			TextView sairTextView = (TextView) findViewById(R.id.sair);
			sairTextView.setText(TEXTO_SAIR);
			
			Button botaoVoltarAjuda = (Button) findViewById(R.id.voltarAjuda);
			botaoVoltarAjuda.setOnClickListener(botaoVoltarAjudaListener());
	    }


	private OnClickListener botaoVoltarAjudaListener() {
		return new OnClickListener() {

			public void onClick(View v) {
				finish();
			}
		};
	}

}
