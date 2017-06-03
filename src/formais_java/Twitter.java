/*
 * Desenvolvio para a cadeira de Linguagens formais, professor Andrei _______
 * 
 * 
 * Desenvolvedores:
 * * Luiz Henrique Freire Barros (email: luizhenriquefbb@gmail.com)
 * * Gabriel Belarmino (email: ____)
 * 2017
 */
package formais_java;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author lhfba
 */
public class Twitter {
    String code, content;
    int recontenteet = 0;
    int favorits = 0;
    List<Twitter> coments = null; 
    Date date;
    String answer; // guarda o id da resposta
    ArrayList<String> hastags;

    
    public Twitter(String content) {
        this.content = content;
        date = new Date();
        splitHastags(content);
        this.hastags = new ArrayList<>();
    }

    public Twitter(String code, String content, Date date, String answer, ArrayList<String> hastags) {
        this.code = code;
        this.content = content;
        this.date = date;
        this.hastags = hastags;
    }
    
    
    
    private void splitHastags (String content){
        String[] n_hastags = content.split("#[^\\s]+");
        
        String n_content = content;
        for (String s : n_hastags){
            n_content = n_content.replaceAll(s, "");
        }
        
        String[] hashs = n_content.split("#");
        
        for (String s : hashs){
            hastags.add(s);
        }
        
        
    }

    @Override
    public String toString() {
        String hash = "";
        for (String s : this.hastags){
            hash+=s + "\n";
        }
        
        return "Tweet\n" +
                "code = " + code +
                "\ncontent = " + content +
                "\nrecontenteet = " + recontenteet +
                "\nfavorits = " + favorits +
                "\ncoments = " + coments +
                "\ndate = " + date +
                "\nanswer = " + answer +
                "\nhastags = " + hash;
    }
    
    
   

}
