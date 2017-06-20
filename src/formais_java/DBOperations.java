package formais_java;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoWriteException;
import com.mongodb.client.FindIterable;
import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.result.DeleteResult;
import static formais_java.Main.collection_twitteres;
import static formais_java.Main.collection_users;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.Document;
import org.bson.types.ObjectId;

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
    
    public static void generateFollower(User a,User b){
        
        collection_users.updateOne(gte("User", a.user), new Document("$addToSet", new Document("segue", b.user)));
        collection_users.updateOne(gte("User", b.user), new Document("$addToSet", new Document("seguido", a.user)));

    }
    
    
    //TODO: tratar retorno null
    public static User findUserByNick(String nick){
        User user;
        Document doc1 = Main.collection_users.find(eq("User", nick)).first();
        System.out.println(doc1);
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
        return user;
    }
    
    public static ArrayList<User> findUserByName(String name){
        ArrayList<User> list = new ArrayList<>();
        //TODO: ver a lista
        FindIterable<Document> doc = Main.collection_users.find(eq("Nome", name));
        
        for (Document doc1 : doc){
        
       list.add(new User((String) doc1.get("Nome"),
                (String) doc1.get("Telefone"),
                (String) doc1.get("User"),
                (String) doc1.get("Senha"),
                (String) doc1.get("Email"),
                (String) doc1.get("País"),
                (String) doc1.get("Língua"),
                (ArrayList<Twitter>) doc1.get("tweet"),
                (ArrayList<User>) doc1.get("segue"),
                (ArrayList<User>) doc1.get("seguido")));
        }
        return list;
    }
    
    public static User findUserByEmail(String email){
        User user;
        Document doc1 = Main.collection_users.find(gte("Email", email)).first();
        
        user = new User((String) doc1.get("Nome"),
                (String) doc1.get("Telefone"),
                (String) doc1.get("User"),
                (String) doc1.get("Senha"),
                (String) doc1.get("Email"),
                (String) doc1.get("País"),
                (String) doc1.get("Língua"),
                (ArrayList<Twitter>) doc1.get("tweet"),
                (ArrayList<User>) doc1.get("segue"),
                (ArrayList<User>) doc1.get("seguido"));
        
        return user;
    }
    
    public static User findUserByTelefone(String telefone){
        User user;
        Document doc1 = Main.collection_users.find(gte("Telefone", telefone)).first();
        
        user = new User((String) doc1.get("Nome"),
                (String) doc1.get("Telefone"),
                (String) doc1.get("User"),
                (String) doc1.get("Senha"),
                (String) doc1.get("Email"),
                (String) doc1.get("País"),
                (String) doc1.get("Língua"),
                (ArrayList<Twitter>) doc1.get("tweet"),
                (ArrayList<User>) doc1.get("segue"),
                (ArrayList<User>) doc1.get("seguido"));
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
    
    static void clearCollectionTwitts(){
        DeleteResult deleteResult = collection_twitteres.deleteMany(gte("Code", "")); //delentando tudo de 'users' //TODO: TESTAR
        System.out.println("deletou " + deleteResult.getDeletedCount());
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
                 .append("Data", t.date).append("Favoritos",t.favorits).append("Retweets",t.retweet);
        return doc1;
    }
    
    static Twitter documentToTwitter(Document doc){
        Twitter tw = new Twitter((String) doc.get("Conteudo"));
        
        tw.code = (String) doc.get("Code");
        tw.date = (Date) doc.get("Data");
        tw.retweet = (int) doc.get("Retweets");
        tw.favorits = (int) doc.get("Favoritos");
        tw.answer = (String) doc.get("Em_Resposta_a");
        
        return tw;
    }
    
    public static void Twittar_old(User u){
        //insere apenas o ID do twt no campo de usuário
        String[] tweets = {"#first Primeiro Tweet",
                           "#BodyBuilder Partiu Academia!!",
                           "#Trump MAKE AMERICA GREAT AGAIN! ",
                           "#elections This election is a total sham and a travesty. We are not a democracy!",
                           "#NoDiet I have never seen a thin person drinking Diet Coke.",
                           ".@katyperry must have been drunk when she married Russell Brand @rustyrockets – "
                            + " but he did send me a really nice letter of apology!",
                           "Sorry losers and haters, but my I.Q. is one of the highest "
                            + "and you all know it! Please don’t feel so stupid or insecure,it’s not your fault",
                            "Every time I speak of the haters and losers I do so with great love and affection. They cannot help the fact that they were born fucked up!",
                            "#HappyGivings Happy Thanksgiving to all--even the haters and losers!",
                            "#Happy2017 Happy New Year to all, including to my many enemies and those who have fought me and lost so badly they just don't know what to do. Love!"
         };
        System.out.println("Size = "+u.tweet.size());
        String tweet = tweets[(int)Math.ceil(Math.random() * tweets.length)-1];
        System.out.println(tweet);
        Twitter t = new Twitter(tweet);
        t.code = u.user+"-"+(u.tweet.size());
        System.out.println(t.code);
        Document doc1 = tweetToDocument(t);
        collection_twitteres.insertOne(doc1);
        
        Document a = collection_twitteres.find(eq("Code",t.code)).first();
        ObjectId obj = a.getObjectId("_id");
        collection_users.updateOne(gte("User", u.user), new Document("$addToSet", new Document("tweet", obj)));

        
    }
    
    public static void twittarRandom(User u){
        String[] tweets = {"#first Primeiro Tweet",
                           "#BodyBuilder Partiu Academia!!",
                           "#Trump MAKE AMERICA GREAT AGAIN! ",
                           "#elections This election is a total sham and a travesty. We are not a democracy!",
                           "#NoDiet I have never seen a thin person drinking Diet Coke.",
                           ".@katyperry must have been drunk when she married Russell Brand @rustyrockets – "
                            + " but he did send me a really nice letter of apology!",
                           "Sorry losers and haters, but my I.Q. is one of the highest "
                            + "and you all know it! Please don’t feel so stupid or insecure,it’s not your fault",
                            "Every time I speak of the haters and losers I do so with great love and affection. They cannot help the fact that they were born fucked up!",
                            "#HappyGivings Happy Thanksgiving to all--even the haters and losers!",
                            "#Happy2017 Happy New Year to all, including to my many enemies and those who have fought me and lost so badly they just don't know what to do. Love!"
         };
        System.out.println("Size = "+u.tweet.size());
        //pegar um twt aleatorio
        String tweet = tweets[(int)Math.ceil(Math.random() * tweets.length)-1];
        System.out.println(tweet);
        
        Twitter t = new Twitter(tweet);
        t.code = u.user+"-"+(u.tweet.size());
        System.out.println(t.code);
        
        Document doc1 = tweetToDocument(t);
        collection_twitteres.insertOne(doc1);

        //Busca por campo do array tweet
       // db.user.find({"User":"@user0","tweet.Code":"@user0-6"},{"tweet.$.Favoritos":1})
        System.out.println("");
        Document a = collection_twitteres.find(eq("Code",t.code)).first();  
        collection_users.updateOne(gte("User", u.user), new Document("$addToSet", new Document("tweet", a)));
        u.tweet.add(t);
        
        

        
    }
    
}
