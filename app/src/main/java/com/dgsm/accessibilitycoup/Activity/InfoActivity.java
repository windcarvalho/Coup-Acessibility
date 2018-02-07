package com.dgsm.accessibilitycoup.Activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.nfc.NfcAdapter;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.dgsm.accessibilitycoup.R;

import java.util.Locale;

import tardigrade.Tardigrade;
import tardigrade.deck.ICard;
import tardigrade.resources.impl.Deck;

public class InfoActivity extends AppCompatActivity {

    private static final String TAG = "InfoActivity";
    private Button btNome, btDescricao, btDetalhes;

    private Tardigrade game;
    private Deck deck = null;

    /*Componente botão*/
    private Button btVoltar;

    //NFC e TextToSpeech
    private NfcAdapter mNfcAdapter;

    /*Variável do Arquivo gerado do SharedPreferences*/
    private static final String ARQUIVO_CARTAS = "ArquivoCartas";

    private ICard card;

    /*Usados para recuperar nome e descrição das cartas, através do CSV*/
    private String nameCard;
    private String descriptionCard;

    /*Usados para recuperar os dados do SharedPreferences*/
    private String[] mDuque      = new String[3];
    private String[] mAssassino  = new String[3];
    private String[] mCondessa   = new String[3];
    private String[] mCapitao    = new String[3];
    private String[] mEmbaixador = new String[3];

    CharSequence[] values = {" Duque ", " Assassino ", " Condessa ",
            " Capitão ", " Embaixador "};

    int[] mContador = new int[5];

    String idUltimaCarta;

