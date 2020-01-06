package com.detectify.screenshotservice.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class EmbeddedKafkaConfig {

    @Bean
    public KafkaAdmin admin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG,
                StringUtils.arrayToCommaDelimitedString(kafkaEmbedded().getBrokerAddresses()));
        return new KafkaAdmin(configs);
    }

    @Bean
    public EmbeddedKafkaBroker kafkaEmbedded() {
        EmbeddedKafkaBroker kafkaEmbedded = new EmbeddedKafkaBroker(2);
        kafkaEmbedded.kafkaPorts(57235, 57238);
        kafkaEmbedded.brokerProperty("auto.create.topics.enable", true);
        kafkaEmbedded.brokerProperty("default.replication.factor", 2);
//        kafkaEmbedded.brokerProperty("num.partitions", 10);
//        kafkaEmbedded.brokerProperty("replica.fetch.max.bytes", 5 * 1024 * 1024);
        kafkaEmbedded.brokerProperty("message.max.bytes", 20 * 1024 * 1024);
        return kafkaEmbedded;
    }

    @Bean
    public NewTopic createScreenShot() {
        return new NewTopic("create-screenshot", 2, (short) 2);
    }

}
