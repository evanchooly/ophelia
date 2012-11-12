function OpheliaController($scope, $http) {
    handlers = {};
    handlers['resultCount'] = showCount;
    handlers['dbResults'] = showResults;
    handlers['collections'] = updateCollections;
    handlers['databaseList'] = databases;
    handlers['error'] = showDBError;
    handlers['info'] = database;
    $http.defaults.headers.post['Content-Type'] = '';
    resetState();
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
        queryBookmark:function () {
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
    function clearResults() {
        resetState();
    }

    function resetState() {
        $scope.collections = [];
        $scope.count = -1;
        $scope.databases = [];
        $scope.database = '';
        $scope.errorMessage = '';
        $scope.results = [];
        $scope.showCount = false;
        $scope.showError = false;
        $scope.showList = false;
    }

    $scope.submitQuery = function () {
        clearResults();
        $http({
            method:'POST',
            url:'/ophelia/app/query',
            data:$scope.query,
            headers:{
                "Content-Type":"application/json"
            }
        })
            .success(function (data, status, headers, config) {
                processResponse(data);
            })
            .error(function (data, status, headers, config) {
//                angular.element('.errors').html(data.errors.join('<br>')).slideDown();
                $scope.errorMessage = status.responseText;
            });
    };
    $scope.update = function (db) {
        get('/ophelia/app/database/' + db);
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
        for (var key in response) {
            var handler = handlers[key];
            if (handler) {
                handler(response[key])
            } else {
                console.log("no handler for " + key);
            }
        }
    }

    function updateCollections(collections) {
        $scope.collections = collections;
    }

    function showResults(results) {
        $scope.results = results;
    }

    function showCount(count) {
        $scope.showCount = true;
        $scope.count = count;
    }

    function showDBError(error) {
        $scope.errorMessage = error;
        $scope.showError = true;
    }

    $scope.syntaxHighlight = function (json) {
        // This function courtesy of StackOverflow user Pumbaa80
        // http://stackoverflow.com/questions/4810841/json-pretty-print-using-javascript
        if (typeof json != 'string') {
            json = JSON.stringify(json, undefined, 2);
        }
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
    function databases(dbs) {
        $scope.databases = dbs;
    }

    function database(info) {
        $scope.database = info.database;
    }

    get('/ophelia/app/content');
}