angular.module("ngLocale", [], [
    "$provide", function ($provide) {
        var PLURAL_CATEGORY = {ZERO:"zero", ONE:"one", TWO:"two", FEW:"few", MANY:"many", OTHER:"other"};
        $provide.value("$locale", {"DATETIME_FORMATS":{"MONTH":[
            "janvāris", "februāris", "marts", "aprīlis", "maijs", "jūnijs", "jūlijs",
            "augusts", "septembris", "oktobris", "novembris", "decembris"
        ], "SHORTMONTH":[
            "janv.", "febr.", "marts", "apr.", "maijs", "jūn.", "jūl.", "aug.", "sept.", "okt.", "nov.",
            "dec."
        ], "DAY":[
            "svētdiena", "pirmdiena", "otrdiena", "trešdiena", "ceturtdiena", "piektdiena", "sestdiena"
        ], "SHORTDAY":["Sv", "Pr", "Ot", "Tr", "Ce", "Pk", "Se"], "AMPMS":[
            "priekšpusdienā", "pēcpusdienā"
        ], "medium":"y. 'gada' d. MMM HH:mm:ss", "short":"dd.MM.yy HH:mm", "fullDate":"EEEE, y. 'gada' d. MMMM", "longDate":"y. 'gada' d. MMMM", "mediumDate":"y. 'gada' d. MMM", "shortDate":"dd.MM.yy", "mediumTime":"HH:mm:ss", "shortTime":"HH:mm"}, "NUMBER_FORMATS":{"DECIMAL_SEP":",", "GROUP_SEP":" ", "PATTERNS":
            [
                {"minInt":1, "minFrac":0, "macFrac":0, "posPre":"", "posSuf":"", "negPre":"-", "negSuf":"", "gSize":3, "lgSize":3, "maxFrac":3},
                {"minInt":1, "minFrac":2, "macFrac":0, "posPre":"", "posSuf":" \u00A4", "negPre":"-", "negSuf":" \u00A4", "gSize":3, "lgSize":3, "maxFrac":2}
            ], "CURRENCY_SYM":"Ls"}, "pluralCat":function (n) {
            if (n == 0) {
                return PLURAL_CATEGORY.ZERO;
            }
            if ((n % 10) == 1 && (n % 100) != 11) {
                return PLURAL_CATEGORY.ONE;
            }
            return PLURAL_CATEGORY.OTHER;
        }, "id":"lv"});
    }
]);