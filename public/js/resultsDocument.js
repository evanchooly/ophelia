function clearResults() {
    $("#error").css('display', 'none');
    function clear(holder) {
        var children = holder.children();
        if (children) {
            children.remove();
        }
    }
    clear($("#resultsHolder"));
    clear($("#countValue"));
    $("#countHolder").css("display", "none")
}

function showDBError(error) {
    $("#error").text(error);
    $("#error").css('display', 'inherit');
}

function showCount(count) {
    $("#countHolder").css("display", "inherit");
    var holder = $("#countValue");
    holder.text(count);
}

function showResults(results) {
    var holder = $("#resultsHolder");
    var table = $("<table></table>");

    results.forEach(function (data) {
        var row = $("<tr></tr>");
        var cell = $("<td></td>");
        cell.append("<pre>" + syntaxHighlight(data) + "</pre>");
        row.append(cell);
        table.append(row);
    });

    holder.append(table);
}

// This function courtesy of StackOverflow user Pumbaa80
// http://stackoverflow.com/questions/4810841/json-pretty-print-using-javascript
function syntaxHighlight(json) {
    if (typeof json != 'string') {
        json = JSON.stringify(json, undefined, 2);
    }
    json = json.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;');
    return json.replace(/("(\\u[a-zA-Z0-9]{4}|\\[^u]|[^\\"])*"(\s*:)?|\b(true|false|null)\b|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?)/g, function (match) {
        var cls = 'number';
        if (/^"/.test(match)) {
            if (/:$/.test(match)) {
                cls = 'key';
            } else {
                cls = 'string';
            }
        } else if (/true|false/.test(match)) {
            cls = 'boolean';
        } else if (/null/.test(match)) {
            cls = 'null';
        }
        return '<span class="' + cls + '">' + match + '</span>';
    });
}