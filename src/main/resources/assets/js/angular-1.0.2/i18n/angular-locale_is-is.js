angular.module("ngLocale", [], [
    "$provide", function ($provide) {
        var PLURAL_CATEGORY = {ZERO: "zero", ONE: "one", TWO: "two", FEW: "few", MANY: "many", OTHER: "other"};
        $provide.value("$locale", {"NUMBER_FORMATS": {"DECIMAL_SEP": ",", "GROUP_SEP": ".", "PATTERNS": [
            {"minInt": 1, "minFrac": 0, "macFrac": 0, "posPre": "", "posSuf": "", "negPre": "-", "negSuf": "", "gSize": 3, "lgSize": 3, "maxFrac": 3},
            {"minInt": 1, "minFrac": 2, "macFrac": 0, "posPre": "", "posSuf": " \u00A4", "negPre": "-", "negSuf": " \u00A4", "gSize": 3, "lgSize": 3, "maxFrac": 2}
        ], "CURRENCY_SYM": "kr"}, "pluralCat": function (n) {
            if (n == 1) {
                return PLURAL_CATEGORY.ONE;
            }
            return PLURAL_CATEGORY.OTHER;
        }, "DATETIME_FORMATS": {"MONTH": [
            "janúar",
            "febrúar",
            "mars",
            "apríl",
            "maí",
            "júní",
            "júlí",
            "ágúst",
            "september",
            "október",
            "nóvember",
            "desember"
        ], "SHORTMONTH": [
            "jan",
            "feb",
            "mar",
            "apr",
            "maí",
            "jún",
            "júl",
            "ágú",
            "sep",
            "okt",
            "nóv",
            "des"
        ], "DAY": [
            "sunnudagur",
            "mánudagur",
            "þriðjudagur",
            "miðvikudagur",
            "fimmtudagur",
            "föstudagur",
            "laugardagur"
        ], "SHORTDAY": ["sun", "mán", "þri", "mið", "fim", "fös", "lau"], "AMPMS": [
            "f.h.",
            "e.h."
        ], "medium": "d.M.yyyy HH:mm:ss", "short": "d.M.yyyy HH:mm", "fullDate": "EEEE, d. MMMM y", "longDate": "d. MMMM y", "mediumDate": "d.M.yyyy", "shortDate": "d.M.yyyy", "mediumTime": "HH:mm:ss", "shortTime": "HH:mm"}, "id": "is-is"});
    }
]);