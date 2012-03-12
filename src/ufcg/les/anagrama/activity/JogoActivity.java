package ufcg.les.anagrama.activity;

import java.util.ArrayList;
import java.util.List;

import ufcg.les.anagrama.R;
import ufcg.les.anagrama.enummeration.Nivel;
import ufcg.les.anagrama.exceptions.AnagramaNaoExistenteException;
import ufcg.les.anagrama.exceptions.PalavraJaEncontradaException;
import ufcg.les.anagrama.model.Jogo;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class JogoActivity extends Activity {
	
	private static final String LOGS = "logs";
	private static final String BOA_SORTE = "Boa Sorte, ";
	
	private static List<String> palavrasEncontradasListPrimeiraColuna = new ArrayList<String>();
	private static List<String> palavrasEncontradasListSegundaColuna = new ArrayList<String>();
	private static List<String> palavrasEncontradasListTerceiraColuna = new ArrayList<String>();
	
	private Jogo jogoAtual; 
	
	private TextView palavraTextView;
	private EditText respostaEditText;
	
	
	private List<List<String>> palavras = new ArrayList<List<String>>();

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
		
		int tamanhoPalavra = jogo.carregarNovoAnagrama();
		
		carregaVariaveisDoJogo(jogo);
		
		Button botaoEnviar = (Button) findViewById(R.id.botaoEnviar);
		botaoEnviar.setOnClickListener(botaoEnviarListener());
		//carregaCaixaLetras(tamanhoPalavra);
	}

	private OnClickListener botaoEnviarListener() {
		return new OnClickListener() {
			
			public void onClick(View v) {
				String resposta = respostaEditText.getText().toString();
				
				try {
					jogoAtual.checarPalavra(resposta);
					
					TextView acertoColuna1 = (TextView) findViewById(R.id.acertosColuna1);
					TextView acertoColuna2 = (TextView) findViewById(R.id.acertosColuna2);
					TextView acertoColuna3 = (TextView) findViewById(R.id.acertosColuna3);
					
					if (primeiraListaVazia()) {
						palavrasEncontradasListPrimeiraColuna.add(resposta);
					
					} else if (primeiraListaCheira()) {
						palavrasEncontradasListSegundaColuna.add(resposta);
					
					} else {
						palavrasEncontradasListTerceiraColuna.add(resposta);
					}
					
					mostraPalavras(acertoColuna1, acertoColuna2, acertoColuna3);
				
				} catch (AnagramaNaoExistenteException re) {
					trataExcecao("Esta não é uma palavra listada!");
				
				} catch (PalavraJaEncontradaException pe) {
					trataExcecao("Esta palavra já foi encontrada!");
				}
			}

			private boolean primeiraListaVazia() {
				return palavrasEncontradasListPrimeiraColuna.size() < 5;
			}

			private boolean primeiraListaCheira() {
				return palavrasEncontradasListPrimeiraColuna.size() > 4 &&
						palavrasEncontradasListSegundaColuna.size() < 5;
			}

			
		};
	}
	
	private void mostraPalavras(TextView acertoColuna1,
			TextView acertoColuna2, TextView acertoColuna3) {
		
		acertoColuna1.setText(mostraPalavrasEncontradas(
				palavrasEncontradasListPrimeiraColuna));
		acertoColuna1.setVisibility(TextView.VISIBLE);
		
		acertoColuna2.setText(mostraPalavrasEncontradas(
				palavrasEncontradasListSegundaColuna));
		acertoColuna2.setVisibility(TextView.VISIBLE);
		
		acertoColuna3.setText(mostraPalavrasEncontradas(
				palavrasEncontradasListTerceiraColuna));
		acertoColuna3.setVisibility(TextView.VISIBLE);
	}
	
	private static CharSequence mostraPalavrasEncontradas(List<String> palavras) {
		String palavrasEncontradas = "";
		for (String palavra : palavras) {
			palavrasEncontradas += "\n- " + palavra;
		}
		System.out.println(palavrasEncontradas);
		return palavrasEncontradas;
	}

	private void carregaVariaveisDoJogo(Jogo jogo) {
		palavraTextView = (TextView) findViewById(R.id.textViewPalavraEmbaralhada);
		palavraTextView.setText(jogo.getPalavraEmbaralhada());
		
		TextView nomeJogadorTextView = (TextView) findViewById(R.id.textViewJogador);
		nomeJogadorTextView.setText(BOA_SORTE + jogo.getNomeJogador() + "!");
		
		respostaEditText = (EditText) findViewById(R.id.resposta);
		
		Chronometer cron = (Chronometer) findViewById(R.id.chronometer1);
		cron.start();
	}
	
	
	private void trataExcecao(String msg) {
		AlertDialog alertaPalavraInexistente = new AlertDialog.Builder(
				JogoActivity.this).create();
		alertaPalavraInexistente.setMessage(msg);
		alertaPalavraInexistente.setButton("Ok", alertaPalavraInexistenteListener());
		alertaPalavraInexistente.show();
	}
	
	private DialogInterface.OnClickListener alertaPalavraInexistenteListener() {
		return new DialogInterface.OnClickListener() {
			
		      public void onClick(DialogInterface dialog, int which) {
		         dialog.cancel();
		       } };
	}
	
	// --------------- Gambiarra --------------------

	private void carregaCaixaLetras(int tamanhoPalavra) {
		if (tamanhoPalavra == 3) {
			carregaTresCaixas();
		
		} else if (tamanhoPalavra == 4) {
			carregaQuatroCaixas();
		
		} else if (tamanhoPalavra == 5) {
			carregaCincoCaixas();
		
		} else if (tamanhoPalavra == 6) {
			carregaSeisCaixas();
		
		} else if (tamanhoPalavra == 7) {
			carregaSeteCaixas();
		
		} else {
			carregaOitoCaixas();
		}
	}

	
	private void carregaOitoCaixas() {
		carregaSeteCaixas();
		ImageView imagem8 = (ImageView) findViewById(R.id.caixa_letra8);
		imagem8.setVisibility(ImageView.VISIBLE);
	}

	private void carregaSeteCaixas() {
		carregaSeisCaixas();
		ImageView imagem7 = (ImageView) findViewById(R.id.caixa_letra7);
		imagem7.setVisibility(ImageView.VISIBLE);
	}

	private void carregaSeisCaixas() {
		carregaCincoCaixas();
		ImageView imagem6 = (ImageView) findViewById(R.id.caixa_letra6);
		imagem6.setVisibility(ImageView.VISIBLE);
	}

	private void carregaCincoCaixas() {
		carregaQuatroCaixas();
		ImageView imagem5 = (ImageView) findViewById(R.id.caixa_letra5);
		imagem5.setVisibility(ImageView.VISIBLE);
	}

	private void carregaQuatroCaixas() {
		carregaTresCaixas();
		ImageView imagem4 = (ImageView) findViewById(R.id.caixa_letra4);
		imagem4.setVisibility(ImageView.VISIBLE);
	}

	private void carregaTresCaixas() {
		ImageView imagem1 = (ImageView) findViewById(R.id.caixa_letra1);
		ImageView imagem2 = (ImageView) findViewById(R.id.caixa_letra2);
		ImageView imagem3 = (ImageView) findViewById(R.id.caixa_letra3);
		
		imagem1.setVisibility(ImageView.VISIBLE);
		imagem2.setVisibility(ImageView.VISIBLE);
		imagem3.setVisibility(ImageView.VISIBLE);
	}
	
	// --------------- FIM Gambiarra --------------------

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

	public TextView getPalavraTextView() {
		return palavraTextView;
	}

	public void setPalavraTextView(TextView palavraTextView) {
		this.palavraTextView = palavraTextView;
	}

}
