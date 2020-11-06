package org.example.paho;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

// Watson IoT Platform Quickstart にデータを送信する
// camel-pahoをpom.xmlに追加する必要あり
public class PahoRouteBuilder extends RouteBuilder {

    // deviceId
    String deviceId = "shti_f";

    // Quickstart
    String brokerUrl = "tcp://quickstart.messaging.internetofthings.ibmcloud.com";
    String clientId = String.format("d:quickstart:myDevice:%s", deviceId);
    String topic = "iot-2/evt/myEvent/fmt/json";
    int qos = 0;

    public void configure() {

        String pahoUri = "paho:" + topic;
        pahoUri += "?brokerUrl=" + brokerUrl;
        pahoUri += "&clientId=" + clientId;
        pahoUri += "&qos=" + qos;

        System.out.println("paho: " + pahoUri);

        // Publish message with Paho
        from("timer://sample?period=1000&repeatCount=5").routeId("pahoRoute")
                .setBody().header(Exchange.TIMER_COUNTER)
                .setBody().simple("{\"d\":{\"status\":${body}}}")
                .log("body -> ${body}")
                .to(pahoUri)
                .log("done");

    }
}
