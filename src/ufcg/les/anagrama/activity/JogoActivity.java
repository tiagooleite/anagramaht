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
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class JogoActivity extends Activity {

	private static final String BOA_SORTE = "Boa Sorte, ";
	public static int palavrasRestantes;
	public static final String VAZIO = "";
	private static final int TAMANHO_CAIXINHA = 55; //setar para 30 quando for passar para o celular
	private static final float TAMANHO_LETRA = 15; //comenta isso quando for passar para o celular
	private static String meusPalpites = "";
	

	private List<Button> listaBotoesPalavras = new ArrayList<Button>();
	private static List<Button> bufferBotoes = new ArrayList<Button>();

	private static List<String> palavrasEncontradasListPrimeiraColuna = new ArrayList<String>();
	private static List<String> palavrasEncontradasListSegundaColuna = new ArrayList<String>();

	private Jogo jogoAtual;

	private TextView pontuacaoTextView;
	private EditText respostaEditText;
	private Chronometer cronometro;
	private ImageButton botaoSair;
	private ImageButton botaoVoltar;
	

	private List<List<String>> palavras = new ArrayList<List<String>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		limpaVariaveisDaClasse();
		
		Intent jogoIntent = getIntent();
		Jogo jogo = carregaVariaveisDoJogador(jogoIntent);
		jogo.carregarNovoAnagrama();
		
		LinearLayout layout = carregaBotoesPalavra();

        setContentView(layout);
		
		carregaVariaveisDoJogo();

		Button botaoEnviar = (Button) findViewById(R.id.botaoEnviar);
		botaoEnviar.setOnClickListener(botaoEnviarListener());
	}

	private static void limpaVariaveisDaClasse() {
		limpaPalpites();
		limpaPalavrasEncontradas();
		limpaBuffer();
	}

	private static void limpaPalpites() {
		meusPalpites = "";
	}

	private LinearLayout carregaBotoesPalavra() {
		String palavraEmb = jogoAtual.getPalavraEmbaralhada();
		
		String[] splitPalavraEmb = palavraEmb.split("");
		
		LinearLayout layout = (LinearLayout) View.inflate(this,
				R.layout.page_jogo, null);
		LinearLayout vTblRow = (LinearLayout)layout.
				findViewById(R.id.groupCaixinhas);
		
		for (int i = 1; i < splitPalavraEmb.length; i++) {
			Button botaoLetra = new Button(this);
			botaoLetra.setText(splitPalavraEmb[i]);
			botaoLetra.setTextSize(TAMANHO_LETRA);
			botaoLetra.setLayoutParams(new TableRow.
					LayoutParams(TAMANHO_CAIXINHA, TAMANHO_CAIXINHA));
			
			vTblRow.addView(botaoLetra);
			listaBotoesPalavras.add(botaoLetra);
		}
		
		addOnClickListenerBotoes();
		
		return layout;
	}

	private void addOnClickListenerBotoes() {
		for (Button botaoLetra : listaBotoesPalavras) {
			String letra = (String) botaoLetra.getText();
			botaoLetra.setOnClickListener(clickListenerBotaoPalavra(letra, botaoLetra));
		}
		
	}

	private OnClickListener clickListenerBotaoPalavra(final String letra,
			final Button botaoLetra) {
		return new OnClickListener() {
			
			public void onClick(View v) {
				meusPalpites += letra;
				bufferBotoes.add(botaoLetra);
				respostaEditText.setText(meusPalpites);
				botaoLetra.setVisibility(Button.INVISIBLE);
			}
		};
	}

	private Jogo carregaVariaveisDoJogador(Intent jogoIntent) {
		String nomeJogador = jogoIntent.getStringExtra("nomeJogador");

		Nivel nivel = null;
		nivel = (Nivel) jogoIntent.getSerializableExtra("nivel");

		Jogo jogo = new Jogo(nomeJogador, nivel);
		setJogoAtual(jogo);
		return jogo;
	}

	private static void limpaPalavrasEncontradas() {
		palavrasEncontradasListPrimeiraColuna.clear();
		palavrasEncontradasListSegundaColuna.clear();
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
	
	private void apresentaBotoesPalavra() {
		for (Button botao : listaBotoesPalavras) {
			botao.setVisibility(Button.VISIBLE);
		}
		respostaEditText.setText("");
		limpaPalpites();
	}

	private OnClickListener botaoEnviarListener() {
		return new OnClickListener() {

			public void onClick(View v) {
				String resposta = respostaEditText.getText().toString();

				try {
					jogoAtual.checarPalavra(resposta);

					TextView acertoColuna1 = (TextView) findViewById(R.id.acertosColuna1);
					acertoColuna1.setTextSize(15);
					TextView acertoColuna2 = (TextView) findViewById(R.id.acertosColuna2);
					acertoColuna2.setTextSize(15);
					
					atualizaVariaveisDoJogo();

					mostraPalavrasTela(resposta, acertoColuna1, acertoColuna2);

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
					apresentaBotoesPalavra();
					limpaBuffer();
				}
			}

			private void mostraPalavrasTela(String resposta,
					TextView acertoColuna1, TextView acertoColuna2) {
				if (primeiraListaVazia()) {
					palavrasEncontradasListPrimeiraColuna.add(resposta);
				} else {
					palavrasEncontradasListSegundaColuna.add(resposta);
				} 
				mostraPalavras(acertoColuna1, acertoColuna2);
			}

			private boolean primeiraListaVazia() {
				return palavrasEncontradasListPrimeiraColuna.size() < 5;
			}
		};
	}
	
	private static void limpaBuffer() {
		bufferBotoes = new ArrayList<Button>();
	}

	private void mostraPalavras(TextView acertoColuna1, TextView acertoColuna2) {

		acertoColuna1
				.setText(mostraPalavrasEncontradas(palavrasEncontradasListPrimeiraColuna));
		acertoColuna1.setVisibility(TextView.VISIBLE);

		acertoColuna2
				.setText(mostraPalavrasEncontradas(palavrasEncontradasListSegundaColuna));
		acertoColuna2.setVisibility(TextView.VISIBLE);

	}

	private static CharSequence mostraPalavrasEncontradas(List<String> palavras) {
		String palavrasEncontradas = "";
		
		for (String palavra : palavras) {
			palavrasEncontradas += "\n- " + palavra;
		}
		return palavrasEncontradas;
	}

	private void carregaVariaveisDoJogo() {
		
		botaoVoltar = (ImageButton) findViewById(R.id.imageBotaoVoltar);
		botaoVoltar.setOnClickListener(botaoVoltarListener());
		
		botaoSair = (ImageButton) findViewById(R.id.imageBotaoSair);
		botaoSair.setOnClickListener(botaoSairListener());

		pontuacaoTextView = (TextView) findViewById(R.id.textViewPontuacao);
		pontuacaoTextView.setVisibility(TextView.VISIBLE);
		atualizaPontuacao();

		TextView nomeJogadorTextView = (TextView) findViewById(R.id.textViewJogador);
		nomeJogadorTextView.setText(BOA_SORTE + jogoAtual.getNomeJogador() + "!");

		respostaEditText = (EditText) findViewById(R.id.resposta);

		cronometro = (Chronometer) findViewById(R.id.chronometer1);
		cronometro.start();

		atualizaVariaveisDoJogo();
	}

	private OnClickListener botaoVoltarListener() {
		return new OnClickListener() {
			
			public void onClick(View v) {
				if (!meusPalpites.equals("")) {
					setMeusPalpites(meusPalpites.substring(0, meusPalpites.length() - 1));
					respostaEditText.setText(meusPalpites);
					bufferBotoes.remove(bufferBotoes.size() - 1).setVisibility(Button.VISIBLE);
				}
			}
		};
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
	
	public static void setMeusPalpites(String meusPalpites) {
		JogoActivity.meusPalpites = meusPalpites;
	}

}
