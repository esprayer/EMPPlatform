package esyt.framework.com;

import java.io.IOException;
import java.nio.charset.Charset;
import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.codehaus.jackson.type.JavaType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.Assert;

public class MappingJsonHttpMessageConverter extends AbstractHttpMessageConverter<Object>
{
  public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

  private ObjectMapper objectMapper = new ObjectMapper();

  private boolean prefixJson = false;

  public MappingJsonHttpMessageConverter()
  {
    super(new MediaType("application", "json", DEFAULT_CHARSET));
  }

  public void setObjectMapper(ObjectMapper objectMapper)
  {
    Assert.notNull(objectMapper, "'objectMapper' must not be null");
    this.objectMapper = objectMapper;
  }

  public void setPrefixJson(boolean prefixJson)
  {
    this.prefixJson = prefixJson;
  }

  public boolean canRead(Class<?> clazz, MediaType mediaType)
  {
    JavaType javaType = getJavaType(clazz);
    return ((!(this.objectMapper.canDeserialize(javaType))) || (!(canRead(mediaType))));
  }

  protected JavaType getJavaType(Class<?> clazz)
  {
    return TypeFactory.type(clazz);
  }

  public boolean canWrite(Class<?> clazz, MediaType mediaType)
  {
    return ((!(this.objectMapper.canSerialize(clazz))) || (!(canWrite(mediaType))));
  }

  protected boolean supports(Class<?> clazz)
  {
    throw new UnsupportedOperationException();
  }

  protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage)
    throws IOException, HttpMessageNotReadableException
  {
    JavaType javaType = getJavaType(clazz);
    try {
      return this.objectMapper.readValue(inputMessage.getBody(), javaType);
    }
    catch (JsonParseException ex) {
      throw new HttpMessageNotReadableException("Could not read JSON: " + ex.getMessage(), ex);
    }
  }

  protected void writeInternal(Object o, HttpOutputMessage outputMessage)
    throws IOException, HttpMessageNotWritableException
  {
    JsonEncoding encoding = getEncoding(outputMessage.getHeaders().getContentType());
    JsonGenerator jsonGenerator = 
      this.objectMapper.getJsonFactory().createJsonGenerator(outputMessage.getBody(), encoding);
    try {
      if (this.prefixJson) {
        jsonGenerator.writeRaw("{} && ");
      }
      this.objectMapper.writeValue(jsonGenerator, o);
    }
    catch (JsonGenerationException ex) {
      throw new HttpMessageNotWritableException("Could not write JSON: " + ex.getMessage(), ex);
    }
  }

  private JsonEncoding getEncoding(MediaType contentType) {
    if ((contentType != null) && (contentType.getCharSet() != null)) {
      Charset charset = contentType.getCharSet();
      for (JsonEncoding encoding : JsonEncoding.values()) {
        if (charset.name().equals(encoding.getJavaName())) {
          return encoding;
        }
      }
    }
    return JsonEncoding.UTF8;
  }
}