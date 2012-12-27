package com.antwerkz.ophelia.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializationFeature;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Map;

//@Provider
//@Consumes(MediaType.APPLICATION_JSON)
public class JacksonBodyReader implements MessageBodyReader<Map<String, String>> {
    private JacksonMapper mapper;

    public JacksonBodyReader() {
        mapper = new JacksonMapper();
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }

    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return false;
    }

    @Override
    public Map<String, String> readFrom(Class<Map<String, String>> type, Type genericType, Annotation[] annotations,
                                        MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
            throws IOException, WebApplicationException {
        int available = entityStream.available();
        if (available > 0) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(entityStream));
            String s = reader.readLine();
            JsonNode o = mapper.readTree(new StringReader(s));
            return null;
        }
        return null;
    }
}