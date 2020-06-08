package br.com.challenge.core.base.jackson.databind.serializer;

import java.io.IOException;

import br.com.challenge.core.util.ApplicationContextUtil;
import org.hashids.Hashids;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;


/**
 * Long serializer to encode internal database ids
 *
 * @author rodrigo.neves
 */
public class HashidSerializer extends JsonSerializer<Long> {
    @Override
    public void serialize(Long value, JsonGenerator jgen, SerializerProvider sp)
            throws IOException {
        if (value != null)
            jgen.writeString(ApplicationContextUtil.getBean(Hashids.class).encode(value));
    }
}
