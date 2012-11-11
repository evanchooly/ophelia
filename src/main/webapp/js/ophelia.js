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
function database(db) {
    $("#db").text(db.database);
}