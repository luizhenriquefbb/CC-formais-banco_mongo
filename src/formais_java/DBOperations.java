/*
 * Desenvolvido para a cadeira de Linguagens formais, professor Andrei _______
 * 
 * 
 * Desenvolvedores:
 * * Luiz Henrique Freire Barros (email: luizhenriquefbb@gmail.com)
 * * Gabriel Belarmino (email: ____)
 * 2017
 */


package formais_java;


import com.mongodb.MongoWriteException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gte;
import com.mongodb.client.result.DeleteResult;
import static formais_java.Main.collection_users;
import static formais_java.Main.collection_twitteres;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 * Classe que lida com as operações com o BD
 * @author LuizHenrique & Gabriel
 */
public class DBOperations {
    /**
     * insere um user à classe de usuários
     * @param user
     * @return boolean - inserido com sucesso ou não
     */
    public static boolean inserindo(User user){
        Document doc1 = new Document();
        doc1.append("Nome",user.name).append("Telefone",user.telephone).append("User",user.user)
                 .append("Senha", user.password).append("email",user.email).append("País",user.country)
                 .append("Lingua", user.language).append("tweet", user.tweet).append("segue", user.follow)
                 .append("seguido", user.followed);
        
        try{
            collection_users.insertOne(doc1);
        }catch(MongoWriteException e){
            System.err.println("nick ja esta em uso");
            return false;
        }
        
        
        return true;       
    }
    
    /**
     * método que cria x usuários e adiciona no banco
     * @param x  numero de usuarios
     * @return void
     * @author LuizHenrique
     */
    public static void generateUsers(int x){
        
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
        
    }
    
    /**
     * um usuário "é seguido" por outro e esse outro "segue" o primeiro
     * @param User
     * @param User 
     */
    public static void generateFollower(User a,User b){
        
        collection_users.updateOne(eq("User", a.user), new Document("$addToSet", new Document("segue", b.user)));
        collection_users.updateOne(eq("User", b.user), new Document("$addToSet", new Document("seguido", a.user)));

    }

