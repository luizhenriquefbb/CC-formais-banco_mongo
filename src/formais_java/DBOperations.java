package formais_java;

import com.mongodb.MongoWriteException;
import static com.mongodb.client.model.Filters.gte;
import com.mongodb.client.result.DeleteResult;
import static formais_java.Formais_java.collection_users;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.Document;

/**
 * Metodo que insere um registro na collection de users
 * @author lhfba
 */
public class DBOperations {
    public static boolean inserindo(User user) throws MongoWriteException{
        Document doc1 = new Document();
        doc1.append("Nome",user.name).append("Telefone",user.telephone).append("User",user.user)
                 .append("Senha", user.password).append("email",user.email).append("País",user.country)
                 .append("Lingua", user.language).append("tweet", user.tweet).append("segue", user.follow)
                 .append("seguido", user.followed);
        collection_users.insertOne(doc1);
        return true;       
    }
    
    /**
     * método que cria x usuários e adiciona no banco
     * @param x  numero de usuarios
     * @return void
     * @author lhfba
     */
    public static void generateUsers(int x){
        long tempoInicial = System.currentTimeMillis();
        
        User tem_user;
        for (int i=0; i < x; i++){
            tem_user = (new User("nome"+(i), "@user"+(i)));
            try{
                inserindo(tem_user);
            }catch (MongoWriteException ex){
                Logger.getLogger(DBOperations.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("erro na insercao");
                System.exit(1); //tratando o erro, não vamos mais crashar mais
            }
        }
        
        System.out.println("o metodo de gerar " + x + " usuarios executou em " +(float)(  System.currentTimeMillis() - tempoInicial));
    }
    
    /**
     * @param nick
     * @return user ou null
     * @author lhfba
     */
    public static User findUserByNick(String nick){
        User user;
        Document doc1 = Formais_java.collection_users.find(gte("User", nick)).first();
        if (doc1 != null){
            user = new User((String) doc1.get("Nome"),
                    (String) doc1.get("User"),
                    (String) doc1.get("Senha"),
                    (String) doc1.get("Email"),
                    (String) doc1.get("País"),
                    (String) doc1.get("Língua"),
                    (String) doc1.get("Telefone"),
                    (ArrayList<Twitter>) doc1.get("tweet"),
                    (ArrayList<User>) doc1.get("segue"),
                    (ArrayList<User>) doc1.get("seguido"));
        }else {
            return null;
        }
        return user;
    }
    
    /**
     * 
     * @param name
     * @return user ou null
     * @author lhfba
     */
    public static User findUserByName(String name){
        User user;
        
        //busca
        //TODO: O método abaixo retorna uma lista. listá-la??
        //TODO: assim, seria melhor retornar a lista de usuários em vez de apenas o primeiro
        Document doc1 = Formais_java.collection_users.find(gte("Nome", name)).first();
        
        if (doc1 != null){
            user = new User((String) doc1.get("Nome"),
                    (String) doc1.get("User"),
                    (String) doc1.get("Senha"),
                    (String) doc1.get("Email"),
                    (String) doc1.get("País"),
                    (String) doc1.get("Língua"),
                    (String) doc1.get("Telefone"),
                    (ArrayList<Twitter>) doc1.get("tweet"),
                    (ArrayList<User>) doc1.get("segue"),
                    (ArrayList<User>) doc1.get("seguido"));
        } else{
            return null;
        }
        return user;
    }
    
    /**
     * 
     * @param email
     * @return user ou null
     * @author lhfba
     */
    public static User findUserByEmail(String email){
        User user;
        
        //busca
        Document doc1 = Formais_java.collection_users.find(gte("Email", email)).first();
        if (doc1 != null){
           user = new User((String) doc1.get("Nome"),
                    (String) doc1.get("User"),
                    (String) doc1.get("Senha"),
                    (String) doc1.get("Email"),
                    (String) doc1.get("País"),
                    (String) doc1.get("Língua"),
                    (String) doc1.get("Telefone"),
                    (ArrayList<Twitter>) doc1.get("tweet"),
                    (ArrayList<User>) doc1.get("segue"),
                    (ArrayList<User>) doc1.get("seguido"));
        } else {
            return null;
        }
        
        return user;
    }

    /**
     * 
     * @param telefone
     * @return user ou null
     * @author lhfba
     */
    public static User findUserByTelefone(String telefone){
        User user;
        
        Document doc1 = Formais_java.collection_users.find(gte("Telefone", telefone)).first();
        
        if (doc1 != null){
            user = new User((String) doc1.get("Nome"),
                    (String) doc1.get("User"),
                    (String) doc1.get("Senha"),
                    (String) doc1.get("Email"),
                    (String) doc1.get("País"),
                    (String) doc1.get("Língua"),
                    (String) doc1.get("Telefone"),
                    (ArrayList<Twitter>) doc1.get("tweet"),
                    (ArrayList<User>) doc1.get("segue"),
                    (ArrayList<User>) doc1.get("seguido"));
        }else {
            return null;
        }
     
        return user;
    }
    
    /**
     * Deletar em massa: coloca os parametros (campo, valor_minimo) Parametros:
     * valor_minimo == deleta registros maiores ou igual ao valor minimo 
     */
    static void clearCollectionUser() {
        DeleteResult deleteResult = collection_users.deleteMany(gte("Nome", "")); //delentando tudo de 'users' //TODO: TESTAR
        System.out.println("deletou " + deleteResult.getDeletedCount());
    }
    
    
}
