handlers = {};
handlers['resultCount'] = showCount;
handlers['dbResults'] = showResults;
handlers['collections'] = collections;
handlers['databaseList'] = databases;
handlers['error'] = showDBError;
handlers['info'] = database;

function processResponse(response) {
    clearResults();
    for (var key in response) {
        var handler = handlers[key];
        if (handler) {
            handler(response[key])
        } else {
            console.log("no handler for " + key);
        }
    }
}

function databases(dbs) {
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
    $.get('database/' + db, function(data) {
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
        var td = $("<td></td>");
        var text = $("<div></div>").addClass("dbName");
        text.text(collection);
        td.append(text);
        row.append(td);
        var count = collections[collection];
        var cell = $("<td></td>").addClass("count");
        text = $("<span></span>").addClass("countContent");
        text.text(count);
        cell.append(text);
        row.append(cell);
        table.append(row);
    }
    dbClick();
}

function database(db) {
    $("#db").text(db.database);
}

function dbClick() {
    $(".dbName").click(function () {
        $("#mongo").val("db." + this.textContent + ".find()");
    });
}
