angular.module("ngLocale", [], [
    "$provide", function ($provide) {
        var PLURAL_CATEGORY = {ZERO: "zero", ONE: "one", TWO: "two", FEW: "few", MANY: "many", OTHER: "other"};
        $provide.value("$locale", {"DATETIME_FORMATS": {"MONTH": [
            "มกราคม", "กุมภาพันธ์", "มีนาคม", "เมษายน", "พฤษภาคม", "มิถุนายน", "กรกฎาคม",
            "สิงหาคม", "กันยายน", "ตุลาคม", "พฤศจิกายน", "ธันวาคม"
        ], "SHORTMONTH": [
            "ม.ค.", "ก.พ.", "มี.ค.", "เม.ย.", "พ.ค.", "มิ.ย.", "ก.ค.", "ส.ค.", "ก.ย.", "ต.ค.", "พ.ย.",
            "ธ.ค."
        ], "DAY": [
            "วันอาทิตย์",
            "วันจันทร์",
            "วันอังคาร",
            "วันพุธ",
            "วันพฤหัสบดี",
            "วันศุกร์",
            "วันเสาร์"
        ], "SHORTDAY": [
            "อา.", "จ.", "อ.", "พ.", "พฤ.", "ศ.", "ส."
        ], "AMPMS": [
            "ก่อนเที่ยง", "หลังเที่ยง"
        ], "medium": "d MMM y H:mm:ss", "short": "d/M/yyyy H:mm", "fullDate": "EEEEที่ d MMMM G y", "longDate": "d MMMM y", "mediumDate": "d MMM y", "shortDate": "d/M/yyyy", "mediumTime": "H:mm:ss", "shortTime": "H:mm"}, "NUMBER_FORMATS": {"DECIMAL_SEP": ".", "GROUP_SEP": ",", "PATTERNS": [
            {"minInt": 1, "minFrac": 0, "macFrac": 0, "posPre": "", "posSuf": "", "negPre": "-", "negSuf": "", "gSize": 3, "lgSize": 3, "maxFrac": 3},
            {"minInt": 1, "minFrac": 2, "macFrac": 0, "posPre": "\u00A4", "posSuf": "", "negPre": "\u00A4-", "negSuf": "", "gSize": 3, "lgSize": 3, "maxFrac": 2}
        ], "CURRENCY_SYM": "฿"}, "pluralCat": function (n) {
            return PLURAL_CATEGORY.OTHER;
        }, "id": "th"});
    }
]);