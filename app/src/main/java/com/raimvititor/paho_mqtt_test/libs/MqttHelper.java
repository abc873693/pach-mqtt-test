package com.raimvititor.paho_mqtt_test.libs;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttHelper {

    private static String BROKER = "tcp://iot.eclipse.org:1883";

    public static MqttClient connect(String clientId) {
        MemoryPersistence persistence = new MemoryPersistence();
        try {
            MqttClient sampleClient = new MqttClient(BROKER, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            sampleClient.connect(connOpts);
            return sampleClient;
        } catch (MqttException me) {
            me.printStackTrace();
        }
        return null;
    }
}
