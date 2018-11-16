package ykarav.lb.LoadBalance_Caller;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name="RESPONDER" )
@RibbonClient(name="RESPONDER")
public interface ResponderProxy {

	@RequestMapping("/getPersons")
	public List<Person> persons();
}
