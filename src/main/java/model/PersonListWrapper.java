package model;

import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

//Helper class to wrap a list of persons - for saving to XML
@XmlRootElement(name = "persons")
public class PersonListWrapper {

	private List<Person> persons;
	
	@XmlElement(name = "person")
	public List<Person> getPersons(){
		return persons;
	}
	
	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}
}
