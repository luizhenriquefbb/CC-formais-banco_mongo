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
import java.util.Scanner;
import org.bson.Document;


/**
 *
 * @author lhfba
 */
public class Formais_java {

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
//        buildCollectionTwitter();
        
        int x; // ler qnt de usuários
        User user_obj; //resultado dos retornos do menu
        String name, user, password, email, country, language, telephone; //case2
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
                    DBOperations.generateUsers(x);
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
                    
                    DBOperations.inserindo(new User(name, user, password, email, country, language, telephone));
                    break;
                    
                case 3: //busca por telefone
                    //System.out.println("Em densenvolvimento...");
                    System.out.println("Digite telefone a ser buscado:");
                    telephone = read.nextLine();
                    user_obj = DBOperations.findUserByTelefone(telephone);
                    
                    if (user_obj != null){
                        System.out.println("retorno da busca:\n"
                                + user_obj);
                    }else{
                        System.out.println("nenhum encontrado encontrado");
                    }
                    break;
                case 4: //remover usuário
                    System.out.println("Em densenvolvimento...");
                    break;
                    
                case 5: //limpar tabela de usuario
                    DBOperations.clearCollectionUser();
                    break;
                    
                case 6: //twittar
                    System.out.println("Em densenvolvimento...");
                    break;
                    
                case 7: //Buscar uma hashtag
                    System.out.println("Em densenvolvimento...");
                    break;
            }
        }while (op != 0);
        
        
        
        
        
//        long tempoInicial = System.currentTimeMillis();
//        System.out.println("o metodo executou em " +(float)(  System.currentTimeMillis() - tempoInicial));
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
        collection_twitteres.createIndex(Indexes.ascending("_id"), indexOptions);
    }
      
    
    private static String menu(){
        return(
                  "======================\n"
                + "0 - sair\n"
                + "1 - gerar x usuários genericos\n"
                + "2 - adicionar um usuario\n"
                + "3 - buscar usuario por telefone\n"
                + "4 - remover usuario\n"
                + "5 - limpar tabela de usuarios\n"
                + "6 - twettar\n"
                + "7 - Buscar uma hashtag\n"
                + "=====================");
        
    }
    
//    /**
//     * método que cria x usuários
//     * @return list_of_users
//     */
//    private static ArrayList<User> getUsers_old(){
//        ArrayList<User> users_list = new ArrayList<>();
//	for (int i=0; i < 500; i++)
//            users_list.add(new User("nome"+(i), "@user"+(i)));
//
//	return users_list;
//    }
    
}
