angular.module("ngLocale", [], [
    "$provide", function ($provide) {
        var PLURAL_CATEGORY = {ZERO: "zero", ONE: "one", TWO: "two", FEW: "few", MANY: "many", OTHER: "other"};
        $provide.value("$locale", {"DATETIME_FORMATS": {"MONTH": [
            "janar",
            "shkurt",
            "mars",
            "prill",
            "maj",
            "qershor",
            "korrik",
            "gusht",
            "shtator",
            "tetor",
            "nëntor",
            "dhjetor"
        ], "SHORTMONTH": [
            "Jan",
            "Shk",
            "Mar",
            "Pri",
            "Maj",
            "Qer",
            "Kor",
            "Gsh",
            "Sht",
            "Tet",
            "Nën",
            "Dhj"
        ], "DAY": ["e diel", "e hënë", "e martë", "e mërkurë", "e enjte", "e premte", "e shtunë"], "SHORTDAY": [
            "Die",
            "Hën",
            "Mar",
            "Mër",
            "Enj",
            "Pre",
            "Sht"
        ], "AMPMS": [
            "PD",
            "MD"
        ], "medium": "yyyy-MM-dd h.mm.ss.a", "short": "yy-MM-dd h.mm.a", "fullDate": "EEEE, dd MMMM y", "longDate": "dd MMMM y", "mediumDate": "yyyy-MM-dd", "shortDate": "yy-MM-dd", "mediumTime": "h.mm.ss.a", "shortTime": "h.mm.a"}, "NUMBER_FORMATS": {"DECIMAL_SEP": ",", "GROUP_SEP": ".", "PATTERNS": [
            {"minInt": 1, "minFrac": 0, "macFrac": 0, "posPre": "", "posSuf": "", "negPre": "-", "negSuf": "", "gSize": 3, "lgSize": 3, "maxFrac": 3},
            {"minInt": 1, "minFrac": 2, "macFrac": 0, "posPre": "\u00A4", "posSuf": "", "negPre": "\u00A4-", "negSuf": "", "gSize": 3, "lgSize": 3, "maxFrac": 2}
        ], "CURRENCY_SYM": "Lek"}, "pluralCat": function (n) {
            if (n == 1) {
                return PLURAL_CATEGORY.ONE;
            }
            return PLURAL_CATEGORY.OTHER;
        }, "id": "sq"});
    }
]);