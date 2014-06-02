angular.module("ngLocale", [], [
    "$provide", function ($provide) {
        var PLURAL_CATEGORY = {ZERO: "zero", ONE: "one", TWO: "two", FEW: "few", MANY: "many", OTHER: "other"};
        $provide.value("$locale", {"DATETIME_FORMATS": {"MONTH": [
            "sausis",
            "vasaris",
            "kovas",
            "balandis",
            "gegužė",
            "birželis",
            "liepa",
            "rugpjūtis",
            "rugsėjis",
            "spalis",
            "lapkritis",
            "gruodis"
        ], "SHORTMONTH": [
            "Sau",
            "Vas",
            "Kov",
            "Bal",
            "Geg",
            "Bir",
            "Lie",
            "Rgp",
            "Rgs",
            "Spl",
            "Lap",
            "Grd"
        ], "DAY": [
            "sekmadienis",
            "pirmadienis",
            "antradienis",
            "trečiadienis",
            "ketvirtadienis",
            "penktadienis",
            "šeštadienis"
        ], "SHORTDAY": ["Sk", "Pr", "An", "Tr", "Kt", "Pn", "Št"], "AMPMS": [
            "priešpiet",
            "popiet"
        ], "medium": "yyyy.MM.dd HH:mm:ss", "short": "yyyy-MM-dd HH:mm", "fullDate": "y 'm'. MMMM d 'd'.,EEEE", "longDate": "y 'm'. MMMM d 'd'.", "mediumDate": "yyyy.MM.dd", "shortDate": "yyyy-MM-dd", "mediumTime": "HH:mm:ss", "shortTime": "HH:mm"}, "NUMBER_FORMATS": {"DECIMAL_SEP": ",", "GROUP_SEP": ".", "PATTERNS": [
            {"minInt": 1, "minFrac": 0, "macFrac": 0, "posPre": "", "posSuf": "", "negPre": "-", "negSuf": "", "gSize": 3, "lgSize": 3, "maxFrac": 3},
            {"minInt": 1, "minFrac": 2, "macFrac": 0, "posPre": "", "posSuf": " \u00A4", "negPre": "-", "negSuf": " \u00A4", "gSize": 3, "lgSize": 3, "maxFrac": 2}
        ], "CURRENCY_SYM": "Lt"}, "pluralCat": function (n) {
            if ((n % 10) == 1 && ((n % 100) < 11 || (n % 100) > 19)) {
                return PLURAL_CATEGORY.ONE;
            }
            if ((n % 10) >= 2 && (n % 10) <= 9 && ((n % 100) < 11 || (n % 100) > 19) && n == Math.floor(n)) {
                return PLURAL_CATEGORY.FEW;
            }
            return PLURAL_CATEGORY.OTHER;
        }, "id": "lt"});
    }
]);