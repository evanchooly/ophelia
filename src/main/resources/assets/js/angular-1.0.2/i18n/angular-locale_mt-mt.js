angular.module("ngLocale", [], [
    "$provide", function ($provide) {
        var PLURAL_CATEGORY = {ZERO: "zero", ONE: "one", TWO: "two", FEW: "few", MANY: "many", OTHER: "other"};
        $provide.value("$locale", {"NUMBER_FORMATS": {"DECIMAL_SEP": ".", "GROUP_SEP": ",", "PATTERNS": [
            {"minInt": 1, "minFrac": 0, "macFrac": 0, "posPre": "", "posSuf": "", "negPre": "-", "negSuf": "", "gSize": 3, "lgSize": 3, "maxFrac": 3},
            {"minInt": 1, "minFrac": 2, "macFrac": 0, "posPre": "\u00A4", "posSuf": "", "negPre": "\u00A4-", "negSuf": "", "gSize": 3, "lgSize": 3, "maxFrac": 2}
        ], "CURRENCY_SYM": "₤"}, "pluralCat": function (n) {
            if (n == 1) {
                return PLURAL_CATEGORY.ONE;
            }
            if (n == 0 || ((n % 100) >= 2 && (n % 100) <= 4 && n == Math.floor(n))) {
                return PLURAL_CATEGORY.FEW;
            }
            if ((n % 100) >= 11 && (n % 100) <= 19 && n == Math.floor(n)) {
                return PLURAL_CATEGORY.MANY;
            }
            return PLURAL_CATEGORY.OTHER;
        }, "DATETIME_FORMATS": {"MONTH": [
            "Jannar",
            "Frar",
            "Marzu",
            "April",
            "Mejju",
            "Ġunju",
            "Lulju",
            "Awwissu",
            "Settembru",
            "Ottubru",
            "Novembru",
            "Diċembru"
        ], "SHORTMONTH": [
            "Jan",
            "Fra",
            "Mar",
            "Apr",
            "Mej",
            "Ġun",
            "Lul",
            "Aww",
            "Set",
            "Ott",
            "Nov",
            "Diċ"
        ], "DAY": [
            "Il-Ħadd",
            "It-Tnejn",
            "It-Tlieta",
            "L-Erbgħa",
            "Il-Ħamis",
            "Il-Ġimgħa",
            "Is-Sibt"
        ], "SHORTDAY": ["Ħad", "Tne", "Tli", "Erb", "Ħam", "Ġim", "Sib"], "AMPMS": [
            "QN",
            "WN"
        ], "medium": "dd MMM y HH:mm:ss", "short": "dd/MM/yyyy HH:mm", "fullDate": "EEEE, d 'ta'’ MMMM y", "longDate": "d 'ta'’ MMMM y", "mediumDate": "dd MMM y", "shortDate": "dd/MM/yyyy", "mediumTime": "HH:mm:ss", "shortTime": "HH:mm"}, "id": "mt-mt"});
    }
]);