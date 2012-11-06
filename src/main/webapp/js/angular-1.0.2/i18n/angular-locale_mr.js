angular.module("ngLocale", [], [
    "$provide", function ($provide) {
        var PLURAL_CATEGORY = {ZERO:"zero", ONE:"one", TWO:"two", FEW:"few", MANY:"many", OTHER:"other"};
        $provide.value("$locale", {"DATETIME_FORMATS":{"MONTH":[
            "जानेवारी", "फेब्रुवारी", "मार्च", "एप्रिल", "मे", "जून", "जुलै", "ऑगस्ट",
            "सप्टेंबर", "ऑक्टोबर", "नोव्हेंबर", "डिसेंबर"
        ], "SHORTMONTH":[
            "जानेवारी", "फेब्रुवारी", "मार्च", "एप्रिल", "मे", "जून", "जुलै", "ऑगस्ट", "सप्टेंबर",
            "ऑक्टोबर", "नोव्हेंबर", "डिसेंबर"
        ], "DAY":["रविवार", "सोमवार", "मंगळवार", "बुधवार", "गुरुवार", "शुक्रवार", "शनिवार"], "SHORTDAY":[
            "रवि", "सोम",
            "मंगळ", "बुध", "गुरु", "शुक्र", "शनि"
        ], "AMPMS":[
            "am", "pm"
        ], "medium":"d MMM y h-mm-ss a", "short":"d-M-yy h-mm a", "fullDate":"EEEE d MMMM y", "longDate":"d MMMM y", "mediumDate":"d MMM y", "shortDate":"d-M-yy", "mediumTime":"h-mm-ss a", "shortTime":"h-mm a"}, "NUMBER_FORMATS":{"DECIMAL_SEP":".", "GROUP_SEP":",", "PATTERNS":
            [
                {"minInt":1, "minFrac":0, "macFrac":0, "posPre":"", "posSuf":"", "negPre":"-", "negSuf":"", "gSize":2, "lgSize":3, "maxFrac":3},
                {"minInt":1, "minFrac":2, "macFrac":0, "posPre":"\u00A4 ", "posSuf":"", "negPre":"\u00A4 -", "negSuf":"", "gSize":2, "lgSize":3, "maxFrac":2}
            ], "CURRENCY_SYM":"Rs"}, "pluralCat":function (n) {
            if (n == 1) {
                return PLURAL_CATEGORY.ONE;
            }
            return PLURAL_CATEGORY.OTHER;
        }, "id":"mr"});
    }
]);