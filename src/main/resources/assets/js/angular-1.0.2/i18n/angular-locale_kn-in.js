angular.module("ngLocale", [], [
    "$provide", function ($provide) {
        var PLURAL_CATEGORY = {ZERO: "zero", ONE: "one", TWO: "two", FEW: "few", MANY: "many", OTHER: "other"};
        $provide.value("$locale", {"NUMBER_FORMATS": {"DECIMAL_SEP": ".", "GROUP_SEP": ",", "PATTERNS": [
            {"minInt": 1, "minFrac": 0, "macFrac": 0, "posPre": "", "posSuf": "", "negPre": "-", "negSuf": "", "gSize": 2, "lgSize": 3, "maxFrac": 3},
            {"minInt": 1, "minFrac": 2, "macFrac": 0, "posPre": "\u00A4 ", "posSuf": "", "negPre": "\u00A4 -", "negSuf": "", "gSize": 2, "lgSize": 3, "maxFrac": 2}
        ], "CURRENCY_SYM": "Rs"}, "pluralCat": function (n) {
            return PLURAL_CATEGORY.OTHER;
        }, "DATETIME_FORMATS": {"MONTH": [
            "ಜನವರೀ", "ಫೆಬ್ರವರೀ", "ಮಾರ್ಚ್", "ಎಪ್ರಿಲ್", "ಮೆ", "ಜೂನ್", "ಜುಲೈ", "ಆಗಸ್ಟ್",
            "ಸಪ್ಟೆಂಬರ್", "ಅಕ್ಟೋಬರ್", "ನವೆಂಬರ್", "ಡಿಸೆಂಬರ್"
        ], "SHORTMONTH": [
            "ಜನವರೀ", "ಫೆಬ್ರವರೀ", "ಮಾರ್ಚ್", "ಎಪ್ರಿಲ್", "ಮೆ", "ಜೂನ್", "ಜುಲೈ", "ಆಗಸ್ಟ್", "ಸಪ್ಟೆಂಬರ್",
            "ಅಕ್ಟೋಬರ್", "ನವೆಂಬರ್", "ಡಿಸೆಂಬರ್"
        ], "DAY": ["ರವಿವಾರ", "ಸೋಮವಾರ", "ಮಂಗಳವಾರ", "ಬುಧವಾರ", "ಗುರುವಾರ", "ಶುಕ್ರವಾರ", "ಶನಿವಾರ"], "SHORTDAY": [
            "ರ.", "ಸೋ.",
            "ಮಂ.", "ಬು.", "ಗು.", "ಶು.", "ಶನಿ."
        ], "AMPMS": [
            "am", "pm"
        ], "medium": "d MMM y hh:mm:ss a", "short": "d-M-yy hh:mm a", "fullDate": "EEEE d MMMM y", "longDate": "d MMMM y", "mediumDate": "d MMM y", "shortDate": "d-M-yy", "mediumTime": "hh:mm:ss a", "shortTime": "hh:mm a"}, "id": "kn-in"});
    }
]);