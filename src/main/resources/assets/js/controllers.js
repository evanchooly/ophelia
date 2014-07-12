/*
 * Copyright (C) 2012-2014 Justin Lee <jlee@antwerkz.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
var app = angular.module('ophelia', ['ui.bootstrap']);

app.controller('OpheliaController', function($scope, $http, $sce) {
    angular.module('ophelia', ['ui']);
    var contextPath = location.pathname;
    if (contextPath == "/") {
        contextPath = "";
    }
    $scope.view = 'query.html';
    $scope.query = {
        queryString: '',
        database: '',
        limit: 100,
        showCount: true,
        params: {}
    };
    $scope.showSaveBookmark = false;
    $scope.sofia = sofia;

    function resetState() {
        $scope.bookmarks = [];
        $scope.collections = [];
        $scope.count = -1;
        $scope.database = '';
        $scope.databases = [];
        $scope.errorMessage = '';
        $scope.results = [];
        $scope.showCount = false;
        $scope.showError = false;
        $scope.showList = false;
        $scope.showSaveBookmark = false;
        $scope.query['bookmark'] = '';
    }

    /*
     $scope.export = function () {
     $http({
     method: 'POST',
     url: contextPath + '/ophelia/app/export',
     data: $scope.query,
     headers: {
     "Content-Type": "application/json"
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
     */
    $scope.submitQuery = function () {
        $http({
            method: 'POST',
            url: contextPath + 'query',
            data: $scope.query,
            headers: {
                "Content-Type": "application/json"
            }
        })
                .success(function (data, status, headers, config) {
                    processResponse(data);
                })
                .error(function (data, status, headers, config) {
                    alert(data)
                    angular.element('.errors').html(data.errors.join('<br>')).slideDown();
                    $scope.errorMessage = status.responseText;
                });
    };
    $scope.explain = function () {
        $http({
            method: 'POST',
            url: contextPath + 'explain',
            data: $scope.query,
            headers: {
                "Content-Type": "application/json"
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
        get(contextPath + 'database/' + db);
        $scope.showList = false;
    };
    /*
     $scope.queryChange = function () {
     var index = 0;
     var end = 0;
     var query = $scope.query['queryString'];
     var newParams = {};
     var added = false;
     while ((index = query.indexOf("{{", index)) != -1 && (end = query.indexOf("}}", index)) != -1) {
     var name = query.substring(index + 2, end);
     newParams[name] = 'name';
     index = end;
     added = true;
     }
     for (var key in newParams) {
     if (!$scope.query.params[key]) {
     $scope.query.params[key] = '';
     }
     }
     for (var key in $scope.query.params) {
     if (!newParams[key]) {
     delete $scope.query.params[key];
     }
     }
     };
     */
    function get(url) {
        $http.get(url)
                .success(function (data, status, headers, config) {
                    processResponse(data);
                })
                .error(function (data, status, headers, config) {
                    alert(data);
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
                $scope.query.database = response[key].database;
            } else {
                console.log("no handler for " + key);
            }
        }
    }

    $scope.syntaxHighlight = function (json) {
        // This function courtesy of StackOverflow user Pumbaa80
        // http://stackoverflow.com/questions/4810841/json-pretty-print-using-javascript
        delete json.$$hashKey
        if (typeof json != 'string') {
            json = JSON.stringify(json, undefined, 2);
        }
        console.log("json = " + json);
        json = json.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;')
            .replace(/("(\\u[a-zA-Z0-9]{4}|\\[^u]|[^\\"])*"(\s*:)?|\b(true|false|null)\b|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?)/g,
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
        console.log("now json = " + json);

        return $sce.trustAsHtml(json/*.slice(1, -1)*/);
    };
    /*
     $scope.useBookmark = function (bookmark) {
     $scope.query.queryString = bookmark['queryString'];
     $scope.modalShown = false;
     //        $scope.queryChange();
     };
     $scope.deleteBookmark = function (bookmark) {
     get(contextPath + contextPath + '/ophelia/app/deleteBookmark/' + bookmark['id']);
     $scope.modalShown = false;
     };
     $scope.saveBookmark = function (bookmark) {
     $scope.query.bookmark = bookmark;
     $http({
     method: 'POST',
     url: contextPath + '/ophelia/app/bookmark',
     data:  $scope.query,
     headers: {
     "Content-Type": "application/json"
     }
     })
     .success(function (data, status, headers, config) {
     $scope.showSaveBookmark = false;
     processResponse(data);
     })
     .error(function (data, status, headers, config) {
     $scope.showSaveBookmark = false;
     angular.element('.errors').html(data.errors.join('<br>')).slideDown();
     $scope.errorMessage = status.responseText;
     });
     };
     */
    resetState();
    get(contextPath + 'content');
})

