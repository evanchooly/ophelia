function showResults(results) {
    $("#resultsHolder table").remove();
    var holder = document.querySelector('#resultsHolder');
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
    $('#resultsHolder div').each(function () {
        $(this).addClass('result');
    });
}