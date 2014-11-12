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
                                <div ng-click="selectCollection(key)">{{key}}</div>
                            </td>
                            <td class="count"><span class="countContent">{{value}}</span></td>
                        </tr>
                    </table>
                </li>
            </ul>
        </div>
    </div>
    <div class="span9 main-content">
        <tabset>
            <tab heading="Query">
                <span ng-include="'find.html'"></span>
            </tab>
            <tab heading="Update">
                <span ng-include="'update.html'"></span>
            </tab>
        </tabset>
    </div>
<#--
    <div class="span3">
        <div class="well sidebar-nav collections">
            <ul class="nav nav-list">
                <li class="nav-header">{{operation.collection}} {{sofia.collectionStats()}}</li>
                <li class="nav-pills" ng-switch="collection.length">
                    <div class="user-advice" ng-switch-when="0">{{sofia.selectCollection()}}</div>
                    <table id="countTable" ng-switch-default>
                        <tr ng-repeat="(key, value) in collectionStats">
                            <td class="dbName">
                                <div ng-click="query.query='db.' + key + '.find( {\n\n} )'">{{key}}</div>
                            </td>
                            <td class="count"><span class="countContent">{{value}}</span></td>
                        </tr>
                    </table>
                </li>
            </ul>
            <hr/>
            <ul class="nav nav-list">
                <li class="nav-header">{{operation.collection}} {{sofia.indexes()}}</li>
                <li class="nav-pills">
                        <table id="countTable">
                        <tr ng-repeat="index in collectionIndexes">
                            <td>
                                <pre ng-bind-html="syntaxHighlight(index)"></pre>
                            </td>
                        </tr>
                    </table>
                </li>
            </ul>
        </div>
    </div>
-->
</div>

<#--
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
-->

<#--
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
                                   title="{{bookmark['query']}}">{{bookmark['bookmark']}}</a>
                            </td>
                            <td><a ng-click="deleteBookmark(bookmark)" title="{{sofia.delete()}}">X</a></td>
                        </tr>
                    </table>
                </li>
            </ul>
        </div>
    </div>

    <!--<div class="modal-footer">&ndash;&gt;
    <!--<a class="btn" ng-click="modalShown=false" href="">{{sofia.close()}}</a>&ndash;&gt;
    <!--</div>&ndash;&gt;
</div>
-->
