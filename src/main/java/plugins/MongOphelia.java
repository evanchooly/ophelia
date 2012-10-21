package plugins;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.mongodb.DB;
import com.mongodb.Mongo;
import models.ConnectionInfo;

import java.net.UnknownHostException;
import java.util.List;

public class MongOphelia  {
    private static ThreadLocal<Mongo> pool = new ThreadLocal<>();
    private static Morphia morphia = new Morphia();

    static {
        morphia.mapPackage(ConnectionInfo.class.getPackage().getName());
    }

    public static Datastore get() {
       return get("ophelia");
    }

    public static Datastore get(String database) {
        return morphia.createDatastore(getMongo(), database);
    }

    public static Mongo getMongo() {
        Mongo mongo = pool.get();
        if(mongo == null) {
            try {
                mongo = new Mongo();
            } catch (UnknownHostException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
            pool.set(mongo);
        }
        return mongo;
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
