function OpheliaController($scope) {
    $scope.databases = [];
    $scope.database = '';
    $scope.view = 'query.html';
    $scope.sofia = {
        appTitle:function (locale) {
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
    $scope.query = {
        limit:100,
        showCount:true
    }
}
function initOphelia() {
    $.get('/ophelia/app/content', function (data) {
        processResponse(data);
    });
    $("#button").click(function () {
        clearResults();
        $.ajax({
            type:"POST",
            url:"/ophelia/app/query",
            data:$('#queryForm').serialize(),
            contentType:'application/x-www-form-urlencoded',
            success:function (response) {
                processResponse(response);
            },
            error:function (response) {
                $("#error").val(response.responseText);
                $("#error").css('display', 'inherit');
            }
        });
    });
}
