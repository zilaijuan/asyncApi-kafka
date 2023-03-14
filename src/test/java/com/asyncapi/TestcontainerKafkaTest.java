//package com.asyncapi;
//
//
//import com.asyncapi.model.TurnOnOffPayload;
//
//import com.asyncapi.model.TurnOnOffPayload;
//
//import com.asyncapi.model.DimLightPayload;
//
//
//import com.asyncapi.model.LightMeasuredPayload;
//
//import com.asyncapi.service.PublisherService;
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.apache.kafka.clients.consumer.ConsumerRecords;
//import org.apache.kafka.clients.consumer.KafkaConsumer;
//import org.apache.kafka.clients.producer.KafkaProducer;
//import org.apache.kafka.clients.producer.ProducerConfig;
//import org.apache.kafka.clients.producer.ProducerRecord;
//import org.apache.kafka.common.serialization.IntegerDeserializer;
//import org.apache.kafka.common.serialization.IntegerSerializer;
//import org.apache.kafka.connect.json.JsonDeserializer;
//import org.junit.ClassRule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.kafka.support.serializer.JsonSerializer;
//import org.springframework.test.context.DynamicPropertyRegistry;
//import org.springframework.test.context.DynamicPropertySource;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.testcontainers.containers.KafkaContainer;
//import org.testcontainers.shaded.com.google.common.collect.Lists;
//
//import java.time.Duration;
//import java.util.*;
//
//import static java.util.Collections.emptyList;
//import static org.apache.kafka.clients.consumer.ConsumerConfig.*;
//import static org.apache.kafka.clients.producer.ProducerConfig.*;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotEquals;
//
///**
// * Example of tests for kafka based on testcontainers library
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class TestcontainerKafkaTest {
//
//
//    private static final String RECEIVELIGHTMEASUREMENT_PUBLISH_TOPIC = "smartylighting.streetlights.1.0.event.{streetlightId}.lighting.measured";
//
//    private static final String TURNON_SUBSCRIBE_TOPIC = "smartylighting.streetlights.1.0.action.{streetlightId}.turn.on";
//
//    private static final String TURNOFF_SUBSCRIBE_TOPIC = "smartylighting.streetlights.1.0.action.{streetlightId}.turn.off";
//
//    private static final String DIMLIGHT_SUBSCRIBE_TOPIC = "smartylighting.streetlights.1.0.action.{streetlightId}.dim";
//
//    @ClassRule
//    public static KafkaContainer kafka = new KafkaContainer();
//
//    @Autowired
//    private PublisherService publisherService;
//
//    @DynamicPropertySource
//    public static void kafkaProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
//    }
//
//    @Test
//    public void receiveLightMeasurementConsumerTestcontainers() throws Exception {
//        Integer key = 1;
//        LightMeasuredPayload payload = new LightMeasuredPayload();
//
//        ProducerRecord<Integer, Object> producerRecord = new ProducerRecord<>(RECEIVELIGHTMEASUREMENT_PUBLISH_TOPIC, key, payload);
//
//        sendMessage(producerRecord);
//
//        Thread.sleep(1_000);
//    }
//
//
//    @Test
//    public void turnOnProducerTestcontainers() {
//        TurnOnOffPayload payload = new TurnOnOffPayload();
//        Integer key = 1;
//        Integer wrongKey = key + 1;
//
//        consumeMessages(TURNON_SUBSCRIBE_TOPIC);
//
//        publisherService.turnOn(key, payload);
//
//        ConsumerRecord<Integer, Object> consumedMessage = consumeMessage(TURNON_SUBSCRIBE_TOPIC);
//
//        assertEquals("Key is wrong", key, consumedMessage.key());
//        assertNotEquals("Key is wrong", wrongKey, consumedMessage.key());
//    }
//
//
//    @Test
//    public void turnOffProducerTestcontainers() {
//        TurnOnOffPayload payload = new TurnOnOffPayload();
//        Integer key = 1;
//        Integer wrongKey = key + 1;
//
//        consumeMessages(TURNOFF_SUBSCRIBE_TOPIC);
//
//        publisherService.turnOff(key, payload);
//
//        ConsumerRecord<Integer, Object> consumedMessage = consumeMessage(TURNOFF_SUBSCRIBE_TOPIC);
//
//        assertEquals("Key is wrong", key, consumedMessage.key());
//        assertNotEquals("Key is wrong", wrongKey, consumedMessage.key());
//    }
//
//
//    @Test
//    public void dimLightProducerTestcontainers() {
//        DimLightPayload payload = new DimLightPayload();
//        Integer key = 1;
//        Integer wrongKey = key + 1;
//
//        consumeMessages(DIMLIGHT_SUBSCRIBE_TOPIC);
//
//        publisherService.dimLight(key, payload);
//
//        ConsumerRecord<Integer, Object> consumedMessage = consumeMessage(DIMLIGHT_SUBSCRIBE_TOPIC);
//
//        assertEquals("Key is wrong", key, consumedMessage.key());
//        assertNotEquals("Key is wrong", wrongKey, consumedMessage.key());
//    }
//
//
//
//    protected void sendMessage(ProducerRecord message) throws Exception {
//        try (KafkaProducer<Integer, Object> kafkaProducer = createProducer()) {
//            kafkaProducer.send(message).get();
//        }
//    }
//
//    protected void sendMessage(String topic, Object message) throws Exception {
//        try (KafkaProducer<Integer, Object> kafkaProducer = createProducer()) {
//            kafkaProducer.send(new ProducerRecord<>(topic, message)).get();
//        }
//    }
//
//    protected KafkaProducer<Integer, Object> createProducer() {
//        return new KafkaProducer<>(getKafkaProducerConfiguration());
//    }
//
//    protected Map<String, Object> getKafkaProducerConfiguration() {
//        Map<String, Object> configs = new HashMap<>();
//        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafka.getBootstrapServers());
//        configs.put(KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
//        configs.put(VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());
//        return configs;
//    }
//
//    protected ConsumerRecord<Integer, Object> consumeMessage(String topic) {
//        return consumeMessages(topic)
//                .stream()
//                .findFirst()
//                .orElseThrow(() -> new IllegalStateException("no message received"));
//    }
//
//    protected List<ConsumerRecord<Integer, Object>> consumeMessages(String topic) {
//        try (KafkaConsumer<Integer, Object> consumer = createConsumer(topic)) {
//            return pollForRecords(consumer);
//        }
//    }
//
//    protected KafkaConsumer<Integer, Object> createConsumer(String topic) {
//        Properties properties = new Properties();
//        properties.putAll(getKafkaConsumerConfiguration());
//        KafkaConsumer<Integer, Object> consumer = new KafkaConsumer<>(properties);
//        consumer.subscribe(Collections.singleton(topic));
//        return consumer;
//    }
//
//    protected static <K, V> List<ConsumerRecord<K, V>> pollForRecords(KafkaConsumer<K, V> consumer) {
//        ConsumerRecords<K, V> received = consumer.poll(Duration.ofSeconds(10L));
//        return received == null ? emptyList() : Lists.newArrayList(received);
//    }
//
//    protected Map<String, Object> getKafkaConsumerConfiguration() {
//        Map<String, Object> configs = new HashMap<>();
//        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafka.getBootstrapServers());
//        configs.put(GROUP_ID_CONFIG, "testGroup");
//        configs.put(AUTO_OFFSET_RESET_CONFIG, "earliest");
//        configs.put(KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class.getName());
//        configs.put(VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class.getName());
//        return configs;
//    }
//
//}
