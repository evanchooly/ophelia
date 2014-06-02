angular.module("ngLocale", [], [
    "$provide", function ($provide) {
        var PLURAL_CATEGORY = {ZERO: "zero", ONE: "one", TWO: "two", FEW: "few", MANY: "many", OTHER: "other"};
        $provide.value("$locale", {"NUMBER_FORMATS": {"DECIMAL_SEP": ".", "GROUP_SEP": ",", "PATTERNS": [
            {"minInt": 1, "minFrac": 0, "macFrac": 0, "posPre": "", "posSuf": "", "negPre": "-", "negSuf": "", "gSize": 3, "lgSize": 3, "maxFrac": 3},
            {"minInt": 1, "minFrac": 2, "macFrac": 0, "posPre": "\u00A4", "posSuf": "", "negPre": "\u00A4-", "negSuf": "", "gSize": 3, "lgSize": 3, "maxFrac": 2}
        ], "CURRENCY_SYM": "Rs."}, "pluralCat": function (n) {
            if (n == 1) {
                return PLURAL_CATEGORY.ONE;
            }
            return PLURAL_CATEGORY.OTHER;
        }, "DATETIME_FORMATS": {"MONTH": [
            "جنوری",
            "فروری",
            "مار چ",
            "اپريل",
            "مئ",
            "جون",
            "جولائ",
            "اگست",
            "ستمبر",
            "اکتوبر",
            "نومبر",
            "دسمبر"
        ], "SHORTMONTH": [
            "جنوری",
            "فروری",
            "مار چ",
            "اپريل",
            "مئ",
            "جون",
            "جولائ",
            "اگست",
            "ستمبر",
            "اکتوبر",
            "نومبر",
            "دسمبر"
        ], "DAY": ["اتوار", "پير", "منگل", "بده", "جمعرات", "جمعہ", "ہفتہ"], "SHORTDAY": [
            "اتوار",
            "پير",
            "منگل",
            "بده",
            "جمعرات",
            "جمعہ",
            "ہفتہ"
        ], "AMPMS": [
            "قبل دوپہر",
            "بعد دوپہر"
        ], "medium": "d, MMM y h:mm:ss a", "short": "d/M/yy h:mm a", "fullDate": "EEEE, d, MMMM y", "longDate": "d, MMMM y", "mediumDate": "d, MMM y", "shortDate": "d/M/yy", "mediumTime": "h:mm:ss a", "shortTime": "h:mm a"}, "id": "ur-pk"});
    }
]);