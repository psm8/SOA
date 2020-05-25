package web.rest.providers;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;

@Provider
@Produces("text/uri-list")
public class UriListMessageBodyWriter implements MessageBodyWriter<List<Object>> {
    @Override
    public long getSize(List<Object> obj, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return -1;
    }

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return List.class.isAssignableFrom(type);
    }

    @Override
    public void writeTo(List<Object> url, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
                        MultivaluedMap<String, Object> httpHeaders, OutputStream outputStream) throws WebApplicationException {
        httpHeaders.add("Content-Type", "text/uri-list; charset=UTF-8");
        PrintWriter writer = new PrintWriter(outputStream);
        url.forEach(writer::println);
        writer.flush();
    }
}
