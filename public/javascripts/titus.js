function showResults(theDatas) {
    var holder = document.querySelector('#gridHolder');
    var table = put(holder, 'table');
    var theader = put(table, 'thead > tr');
    var tbody = put(table, 'tbody');
    var theDatas = [
        {alpha:1, beta:2, charlie:3, delta:4, echo:2, nested:{heart:'dude', burn:'sweet'}, kilo:'kilo1', bob:'bob2'},
        {alpha:1, beta:2, charlie:3},
        {alpha:1, beta:2, charlie:3, delta:4},
        {alpha:1, beta:2, charlie:3, kilo:6, nested:{heart:'tommy', burn:'gavin'}},
        {alpha:1, beta:2, charlie:3, kilo:6, bob:42},
        {alpha:1, charlie:3, kilo:6},
        {alpha:1}
    ];
    var seenHash = {};

    theDatas.forEach(function (data) {
        buildTable(data);
    });
}
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
    put(valueRow, 'td', typeof value === 'undefined' ? '' : value);
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

