package ykarav.lb.LoadBalance_Responder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PersonService {
	
	@Value("${server.port}")
	private String port;
	
	public List<Person> generatePersons(){
		List<Person> persons = new ArrayList<Person>();
		
		Person p1 = new Person("Giannis", 38);
		Person p2 = new Person("Angeliki", 30);

		p1.setServerPort(port);
		p2.setServerPort(port);
		
		persons.add(p1);
		persons.add(p2);
		
		return persons;
	}
	
}
