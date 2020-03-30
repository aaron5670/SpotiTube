package oose.dea.dao.mongodb;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import oose.dea.dao.IUserDAO;
import org.bson.Document;

import javax.enterprise.inject.Alternative;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

@Alternative
public class UserDAOMongoDB implements IUserDAO {

    MongoClient mongoClient = MongoClients.create();
    MongoDatabase database = mongoClient.getDatabase("spotitube");
    MongoCollection<Document> collection = database.getCollection("users");

    @Override
    public boolean isAuthenticated(String username, String password) {
        Document document = collection.find(and(eq("username", username), eq("password", password))).first();
        return document != null;
    }

    @Override
    public void updateUserTokenInDatabase(String username, String token) {
        collection.updateOne(
                eq("username", username),
                combine(set("token", token)));
    }
}
