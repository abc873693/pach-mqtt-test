package com.raimvititor.paho_mqtt_test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.raimvititor.paho_mqtt_test.libs.MqttHelper;
import com.raimvititor.paho_mqtt_test.libs.Utils;
import com.raimvititor.paho_mqtt_test.models.Author;
import com.raimvititor.paho_mqtt_test.models.Message;
import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.messagesList)
    MessagesList messagesList;
    @BindView(R.id.input)
    MessageInput messageInput;

    String topic = "$SYS/RainVisitor";

    private List<Message> chatList = new ArrayList<>();
    private MessagesListAdapter<Message> adapter;
    private MqttClient mqttClient;
    private Author user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initMqtt();
        initMessageList();
    }

    private void initMqtt() {
        mqttClient = MqttHelper.connect((int) new Date().getTime() + "");
        mqttClient.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) {
                if (topic.equals(MainActivity.this.topic)) {
                    final Message receiveMessage = new Gson().fromJson(message.toString(), Message.class);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.addToStart(receiveMessage, true);
                        }
                    });
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
        try {
            mqttClient.subscribe(topic);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private void initMessageList() {
        user = new Author((int) new Date().getTime(), "user", "");
        ImageLoader imageLoader = new ImageLoader() {
            @Override
            public void loadImage(ImageView imageView, String url) {
                Picasso.with(MainActivity.this).load(R.mipmap.ic_launcher).into(imageView);
            }

        };
        MessagesListAdapter.HoldersConfig holdersConfig = new MessagesListAdapter.HoldersConfig();
        adapter = new MessagesListAdapter<>(user.getId(), holdersConfig, imageLoader);
        adapter.addToEnd(chatList, true);
        messagesList.setAdapter(adapter);
        messageInput.setInputListener(new MessageInput.InputListener() {
            @Override
            public boolean onSubmit(CharSequence input) {
                //validate and send message
                if (user.name.isEmpty()) {
                    user.name = input.toString();
                } else {
                    final Message message = new Message(1, input.toString(), user, Utils.convertChatTime(new Date()));
                    try {
                        MqttMessage mqttMessage = new MqttMessage(new Gson().toJson(message).getBytes());
                        mqttMessage.setQos(2);
                        mqttClient.publish(topic, new MqttMessage(mqttMessage.getPayload()));
                    } catch (MqttException e) {
                        Toast.makeText(MainActivity.this, "錯誤", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
                return true;
            }
        });
    }
}
