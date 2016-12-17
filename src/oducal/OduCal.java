/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oducal;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 *
 * @author fssantos
 */
public class OduCal {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {

        BufferedReader readIn = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Data de Nascimento: __/__/____");

        String data_nascimento = readIn.readLine();

        int frente, nunca, esquerda, direita = 0;
        String[] data = data_nascimento.split("/");
        String dd = data[0];
        String mm = data[1];
        String[] aaaa = quebraAno(data[2]);


        Properties properties = new Properties();
        //String arquivo = "odu.properties";
        InputStream inputStream = OduCal.class.getClassLoader().getResourceAsStream("odu.properties"); 
        //InputStream inputStream = new FileInputStream(arquivo);
        properties.load(inputStream);
        //System.err.println(properties.getProperty("8"));
        String[] numerosSeparados = {dd, mm, aaaa[0], aaaa[1]};
        Integer[] oduFrenteNuca = calculaOduFrenteNuca(numerosSeparados);

        Integer oduDireita = somaOdus(oduFrenteNuca[0], oduFrenteNuca[1]);
        Integer oduEsquerda = somaOdus(oduFrenteNuca[0], oduFrenteNuca[1], oduDireita);
        Integer oduCentro = somaOdus(oduFrenteNuca[0], oduFrenteNuca[1], oduDireita, oduEsquerda);
        Integer oduNascimento = calculaNascimento(numerosSeparados);


        //printTelaInicial(properties.getProperty(oduNascimento+"$"));
        System.err.print("Data Nascimento: " + data_nascimento + "\n");
        //printTelaInicial("ODU");



        System.out.println("Nascimento-->: " + oduNascimento);
        System.out.println(properties.getProperty(oduNascimento.toString()));
        System.out.println("Frente-->: " + oduFrenteNuca[1]);
        System.out.println(properties.getProperty(oduFrenteNuca[1].toString()));
        System.out.println("Costas-->: " + oduFrenteNuca[0]);
        System.out.println(properties.getProperty(oduFrenteNuca[0].toString()));
        System.out.println("Direita-->: " + oduDireita);
        System.out.println(properties.getProperty(oduDireita.toString()));
        System.out.println("Esquerda-->: " + oduEsquerda);
        System.out.println(properties.getProperty(oduEsquerda.toString()));
        System.out.println("Centro-->: " + oduCentro);
        System.out.println(properties.getProperty(oduCentro.toString()));



    }

    private static String[] quebraAno(String ano) {
        String[] a = {ano.substring(0, 2), ano.substring(2, 4)};
        return a;
    }

    private static Integer calculaNascimento(String[] numeros) {
        Integer oduFrente = 0;
        Integer oduCostas = 0;
        for (String n : numeros) {
            oduFrente += new Integer(n.substring(1));
            oduCostas += new Integer(n.substring(0, 1));
        }
        return verificaOduMaiorDezesseis(oduCostas + oduFrente);
    }

    public static Integer[] calculaOduFrenteNuca(String[] numeros) {
        Integer oduFrente = 0;
        Integer oduCostas = 0;
        for (String n : numeros) {
            oduFrente += new Integer(n.substring(1));
            oduCostas += new Integer(n.substring(0, 1));
        }
        oduCostas = verificaOduMaiorDezesseis(oduCostas);
        oduFrente = verificaOduMaiorDezesseis(oduFrente);

        return new Integer[]{oduCostas, oduFrente};
    }

    public static Integer verificaOduMaiorDezesseis(Integer odu) {
        if (odu <= 16) {
            return odu;
        } else {
            Integer colunaDireita = new Integer(odu.toString().substring(1));
            Integer colunaEsquerda = new Integer(odu.toString().substring(0, 1));
            odu = new Integer(colunaDireita + colunaEsquerda);
            return odu;
        }


    }

    private static Integer somaOdus(Integer... odus) {
        Integer soma = 0;
        for (Integer odu : odus) {
            soma += odu;
        }
        return verificaOduMaiorDezesseis(soma);
    }

  
}
