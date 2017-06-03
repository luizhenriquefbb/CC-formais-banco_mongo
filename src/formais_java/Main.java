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

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import java.util.ArrayList;
import java.util.Scanner;
import org.bson.Document;

/**
 *
 * @author LuizHenrique
 */
public class Main {

    //iniciar MongoClinet(ip, porta)
    static MongoClient m1 = new MongoClient("localhost", 27017);

    //MongoDataBase eh imutavel, por isso static
    static MongoDatabase dataBase1 = m1.getDatabase("twitter");

    //abrindo documentos
    static MongoCollection<Document> collection_users = dataBase1.getCollection("users");
    static MongoCollection<Document> collection_twitteres = dataBase1.getCollection("twitter");
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int op;
        buidCollectionUser();
        buildCollectionTwitter();
        
        long tempoInicial;
        User user_obj; //resultado dos retornos do menu
        
        //leituras de teclado
        int x;
        String name, nick, user, password, email, country, language, telephone; 
        String message, hashtag;
        do{
            System.out.println(menu());
            Scanner read = new Scanner(System.in);
            op = Integer.parseInt(read.nextLine());
            
            switch(op){
                case 0:
                    break;
                    
                case 1: //gerar x usuários genéricos automaticamente
                    System.out.println("Quantos usuários genéricos criar?");
                    x = read.nextInt();
                    
                    tempoInicial = System.currentTimeMillis();
                    
                    DBOperations.generateUsers(x);
                    
                    System.out.println("o metodo gerou esses usuarios em " +(float)(  System.currentTimeMillis() - tempoInicial) + 
                            "milisegudos");
                    break;
                     
                case 2: //gerar um usuario personalizado
                    System.out.println("digite nome do usuário");
                    name = read.nextLine();
                    
                    System.out.println("digite user do usuário");
                    user = read.nextLine();
                    
                    System.out.println("digite senha do usuário");
                    password = read.nextLine();
                    
                    System.out.println("digite email do usuário");
                    email = read.nextLine();
                    System.out.println("digite pais do usuário");
                    country = read.nextLine();
                    
                    System.out.println("digite lingua do usuário");
                    language = read.nextLine();
                    
                    System.out.println("digite telefone do usuário");
                    telephone = read.nextLine();
                    
                    tempoInicial = System.currentTimeMillis();
                    
                    DBOperations.inserindo(new User(name, user, password, email, country, language, telephone));
                    
                    System.out.println("o metodo gerou esse usuario em " +(float)(  System.currentTimeMillis() - tempoInicial) + 
                            "milisegudos");
                    break;
                    
                case 3: //busca por nome
                    System.out.println("Digite nome a ser buscado:");
                    name = read.nextLine();
                    tempoInicial = System.currentTimeMillis();
                    
                    ArrayList<User> search = DBOperations.findUserByName(name);
                    
                    System.out.println("o metodo executou em " +(float)(  System.currentTimeMillis() - tempoInicial) + " milisegundos");
                    
                    System.out.println("encontrado "+search.size()+" usuários\n");
                    for (User u: search){
                        System.out.println(u);
                    }
                    break;
                    
                case 4: //busca por telefone
                    System.out.println("Digite telefone a ser buscado:");
                    telephone = read.nextLine();
                    tempoInicial = System.currentTimeMillis();
                    
                    user_obj = DBOperations.findUserByTelefone(telephone);
                    System.out.println("o metodo executou em " +(float)(  System.currentTimeMillis() - tempoInicial) + " milisegundos");
                    if (user_obj != null){
                        System.out.println("retorno da busca:\n"
                                + user_obj);
                    }else{
                        System.out.println("nenhum encontrado");
                    }
                    break;
                    
                    
                case 5: //bus por nick
                    System.out.println("Digite nick a ser buscado:");
                    nick = read.nextLine();
                    tempoInicial = System.currentTimeMillis();
                    
                    user_obj = DBOperations.findUserByNick(nick);
                    System.out.println("o metodo executou em " +(float)(  System.currentTimeMillis() - tempoInicial) + " milisegundos");
                    if (user_obj != null){
                        System.out.println("retorno da busca:\n"
                                + user_obj);
                    }else{
                        System.out.println("nenhum encontrado");
                    }
                    break;
                    
                    
                case 6: //buscar por email
                    System.out.println("Digite email a ser buscado:");
                    email = read.nextLine();
                    tempoInicial = System.currentTimeMillis();
                    
                    user_obj = DBOperations.findUserByEmail(email);
                    System.out.println("o metodo executou em " +(float)(  System.currentTimeMillis() - tempoInicial) + " milisegundos");
                    if (user_obj != null){
                        System.out.println("retorno da busca:\n"
                                + user_obj);
                    }else{
                        System.out.println("nenhum encontrado");
                    }
                    break;
                    
                case 7: //remover usuário
                    System.out.println("Em densenvolvimento...");
                    break;
                    
                case 8: //limpar tabela de usuario
                    tempoInicial = System.currentTimeMillis();
                    
                    DBOperations.clearCollectionUser();
                    System.out.println("o metodo executou em " +(float)(  System.currentTimeMillis() - tempoInicial) + " milisegundos");
                    break;
                    
                case 9: //limpar tabela de twitter
                    tempoInicial = System.currentTimeMillis();
                    
                    DBOperations.clearCollectionTwitts();
                    System.out.println("o metodo executou em " +(float)(  System.currentTimeMillis() - tempoInicial) + " milisegundos");
                    break;
                    
                case 10: //twittar
                    System.out.println("digite o user que twitta");
                    nick = read.nextLine();
                    user_obj = DBOperations.findUserByNick(nick);
                    if (user_obj == null){
                        System.err.println("usuario nao existe");
                        break;
                    }
                    
                    System.out.println("digite o TWITTER");
                    message = read.nextLine();
                    //DBOperations.twittarRandom(u);
                    DBOperations.twittar(user_obj,message);
                    break;
                    
                case 11://Twittar Massivo
                    System.out.println("Quantos Usuarios");
                    int n_user = read.nextInt();
                    System.out.println("Quantos Tweets");
                    int n_tweets = read.nextInt();
                    
                    tempoInicial = System.currentTimeMillis();
                    for(int i = 0;i < n_user; i++){
                        user_obj = DBOperations.findUserByNick("@user"+i);
                        for(int j = 0; j < n_tweets; j++){
                            DBOperations.twittarRandom(user_obj);
                        }     
                    }
                    System.out.println("o metodo executou em " +(float)(  System.currentTimeMillis() - tempoInicial) + " milisegundos");
                    break;
                    
                case 12: //Buscar uma hashtag
                    System.out.println("Em densenvolvimento..."); //TODO
                    System.out.println("Digite a hashtag a ser buscada");
                    hashtag = read.nextLine();
                    ArrayList<Twitter> search_twitter = DBOperations.findHastags(hashtag);
                    if (search_twitter == null){
                        System.out.println("hashtag nao encontrada");
                        break;
                    }
                    
                    System.out.println("foram encontradas "+ search_twitter.size() + 
                            " twitters com essa hash");
                    for (Twitter t : search_twitter){
                        System.out.println(t);
                    }
                    
                    
                    break;
                    
                    
                case 13: //usuario seguir outro
                    User a = DBOperations.findUserByNick("@user0");
                    User b = DBOperations.findUserByNick("@user1");
                    DBOperations.generateFollower(a,b);
                    break;
            }
        }while (op != 0);
        
        
//        long tempoInicial = System.currentTimeMillis();
//        System.out.println("o metodo executou em " +(float)(  System.currentTimeMillis() - tempoInicial) + " milisegundos");
    }
    
 
    /**Configura a collection*/
    private static void buidCollectionUser(){
        //setando 'user' como indice unique
        IndexOptions indexOptions = new IndexOptions().unique(true);
        collection_users.createIndex(Indexes.ascending("User"), indexOptions);
        
    }
    
    /**Configura a collection*/
    private static void buildCollectionTwitter(){
        //setando '_id' como indice unique
        IndexOptions indexOptions = new IndexOptions().unique(true);
        collection_twitteres.createIndex(Indexes.ascending("Code"), indexOptions);
    }
      
    private static String menu(){
        int i=0;
        return(
                  "\n\n\n\n\n======================\n"
                + (i++) +" - sair\n"
                + (i++) +" - gerar x usuários genericos\n"
                + (i++) +" - adicionar um usuario\n\n"
                
                + (i++) +" - buscar por nome\n"
                + (i++) +" - buscar usuario por telefone\n"
                + (i++) +" - buscar por nick\n"
                + (i++) +" - buscar por email\n\n"
                
                + (i++) +" - remover usuario\n"
                + (i++) +" - limpar tabela de usuarios\n"
                + (i++) +" - Limpar tabelas de twitter\n\n"
                
                + (i++) +" - twittar\n"
                + (i++) +" - Varios Tweets\n"
                + (i++) +" - Buscar uma hashtag\n\n"
                
                + (i++) +" - usuario seguir outro\n"
                + "=====================");
        
    }
    

    
}
