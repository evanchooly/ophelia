package plugins;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.mongodb.DB;
import com.mongodb.Mongo;

import java.net.UnknownHostException;
import java.util.List;

public class MongOphelia  {
    private static ThreadLocal<Mongo> pool = new ThreadLocal<>();
    private static Morphia morphia = new Morphia();

    static {
//        morphia.map(Query.class);
//        morphia.map(ConnectionInfo.class);
    }

    public static Datastore get() {
        Mongo mongo = pool.get();
        if(mongo == null) {
            try {
                mongo = new Mongo();
            } catch (UnknownHostException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
            pool.set(mongo);
        }
        return morphia.createDatastore(mongo, "ophelia");
    }

    public static List<String> getDatabaseNames() {
        return get().getDB().getMongo().getDatabaseNames();
    }

    public static void close() {
        Mongo mongo = pool.get();
        if(mongo != null) {
            mongo.close();
            pool.remove();
        }
    }

    public static DB getDB() {
        return get().getDB();
    }
}
