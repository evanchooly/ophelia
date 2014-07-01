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
<!DOCTYPE html>

<html ng-app="ophelia">
    <head>
        <title>@Sofia.appTitle()</title>
        <style type="text/css">
            body {
                padding-top: 60px;
                padding-bottom: 40px;
            }

            .sidebar-nav {
                padding: 9px 0;
            }
        </style>
        <link href="assets/style/bootstrap-responsive.css" rel="stylesheet">

        <!--[if lt IE 9]>
        <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->
        <link href="webjars/angular-ui/0.3.2/angular-ui.css" rel="stylesheet/less" type="text/css">

        <link rel="shortcut icon" type="image/png" href="assets/images/O.png">
        <script src="assets/js/jquery-1.7.2.min.js"></script>
        <script src="assets/js/bootstrap.js"></script>
        <script src="assets/js/bootstrap-modal.js"></script>
        <script src="webjars/angularjs/1.0.2/angular.js"></script>
        <script src="webjars/angular-ui/0.3.2/angular-ui.js"></script>
        <script src="assets/js/ophelia.js"></script>
        <script src="assets/js/controllers.js"></script>

        <link href="assets/style/main.less" rel="stylesheet/less" type="text/css">
        <script src="assets/js/less-1.3.1.min.js" type="text/javascript"></script>
    </head>

    <body ng-controller="OpheliaController">
        <div class="navbar navbar-fixed-top">
            <div class="navbar-inner">
                <div class="container-fluid">
                    <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </a>
                    <a class="brand" href="#">{{sofia.appTitle("US")}}</a>

                    <div class="btn-group pull-right">
                        <a class="btn dropdown-toggle" href="#" ng-model="showList" ng-click="showList = !showList">
                            <i class="icon-home"></i> <span id="db">{{query.database}}</span>
                            <span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu dropdown-form" ng-show="showList">
                            <li ng-repeat="db in databases">
                                <a ng-click="update(db);">{{db}}</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>

        <div class="container-fluid">
            <ng-include src="view"></ng-include>
            <div class="row-fluid">
                <hr>
                <footer>
                    &copy; Justin Lee 2012
                    <div id="projectInfo">
                        <a href="https://github.com/evanchooly/ophelia">Ophelia at github</a>
                    </div>
                </footer>
            </div>
        </div>
    </body>
</html>