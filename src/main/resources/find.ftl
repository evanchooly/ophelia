<div class="query span6">
    <div id="error" ng-show="showError">{{errorMessage}}</div>
    <form id="queryForm" action="/export" ng-model="query" method="post">
        <table>
            <tr>
                <td class="queryColumn" rowspan="4">
                    <span ng-show="operation.collection" class="nav-header">Collection:</span> {{operation.collection}}
                    <textarea name="query" rows="5" ng-model="operation.query"></textarea>

                    <div id="query-buttons">
                        <input id="queryButton" type="button" value="{{sofia.findSubmit()}}"
                               ng-click="operation.collection ? submitQuery() : ''">
                        <input id="explainButton" type="button" value="{{sofia.explainSubmit()}}"
                               ng-click="operation.collection ? explain() : ''">
                    </div>
                </td>
            </tr>
            <tr>
                <td><label for="limit">{{sofia.operationLimit()}}</label></td>
                <td><input id="limit" name="limit" type="number" ng-model="operation.limit"></td>
            </tr>
            <tr>
                <td><label for="showCount">{{sofia.operationShowCount()}}</label></td>
                <td><input id="showCount" name="showCount" type="checkbox" ng-model="operation.showCount"></td>
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