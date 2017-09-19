# spring-boot-amqp-messaging

This is a simple spring-boot app that shows how to configure easily RabbitMQ with AMQP for producing and consuming messages
in default format (java serialized) and JSON.

In this sample project every message is sent as JSON and then decoded on one queue as a generic `Message` object and in the other 
one as the original specific class.

If you want to have more information, with full explanation of this code, you can find it 
[on my blog](http://dev.macero.es/2016/10/23/produce-and-consume-json-messages-with-spring-boot-amqp/).
 


To consume this service from play

	public Result testSendPojo() {
		System.out.println("in testSendPojo");
		Map<String, String> params = new HashMap<String, String>();
		params.put("user", "kstest");
		params.put("today", String.valueOf(new Date()));
		
		NtfMessage message = new NtfMessage("hello-world.html", true, false, false, "1@1.com", "4@2.com,5@3.com", "", "subject", true, null, params);
		ws.url(RABBIT_CLIENT_URL + "send/").post(Json.toJson(message));
		System.out.println("out testSendPojo");
		return ok();
	}
