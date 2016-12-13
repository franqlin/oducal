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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import sun.security.x509.OIDMap;

/**
 *
 * @author fssantos
 */
public class OduCal {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        String data_nascimento = "25/08/1946";
        int frente,nunca,esquerda,direita=0;
        String[] data=data_nascimento.split("/");
        String dd = data[0];
        String mm = data[1];
        String[] aaaa= quebraAno(data[2]);
        
        
        Properties properties = new Properties();
        String arquivo = "C:\\Users\\fssantos\\Documents\\NetBeansProjects\\mprj-adocao\\adocao-frontend\\oduCal\\src\\odu.properties";
        InputStream inputStream = new FileInputStream(arquivo) ;
        properties.load(inputStream);
        //System.err.println(properties.getProperty("8"));
         String[] numerosSeparados={dd,mm,aaaa[0],aaaa[1]};
         Integer[] oduFrenteNuca = calculaOduFrenteNuca(numerosSeparados);
          
          Integer oduDireita = somaOdus(oduFrenteNuca[0],oduFrenteNuca[1]);
          Integer oduEsquerda = somaOdus(oduFrenteNuca[0],oduFrenteNuca[1],oduDireita);
          Integer oduCentro = somaOdus(oduFrenteNuca[0],oduFrenteNuca[1],oduDireita,oduEsquerda);
          Integer oduNascimento = calculaNascimento(numerosSeparados);
          
          
          //printTelaInicial(properties.getProperty(oduNascimento+"$"));
          System.err.print("Data Nascimento: "+ data_nascimento+"\n");
          //printTelaInicial("ODU");
          
          
          
          System.out.println("Nascimento: "+oduNascimento); 
          System.out.println(properties.getProperty(oduNascimento.toString()));
          System.out.println("Frente: "+oduFrenteNuca[1]);
          System.out.println(properties.getProperty(oduFrenteNuca[1].toString()));
          System.out.println("Costas: "+oduFrenteNuca[0]);
          System.out.println(properties.getProperty(oduFrenteNuca[0].toString()));
          System.out.println("Direita: "+oduDireita); 
          System.out.println(properties.getProperty(oduDireita.toString()));
          System.out.println("Esquerda: "+oduEsquerda); 
          System.out.println(properties.getProperty(oduEsquerda.toString()));
          System.out.println("Centro: "+oduCentro); 
          System.out.println(properties.getProperty(oduCentro.toString()));
          
          

    }
    
    private static String[] quebraAno(String ano){
        String[] a = {ano.substring(0, 2),ano.substring(2, 4)};
        return a;
    }
    private  static  Integer calculaNascimento(String[] numeros){
            Integer oduFrente=0;
            Integer oduCostas=0;
            for (String n: numeros){
               oduFrente += new Integer(n.substring(1));
               oduCostas+= new Integer(n.substring(0,1));
            }
        return verificaOduMaiorDezesseis(oduCostas+oduFrente);
    }
    
    public static  Integer[] calculaOduFrenteNuca(String[] numeros){
            Integer oduFrente=0;
            Integer oduCostas=0;
            for (String n: numeros){
               oduFrente += new Integer(n.substring(1));
               oduCostas+= new Integer(n.substring(0,1));
            }
        oduCostas = verificaOduMaiorDezesseis(oduCostas);
        oduFrente =verificaOduMaiorDezesseis(oduFrente);
        
        return new Integer[]{oduCostas,oduFrente};
    }
    public static Integer  verificaOduMaiorDezesseis(Integer odu){
        if(odu<=16){
            return odu;
        }
        else{
            Integer colunaDireita = new Integer(odu.toString().substring(1));
            Integer colunaEsquerda = new Integer(odu.toString().substring(0,1));
            odu = new Integer(colunaDireita+colunaEsquerda); 
            return odu;
          }
        
        
    }

    private static Integer  somaOdus(Integer... odus ){
        Integer soma= 0;
        for(Integer odu : odus){
            soma+=odu;
        }
        return verificaOduMaiorDezesseis(soma);
    }
    
    private static void  printTelaInicial(String mensagen){

        int width = 100;
	int height = 30;

        //BufferedImage image = ImageIO.read(new File("/Users/mkyong/Desktop/logo.jpg"));
	BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	Graphics g = image.getGraphics();
	g.setFont(new Font("SansSerif", Font.BOLD, 24));

	Graphics2D graphics = (Graphics2D) g;
	graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	graphics.drawString(mensagen, 10, 20);

	//save this image
	//ImageIO.write(image, "png", new File("/users/mkyong/ascii-art.png"));

	for (int y = 0; y < height; y++) {
		StringBuilder sb = new StringBuilder();
		for (int x = 0; x < width; x++) {

			sb.append(image.getRGB(x, y) == -16777216 ? " " : "$");
				
		}

		if (sb.toString().trim().isEmpty()) {
			continue;
		}

		System.out.println(sb);
	}

        
        
    }
    
}
