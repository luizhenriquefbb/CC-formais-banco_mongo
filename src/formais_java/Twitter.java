/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
    int retweet = 0;
    int favorits = 0;
    List<Twitter> coments = null; 
    Date date;
    String answer; // guarda o id da resposta

    //TODO: verificar hashtags
    public Twitter(String content) {
        this.content = content;
        date = new Date();
    }

    @Override
    public String toString() {
        return "Tweet\n" +
                "code = " + code +
                "\ncontent = " + content +
                "\nretweet = " + retweet +
                "\nfavorits = " + favorits +
                "\ncoments = " + coments +
                "\ndate = " + date +
                "\nanswer = " + answer;
    }
    
    
   

}
