package com.bilka.zonky.LoanReader.loan;

import java.io.IOException;
import java.time.ZonedDateTime;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class LoanDateDeserializer extends StdDeserializer<ZonedDateTime>  {

	private static final long serialVersionUID = 1L;

	
	public LoanDateDeserializer() {
	    super(ZonedDateTime.class);
	}

	@Override
	public ZonedDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		return LoanDateParser.parse(p.getText());
	}

}
