function OpheliaController($scope) {
    $scope.databases = [];
    $scope.database = 'bob';
    $scope.view = 'query.html';
    $scope.sofia = {
        appTitle:function (locale) {
            return locale === "US" ? "bob" : "Ophelia"
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
    $scope.query = {
        limit:100,
        showCount:true
    }
}