angular.module("ngLocale", [], [
    "$provide", function ($provide) {
        var PLURAL_CATEGORY = {ZERO: "zero", ONE: "one", TWO: "two", FEW: "few", MANY: "many", OTHER: "other"};
        $provide.value("$locale", {"NUMBER_FORMATS": {"DECIMAL_SEP": "/", "GROUP_SEP": "،", "PATTERNS": [
            {"minInt": 1, "minFrac": 0, "macFrac": 0, "posPre": "", "posSuf": "", "negPre": "‪-", "negSuf": "‬", "gSize": 3, "lgSize": 3, "maxFrac": 3},
            {"minInt": 1, "minFrac": 2, "macFrac": 0, "posPre": "", "posSuf": " \u00A4", "negPre": "‪-", "negSuf": "‬ \u00A4", "gSize": 3, "lgSize": 3, "maxFrac": 2}
        ], "CURRENCY_SYM": "﷼"}, "pluralCat": function (n) {
            return PLURAL_CATEGORY.OTHER;
        }, "DATETIME_FORMATS": {"MONTH": [
            "ژانویهٔ", "فوریهٔ", "مارس", "آوریل", "می", "جون", "جولای", "آگوست", "سپتامبر",
            "اکتبر", "نوامبر", "دسامبر"
        ], "SHORTMONTH": [
            "ژانویهٔ", "فوریهٔ", "مارس", "آوریل", "می", "جون", "جولای", "اوت", "سپتامبر", "اکتبر",
            "نوامبر", "دسامبر"
        ], "DAY": ["یکشنبه", "دوشنبه", "سه‌شنبه", "چهارشنبه", "پنجشنبه", "جمعه", "شنبه"], "SHORTDAY": [
            "یکشنبه", "دوشنبه",
            "سه‌شنبه", "چهارشنبه", "پنجشنبه", "جمعه", "شنبه"
        ], "AMPMS": [
            "قبل از ظهر", "بعد از ظهر"
        ], "medium": "MMM d, y H:mm:ss", "short": "M/d/yy H:mm", "fullDate": "EEEE, MMMM d, y", "longDate": "MMMM d, y", "mediumDate": "MMM d, y", "shortDate": "M/d/yy", "mediumTime": "H:mm:ss", "shortTime": "H:mm"}, "id": "fa-ir"});
    }
]);