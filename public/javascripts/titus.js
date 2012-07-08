handlers = {};
handlers['results'] = showResults;
handlers['collections'] = collections;
handlers['databaseList'] = databases;
handlers['database'] = database;

function processResponse(response) {
    for (var key in response) {
        var handler = handlers[key];
        if (handler) {
            handler(response[key])
        }
    }
}

function databases(dbs) {
//    <li><a href="#">to do!</a></li>
    var list = $("#dbList");
    var children = list.children();
    if (children) {
        children.remove();
    }
    for (var database in dbs) {
        var li = $("<li></li>");
        var link = $("<a></a>")
            .attr("onclick", "changeDB('" + dbs[database] + "')")
            .text(dbs[database]);
        li.append(link);
        list.append(li);
    }
}

function changeDB(db) {
    $.get('database', {database: db}, function(data) {
        processResponse(data);
    });
}

function collections(collections) {
    var table = $("#countTable");
    var children = table.children();
    if(children) {
        children.remove();
    }
    for (var collection in collections) {
        var row = $("<tr></tr>");
        row.append($("<td>" + collection + "</td>"));
        var count = collections[collection];
        var cell = $("<td></td>").addClass("count");
        var text = $("<span></span>").addClass("countContent");
        text
            .addClass("countContent")
            .text(count);
        cell.append(text);
        row.append(cell);
        table.append(row);
    }
}

function database(db) {
    $("#db").text(db);
}

function showResults(results) {
    $("#gridHolder table").remove();
    var holder = document.querySelector('#gridHolder');
    var table = put(holder, 'table');
    var theader = put(table, 'thead > tr');
    var tbody = put(table, 'tbody');
    var seenHash = {};
    function header(key, prefix) {
        var header = (typeof prefix === 'undefined' ? '' : prefix + '.') + key;
        if (!(header in seenHash)) {
            seenHash[header] = true;
            put(theader, 'th', header);
        }
    }

    function displayValue(valueRow, item, field) {
        var value = item;
        if (field.indexOf('.') != -1) {
            while (field.indexOf('.') != -1) {
                var key = field.split('.')[0];
                value = value[key];
                field = field.slice(field.indexOf('.') + 1);
            }
            value = typeof value === 'undefined' ? '' : value[field];
        } else {
            value = item[field];
        }
        var td = put(valueRow, 'td');
        var div = put(td, 'div', typeof value === 'undefined' ? '' : value);
        div.style.class = 'result';
    }

    function buildTable(item, prefix) {
        Object.keys(item).forEach(function (key) {
            if (typeof item[key] == "object") {
                for (var data in item[key]) {
                    header(data, key);
                }
            } else {
                header(key, prefix);
            }
        });
        var valueRow = put('tr');
        Object.keys(seenHash).forEach(function (field) {
            displayValue(valueRow, item, field);
        });
        put(tbody, valueRow);
    }

    results.forEach(function (data) {
        buildTable(data);
    });
    $('#gridHolder div').each(function () {
        $(this).addClass('result');
    });
}