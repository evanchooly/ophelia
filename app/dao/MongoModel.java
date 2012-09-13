package dao;

import com.mongodb.Mongo;
import com.mongodb.WriteConcern;
import org.bson.types.ObjectId;
import org.codehaus.jackson.annotate.JsonProperty;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

import java.net.UnknownHostException;

public class MongoModel<T> {

    @JsonProperty("_id")
    private ObjectId _id;

    public ObjectId getId() {
        return _id;
    }

    public void setId(ObjectId id) {
        this._id = id;
    }

    public static <R> R mongo(Operation<R> operation) {
        Mongo mongo = null;
        try {
            mongo = new Mongo("127.0.0.1" , 27017);
            MongoCollection collection = new Jongo(mongo.getDB("ophelia")).getCollection("connection_info");
            return operation.execute(collection);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            if (mongo != null) {
                mongo.close();
            }
        }
    }

    public void save() {
        mongo(new Operation<Void>() {
            @Override
            public Void execute(MongoCollection collection) {
                if(_id == null) {
                    _id = new ObjectId();
                }
                collection.save(MongoModel.this, WriteConcern.FSYNC_SAFE);
                return null;
            }
        });
    }

    public static class Finder<T> {

        private Class<T> clazz;
        public Finder(Class<T> clazz) {
            this.clazz = clazz;
        }

        public T byId(final ObjectId id) {
            return mongo(new Operation<T>() {
                @Override
                public T execute(MongoCollection collection) {
                    T t = collection.findOne(id).as(clazz);
                    return t;
                }
            });
        }

    }

    public interface Operation<R> {
        R execute(MongoCollection collection);
    }
}
