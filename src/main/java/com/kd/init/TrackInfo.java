package com.kd.init;

import java.util.Arrays;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.kd.configure.ConfigurationHelper;
import com.kd.consts.GeneralConst;
import com.kd.model.track.Generalinfo;
import com.kd.utils.KafkaConsumerHelper;

public class TrackInfo {
	private static final Logger logger = LoggerFactory.getLogger(TrackInfo.class);

	public static void main(String[] args) {
		ConfigurationHelper.init();
		new TrackInfo().syncTracking();
	}

	public void syncTracking() {

		try {
			logger.info("Start to sync tracking!");
			String topic = GeneralConst.TRACKTOPIC;

			KafkaConsumer<String, String> consumer = KafkaConsumerHelper.getInstance()
					.getConsumer(GeneralConst.KAFKA_HOST, topic);
			consumer.subscribe(Arrays.asList(topic));
			int messagecounter = 0;
			while (true) {
				ConsumerRecords<String, String> records = consumer.poll(10000);
				for (ConsumerRecord<String, String> record : records) {
					Generalinfo model = JSON.parseObject(record.value(), Generalinfo.class);
					if (model != null) {
						model.save();
					}

					messagecounter++;
				}
				logger.info("received messages:" + messagecounter);
			}
		} catch (Exception e) {
			logger.error("catch an error: " + e);
		}
	}
}
