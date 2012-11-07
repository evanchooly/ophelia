function OpheliaController($scope, $http) {
    handlers = {};
    handlers['resultCount'] = showCount;
    handlers['dbResults'] = showResults;
    handlers['collections'] = updateCollections;
    handlers['databaseList'] = databases;
    handlers['error'] = showDBError;
    handlers['info'] = database;
    $http.defaults.headers.post['Content-Type'] = '';
    $scope.view = 'query.html';
    $scope.collections = [];
    $scope.databases = [];
    $scope.database = '';
    $scope.errorMessage = '';
    $scope.query = {
        bookmark:'',
        queryString:'',
        limit:100,
        showCount:true
    };
    $scope.showCount = false;
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
        $scope.errorMessage = '';
        $scope.showCount = false;
        $("#countHolder").css("display", "none")
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
    $http.get('/ophelia/app/content')
        .success(function (data, status, headers, config) {
            processResponse(data);
        })
        .error(function (data, status, headers, config) {
            alert(data);
//                angular.element('.errors').html(data.errors.join('<br>')).slideDown();
            $scope.errorMessage = status.responseText;
        });
    function processResponse(response) {
        $scope.showCount = true;
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
        dbClick();
    }
}


