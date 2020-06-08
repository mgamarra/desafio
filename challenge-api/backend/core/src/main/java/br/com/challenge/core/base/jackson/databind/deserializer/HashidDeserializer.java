package br.com.challenge.core.base.jackson.databind.deserializer;

import java.io.IOException;

import br.com.challenge.core.util.ApplicationContextUtil;
import org.hashids.Hashids;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;


/**
 * Long id json to decode public hashed ids to internal long ids
 *
 * @author rodrigo.neves
 */
public class HashidDeserializer extends JsonDeserializer<Long> {
	@Override
	public Long deserialize(JsonParser jsonparser, DeserializationContext deserializationcontext)
			throws IOException {
		String encoded = jsonparser.getText();
		long[] id = ApplicationContextUtil.getBean(Hashids.class).decode(encoded);
		return id.length > 0 ? id[0] : null;
	}
}
