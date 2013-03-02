package com.antwerkz.ophelia.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

import com.antwerkz.ophelia.models.Query;
import com.fasterxml.jackson.databind.SerializationFeature;

@Provider
@Consumes(MediaType.APPLICATION_JSON)
public class JacksonBodyReader implements MessageBodyReader<Query> {
    private JacksonMapper mapper;

    public JacksonBodyReader() {
        mapper = new JacksonMapper();
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }

    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return Query.class.equals(type);
    }

    @Override
    public Query readFrom(Class<Query> type, Type genericType, Annotation[] annotations,
        MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
        throws IOException, WebApplicationException {
        if (entityStream.available() > 0) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(entityStream));
            String content = reader.readLine();
            return mapper.readValue(content, Query.class);
        }
        return null;
    }
}