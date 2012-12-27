package com.antwerkz.ophelia;

import com.antwerkz.ophelia.controllers.JacksonMapper;
import com.antwerkz.ophelia.models.ConnectionInfo;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class JacksonBodyReaderTest {
    @Test
    public void decode() {
        JacksonMapper mapper = new JacksonMapper();
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        String string = "queryString=db.ConnectedAccounts.find()&limit=100&showCount=on&bookmark=";
        String[] split = string.split("&");
        Map<String, String> map = new HashMap<>();
        for (String s : split) {
            String[] values = s.split("=", 2);
            map.put(values[0], values[1]);
        }
        ConnectionInfo info = mapper.convertValue(map, ConnectionInfo.class);
        Assert.assertEquals(info.getQueryString(), "db.ConnectedAccounts.find()");
    }
}
