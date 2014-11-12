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
<div class="query span6">
    <div id="error" ng-show="showError">{{errorMessage}}</div>
    <form id="updateForm" action="/export" ng-model="query" method="post">
        <table>
            <tr>
                <td class="queryColumn" rowspan="4">
                    <span ng-show="operation.collection" class="nav-header">Collection:</span> {{operation.collection}}
                    <textarea name="query" rows="5" ng-model="operation.query"></textarea>
                    <textarea name="update" rows="5" ng-model="operation.update"></textarea>

                    <div id="query-buttons">
                        <input id="queryButton" type="button" value="{{sofia.updateSubmit()}}"
                               ng-click="operation.collection ? submitQuery() : ''">
                    </div>
                </td>
            </tr>
            <tr>
                <td><label for="upsert">{{sofia.operationUpsert()}}</label></td>
                <td><input id="upsert" name="upsert" type="checkbox" ng-model="operation.upsert"></td>
            </tr>
            <tr>
                <td><label for="multiple">{{sofia.operationMultiple()}}</label></td>
                <td><input id="multiple" name="multiple" type="checkbox" ng-model="operation.multiple"></td>
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
