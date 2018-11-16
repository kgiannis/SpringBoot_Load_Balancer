package ykarav.lb.LoadBalance_Responder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonResponderCtrl {
	
	@Autowired
	PersonService srv;
	

	@RequestMapping("/getPersons")
	public List<Person> getPersons(){
		List<Person> persons = new ArrayList<Person>();
		persons = srv.generatePersons();
		return persons;
	}
}
