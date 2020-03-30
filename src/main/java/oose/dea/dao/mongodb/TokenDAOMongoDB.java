package oose.dea.dao.mongodb;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import oose.dea.dao.ITokenDAO;
import oose.dea.domain.User;
import org.bson.Document;

import javax.enterprise.inject.Alternative;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

@Alternative
public class TokenDAOMongoDB implements ITokenDAO {

    MongoClient mongoClient = MongoClients.create();
    MongoDatabase database = mongoClient.getDatabase("spotitube");
    MongoCollection<Document> collection = database.getCollection("users");

    ObjectMapper mapper = new ObjectMapper();
    User user = new User();

    @Override
    public boolean verifyToken(String token) {
        Document document = collection.find(and(eq("token", token))).first();
        return document != null;
    }

    @Override
    public String getToken(String username) {
        Document document = collection.find(and(eq("username", username))).first();

        if (document != null) {
            try {
                user = mapper.readValue(document.toJson(), User.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        return user.token;
    }

    @Override
    public String getUsername(String token) {
        Document document = collection.find(and(eq("token", token))).first();

        if (document != null) {
            try {
                user = mapper.readValue(document.toJson(), User.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        return user.username;
    }
}