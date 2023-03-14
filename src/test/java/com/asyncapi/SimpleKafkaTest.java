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
//import org.apache.kafka.clients.consumer.Consumer;
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.apache.kafka.clients.producer.Producer;
//import org.apache.kafka.clients.producer.ProducerRecord;
//import org.apache.kafka.common.serialization.IntegerDeserializer;
//import org.apache.kafka.common.serialization.IntegerSerializer;
//import org.junit.Before;
//import org.junit.ClassRule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
//import org.springframework.kafka.core.DefaultKafkaProducerFactory;
//import org.springframework.kafka.support.serializer.JsonDeserializer;
//import org.springframework.kafka.support.serializer.JsonSerializer;
//import org.springframework.kafka.test.EmbeddedKafkaBroker;
//import org.springframework.kafka.test.rule.EmbeddedKafkaRule;
//import org.springframework.kafka.test.utils.KafkaTestUtils;
//import org.springframework.test.context.DynamicPropertyRegistry;
//import org.springframework.test.context.DynamicPropertySource;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.time.Duration;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.Map;
//
//import static org.springframework.test.util.AssertionErrors.assertEquals;
//
///**
// * Example of tests for kafka based on spring-kafka-test library
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class SimpleKafkaTest {
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
//    public static EmbeddedKafkaRule embeddedKafka = new EmbeddedKafkaRule(1, false, 1, TURNON_SUBSCRIBE_TOPIC, TURNOFF_SUBSCRIBE_TOPIC, DIMLIGHT_SUBSCRIBE_TOPIC);
//
//    private static EmbeddedKafkaBroker embeddedKafkaBroker = embeddedKafka.getEmbeddedKafka();
//
//    @DynamicPropertySource
//    public static void kafkaProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.kafka.bootstrap-servers", embeddedKafkaBroker::getBrokersAsString);
//    }
//
//
//    @Autowired
//    private PublisherService publisherService;
//
//    Consumer<Integer, TurnOnOffPayload> consumerSmartylightingStreetlights10ActionStreetlightIdTurnOn;
//
//    Consumer<Integer, TurnOnOffPayload> consumerSmartylightingStreetlights10ActionStreetlightIdTurnOff;
//
//    Consumer<Integer, DimLightPayload> consumerSmartylightingStreetlights10ActionStreetlightIdDim;
//
//    Producer<Integer, Object> producer;
//
//    @Before
//    public void init() {
//
//        Map<String, Object> consumerConfigs = new HashMap<>(KafkaTestUtils.consumerProps("consumer", "true", embeddedKafkaBroker));
//        consumerConfigs.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
//
//        consumerSmartylightingStreetlights10ActionStreetlightIdTurnOn = new DefaultKafkaConsumerFactory<>(consumerConfigs, new IntegerDeserializer(), new JsonDeserializer<>(TurnOnOffPayload.class)).createConsumer();
//        consumerSmartylightingStreetlights10ActionStreetlightIdTurnOn.subscribe(Collections.singleton(TURNON_SUBSCRIBE_TOPIC));
//        consumerSmartylightingStreetlights10ActionStreetlightIdTurnOn.poll(Duration.ZERO);
//
//        consumerSmartylightingStreetlights10ActionStreetlightIdTurnOff = new DefaultKafkaConsumerFactory<>(consumerConfigs, new IntegerDeserializer(), new JsonDeserializer<>(TurnOnOffPayload.class)).createConsumer();
//        consumerSmartylightingStreetlights10ActionStreetlightIdTurnOff.subscribe(Collections.singleton(TURNOFF_SUBSCRIBE_TOPIC));
//        consumerSmartylightingStreetlights10ActionStreetlightIdTurnOff.poll(Duration.ZERO);
//
//        consumerSmartylightingStreetlights10ActionStreetlightIdDim = new DefaultKafkaConsumerFactory<>(consumerConfigs, new IntegerDeserializer(), new JsonDeserializer<>(DimLightPayload.class)).createConsumer();
//        consumerSmartylightingStreetlights10ActionStreetlightIdDim.subscribe(Collections.singleton(DIMLIGHT_SUBSCRIBE_TOPIC));
//        consumerSmartylightingStreetlights10ActionStreetlightIdDim.poll(Duration.ZERO);
//
//        Map<String, Object> producerConfigs = new HashMap<>(KafkaTestUtils.producerProps(embeddedKafkaBroker));
//        producer = new DefaultKafkaProducerFactory<>(producerConfigs, new IntegerSerializer(), new JsonSerializer()).createProducer();
//
//    }
//
//    @Test
//    public void receiveLightMeasurementConsumerTest() throws InterruptedException {
//        Integer key = 1;
//        LightMeasuredPayload payload = new LightMeasuredPayload();
//
//        ProducerRecord<Integer, Object> producerRecord = new ProducerRecord<>(RECEIVELIGHTMEASUREMENT_PUBLISH_TOPIC, key, payload);
//        producer.send(producerRecord);
//        producer.flush();
//        Thread.sleep(1_000);
//    }
//
//
//    @Test
//    public void turnOnProducerTest() {
//        TurnOnOffPayload payload = new TurnOnOffPayload();
//        Integer key = 1;
//
//        KafkaTestUtils.getRecords(consumerSmartylightingStreetlights10ActionStreetlightIdTurnOn);
//
//        publisherService.turnOn(key, payload);
//
//        ConsumerRecord<Integer, TurnOnOffPayload> singleRecord = KafkaTestUtils.getSingleRecord(consumerSmartylightingStreetlights10ActionStreetlightIdTurnOn, TURNON_SUBSCRIBE_TOPIC);
//
//        assertEquals("Key is wrong", key, singleRecord.key());
//    }
//
//
//    @Test
//    public void turnOffProducerTest() {
//        TurnOnOffPayload payload = new TurnOnOffPayload();
//        Integer key = 1;
//
//        KafkaTestUtils.getRecords(consumerSmartylightingStreetlights10ActionStreetlightIdTurnOff);
//
//        publisherService.turnOff(key, payload);
//
//        ConsumerRecord<Integer, TurnOnOffPayload> singleRecord = KafkaTestUtils.getSingleRecord(consumerSmartylightingStreetlights10ActionStreetlightIdTurnOff, TURNOFF_SUBSCRIBE_TOPIC);
//
//        assertEquals("Key is wrong", key, singleRecord.key());
//    }
//
//
//    @Test
//    public void dimLightProducerTest() {
//        DimLightPayload payload = new DimLightPayload();
//        Integer key = 1;
//
//        KafkaTestUtils.getRecords(consumerSmartylightingStreetlights10ActionStreetlightIdDim);
//
//        publisherService.dimLight(key, payload);
//
//        ConsumerRecord<Integer, DimLightPayload> singleRecord = KafkaTestUtils.getSingleRecord(consumerSmartylightingStreetlights10ActionStreetlightIdDim, DIMLIGHT_SUBSCRIBE_TOPIC);
//
//        assertEquals("Key is wrong", key, singleRecord.key());
//    }
//
//
//}
