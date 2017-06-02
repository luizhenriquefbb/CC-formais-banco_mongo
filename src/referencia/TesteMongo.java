/*
 * Linkando mongo com java
 * Fonte: http://mongodb.github.io/mongo-java-driver/3.4/driver/getting-started/quick-start/
 *
 */
package referencia;

import com.mongodb.MongoClient;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gte;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.result.DeleteResult;
import java.util.ArrayList;
import org.bson.*;
//import static testemongo.editando_documento.collection_produtos;

/**
 * implementando algumas funções java-mongo. dentre elas:
 * -inserir produto
 * -inserir tag 
 * -linkar um tipo no outro (grafo)
 * @author Luiz & gabriel
 */
public class TesteMongo {

    //iniciar MongoClinet(ip, porta)
    static MongoClient m1 = new MongoClient("localhost", 27017);

    //MongoDataBase eh imutavel
    static MongoDatabase dataBase1 = m1.getDatabase("local");

    static MongoCollection<Document> collection_produtos = dataBase1.getCollection("Produtos");
    static MongoCollection<Document> collection_tags = dataBase1.getCollection("Tags");

    public static void main(String[] args) {
        
        ArrayList<String> campos = new ArrayList();
       
        //setando 'nome' como indice unique nas duas collections
        IndexOptions indexOptions = new IndexOptions().unique(true);
        collection_produtos.createIndex(Indexes.ascending("nome"), indexOptions);
        collection_tags.createIndex(Indexes.ascending("nome"), indexOptions);
        
        //========================
        //inserindo produtos
        campos.add("nome");
        campos.add("p1");
        inserirProduto(campos);
        
        campos.clear();
        
        campos.add("nome");
        campos.add("p2");
        inserirProduto(campos);
        
        campos.clear();
        
        //========================
        
        //========================
        //inserindo tags
        campos.add("nome");
        campos.add("t1");
        inserirTag(campos);

        campos.clear();

        campos.add("nome");
        campos.add("t2");
        inserirTag(campos);

        campos.clear();

        //========================
        
        //linkar tag produto
        linkando("p2", "t1");
        linkando("p1", "t1");
        linkando("p1", "t2");
        
        
        //excluirTudo();

        //fechar mongo
        m1.close();
        
        System.out.println("fim");

    }
    
    /**
     * inserindo um registro com varios campos
     * 
     * @param ArrayList 
     * @return null, se houver 'campo' sem 'valor'
     */
    static void inserirProduto(ArrayList campos) {
        Document doc1 = new Document();
        //so adiciona se tem campo - nome, ou seja, numero par de argumento
        if (campos.size() % 2 == 0) {
            for (int i = 0; i < campos.size(); i = i + 2) {
                doc1.append((String) campos.get(i), campos.get(i + 1));
            }
        } else {
            System.out.println("ERRO, Pares");
            return;
        }
        try{
            collection_produtos.insertOne(doc1);
        } catch (MongoWriteException e){ 
            System.out.println("PRODUTO JA EXISTE");
        }
        
    }
    
    /**
     * inserindo um registro com varios campos
     * 
     * @param ArrayList 
     * @return null, se houver 'campo' sem 'valor'
     */
    static void inserirTag(ArrayList campos) {
        Document doc1 = new Document();
        
        //so adiciona se tem campo - nome, ou seja, numero par de argumento
        if (campos.size() % 2 == 0) {
            for (int i = 0; i < campos.size(); i = i + 2) {
                doc1.append((String) campos.get(i), campos.get(i + 1));
            }
        } else {
            System.out.println("ERRO, Pares");
            return;
        }

        try{
            collection_tags.insertOne(doc1);
        } catch (MongoWriteException e){ 
            System.out.println("TAG JA EXISTE");
        }
    }

    /**
     * Deletar em massa: coloca os parametros (campo, valor_minimo) Parametros:
     * valor_minimo == deleta registros maiores ou igual ao valor minimo 
     */
    static void excluirTudo_produto(String campo, Object valor_minimo) {
        DeleteResult deleteResult = collection_produtos.deleteMany(gte(campo, valor_minimo)); //delentando tudo de 'pessoas'
        System.out.println("deletou " + deleteResult.getDeletedCount());
    }
    
    /**
     * Deletar em massa: coloca os parametros (campo, valor_minimo) Parametros:
     * valor_minimo == deleta registros maiores ou igual ao valor minimo 
     */
    static void excluirTudo_tags(String campo, Object valor_minimo) {
        DeleteResult deleteResult = collection_tags.deleteMany(gte(campo, valor_minimo)); //delentando tudo de 'pessoas'
        System.out.println("deletou " + deleteResult.getDeletedCount());
    }

    /**
     * adicionar array de tags em produto
     */
    static void linkarTag(String produto, String tag){
        
        
        //busca produto a ser atualizado
        Document myDoc = collection_produtos.find(eq("nome", produto)).first();
        
        //tratando erro
        if (myDoc == null){
            System.out.println("PRODUTO NAO ENCONTRADO");
            return;
        }
        
        //recupera tags
        ArrayList<String> tags = (ArrayList<String>) myDoc.get("tags");
        if (tags == null) { //se nao tinha nenhuma
            tags = new ArrayList<>();
        }
        
        tags.add(tag);
        collection_produtos.updateOne(eq("nome", produto), new Document("$set", new Document("tags", tags)));
    }
    
     /**
     * adicionar array de tags em produto
     */
    static void linkarProduto(String produto, String tag){
        
        //busca tag a ser atualizada
        Document myDoc = collection_tags.find(eq("nome", tag)).first();
        
        //tratando erro
        if (myDoc == null){
            System.out.println("TAG NAO ENCONTRADa");
            return;
        }
        
        //recupera produtos
        ArrayList<String> produtos = (ArrayList<String>) myDoc.get("produtos");
        if (produtos == null) { //se nao tinha nenhuma
            produtos = new ArrayList<>();
        }
        
        produtos.add(produto);
        collection_tags.updateOne(eq("nome", tag), new Document("$set", new Document("produtos", produtos)));
    }
    
    static void linkando(String produto, String tag){
        linkarProduto(produto, tag);
        linkarTag(produto, tag);
    }
}
