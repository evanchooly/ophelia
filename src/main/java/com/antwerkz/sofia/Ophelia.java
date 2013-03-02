package com.antwerkz.sofia;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Ophelia {
    private static Map<Locale, ResourceBundle> messages = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(Ophelia.class);

    private Ophelia() {
    }

    private static ResourceBundle getBundle(Locale... localeList) {
        Locale locale = localeList.length == 0 ? Locale.getDefault() : localeList[0];
        ResourceBundle labels = loadBundle(locale);
        if (labels == null) {
            labels = loadBundle(Locale.ROOT);
        }
        return labels;
    }

    private static ResourceBundle loadBundle(Locale locale) {
        ResourceBundle bundle = messages.get(locale);
        if (bundle == null) {
            bundle = ResourceBundle.getBundle("ophelia", locale);
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

    public static String bookmarkExists(Locale... locale) {
        return getMessageValue("bookmark.exists", locale);
    }

    public static String bookmarks(Locale... locale) {
        return getMessageValue("bookmarks", locale);
    }

    public static String cancel(Locale... locale) {
        return getMessageValue("cancel", locale);
    }

    public static String close(Locale... locale) {
        return getMessageValue("close", locale);
    }

    public static String collections(Locale... locale) {
        return getMessageValue("collections", locale);
    }

    public static String count(Locale... locale) {
        return getMessageValue("count", locale);
    }

    public static String invalidQuery(Object arg0, Locale... locale) {
        return MessageFormat.format(getMessageValue("invalid.query", locale), arg0);
    }

    public static String load(Locale... locale) {
        return getMessageValue("load", locale);
    }

    public static String loadBookmark(Locale... locale) {
        return getMessageValue("load.bookmark", locale);
    }

    public static String noResults(Locale... locale) {
        return getMessageValue("no.results", locale);
    }

    public static String parameters(Locale... locale) {
        return getMessageValue("parameters", locale);
    }

    public static String queryBookmark(Locale... locale) {
        return getMessageValue("query.bookmark", locale);
    }

    public static String queryExplain(Locale... locale) {
        return getMessageValue("query.explain", locale);
    }

    public static String queryExport(Locale... locale) {
        return getMessageValue("query.export", locale);
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

    public static String save(Locale... locale) {
        return getMessageValue("save", locale);
    }

    public static String unknownQueryMethod(Object arg0, Locale... locale) {
        return MessageFormat.format(getMessageValue("unknown.query.method", locale), arg0);
    }


}
