
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
    $.get('ophelia/app/database/' + db, function (data) {
        processResponse(data);
    });
}
function collections(collections) {
    collections.push(collections);
    var table = $("#countTable");
    var children = table.children();
    if (children) {
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

function submitQuery2() {
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
}

