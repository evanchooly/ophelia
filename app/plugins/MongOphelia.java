package plugins;

import com.mongodb.Mongo;

import java.net.UnknownHostException;

public class MongOphelia  {
    private static ThreadLocal<Mongo> pool = new ThreadLocal<>();

    public static Mongo get() {
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

    public static void close() {
        Mongo mongo = pool.get();
        if(mongo != null) {
            mongo.close();
            pool.remove();
        }
    }
}
