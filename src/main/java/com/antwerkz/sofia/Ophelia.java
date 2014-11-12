package com.antwerkz.sofia;

import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;
import java.security.*;
import java.util.ResourceBundle.Control;

import org.slf4j.*;

public class Ophelia {
    private static Map<Locale, ResourceBundle> messages = new HashMap<>();
        private static final Logger logger = LoggerFactory.getLogger(Ophelia.class);

    private Ophelia() {}

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
            bundle = ResourceBundle.getBundle("ophelia", locale );
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

    public static String collectionStats(Locale... locale) {
        return getMessageValue("collectionStats", locale);
    }

    public static String collections(Locale... locale) {
        return getMessageValue("collections", locale);
    }

    public static String count(Locale... locale) {
        return getMessageValue("count", locale);
    }

    public static String explainSubmit(Locale... locale) {
        return getMessageValue("explain.submit", locale);
    }

    public static String findSubmit(Locale... locale) {
        return getMessageValue("find.submit", locale);
    }

    public static String indexes(Locale... locale) {
        return getMessageValue("indexes", locale);
    }

    public static String insertSubmit(Locale... locale) {
        return getMessageValue("insert.submit", locale);
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

    public static String message(Locale... locale) {
        return getMessageValue("message", locale);
    }

    public static String noResults(Locale... locale) {
        return getMessageValue("no.results", locale);
    }

    public static String operationBookmark(Locale... locale) {
        return getMessageValue("operation.bookmark", locale);
    }

    public static String operationExport(Locale... locale) {
        return getMessageValue("operation.export", locale);
    }

    public static String operationLimit(Locale... locale) {
        return getMessageValue("operation.limit", locale);
    }

    public static String operationMultiple(Locale... locale) {
        return getMessageValue("operation.multiple", locale);
    }

    public static String operationShowCount(Locale... locale) {
        return getMessageValue("operation.showCount", locale);
    }

    public static String operationUpsert(Locale... locale) {
        return getMessageValue("operation.upsert", locale);
    }

    public static String parameters(Locale... locale) {
        return getMessageValue("parameters", locale);
    }

    public static String removeSubmit(Locale... locale) {
        return getMessageValue("remove.submit", locale);
    }

    public static String resultCount(Locale... locale) {
        return getMessageValue("result.count", locale);
    }

    public static String save(Locale... locale) {
        return getMessageValue("save", locale);
    }

    public static String selectCollection(Locale... locale) {
        return getMessageValue("selectCollection", locale);
    }

    public static String unknownQueryMethod(Object arg0, Locale... locale) {
        return MessageFormat.format(getMessageValue("unknown.query.method", locale), arg0);
    }

    public static String updateSubmit(Locale... locale) {
        return getMessageValue("update.submit", locale);
    }


}
