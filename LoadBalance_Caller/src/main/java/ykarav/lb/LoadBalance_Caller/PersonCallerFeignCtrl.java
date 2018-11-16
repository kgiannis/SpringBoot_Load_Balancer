package ykarav.lb.LoadBalance_Caller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonCallerFeignCtrl {

	@Autowired
	ResponderProxy proxy;
	
	@RequestMapping("/caller/feign/findAll")
	public List<Person> findAll(){
		return proxy.persons();
	}
}
