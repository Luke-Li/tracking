package com.kd.utils;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

public class KafkaProducerHelper {

	private KafkaProducer<String, String> kafkaProducer = null;
	private static Map<String, KafkaProducerHelper> host2ProducerHelper = new ConcurrentHashMap<String, KafkaProducerHelper>();

	public static KafkaProducerHelper getInstance(String kafkaHosts) {
		KafkaProducerHelper inst = host2ProducerHelper.get(kafkaHosts);
		if (inst==null){
			synchronized (KafkaProducerHelper.class) {
				inst = host2ProducerHelper.get(kafkaHosts);
				if (inst==null){
					inst = new KafkaProducerHelper(kafkaHosts);
					host2ProducerHelper.put(kafkaHosts, inst);
				}
			}
		}

		return inst;

	}

	public KafkaProducerHelper(String kafkaHosts) {
			Properties props = new Properties();
			props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
					kafkaHosts);
			props.setProperty(ProducerConfig.ACKS_CONFIG, "1");
			props.setProperty(ProducerConfig.RETRIES_CONFIG, "1");
			props.setProperty(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, "120000");
			props.setProperty(ProducerConfig.BATCH_SIZE_CONFIG, "16384");
			props.setProperty(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, "8388608");
			props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
					"org.apache.kafka.common.serialization.StringSerializer");
			props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
					"org.apache.kafka.common.serialization.StringSerializer");
			kafkaProducer = new KafkaProducer<String, String>(props);
	}

	public void send(String topic, String key, String values) throws InterruptedException, ExecutionException {

		ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, key, values);
		kafkaProducer.send(record);
	}

}
