function changeDB(db) {
    $.get('ophelia/app/database/' + db, function (data) {
        processResponse(data);
    });
}
