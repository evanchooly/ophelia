package com.antwerkz.sofia

import org.slf4j.LoggerFactory
import java.text.MessageFormat
import java.util.HashMap
import java.util.Locale
import java.util.ResourceBundle

public class Ophelia {
    private val messages = HashMap<Locale, ResourceBundle>()
    private val logger = LoggerFactory.getLogger(javaClass<Ophelia>())

    private fun getBundle(vararg localeList: Locale): ResourceBundle {
        val locale = if (localeList.size() == 0) Locale.getDefault() else localeList[0]
        var labels: ResourceBundle? = loadBundle(locale)
        if (labels == null) {
            labels = loadBundle(Locale.ROOT)
        }
        return labels!!
    }

    private fun loadBundle(locale: Locale): ResourceBundle {
        var bundle: ResourceBundle? = messages.get(locale)
        if (bundle == null) {
            bundle = ResourceBundle.getBundle("ophelia", locale)
            messages.put(locale, bundle)
        }
        return bundle!!
    }

    private fun getMessageValue(key: String, vararg locale: Locale): String {
        return getBundle(*locale).getObject(key) as String
    }

    public fun appTitle(): String {
        return getMessageValue("app.title")
    }

    public fun appTitle(locale: Locale): String {
        return getMessageValue("app.title", locale)
    }

    public fun bookmarkExists(vararg locale: Locale): String {
        return getMessageValue("bookmark.exists", *locale)
    }

    public fun bookmarks(vararg locale: Locale): String {
        return getMessageValue("bookmarks", *locale)
    }

    public fun cancel(vararg locale: Locale): String {
        return getMessageValue("cancel", *locale)
    }

    public fun close(vararg locale: Locale): String {
        return getMessageValue("close", *locale)
    }

    public fun collectionStats(vararg locale: Locale): String {
        return getMessageValue("collectionStats", *locale)
    }

    public fun collections(vararg locale: Locale): String {
        return getMessageValue("collections", *locale)
    }

    public fun count(vararg locale: Locale): String {
        return getMessageValue("count", *locale)
    }

    public fun explainSubmit(vararg locale: Locale): String {
        return getMessageValue("explain.submit", *locale)
    }

    public fun findSubmit(vararg locale: Locale): String {
        return getMessageValue("find.submit", *locale)
    }

    public fun indexes(vararg locale: Locale): String {
        return getMessageValue("indexes", *locale)
    }

    public fun insertSubmit(vararg locale: Locale): String {
        return getMessageValue("insert.submit", *locale)
    }

    public fun invalidQuery(arg0: Any, vararg locale: Locale): String {
        return MessageFormat.format(getMessageValue("invalid.query", *locale), arg0)
    }

    public fun load(vararg locale: Locale): String {
        return getMessageValue("load", *locale)
    }

    public fun loadBookmark(vararg locale: Locale): String {
        return getMessageValue("load.bookmark", *locale)
    }

    public fun message(vararg locale: Locale): String {
        return getMessageValue("message", *locale)
    }

    public fun noResults(vararg locale: Locale): String {
        return getMessageValue("no.results", *locale)
    }

    public fun operationBookmark(vararg locale: Locale): String {
        return getMessageValue("operation.bookmark", *locale)
    }

    public fun operationExport(vararg locale: Locale): String {
        return getMessageValue("operation.export", *locale)
    }

    public fun operationLimit(vararg locale: Locale): String {
        return getMessageValue("operation.limit", *locale)
    }

    public fun operationMultiple(vararg locale: Locale): String {
        return getMessageValue("operation.multiple", *locale)
    }

    public fun operationShowCount(vararg locale: Locale): String {
        return getMessageValue("operation.showCount", *locale)
    }

    public fun operationUpsert(vararg locale: Locale): String {
        return getMessageValue("operation.upsert", *locale)
    }

    public fun parameters(vararg locale: Locale): String {
        return getMessageValue("parameters", *locale)
    }

    public fun removeSubmit(vararg locale: Locale): String {
        return getMessageValue("remove.submit", *locale)
    }

    public fun resultCount(vararg locale: Locale): String {
        return getMessageValue("result.count", *locale)
    }

    public fun save(vararg locale: Locale): String {
        return getMessageValue("save", *locale)
    }

    public fun selectCollection(vararg locale: Locale): String {
        return getMessageValue("selectCollection", *locale)
    }

    public fun unknownQueryMethod(arg0: Any, vararg locale: Locale): String {
        return MessageFormat.format(getMessageValue("unknown.query.method", *locale), arg0)
    }

    public fun updateSubmit(vararg locale: Locale): String {
        return getMessageValue("update.submit", *locale)
    }

}
