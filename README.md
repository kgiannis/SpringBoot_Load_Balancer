# SpringBoot_Load_Balancer
A Spring Boot project demonstrating the usage of: 

- **Ribbon**  (Load Balancer) 
- **Eureka**  (Service Discovery) 
- **Feign**   (Declarative Rest Client)

In this project you can see how to use Spring Boot and have a client side load balancer. We will use two services:

- LoadBalance_Caller
- LoadBalance_Responder

Both services will register themselves on Eureka server:

- LoadBalancer_Eureka

Once all are up and running, you can see in action how the load balancer can route the incoming requests to the registered services.

# Steps to get started

First start the **LoadBalancer_Eureka**. This is your Service Discovery server. 

Both our services (**LoadBalance_Caller**, **LoadBalance_Responder**) needs to register themselves on the Discovery server.
So start those services after you have started the Eureka server.

## LoadBalancer_Eureka - Configuration

Check file: **application.properties**

We have the following:
```
# Application Info
spring.application.name=YkaravEurekaServer
server.port=9000

# Eureka Server Info
eureka.client.serviceUrl.defaultZone=http://localhost:9000/eureka/
eureka.client.register-with-eureka=false
eureka.client.fetch-registry=false
```

Explanation of the properties:

| Property | Explanation |
|----------|-------------|
|spring.application.name|The name of our application|
|server.port|The port on which our Eureka server is running|
|eureka.client.serviceUrl.defaultZone|Set default zone in case there are many Eureka servers|
|eureka.client.register-with-eureka|Set if Eureka server will also be registered as client|
|eureka.client.fetch-registry|Set if Eureka server will register itself on the service registry|

We alse have to set the application as Eureka server. 

Use annotation **@EnableEurekaServer** on class 
```
LoadBalanceEurekaApplication
```

And that's it! Our Eureka server is ready.

## LoadBalancer_Responder - Configuration

Check file: **application.properties**

We have the following:
```
# Application Info
spring.application.name=responder
server.port=8082

# Eureka Server Info
eureka.instance.hostname=localhost
eureka.client.serviceUrl.defaultZone=http://localhost:9000/eureka/
```

Explanation of the properties:

| Property | Explanation |
|----------|-------------|
|spring.application.name|The name of our application|
|server.port|The port on which our Eureka server is running|
|eureka.instance.hostname|The hostname of our eureka instance|
|eureka.client.serviceUrl.defaultZone|URL of the eureka server|

We have to register our service so it can be traced by our Eureka server. 

On class **LoadBalanceResponderApplication**, use annotiation **@EnableDiscoveryClient** to activate the Netflix Eureka DiscoveryClient implementation.

## LoadBalancer_Caller - Configuration

Check file: **application.properties**

We have the following:
```
# Application Info
spring.application.name=caller
server.port=8081

# Eureka Server Info
eureka.instance.hostname=localhost
eureka.client.serviceUrl.defaultZone=http://localhost:9000/eureka/
```

Explanation is the same as on LoadBalancer_Responder.

We have to register our service so it can be traced by our Eureka server. 

On class **LoadBalanceCallerApplication**, use annotiation **@EnableDiscoveryClient** to activate the Netflix Eureka DiscoveryClient implementation.

We are going to use Feign so we also have to activate it. 
Use annotation  **@EnableFeignClients**.

# The magic part

Let's see class **PersonCallerFeignCtrl**

```
@RestController
public class PersonCallerFeignCtrl {

	@Autowired
	ResponderProxy proxy;
	
	@RequestMapping("/caller/feign/findAll")
	public List<Person> findAll(){
		return proxy.persons();
	}
}
```

When the user calls 
```
http://localhost:8081//caller/feign/findAll
```

he is actually calling for:
```
http://localhost:8082/getPersons
```

How is it done? This is the way:

The method _findAll_ returns the result of the _persons_ method of instance **proxy**.

Looking at class **ResponderProxy** 
```
@FeignClient(name="RESPONDER" )
@RibbonClient(name="RESPONDER")
public interface ResponderProxy {

	@RequestMapping("/getPersons")
	public List<Person> persons();
}
```
We can see that method _persons_ maps to request: /getPersons. And _proxy_ instance is basically calling for service with name **responder**.
Which is already registered on our Eureka server.

