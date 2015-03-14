var sofiaBundles = [];
    sofiaBundles[''] = {
        'app.title' : 'Ophelia',
        'bookmark.exists' : 'Bookmark already exists',
        'bookmarks' : 'Bookmarks',
        'cancel' : 'Cancel',
        'close' : 'Close',
        'collectionStats' : 'Stats',
        'collections' : 'Collections',
        'count' : 'Count',
        'explain.submit' : 'Explain',
        'find.submit' : 'Find',
        'indexes' : 'Indexes',
        'insert.submit' : 'Insert',
        'invalid.query' : 'Bad query: {0}',
        'load' : 'Load',
        'load.bookmark' : 'Load Bookmark',
        'message' : 'message',
        'no.results' : 'No results found',
        'operation.bookmark' : 'Bookmark',
        'operation.export' : 'Export',
        'operation.limit' : 'Limit:',
        'operation.multiple' : 'Update Multiple',
        'operation.showCount' : 'Show Count:',
        'operation.upsert' : 'Upsert',
        'parameters' : 'Parameters',
        'remove.submit' : 'Remove',
        'result.count' : 'Result count:',
        'save' : 'Save',
        'selectCollection' : 'Choose a collection',
        'unknown.query.method' : 'Unknown or unsupported query method: {0}',
        'update.submit' : 'Update'
    };
    sofiaBundles['en_US'] = {
        'app.title' : 'Ophelia',
        'bookmark.exists' : 'Bookmark already exists',
        'bookmarks' : 'Bookmarks',
        'cancel' : 'Cancel',
        'close' : 'Close',
        'collectionStats' : 'Stats',
        'collections' : 'Collections',
        'count' : 'Count',
        'explain.submit' : 'Explain',
        'find.submit' : 'Find',
        'indexes' : 'Indexes',
        'insert.submit' : 'Insert',
        'invalid.query' : 'Bad query: {0}',
        'load' : 'Load',
        'load.bookmark' : 'Load Bookmark',
        'message' : 'message',
        'no.results' : 'No results found',
        'operation.bookmark' : 'Bookmark',
        'operation.export' : 'Export',
        'operation.limit' : 'Limit:',
        'operation.multiple' : 'Update Multiple',
        'operation.showCount' : 'Show Count:',
        'operation.upsert' : 'Upsert',
        'parameters' : 'Parameters',
        'remove.submit' : 'Remove',
        'result.count' : 'Result count:',
        'save' : 'Save',
        'selectCollection' : 'Choose a collection',
        'unknown.query.method' : 'Unknown or unsupported query method: {0}',
        'update.submit' : 'Update'
    };

var sofiaLang = navigator.language || navigator.userLanguage;
sofia = {
    format: function(value, arguments) {
        var formatted = value;
        if (arguments) {
            for (var arg in arguments) {
                formatted = formatted.replace("{" + arg + "}", arguments[arg]);
            }
        }
        return formatted;
    },
    appTitle: function() {
        return format(sofiaBundles[sofiaLang]['app.title']);
    },
    bookmarkExists: function() {
        return format(sofiaBundles[sofiaLang]['bookmark.exists']);
    },
    bookmarks: function() {
        return format(sofiaBundles[sofiaLang]['bookmarks']);
    },
    cancel: function() {
        return format(sofiaBundles[sofiaLang]['cancel']);
    },
    close: function() {
        return format(sofiaBundles[sofiaLang]['close']);
    },
    collectionStats: function() {
        return format(sofiaBundles[sofiaLang]['collectionStats']);
    },
    collections: function() {
        return format(sofiaBundles[sofiaLang]['collections']);
    },
    count: function() {
        return format(sofiaBundles[sofiaLang]['count']);
    },
    explainSubmit: function() {
        return format(sofiaBundles[sofiaLang]['explain.submit']);
    },
    findSubmit: function() {
        return format(sofiaBundles[sofiaLang]['find.submit']);
    },
    indexes: function() {
        return format(sofiaBundles[sofiaLang]['indexes']);
    },
    insertSubmit: function() {
        return format(sofiaBundles[sofiaLang]['insert.submit']);
    },
    invalidQuery: function(arg0) {
        return format(sofiaBundles[sofiaLang]['invalid.query']);
    },
    load: function() {
        return format(sofiaBundles[sofiaLang]['load']);
    },
    loadBookmark: function() {
        return format(sofiaBundles[sofiaLang]['load.bookmark']);
    },
    message: function() {
        return format(sofiaBundles[sofiaLang]['message']);
    },
    noResults: function() {
        return format(sofiaBundles[sofiaLang]['no.results']);
    },
    operationBookmark: function() {
        return format(sofiaBundles[sofiaLang]['operation.bookmark']);
    },
    operationExport: function() {
        return format(sofiaBundles[sofiaLang]['operation.export']);
    },
    operationLimit: function() {
        return format(sofiaBundles[sofiaLang]['operation.limit']);
    },
    operationMultiple: function() {
        return format(sofiaBundles[sofiaLang]['operation.multiple']);
    },
    operationShowCount: function() {
        return format(sofiaBundles[sofiaLang]['operation.showCount']);
    },
    operationUpsert: function() {
        return format(sofiaBundles[sofiaLang]['operation.upsert']);
    },
    parameters: function() {
        return format(sofiaBundles[sofiaLang]['parameters']);
    },
    removeSubmit: function() {
        return format(sofiaBundles[sofiaLang]['remove.submit']);
    },
    resultCount: function() {
        return format(sofiaBundles[sofiaLang]['result.count']);
    },
    save: function() {
        return format(sofiaBundles[sofiaLang]['save']);
    },
    selectCollection: function() {
        return format(sofiaBundles[sofiaLang]['selectCollection']);
    },
    unknownQueryMethod: function(arg0) {
        return format(sofiaBundles[sofiaLang]['unknown.query.method']);
    },
    updateSubmit: function() {
        return format(sofiaBundles[sofiaLang]['update.submit']);
    }
};