    Toast toast;
    LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_info);

        setTitle("Tela ler informações das Cartas");
        mCasts();
        game = Tardigrade.getInstance(this);
        deck = Deck.getInstance(this);
        toast = new Toast(this);
        inflater = getLayoutInflater();

        verificaNFC();

        btNome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readNameCard(recuperarDados("idUltimaCarta"));
            }
        });

        btDescricao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readDescription(recuperarDados("idUltimaCarta"));
            }
        });

        btDetalhes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readDescriptionDetailed(recuperarDados("idUltimaCarta"));
            }
        });

    }

    private void mCasts(){
        Log.i(TAG,"mCasts");
        btNome = findViewById(R.id.btNome);
        btDescricao = findViewById(R.id.btDescricao);
        btDetalhes = findViewById(R.id.btDetalhes);
    }

    /*Cadastrar Cartas*/
    private void alertDialogCadastrar(final String id){
        new MaterialDialog.Builder(this)
                .title("Tela de Cadastro!\nSelecione a carta que deseja atribuir a TAG!")
                .items(values)
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int myItem, CharSequence text) {

                        switch (myItem) {
                            case 0: {
                                switch (mContador[0]) {
                                    case 0: {
                                        Log.i(TAG, "Duque 1");
                                        salvarDados("Duque","duque1", id);
                                        mContador[0]++;
                                        break;
                                    }
                                    case 1: {
                                        Log.i(TAG, "Duque 2");
                                        salvarDados("Duque","duque2", id);
                                        mContador[0]++;
                                        break;
                                    }
                                    case 2: {
                                        Log.i(TAG, "Duque 3");
                                        salvarDados("Duque","duque3", id);
                                        mContador[0]++;
                                        break;
                                    }
                                    default: mContador[0] = 0;
                                }
                                break;
                            }

                            case 1:{
                                switch (mContador[1]) {
                                    case 0: {
                                        Log.i(TAG, "Assassino 1");
                                        salvarDados("Assassino","assassino1", id);
                                        mContador[1]++;
                                        break;
                                    }
                                    case 1: {
                                        Log.i(TAG, "Assassino 2");
                                        salvarDados("Assassino","assassino2", id);
                                        mContador[1]++;
                                        break;
                                    }
                                    case 2: {
                                        Log.i(TAG, "Assassino 3");
                                        salvarDados("Assassino","assassino3", id);
                                        mContador[1]++;
                                        break;
                                    }
                                    default: mContador[1] = 0;
                                }
                                break;
                            }

                            case 2:{//Condessa
                                switch (mContador[2]) {
                                    case 0: {
                                        Log.i(TAG, "Condessa 1");
                                        salvarDados("Condessa","condessa1", id);
                                        mContador[2]++;
                                        break;
                                    }
                                    case 1: {
                                        Log.i(TAG, "Condessa 2");
                                        salvarDados("Condessa","condessa2", id);
                                        mContador[2]++;
                                        break;
                                    }
                                    case 2: {
                                        Log.i(TAG, "Condessa 3");
                                        salvarDados("Condessa","condessa3", id);
                                        mContador[2]++;
                                        break;
                                    }
                                    default: mContador[2] = 0;
                                }
                                break;
                            }

                            case 3:{//Capitão
                                switch (mContador[3]) {
                                    case 0: {
                                        Log.i(TAG, "Capitão 1");
                                        salvarDados("Capitão","capitao1", id);
                                        mContador[3]++;
                                        break;
                                    }
                                    case 1: {
                                        Log.i(TAG, "Capitão 2");
                                        salvarDados("Capitão","capitao2", id);
                                        mContador[3]++;
                                        break;
                                    }
                                    case 2: {
                                        Log.i(TAG, "Capitão 3");
                                        salvarDados("Capitão","capitao3", id);
                                        mContador[3]++;
                                        break;
                                    }
                                    default: mContador[3] = 0;
                                }
                                break;
                            }

                            case 4:{//Embaixador
                                switch (mContador[4]) {
                                    case 0: {
                                        Log.i(TAG, "Embaixador 1");
                                        salvarDados("Embaixador","embaixador1", id);
                                        mContador[4]++;
                                        break;
                                    }
                                    case 1: {
                                        Log.i(TAG, "Embaixador 2");
                                        salvarDados("Embaixador","embaixador2", id);
                                        mContador[4]++;
                                        break;
                                    }
                                    case 2: {
                                        Log.i(TAG, "Embaixador 3");
                                        salvarDados("Embaixador","embaixador3", id);
                                        mContador[4]++;
                                        break;
                                    }
                                    default: mContador[4] = 0;
                                }
                                break;
                            }
                        }

                        return true;
                    }
                })
                //.positiveText("Cadastrar")
                .show();
    }

    /*Mostra Mensagens Toast*/
    public void N(final String message){
        View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.custom_toast_container));
        TextView myText = layout.findViewById(R.id.text);
        myText.setText(message);
        toast.setView(layout);
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER,0,100);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    /*Recupera as informações do SharedPreferences para as Variáveis*/
    private void recuperaDadosParaVariaveis() {
        mDuque[0] = recuperarDados("duque1");
        mDuque[1] = recuperarDados("duque2");
        mDuque[2] = recuperarDados("duque3");

        mAssassino[0] = recuperarDados("assassino1");
        mAssassino[1] = recuperarDados("assassino2");
        mAssassino[2] = recuperarDados("assassino3");

        mCondessa[0] = recuperarDados("condessa1");
        mCondessa[1] = recuperarDados("condessa2");
        mCondessa[2] = recuperarDados("condessa3");

        mCapitao[0] = recuperarDados("capitao1");
        mCapitao[1] = recuperarDados("capitao2");
        mCapitao[2] = recuperarDados("capitao3");

        mEmbaixador[0] = recuperarDados("embaixador1");
        mEmbaixador[1] = recuperarDados("embaixador2");
        mEmbaixador[2] = recuperarDados("embaixador3");

        idUltimaCarta = recuperarDados("idUltimaCarta");
    }

    /*Recupera o nome da carta do CSV*/
    private void recuperaNomeCartaCSV(String idCsv){
        card = deck.getCard(idCsv);
        nameCard = card.getName();
        N("A carta lida foi: "+nameCard);
    }

    /*Recupera a descrição da carta do CSV*/
    private void recuperaDescricaoCartaCSV(String idCsv){
        card = deck.getCard(idCsv);
        descriptionCard = card.getDescription();
        N(descriptionCard);
    }

    /*Nome das Cartas*/
    public void readNameCard(String id){

        recuperaDadosParaVariaveis();

        Log.i(TAG,"\n\nÚltimo ID lido: "+id);
        Log.i(TAG,"mDuque[0]: "+mDuque[0]);
        Log.i(TAG,"mDuque[1]: "+mDuque[1]);
        Log.i(TAG,"mDuque[2]: "+mDuque[2]);

        if (mDuque[0].equals(id) || mDuque[1].equals(id) || mDuque[2].equals(id)){
            Log.i(TAG,"mDuque: "+id);
            recuperaNomeCartaCSV("1");
            salvarDadosUltimaCartaLida(id);
        }

        else if (mAssassino[0].equals(id) || mAssassino[1].equals(id) || mAssassino[2].equals(id)){
            Log.i(TAG,"mAssassino: "+id);
            recuperaNomeCartaCSV("2");
            salvarDadosUltimaCartaLida(id);
        }

        else if (mCondessa[0].equals(id) || mCondessa[1].equals(id) || mCondessa[2].equals(id)){
            Log.i(TAG,"mCondessa: "+id);
            recuperaNomeCartaCSV("3");
            salvarDadosUltimaCartaLida(id);
        }


        else if (mCapitao[0].equals(id) || mCapitao[1].equals(id) || mCapitao[2].equals(id)){
            Log.i(TAG,"mCapitao: "+id);
            recuperaNomeCartaCSV("4");
            salvarDadosUltimaCartaLida(id);
        }


        else if (mEmbaixador[0].equals(id) || mEmbaixador[1].equals(id) || mEmbaixador[2].equals(id)){
            Log.i(TAG,"mEmbaixador: "+id);
            recuperaNomeCartaCSV("5");
            salvarDadosUltimaCartaLida(id);
        }

        else if (id.equals("")){
            N("LEIA UMA CARTA ANTES");
        }else{
            alertDialogCadastrar(id);
        }

    }

    /*Descrição das Cartas*/
    public void readDescription(String id){

        recuperaDadosParaVariaveis();

        Log.i(TAG,"\n\nReadDescription: ");
        Log.i(TAG,"Último ID lido: "+id);
        Log.i(TAG,"mDuque[0]: "+mDuque[0]);
        Log.i(TAG,"mDuque[1]: "+mDuque[1]);
        Log.i(TAG,"mDuque[2]: "+mDuque[2]);


        if (mDuque[0].equals(id) || mDuque[1].equals(id) || mDuque[2].equals(id)){
            Log.i(TAG,"mDuque: "+id);
            //N("Duque");
            recuperaDescricaoCartaCSV("1");
        }

        else if (mAssassino[0].equals(id) || mAssassino[1].equals(id) || mAssassino[2].equals(id)){
            Log.i(TAG,"mAssassino: "+id);
            //N("Assassino");
            recuperaDescricaoCartaCSV("2");
        }

        else if (mCondessa[0].equals(id) || mCondessa[1].equals(id) || mCondessa[2].equals(id)){
            Log.i(TAG,"mCondessa: "+id);
            //N("Condessa");
            recuperaDescricaoCartaCSV("3");
        }


        else if (mCapitao[0].equals(id) || mCapitao[1].equals(id) || mCapitao[2].equals(id)){
            Log.i(TAG,"mCapitao: "+id);
            //N("Capitão");
            recuperaDescricaoCartaCSV("4");
        }


        else if (mEmbaixador[0].equals(id) || mEmbaixador[1].equals(id) || mEmbaixador[2].equals(id)){
            Log.i(TAG,"mEmbaixador: "+id);
            //N("Embaixador");
            recuperaDescricaoCartaCSV("5");
        }

        else if(id.equals("")){
            N("LEIA UMA CARTA ANTES");
        }

        else{
            alertDialogCadastrar(id);
            Log.i(TAG,"id vale: "+id);
        }
    }

    /*Descrição das Cartas*/
    public void readDescriptionDetailed(String id){

        recuperaDadosParaVariaveis();

        if (mDuque[0].equals(id) || mDuque[1].equals(id) || mDuque[2].equals(id)){
            Log.i(TAG,"mDuque: "+id);
            //N("Duque");
            recuperaDescricaoCartaCSV("6");
        }

        else if (mAssassino[0].equals(id) || mAssassino[1].equals(id) || mAssassino[2].equals(id)){
            Log.i(TAG,"mAssassino: "+id);
            //N("Assassino");
            recuperaDescricaoCartaCSV("7");
        }

        else if (mCondessa[0].equals(id) || mCondessa[1].equals(id) || mCondessa[2].equals(id)){
            Log.i(TAG,"mCondessa: "+id);
            //N("Condessa");
            recuperaDescricaoCartaCSV("8");
        }


        else if (mCapitao[0].equals(id) || mCapitao[1].equals(id) || mCapitao[2].equals(id)){
            Log.i(TAG,"mCapitao: "+id);
            //N("Capitão");
            recuperaDescricaoCartaCSV("9");
        }


        else if (mEmbaixador[0].equals(id) || mEmbaixador[1].equals(id) || mEmbaixador[2].equals(id)){
            Log.i(TAG,"mEmbaixador: "+id);
            //N("Embaixador");
            recuperaDescricaoCartaCSV("10");
        }

        else if (id.equals("")){
            N("LEIA UMA CARTA ANTES");
        }else{
            alertDialogCadastrar(id);
        }
    }

    /************************************** SHARED PREFERENCES *********************************************/
    private void salvarDados(String nome,String nomeCarta, String tagCarta){

        limpaDadoCartaEspecifica(tagCarta);

        SharedPreferences preferences = getSharedPreferences(ARQUIVO_CARTAS, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(nomeCarta, tagCarta);
        editor.commit();
        N("Carta "+nome+" cadastrada com sucesso!");
        Log.i(TAG,"\n\nNome Carta: "+nomeCarta+"\tTAG ID: "+tagCarta);
    }

    private void salvarDadosUltimaCartaLida(String idUltimaCarta){

        limpaDadoCartaEspecifica("idUltimaCarta");

        SharedPreferences preferences = getSharedPreferences(ARQUIVO_CARTAS, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("idUltimaCarta", idUltimaCarta);
        editor.commit();
        Log.i(TAG,"\n\nid da última carta SALVA foi: "+idUltimaCarta);
    }

    private String recuperarDados(String nameCard){
        SharedPreferences preferences = getSharedPreferences(ARQUIVO_CARTAS, MODE_PRIVATE);
        String cardID = preferences.getString(nameCard,"Carta não cadastrada!");
        return cardID;
    }

    private void limpaDadoCartaEspecifica(String removerCarta){
        SharedPreferences preferences = getSharedPreferences(ARQUIVO_CARTAS, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Log.i(TAG,"\n\nRemoverCarta: "+removerCarta);
        editor.remove(removerCarta);
        editor.commit();
    }

    private void limpaDadosUltimaCartaLida(String idUltimaCarta){
        SharedPreferences preferences = getSharedPreferences(ARQUIVO_CARTAS, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(idUltimaCarta);
        editor.commit();
    }

    public void limpaTodosDadosSharedPreferences(){
        SharedPreferences preferences = getSharedPreferences(ARQUIVO_CARTAS, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
        N("Dados apagados com sucesso!");
    }

    /************************************* MÉTODOS DA TAG NFC ***********************************************/

    public void verificaNFC(){

        //Check for available NFC Adapter
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if (mNfcAdapter == null){
            N("O seu dispositivo não possui NFC!!!");
            finish();
            return;
        }

        if((!mNfcAdapter.isEnabled())){
            N("Ative o NFC do seu dispositivo!");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                Intent intent = new Intent(Settings.ACTION_NFC_SETTINGS);
                startActivity(intent);
            } else {
                Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                startActivity(intent);
            }
            Log.i(TAG,"Pede para ativar o NFC!");
        }

        else
            Log.i(TAG,"O NFC já está ativado!");
    }

    /*Lê o ID da TAG*/
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.i(TAG,"Carta lida");
        String tagContentID = ByteArrayToHexString(intent.getByteArrayExtra(NfcAdapter.EXTRA_ID)).toString();
        readNameCard(tagContentID);
    }

    /*Converte o ID da TAG de Hexadecimal para Decimal*/
    private String ByteArrayToHexString(byte [] inarray) {
        int i, j, in;
        String [] hex = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};
        String out= "";

        for(j = 0 ; j < inarray.length ; ++j)
        {
            in = (int) inarray[j] & 0xff;
            i = (in >> 4) & 0x0f;
            out += hex[i];
            i = in & 0x0f;
            out += hex[i];
        }
        return out;
    }

    /*Códigos extras da TAG NFC*/
    private void enableForegroundDispatchSystem(){
        Intent intent = new Intent(this,InfoActivity.class).addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);
        IntentFilter[] intentFilters = new IntentFilter[]{};

        mNfcAdapter.enableForegroundDispatch(this,pendingIntent,intentFilters,null);
    }

    private void disableForegroundDispatchSystem(){
        mNfcAdapter.disableForegroundDispatch(this);
    }


    /********************************* CICLO DE VIDA DA ACTIVITY ********************************************/
    @Override
    protected void onResume() {
        enableForegroundDispatchSystem();
        Log.i(TAG,"onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        disableForegroundDispatchSystem();
        Log.i(TAG,"onPause");
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        salvarDadosUltimaCartaLida("");
        Log.i(TAG,"onDestroy");
        super.onDestroy();
    }


    /******************************************** TARDIGRADE ***********************************************/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0){
            if (resultCode == RESULT_OK){
                String result = intent.getStringExtra("SCAN_RESULT");
                ICard card = deck.getCard(result);
                card.execute();
            }
        }
    }

}
