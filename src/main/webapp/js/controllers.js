var app = angular.module('ophelia', ['ui']);
function OpheliaController($scope, $http) {
    angular.module('ophelia', ['ui']);
    contextPath = location.pathname;
    if (contextPath == "/") {
        contextPath = "";
    }
    $scope.view = 'query.html';
    $scope.query = {
        bookmark:'',
        queryString:'',
        limit:100,
        showCount:true
    };
    $scope.sofia = {
        appTitle:function () {
            return "Ophelia"
        },
        collections:function () {
            return "Collections"
        },
        bookmarkLoadTitle:function () {
            return "Load Bookmark"
        },
        loadBookmark:function () {
            return "Load"
        },
        saveBookmark:function () {
            return "Bookmark:"
        },
        queryShowCount:function () {
            return "Show Count:"
        },
        queryLimit:function () {
            return "Limit:"
        },
        querySubmit:function () {
            return "Query"
        },
        resultCount:function () {
            return "Result count:"
        }
    };
    function resetState() {
        $scope.bookmarks = [];
        $scope.collections = [];
        $scope.count = -1;
        $scope.databases = [];
        $scope.database = '';
        $scope.errorMessage = '';
        $scope.results = [];
        $scope.showCount = false;
        $scope.showError = false;
        $scope.showList = false;
        $scope.query['bookmark'] = '';
    }

    $scope.submitQuery = function () {
        var data = $scope.query;
        data['database'] = $scope.database;
        $http({
            method:'POST',
            url:contextPath + '/ophelia/app/query',
            data:$scope.query,
            headers:{
                "Content-Type":"application/json"
            }
        })
            .success(function (data, status, headers, config) {
                processResponse(data);
            })
            .error(function (data, status, headers, config) {
                angular.element('.errors').html(data.errors.join('<br>')).slideDown();
                $scope.errorMessage = status.responseText;
            });
    };
    $scope.update = function (db) {
        get(contextPath + '/ophelia/app/database/' + db);
        $scope.showList = false;
    };
    function get(url) {
        $http.get(url)
            .success(function (data, status, headers, config) {
                processResponse(data);
            })
            .error(function (data, status, headers, config) {
                alert(data);
//                angular.element('.errors').html(data.errors.join('<br>')).slideDown();
                $scope.errorMessage = status.responseText;
            });
    }

    function processResponse(response) {
        resetState();
        for (var key in response) {
            if (key == 'resultCount') {
                $scope.showCount = true;
                $scope.count = response[key];
            } else if (key == 'dbResults') {
                $scope.results = response[key];
            } else if (key == 'bookmarks') {
                $scope.bookmarks = response[key];
            } else if (key == 'collections') {
                $scope.collections = response[key];
            } else if (key == 'databaseList') {
                $scope.databases = response[key];
            } else if (key == 'error') {
                $scope.errorMessage = response[key];
                $scope.showError = true;
            } else if (key == 'info') {
                $scope.database = response[key].database;
            } else {
                console.log("no handler for " + key);
            }
        }
    }

    $scope.syntaxHighlight = function (json) {
        // This function courtesy of StackOverflow user Pumbaa80
        // http://stackoverflow.com/questions/4810841/json-pretty-print-using-javascript
        console.log("json = " + json);
        delete json.$$hashKey
        if (typeof json != 'string') {
            json = JSON.stringify(json, undefined, 2);
        }
        console.log("json = " + json);
        console.log("json = " + json);
        json = json.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;');
        var text = json.replace(/("(\\u[a-zA-Z0-9]{4}|\\[^u]|[^\\"])*"(\s*:)?|\b(true|false|null)\b|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?)/g,
            function (match) {
                var cls = 'number';
                if (/^"/.test(match)) {
                    if (/:$/.test(match)) {
                        cls = 'key';
                    } else {
                        cls = 'string';
                    }
                } else if (/true|false/.test(match)) {
                    cls = 'boolean';
                } else if (/null/.test(match)) {
                    cls = 'null';
                }
                return '<span class="' + cls + '">' + match + '</span>';
            });
        return text;
    };
    $scope.useBookmark = function (bookmark) {
        $scope.query.queryString = bookmark['queryString'];
        $scope.modalShown = false;
    };
    $scope.deleteBookmark = function (bookmark) {
        get(contextPath + '/ophelia/app/deleteBookmark/' + bookmark['id']);
        $scope.modalShown = false;
    };
    resetState();
    get(contextPath + '/ophelia/app/content');
}