package com.kd.utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;


public class KafkaConsumerHelper {
	private static KafkaConsumerHelper instance = new KafkaConsumerHelper();

	public static KafkaConsumerHelper getInstance() {
		return instance;
	}

    private static KafkaConsumer<String, String> kc= null;
    public KafkaConsumer<String, String> getConsumer(String kafkaHost, String groupId) {
        if(kc == null) {
            Properties props = new Properties();
            props.put("bootstrap.servers", kafkaHost);
            props.put("group.id", groupId);
            props.put("enable.auto.commit", "true");
            props.put("auto.commit.interval.ms", "1000");
            props.put("session.timeout.ms", "30000");
            props.put("receive.buffer.bytes", 10485760);
            props.put("max.partition.fetch.bytes", 8*1024*1024);
           // props.setProperty("auto.offset.reset", "earliest");
           // props.put("auto.offset.reset", "earliest");
            props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            kc = new KafkaConsumer<String, String>(props);
        }
        return kc;
    }

    public static void main(String[] args) throws IOException {
    	String topic = "test";

        KafkaConsumer<String, String> consumer = KafkaConsumerHelper.getInstance().getConsumer("10.27.224.63:9192", topic);
        consumer.subscribe(Arrays.asList(topic));
        int messagecounter = 0;
        while(true) {
            ConsumerRecords<String, String> records = consumer.poll(1000);
            for(ConsumerRecord<String, String> record : records) {
            	System.out.println(record.value());
            	messagecounter++;
            }
            System.out.println("received messages:"+messagecounter);
        }

    }
}
