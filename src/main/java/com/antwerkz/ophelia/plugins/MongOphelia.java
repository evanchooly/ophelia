package com.antwerkz.ophelia.plugins;

import com.antwerkz.ophelia.models.ConnectionInfo;
import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.mongodb.DB;
import com.mongodb.Mongo;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

@WebFilter("/ophelia/*")
public class MongOphelia implements Filter {
    private static ThreadLocal<Mongo> pool = new ThreadLocal<>();
    private static Morphia morphia = new Morphia();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        morphia.mapPackage(ConnectionInfo.class.getPackage().getName());
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        chain.doFilter(request, response);
        if (pool.get() != null) {
            pool.get().close();
            pool.set(null);
        }
    }

    public static Datastore get() {
        return get("ophelia");
    }

    public static Datastore get(String database) {
        return morphia.createDatastore(getMongo(), database);
    }

    public static Mongo getMongo() {
        Mongo mongo = pool.get();
        if (mongo == null) {
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
        if (mongo != null) {
            mongo.close();
            pool.remove();
        }
    }

    public static DB getDB() {
        return get().getDB();
    }
}
