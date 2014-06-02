angular.module("ngLocale", [], [
    "$provide", function ($provide) {
        var PLURAL_CATEGORY = {ZERO: "zero", ONE: "one", TWO: "two", FEW: "few", MANY: "many", OTHER: "other"};
        $provide.value("$locale", {"DATETIME_FORMATS": {"MONTH": [
            "జనవరి", "ఫిబ్రవరి", "మార్చి", "ఏప్రిల్", "మే", "జూన్", "జూలై", "ఆగస్టు",
            "సెప్టెంబర్", "అక్టోబర్", "నవంబర్", "డిసెంబర్"
        ], "SHORTMONTH": [
            "జనవరి", "ఫిబ్రవరి", "మార్చి", "ఏప్రిల్", "మే", "జూన్", "జూలై", "ఆగస్టు", "సెప్టెంబర్",
            "అక్టోబర్", "నవంబర్", "డిసెంబర్"
        ], "DAY": ["ఆదివారం", "సోమవారం", "మంగళవారం", "బుధవారం", "గురువారం", "శుక్రవారం", "శనివారం"], "SHORTDAY": [
            "ఆది",
            "సోమ", "మంగళ", "బుధ", "గురు", "శుక్ర", "శని"
        ], "AMPMS": [
            "ఉ", "సా"
        ], "medium": "d MMM y h:mm:ss a", "short": "dd-MM-yy h:mm a", "fullDate": "EEEE d MMMM y", "longDate": "d MMMM y", "mediumDate": "d MMM y", "shortDate": "dd-MM-yy", "mediumTime": "h:mm:ss a", "shortTime": "h:mm a"}, "NUMBER_FORMATS": {"DECIMAL_SEP": ".", "GROUP_SEP": ",", "PATTERNS": [
            {"minInt": 1, "minFrac": 0, "macFrac": 0, "posPre": "", "posSuf": "", "negPre": "-", "negSuf": "", "gSize": 2, "lgSize": 3, "maxFrac": 3},
            {"minInt": 1, "minFrac": 2, "macFrac": 0, "posPre": "\u00A4 ", "posSuf": "", "negPre": "\u00A4 -", "negSuf": "", "gSize": 2, "lgSize": 3, "maxFrac": 2}
        ], "CURRENCY_SYM": "Rs"}, "pluralCat": function (n) {
            if (n == 1) {
                return PLURAL_CATEGORY.ONE;
            }
            return PLURAL_CATEGORY.OTHER;
        }, "id": "te"});
    }
]);