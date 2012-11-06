package utils;

import java.io.IOException;
import java.io.InputStream;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;



public class Sofia {
    private static Map<Locale, ResourceBundle> messages = new HashMap<>();

    private static ResourceBundle getBundle(Locale... localeList) {
        Locale locale = localeList.length == 0 ? Locale.getDefault() : localeList[0];
        ResourceBundle labels = loadBundle(locale);
        if(labels == null) {
            labels = loadBundle(Locale.ROOT);
        }
        return labels;
    }

    private static ResourceBundle loadBundle(Locale locale) {
        ResourceBundle bundle = messages.get(locale);
        if(bundle == null) {
            bundle = ResourceBundle.getBundle("ophelia.properties", locale , new SofiaControl() );
            messages.put(locale, bundle);
        }
        return bundle;
    }

    private static String getMessageValue(String key, Locale... locale) {
        return (String) getBundle(locale).getObject(key);
    }

    public static String appTitle(Locale... locale) {
        return getMessageValue("app.title", locale);
    }

    public static String invalidQuery(Object arg0, Locale... locale) {
        return MessageFormat.format(getMessageValue("invalid.query", locale), arg0);
    }

    public static String invalidObjectId(Object arg0, Locale... locale) {
        return MessageFormat.format(getMessageValue("invalidObjectId", locale), arg0);
    }

    public static String queryBookmark(Locale... locale) {
        return getMessageValue("query.bookmark", locale);
    }

    public static String queryLimit(Locale... locale) {
        return getMessageValue("query.limit", locale);
    }

    public static String queryShowCount(Locale... locale) {
        return getMessageValue("query.showCount", locale);
    }

    public static String querySubmit(Locale... locale) {
        return getMessageValue("query.submit", locale);
    }

    public static String resultCount(Locale... locale) {
        return getMessageValue("result.count", locale);
    }

    public static String unknownQueryMethod(Object arg0, Locale... locale) {
        return MessageFormat.format(getMessageValue("unknown.query.method", locale), arg0);
    }


    private static class SofiaControl extends Control {
    @Override
    public ResourceBundle newBundle(String baseName, Locale locale, String format, final ClassLoader loader,
        boolean reload) throws IllegalAccessException, InstantiationException, IOException {

        ResourceBundle bundle = null;
        String name = toBundleName(baseName, locale);
        if(name.contains("_")) {
            name = name.replaceFirst("_", ".");
        }
        InputStream stream;
        try {
            final String bundleName = name;
            stream = AccessController.doPrivileged(
                new PrivilegedExceptionAction<InputStream>() {
                    public InputStream run() throws IOException {
                        return loader.getResourceAsStream(bundleName);
                    }
                });
        } catch (PrivilegedActionException e) {
            throw (IOException) e.getException();
        }
        if (stream != null) {
            try {
                bundle = new PropertyResourceBundle(stream);
            } finally {
                stream.close();
            }
        }

        return bundle;
      }
    }
}