    /**
     * @param nick
     * @return user ou null
     * @author LuizHenrique
     */
    public static User findUserByNick(String nick){
        
        User user;
        Document doc1 = Main.collection_users.find(eq("User", nick)).first();
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
     * @return ArrayList(user) ou null
     * @author LuizHenrique
     */
    public static ArrayList<User> findUserByName(String name){
        
        User user;
        
        //busca
        //Document doc1 = Main.collection_users.find(eq("Nome", name)).first();
        FindIterable<Document> iterable = Main.collection_users.find(eq("Nome", name));
        ArrayList<User> users = new ArrayList<>();
        
        if (iterable == null){
            return null;
        }
        
        for(Document doc1 : iterable){
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
            
            users.add(user);
        
        }
        return users;
    }
    
    /**
     * 
     * @param email
     * @return user ou null
     * @author LuizHenrique
     */
    public static User findUserByEmail(String email){
        User user;
        
        //busca
        Document doc1 = Main.collection_users.find(eq("Email", email)).first();
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
     * @author LuizHenrique
     */
    public static User findUserByTelefone(String telefone){
        long tempoInicial = System.currentTimeMillis();
        User user;
        
        Document doc1 = Main.collection_users.find(eq("Telefone", telefone)).first();
        
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
            System.out.println("o metodo executou em "
                +(float)(  System.currentTimeMillis() - tempoInicial) + " milisegundos");
            return null;
        }
        System.out.println("o metodo executou em "
                +(float)(  System.currentTimeMillis() - tempoInicial) + " milisegundos");
        return user;
    }
    
    static Document userToDocument(User u){
        Document doc1 = new Document();
        doc1.append("Nome",u.name).append("Telefone",u.telephone).append("User",u.user)
                 .append("Senha", u.password).append("email",u.email).append("País",u.country)
                 .append("Lingua", u.language).append("tweet", u.tweet).append("segue", u.follow)
                 .append("seguido", u.followed);
        return doc1;
    }
    
    static Document tweetToDocument(Twitter t){
        Document doc1 = new Document();
        doc1.append("Conteudo",t.content).append("Em_Resposa_a",t.answer).append("Code",t.code)
                 .append("Data", t.date).append("Favoritos",t.favorits).append("Hastags", t.hashtags);
        return doc1;
    }

    /**
     * Deletar em massa: coloca os parametros (campo, valor_minimo) Parametros:
     * valor_minimo == deleta registros maiores ou igual ao valor minimo 
     */
    static void clearCollectionUser() {
        DeleteResult deleteResult = collection_users.deleteMany(gte("Nome", "")); //delentando tudo de 'users'
        System.out.println("deletou " + deleteResult.getDeletedCount());
    }
    
    /**
     * Deletar em massa: coloca os parametros (campo, valor_minimo) Parametros:
     * valor_minimo == deleta registros maiores ou igual ao valor minimo 
     */
    static void clearCollectionTwitts() {
        DeleteResult deleteResult = collection_twitteres.deleteMany(gte("Conteudo", "")); //delentando tudo de 'Twitter'
        System.out.println("deletou " + deleteResult.getDeletedCount());
    }

    /**
     * gera uma lista de tt pre determinados e desses, escolhe um aleatoriamente
     * para twittar
     * @param u 
     */
    public static void twittarRandom(User u){
        //lista de twitters aleatorios
        String[] tweets = {"#first Primeiro Tweet",
            
                           "#BodyBuilder #Partiu Academia!!",
                           
                           "#Trump MAKE AMERICA GREAT AGAIN! #elections #elections2017",
                           
                           "#elections This election is a total sham and a travesty. We are not a democracy! #Trump",
                           
                           "#NoDiet I have never seen a thin person drinking Diet Coke.",
                           
                           ".@katyperry must have been drunk when she married Russell Brand @rustyrockets – "
                            + " but he did send me a really nice letter of apology!",
                           
                           "Sorry losers and haters, but my I.Q. is one of the highest "
                            + "and you all know it! Please don’t feel so stupid or insecure,it’s not your fault",
                           
                            "Every time I speak of the haters and losers I do so with great love and affection."
                                + "They cannot help the fact that they were born fucked up!",
                            
                            "#HappyGivings Happy Thanksgiving to all--even the haters and losers!",
                            
                            "#GoPackGo",
                            
                            "#Happy2017 Happy New Year to all, including to my many"
                                   + "enemies and those who have fought me and lost"
                                            + "so badly they just don't know what to do. Love!",
                            
                            "#trump #trump #trump"
                
                
         };
        
        //pega um tt aleatorio
        String tweet = tweets[(int)Math.ceil(Math.random() * tweets.length)-1];
        Twitter t = new Twitter(tweet);
        
        //seta o codigo do twitter
        t.code = u.user+"-"+(u.tweet.size());
        
        Document doc1 = tweetToDocument(t);
        try{
            collection_twitteres.insertOne(doc1);
            Document a = collection_twitteres.find(eq("Code",t.code)).first();
            ObjectId obj = a.getObjectId("_id");
            if(obj!= null)
                collection_users.updateOne(eq("User", u.user), new Document("$addToSet", new Document("tweet", obj)));
            else
                System.err.println("falha nossa:Falha no update de Tweet");
        }catch (MongoWriteException e){
            System.err.println("falha nossa");
        }
       

        
    }
    
    /**
     * recebe u usuário e o twitter que ele escreveu. </p>
     * coloca essa "ligação" no banco
     * @param user
     * @param message 
     * @author luizHenrique
     */
    public static void twittar(User u, String message){
        
        Twitter t = new Twitter(message);
        
        //seta o codigo do twitter
        t.code = u.user+"-"+(u.tweet.size());
        
        Document doc1 = tweetToDocument(t);
        try{
            collection_twitteres.insertOne(doc1);
            Document a = collection_twitteres.find(eq("Code",t.code)).first();
            ObjectId obj = a.getObjectId("_id");
            if(obj!= null)
                collection_users.updateOne(eq("User", u.user), new Document("$addToSet", new Document("tweet", obj)));
            else
                System.err.println("falha nossa:Falha no update de Tweet");
        }catch (MongoWriteException e){
            System.err.println("falha nossa");
        }
       

        
    }
    
    
    /**
     * 
     * @param hastag
     * @return ArrayList<Twitter> ou null
     * 
     */
    public static ArrayList<Twitter> findHastags(String hashtag){
        ArrayList<Twitter> tweets = new ArrayList<>();
        MongoCursor<Document> cursor = collection_twitteres.find(eq("Hashtags", hashtag)).iterator();
        Document doc = new Document();
        while (cursor.hasNext()) {
            doc = cursor.next();

            //String code, String content, Date date, String answer, ArrayList<String> hashtags
            tweets.add(new Twitter(
                    (String) doc.get("Code"),
                    (String) doc.get("Conteudo"),
                    (Date) doc.get("Data"),
                    (String) doc.get("Em_resposta_a"),
                    (ArrayList<String>) doc.get("Hashtags")
            ));

        }
        
        if (tweets.size() > 0)
            return tweets;
        else
            return null;
    }
    
    
    
}
