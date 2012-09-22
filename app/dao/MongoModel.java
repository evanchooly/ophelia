package dao;

import com.mongodb.Mongo;
import com.mongodb.WriteConcern;
import org.bson.types.ObjectId;
import org.codehaus.jackson.annotate.JsonProperty;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import plugins.MongOphelia;

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
        Mongo mongo = MongOphelia.get();
        Jongo jongo = new Jongo(mongo.getDB("ophelia"));
        return operation.execute(jongo.getCollection("connection_info"));
    }

    public void save() {
        mongo(new Operation<Void>() {
            @Override
            public Void execute(MongoCollection collection) {
                if (_id == null) {
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
                    return collection.findOne(id).as(clazz);
                }
            });
        }

    }

    public interface Operation<R> {
        R execute(MongoCollection collection);
    }
}