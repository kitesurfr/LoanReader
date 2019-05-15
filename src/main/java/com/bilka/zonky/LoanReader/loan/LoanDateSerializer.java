package com.bilka.zonky.LoanReader.loan;

import java.io.IOException;
import java.time.ZonedDateTime;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class LoanDateSerializer  extends StdSerializer<ZonedDateTime>  {

	private static final long serialVersionUID = 1L;

	public LoanDateSerializer() {
	    super(ZonedDateTime.class);
	}
	
	@Override
	public void serialize(ZonedDateTime value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeString(LoanDateParser.LOAN_DATE_FORMATTER.format(value));
	}

}
