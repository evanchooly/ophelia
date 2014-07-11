<!--

    Copyright (C) 2012-2014 Justin Lee <jlee@antwerkz.com>

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

            http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

-->
<div class="row-fluid span12">
    <div class="span3">
        <div class="well sidebar-nav collections">
            <ul class="nav nav-list">
                <li class="nav-header">{{sofia.collections()}}</li>
                <li class="nav-pills">
                    <table id="countTable">
                        <tr ng-repeat="(key, value) in collections">
                            <td class="dbName">
                                <div ng-click="query.queryString='db.' + key + '.find( {\n\n} )'">{{key}}</div>
                            </td>
                            <td class="count"><span class="countContent">{{value}}</span></td>
                        </tr>
                    </table>
                </li>
            </ul>
        </div>
    </div>

    <div class="span9 main-content">
        <div class="query">
            <div id="error" ng-show="showError">{{errorMessage}}</div>
            <form id="queryForm" action="/export" ng-model="query" method="post">
            <table>
                    <tr>
                        <td class="queryColumn" rowspan="4">
                            <textarea id="mongo" name="queryString" rows="5" ng-model="query.queryString"></textarea>

                            <div id="query-buttons">
                                <input id="queryButton" type="button" value="{{sofia.querySubmit()}}"
                                       ng-click="submitQuery()">
                                <input id="explainButton" type="button" value="{{sofia.queryExplain()}}"
                                       ng-click="explain()">
                                <input id="exportButton" type="submit" value="{{sofia.queryExport()}}">
                                <input type="hidden" name="database" value="{{query.database}}"
                                       ng-model="query.database"/>
                                <!--<input id="bookmark" type="button" value="{{sofia.queryBookmark()}}" ng-click="showSaveBookmark=true;">-->
                            </div>
                        </td>
                        <!--
                            <td>
                                <label for="bookmark">{{sofia.queryBookmark()}}
                                    <span ng-show="bookmarks.length > 0">(<a
                                        ng-click="modalShown=true">{{sofia.load()}}</a>)</span>
                                </label>
                            </td>
                            <td>
                                <input id="bookmark" name="bookmark" type="text" ng-model="query.bookmark">
                            </td>
                            <td class="paramsColumn" rowspan="4">
                                <table id="queryParameters" ng-hide="query.params.length == 0">
                                    <thead>
                                        <tr>
                                            <td colspan="2"><span class="nav-header">{{sofia.parameters()}}</span></td>
                                        </tr>
                                        <tr ng-repeat="param in query.params">
                                            <td>{{param.key}}</td>
                                            <td><input type="text" ></td>
                                        </tr>
                                        <tr>
                                            <td>fixed</td>
                                            <td><input type="text" ></td>
                                        </tr>
                                        <tr ng-repeat="(name, value) in query.params">
                                            <td>{{name}}</td>
                                            <td><input type="text" ng-model="query.params[name]"></td>
                                        </tr>
                                    </thead>
                                </table>
                            </td>
                        -->
                    </tr>
                    <tr>
                        <td><label for="limit">{{sofia.queryLimit()}}</label></td>
                        <td><input id="limit" name="limit" type="number" ng-model="query.limit"></td>
                    </tr>
                    <tr>
                        <td><label for="showCount">{{sofia.queryShowCount()}}</label></td>
                        <td><input id="showCount" name="showCount" type="checkbox" ng-model="query.showCount"></td>
                    </tr>
                </table>
            </form>
        </div>
        <div class="table" ng-show="showCount">
            {{sofia.resultCount()}} <span id="countValue">{{count}}</span>
        </div>
        <div class="table">
            <table>
                <tr ng-repeat="row in results">
                    <td>
                        <pre ng-bind-html="syntaxHighlight(row)"></pre>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</div>
<div id="bookmarkModal" ng-show="showSaveBookmark">
    <div class="modal-header">
        <button type="button" class="close" ng-click="showSaveBookmark=false;">×</button>
        <h3 id="myModalLabel">{{sofia.bookmarks()}}</h3>
    </div>
    <div class="modal-body">
        <form>
            <table>
                <tr>
                    <td class="formLabel">{{sofia.queryBookmark()}}</td>
                    <td><input type="text" name="name" ng-model="newBookmark"></td>
                </tr>
            </table>
        </form>
    </div>
    <div class="modal-footer">
        <button class="cancelButton" ng-click="showSaveBookmark=false;">{{sofia.cancel()}}</button>
        <button class="saveButton" ng-click="saveBookmark(newBookmark)">{{sofia.save()}}</button>
    </div>
</div>

<div ui-modal ng-model="modalShown">
    <div class="modal-body">
        <div class="well sidebar-nav">
            <ul class="nav nav-list">
                <li class="nav-header">{{sofia.loadBookmark()}}</li>
                <li class="nav-pills">
                    <table>
                        <tr ng-repeat="bookmark in bookmarks">
                            <td>
                                <a class="bookmarkName" ng-click="useBookmark(bookmark);modalShown=false;"
                                   title="{{bookmark['queryString']}}">{{bookmark['bookmark']}}</a>
                            </td>
                            <td><a ng-click="deleteBookmark(bookmark)" title="{{sofia.delete()}}">X</a></td>
                        </tr>
                    </table>
                </li>
            </ul>
        </div>
    </div>

    <!--<div class="modal-footer">-->
    <!--<a class="btn" ng-click="modalShown=false" href="">{{sofia.close()}}</a>-->
    <!--</div>-->
</div>
