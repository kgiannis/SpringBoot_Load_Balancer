## Configure Eureka Server


#### 1. application.properties file

Set application name
*spring.application.name*
 
Set application server port
*server.port*

Set default zone in case there are many Eureka servers
*eureka.client.serviceUrl.defaultZone*

Set if Eureka server will also be registered as client
*eureka.client.register-with-eureka*
*eureka.client.fetch-registry*



#### 2. class LoadBalanceEurekaApplication

Add annotation to mark app as Eureka server
*@EnableEurekaServer*