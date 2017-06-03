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

/**
 *
 * @author LuizHenrique
 */
public class User {
    String name, user, password, email, country, language, telephone;
    ArrayList<Twitter> tweet = new ArrayList<>();
    ArrayList<User> follow = new ArrayList<>();
    ArrayList<User> followed = new ArrayList<>();
    
    public User(String name,String user,String password,String email,
           String country,String language,String telephone){
        this.name = name;
        this.user = user;
        this.password = password;
        this.email = email;
        this.country = country;
        this.language = language;
        this.telephone = telephone;
    }

    User(String name, String user) {
        this.name = name;
        this.user = user;
        this.password = "123";
        this.email = "email_generico";
        this.country = "Brasil";
        this.language = "PT-BR";
        this.telephone = "";
    }

     public User(String name,String user,String password,String email,
           String country,String language,String telephone,
           ArrayList<Twitter> twitter, ArrayList<User> follow, ArrayList<User> followed){
        this.name = name;
        this.user = user;
        this.password = password;
        this.email = email;
        this.country = country;
        this.language = language;
        this.telephone = telephone;
        this.tweet = twitter;
        this.follow = follow;
        this.followed = followed;
    }
    
    
    
    @Override
    public String toString(){
        return "name = " + this.name + "\n" +
		"user = " + this.user + "\n" +
		"password = " + this.password + "\n" +
		"email = " + this.email + "\n" +
		"country = " + this.country + "\n" +
		"language = " + this.language + "\n" +
		"telephone = " + this.telephone + "\n" //+
//		"tweet = " + this.tweet + "\n" +
//		"follow = " + this.follow + "\n" +
//		"followed = " + this.followed + "\n"
                ;
    }
    
    
}
