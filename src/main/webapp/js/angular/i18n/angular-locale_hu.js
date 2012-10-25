angular.module("ngLocale", [], [
    "$provide", function ($provide) {
        var PLURAL_CATEGORY = {ZERO:"zero", ONE:"one", TWO:"two", FEW:"few", MANY:"many", OTHER:"other"};
        $provide.value("$locale", {"DATETIME_FORMATS":{"MONTH":[
            "január", "február", "március", "április", "május", "június", "július",
            "augusztus", "szeptember", "október", "november", "december"
        ], "SHORTMONTH":[
            "jan.", "febr.", "márc.", "ápr.", "máj.", "jún.", "júl.", "aug.", "szept.", "okt.", "nov.",
            "dec."
        ], "DAY":["vasárnap", "hétfő", "kedd", "szerda", "csütörtök", "péntek", "szombat"], "SHORTDAY":[
            "V", "H", "K",
            "Sze", "Cs", "P", "Szo"
        ], "AMPMS":[
            "de.", "du."
        ], "medium":"yyyy.MM.dd. H:mm:ss", "short":"yyyy.MM.dd. H:mm", "fullDate":"y. MMMM d., EEEE", "longDate":"y. MMMM d.", "mediumDate":"yyyy.MM.dd.", "shortDate":"yyyy.MM.dd.", "mediumTime":"H:mm:ss", "shortTime":"H:mm"}, "NUMBER_FORMATS":{"DECIMAL_SEP":",", "GROUP_SEP":" ", "PATTERNS":
            [
                {"minInt":1, "minFrac":0, "macFrac":0, "posPre":"", "posSuf":"", "negPre":"-", "negSuf":"", "gSize":3, "lgSize":3, "maxFrac":3},
                {"minInt":1, "minFrac":2, "macFrac":0, "posPre":"", "posSuf":" \u00A4", "negPre":"-", "negSuf":" \u00A4", "gSize":3, "lgSize":3, "maxFrac":2}
            ], "CURRENCY_SYM":"Ft"}, "pluralCat":function (n) {
            return PLURAL_CATEGORY.OTHER;
        }, "id":"hu"});
    }
]);