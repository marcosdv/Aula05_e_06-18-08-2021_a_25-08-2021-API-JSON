package com.example.aula05_18_08_2021_apijson;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class BuscaDadosWeb extends AsyncTask<String, Void, ArrayList<Emissora>> {

    @Override
    protected ArrayList<Emissora> doInBackground(String... parametros) {
        ArrayList<Emissora> listaRetorno = new ArrayList<>();

        //pegando o link da API que veio por parametro
        String link = parametros[0];

        try {
            //criando uma URL valida a partir do link
            URL url = new URL(link);

            //criando a conexao com a URL
            URLConnection connection = url.openConnection();

            //cria um espaço na memoria para salvar os dados da web (API)
            InputStreamReader inputStreamReader =
                    new InputStreamReader(connection.getInputStream());

            //classe que permite manipulador os dados salvos na memoria
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String linha;

            while ((linha = bufferedReader.readLine()) != null) {
                //criando um array de objetos JSON com o retorno da web
                JSONArray ja = new JSONArray(linha);

                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jo = ja.getJSONObject(i);

                    //criando a emissora
                    Emissora emissora = new Emissora();

                    //pegando os valores do JSON e salvando nos campos da classe
                    emissora.codigo = jo.getInt("Emi_Codigo");
                    emissora.nome = jo.getString("Emi_Nome");
                    emissora.logo = jo.getString("Emi_Logo");

                    emissora.logo = emissora.logo.replace("~/",
                            "http://controle.mdvsistemas.com.br/");

                    //adiconando a emissora na lista de retorno
                    listaRetorno.add(emissora);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return listaRetorno;
    }
}