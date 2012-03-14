package ufcg.les.anagrama.activity;

import java.util.ArrayList;
import java.util.List;

import ufcg.les.anagrama.R;
import ufcg.les.anagrama.enummeration.Nivel;
import ufcg.les.anagrama.exceptions.AnagramaNaoExistenteException;
import ufcg.les.anagrama.exceptions.PalavraJaEncontradaException;
import ufcg.les.anagrama.model.Jogo;
import ufcg.les.anagrama.persistence.dao.Usuario;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class JogoActivity extends Activity {

	private static final String BOA_SORTE = "Boa Sorte, ";
	public static int palavrasRestantes;
	public static final String VAZIO = "";

	private static List<String> palavrasEncontradasListPrimeiraColuna = new ArrayList<String>();
	private static List<String> palavrasEncontradasListSegundaColuna = new ArrayList<String>();
	private static List<String> palavrasEncontradasListTerceiraColuna = new ArrayList<String>();

	private Jogo jogoAtual;

	private TextView palavraTextView;
	private TextView pontuacaoTextView;
	private EditText respostaEditText;
	private Chronometer cronometro;

	private List<List<String>> palavras = new ArrayList<List<String>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.page_jogo);

		limpaPalavrasEncontradas();

		Intent jogoIntent = getIntent();
		String nomeJogador = jogoIntent.getStringExtra("nomeJogador");

		Nivel nivel = null;
		nivel = (Nivel) jogoIntent.getSerializableExtra("nivel");

		Jogo jogo = new Jogo(nomeJogador, nivel);
		setJogoAtual(jogo);

		jogo.carregarNovoAnagrama();

		carregaVariaveisDoJogo(jogo);

		Button botaoEnviar = (Button) findViewById(R.id.botaoEnviar);
		botaoEnviar.setOnClickListener(botaoEnviarListener());

		// carregaCaixaLetras(tamanhoPalavra);
	}

	private static void limpaPalavrasEncontradas() {
		palavrasEncontradasListPrimeiraColuna.clear();
		palavrasEncontradasListSegundaColuna.clear();
		palavrasEncontradasListTerceiraColuna.clear();
	}

	private void verificaFimDoJogo() {
		if (palavrasRestantes == 0) {
			salvaTempo();
			Usuario usuario = criaUsuario();

			mostraDialog(
					"FIM DE JOGO" + "\n\n Parabéns: "
							+ jogoAtual.getNomeJogador() + "\n Pontuação: "
							+ jogoAtual.getPontuacao() + "\n Tempo: "
							+ cronometro.getText(), alertaFimListener(usuario));
		}
	}

	private Usuario criaUsuario() {
		Usuario usuario = new Usuario(jogoAtual.getNomeJogador(),
				jogoAtual.getPontuacao(), jogoAtual.getTempo());
		return usuario;
	}

	private void salvaTempo() {
		jogoAtual.setTempo(cronometro.getBase());
		paraCronometro();
	}

	private void paraCronometro() {
		cronometro.stop();
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

					atualizaVariaveisDoJogo();

					mostraPalavrasTela(resposta, acertoColuna1, acertoColuna2,
							acertoColuna3);

					verificaFimDoJogo();

					respostaEditText.setText(VAZIO);

				} catch (AnagramaNaoExistenteException re) {

					mostraDialog("Esta não é uma palavra listada!",
							alertaListener());

				} catch (PalavraJaEncontradaException pe) {
					mostraDialog("Esta palavra já foi encontrada!",
							alertaListener());

				} finally {
					atualizaPontuacao();
				}
			}

			private void mostraPalavrasTela(String resposta,
					TextView acertoColuna1, TextView acertoColuna2,
					TextView acertoColuna3) {
				if (primeiraListaVazia()) {
					palavrasEncontradasListPrimeiraColuna.add(resposta);

				} else if (primeiraListaCheira()) {
					palavrasEncontradasListSegundaColuna.add(resposta);

				} else {
					palavrasEncontradasListTerceiraColuna.add(resposta);
				}

				mostraPalavras(acertoColuna1, acertoColuna2, acertoColuna3);
			}

			private boolean primeiraListaVazia() {
				return palavrasEncontradasListPrimeiraColuna.size() < 5;
			}

			private boolean primeiraListaCheira() {
				return palavrasEncontradasListPrimeiraColuna.size() > 4
						&& palavrasEncontradasListSegundaColuna.size() < 5;
			}

		};
	}

	private void mostraPalavras(TextView acertoColuna1, TextView acertoColuna2,
			TextView acertoColuna3) {

		acertoColuna1
				.setText(mostraPalavrasEncontradas(palavrasEncontradasListPrimeiraColuna));
		acertoColuna1.setVisibility(TextView.VISIBLE);

		acertoColuna2
				.setText(mostraPalavrasEncontradas(palavrasEncontradasListSegundaColuna));
		acertoColuna2.setVisibility(TextView.VISIBLE);

		acertoColuna3
				.setText(mostraPalavrasEncontradas(palavrasEncontradasListTerceiraColuna));
		acertoColuna3.setVisibility(TextView.VISIBLE);
	}

	private static CharSequence mostraPalavrasEncontradas(List<String> palavras) {
		String palavrasEncontradas = "";
		for (String palavra : palavras) {
			palavrasEncontradas += "\n- " + palavra;
		}
		return palavrasEncontradas;
	}

	private void carregaVariaveisDoJogo(Jogo jogo) {
		palavraTextView = (TextView) findViewById(R.id.textViewPalavraEmbaralhada);
		palavraTextView.setText(jogo.getPalavraEmbaralhada());
		
		ImageButton botaoSair = (ImageButton) findViewById(R.id.imageButton1);
		botaoSair.setOnClickListener(botaoSairListener());

		pontuacaoTextView = (TextView) findViewById(R.id.textViewPontuacao);
		pontuacaoTextView.setVisibility(TextView.VISIBLE);
		atualizaPontuacao();

		TextView nomeJogadorTextView = (TextView) findViewById(R.id.textViewJogador);
		nomeJogadorTextView.setText(BOA_SORTE + jogo.getNomeJogador() + "!");

		respostaEditText = (EditText) findViewById(R.id.resposta);

		cronometro = (Chronometer) findViewById(R.id.chronometer1);
		cronometro.start();

		atualizaVariaveisDoJogo();
	}

	private OnClickListener botaoSairListener() {
		return new OnClickListener() {
			
			public void onClick(View v) {
				Intent fimIntent = new Intent(JogoActivity.this,
						AnagramaHTActivity.class);
				startActivity(fimIntent);
				finish();
			}
		};
	}

	private void atualizaVariaveisDoJogo() {
		atualizaPalavrasRestantes();
		// atualizaPontuacao();
	}

	private void atualizaPontuacao() {
		pontuacaoTextView.setText("Pontuação: " + jogoAtual.getPontuacao());
	}

	private void atualizaPalavrasRestantes() {
		palavrasRestantes = jogoAtual.totalPalavrasRestantes();

		TextView palavrasRestantesTextView = (TextView)
				findViewById(R.id.textViewPalavrasRestantes);
		if (palavrasRestantes > 1) {
			palavrasRestantesTextView.setText("Faltam: " + palavrasRestantes
					+ " palavras");

		} else {
			palavrasRestantesTextView.setText("Falta: " + palavrasRestantes
					+ " palavra");
		}

		palavrasRestantesTextView.setVisibility(TextView.VISIBLE);
	}

	private void mostraDialog(String msg,
			DialogInterface.OnClickListener listener) {
		AlertDialog alerta = new AlertDialog.Builder(JogoActivity.this)
				.create();
		alerta.setMessage(msg);
		alerta.setButton("Ok", listener);
		alerta.show();
	}

	private DialogInterface.OnClickListener alertaFimListener(
			final Usuario usuario) {
		return new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				mudaContexto(usuario);
			}

			//TODO Tentativa quando existia o bd em memoria
			private void mudaContexto(Usuario usuario) {
				Intent fimIntent = new Intent(JogoActivity.this,
						AnagramaHTActivity.class);
				fimIntent.putExtra("usuario", usuario);

				startActivity(fimIntent);
				finish();

			}
		};
	}

	private DialogInterface.OnClickListener alertaListener() {
		return new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		};
	}

	// --------------- Gambiarra --------------------

	// private void carregaCaixaLetras(int tamanhoPalavra) {
	// if (tamanhoPalavra == 3) {
	// carregaTresCaixas();
	//
	// } else if (tamanhoPalavra == 4) {
	// carregaQuatroCaixas();
	//
	// } else if (tamanhoPalavra == 5) {
	// carregaCincoCaixas();
	//
	// } else if (tamanhoPalavra == 6) {
	// carregaSeisCaixas();
	//
	// } else if (tamanhoPalavra == 7) {
	// carregaSeteCaixas();
	//
	// } else {
	// carregaOitoCaixas();
	// }
	// }
	//
	//
	// private void carregaOitoCaixas() {
	// carregaSeteCaixas();
	// ImageView imagem8 = (ImageView) findViewById(R.id.caixa_letra8);
	// imagem8.setVisibility(ImageView.VISIBLE);
	// }
	//
	// private void carregaSeteCaixas() {
	// carregaSeisCaixas();
	// ImageView imagem7 = (ImageView) findViewById(R.id.caixa_letra7);
	// imagem7.setVisibility(ImageView.VISIBLE);
	// }
	//
	// private void carregaSeisCaixas() {
	// carregaCincoCaixas();
	// ImageView imagem6 = (ImageView) findViewById(R.id.caixa_letra6);
	// imagem6.setVisibility(ImageView.VISIBLE);
	// }
	//
	// private void carregaCincoCaixas() {
	// carregaQuatroCaixas();
	// ImageView imagem5 = (ImageView) findViewById(R.id.caixa_letra5);
	// imagem5.setVisibility(ImageView.VISIBLE);
	// }
	//
	// private void carregaQuatroCaixas() {
	// carregaTresCaixas();
	// ImageView imagem4 = (ImageView) findViewById(R.id.caixa_letra4);
	// imagem4.setVisibility(ImageView.VISIBLE);
	// }
	//
	// private void carregaTresCaixas() {
	// ImageView imagem1 = (ImageView) findViewById(R.id.caixa_letra1);
	// ImageView imagem2 = (ImageView) findViewById(R.id.caixa_letra2);
	// ImageView imagem3 = (ImageView) findViewById(R.id.caixa_letra3);
	//
	// imagem1.setVisibility(ImageView.VISIBLE);
	// imagem2.setVisibility(ImageView.VISIBLE);
	// imagem3.setVisibility(ImageView.VISIBLE);
	// }

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
