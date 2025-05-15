package util;

import java.time.LocalDate;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

//Adapter to convert between the LocalDate and the ISO 8601 String representation of the date such as yyyy-mm-dd

public class LocalDateAdapter extends XmlAdapter<String, LocalDate>{

	@Override
	public LocalDate unmarshal(String v) throws Exception{
		return LocalDate.parse(v);
	}

	@Override
	public String marshal(LocalDate v) throws Exception {
		return v.toString();
	}
}
