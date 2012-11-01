package controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;

import com.fasterxml.jackson.databind.SerializationFeature;

public class JacksonBodyReader implements MessageBodyReader<Object> {
  private JacksonMapper mapper;

  public JacksonBodyReader() {
    mapper = new JacksonMapper();
    mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
  }

  @Override
  public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
    return true;
  }

  @Override
  public Object readFrom(Class<Object> type, Type genericType, Annotation[] annotations, MediaType mediaType,
    MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException, WebApplicationException {
    int available = entityStream.available();
    if (available > 0) {
      BufferedReader reader = new BufferedReader(new InputStreamReader(entityStream));
      String s = reader.readLine();
      Object o = mapper.convertValue(s, type);
      return o;
    }
    return null;
  }
}