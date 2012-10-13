package dao;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.annotations.Id;
import org.bson.types.ObjectId;
import plugins.MongOphelia;

public class MongoModel<T> {

    @Id
    private ObjectId _id;

    public ObjectId getId() {
        return _id;
    }

    public void setId(ObjectId id) {
        this._id = id;
    }

    public static <R> R mongo(Operation<R> operation) {
        Datastore ds = MongOphelia.get();
        return operation.execute(ds);
    }

    public void save() {
        mongo(new Operation<Void>() {
            @Override
            public Void execute(Datastore ds) {
                if (_id == null) {
                    _id = new ObjectId();
                }
                ds.save(MongoModel.this);
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
                public T execute(Datastore ds) {
                    return ds.createQuery(clazz).field("_id").equal(id).get();
                }
            });
        }
    }

    public interface Operation<R> {
        R execute(Datastore ds);
    }
}