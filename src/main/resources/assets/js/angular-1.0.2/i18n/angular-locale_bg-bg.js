angular.module("ngLocale", [], [
    "$provide", function ($provide) {
        var PLURAL_CATEGORY = {ZERO: "zero", ONE: "one", TWO: "two", FEW: "few", MANY: "many", OTHER: "other"};
        $provide.value("$locale", {"NUMBER_FORMATS": {"DECIMAL_SEP": ",", "GROUP_SEP": " ", "PATTERNS": [
            {"minInt": 1, "minFrac": 0, "macFrac": 0, "posPre": "", "posSuf": "", "negPre": "-", "negSuf": "", "gSize": 3, "lgSize": 3, "maxFrac": 3},
            {"minInt": 1, "minFrac": 2, "macFrac": 0, "posPre": "", "posSuf": " \u00A4", "negPre": "-", "negSuf": " \u00A4", "gSize": 3, "lgSize": 3, "maxFrac": 2}
        ], "CURRENCY_SYM": "лв"}, "pluralCat": function (n) {
            if (n == 1) {
                return PLURAL_CATEGORY.ONE;
            }
            return PLURAL_CATEGORY.OTHER;
        }, "DATETIME_FORMATS": {"MONTH": [
            "януари", "февруари", "март", "април", "май", "юни", "юли", "август",
            "септември", "октомври", "ноември", "декември"
        ], "SHORTMONTH": [
            "ян.", "февр.", "март", "апр.", "май", "юни", "юли", "авг.", "септ.", "окт.", "ноем.", "дек."
        ], "DAY": ["неделя", "понеделник", "вторник", "сряда", "четвъртък", "петък", "събота"], "SHORTDAY": [
            "нд", "пн",
            "вт", "ср", "чт", "пт", "сб"
        ], "AMPMS": [
            "пр. об.", "сл. об."
        ], "medium": "dd.MM.yyyy HH:mm:ss", "short": "dd.MM.yy HH:mm", "fullDate": "dd MMMM y, EEEE", "longDate": "dd MMMM y", "mediumDate": "dd.MM.yyyy", "shortDate": "dd.MM.yy", "mediumTime": "HH:mm:ss", "shortTime": "HH:mm"}, "id": "bg-bg"});
    }
]);