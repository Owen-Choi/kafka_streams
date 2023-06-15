package com.spring.kafka.consumer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.kafka.domain.model.DispatchDTO;
import com.spring.kafka.domain.model.EventDTO;
import com.spring.kafka.domain.model.WeatherDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Properties;

@Slf4j
@Component
public class KafkaMessageConsumer {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;
    @Value("${spring.kafka.producer.key-serializer}")
    private String keySerializer;
    @Value("${spring.kafka.producer.value-serializer}")
    private String valueSerializer;


    @KafkaListener(topics = "dbserver1.emergency.event", containerFactory = "kafkaListenerContainerFactory")
    public void listenEventDomain(@Payload String payload) {
        log.debug("Received Headers : " + payload);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(payload);

            // Access the payload object
            JsonNode payloadNode = rootNode.path("payload");

            // Extract fields
            long id = payloadNode.path("event_id").asLong();
            long callDate = payloadNode.path("call_date").asLong();
            long streetAddressId = payloadNode.path("street_address_id").asLong();
            String __deleted = payloadNode.path("__deleted").asText();
            LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(callDate), ZoneOffset.UTC);
            EventDTO eventDTO = EventDTO.builder()
                    .eventId(id)
                    .streetAddressId(streetAddressId)
                    .isDeleted(__deleted)
                    .callDate(callDate)
                    .build();

            Properties properties = new Properties();
            properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
            properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer);
            properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer);
            KafkaProducer<String, EventDTO> producer = new KafkaProducer<>(properties);
            ProducerRecord<String, EventDTO> message = new ProducerRecord<>("OwenTopicTester", eventDTO);
            producer.send(message);

        } catch (Exception e) {
            // Exception handling (e.g., JsonParseException or JsonMappingException)
        }
    }


    @KafkaListener(topics = "dbserver1.emergency.weather_essential", containerFactory = "kafkaListenerContainerFactory")
    public void listenWeatherDomain(@Payload String payload) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(payload);
            JsonNode payloadNode = rootNode.path("payload");
            long weather_essential_id = payloadNode.path("weather_essential_id").asLong();
            long measured_date = payloadNode.path("measured_date").asLong();
            int temperature = payloadNode.path("temperature").asInt();
            int visibility_distance = payloadNode.path("visibility_distance").asInt();
            int wind_speed = payloadNode.path("wind_speed").asInt();
            long city_address_id = payloadNode.path("city_address_id").asLong();
            String __deleted = payloadNode.path("__deleted").asText();
            WeatherDTO weatherDTO = WeatherDTO.builder()
                    .id(weather_essential_id)
                    .measuredDate(measured_date)
                    .temperature(temperature)
                    .visibility(visibility_distance)
                    .windSpeed(wind_speed)
                    .cityAddressId(city_address_id)
                    .isDeleted(__deleted)
                    .build();
            Properties properties = new Properties();
            properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
            properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer);
            properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer);
            KafkaProducer<String, WeatherDTO> producer = new KafkaProducer<>(properties);
            ProducerRecord<String, WeatherDTO> message = new ProducerRecord<>("OwenTopicTester", weatherDTO);
            producer.send(message);
        } catch (Exception e) {
            // Exception handling (e.g., JsonParseException or JsonMappingException)
        }
    }

    @KafkaListener(topics = "dbserver1.emergency.dispatch", containerFactory = "kafkaListenerContainerFactory")
    public void listenDispatchDomain(@Payload String payload) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(payload);
            JsonNode payloadNode = rootNode.path("payload");
            long dispatch_id = payloadNode.path("dispatch_id").asLong();
            long arrival_date = payloadNode.path("arrival_date").asLong();
            long dispatch_date = payloadNode.path("dispatch_date").asLong();
            long dispatch_over_date = payloadNode.path("dispatch_over_date").asLong();
            int distance = payloadNode.path("distance").asInt();
            String driver_agent = payloadNode.path("driver_agent").asText();
            long event_id = payloadNode.path("event_id").asLong();
            long firestation_id = payloadNode.path("firestation_id").asLong();
            long weather_essential_id = payloadNode.path("weather_essential_id").asLong();
            String __deleted = payloadNode.path("__deleted").asText();
            DispatchDTO dispatchDTO = DispatchDTO.builder()
                    .id(dispatch_id)
                    .arrivalDate(arrival_date)
                    .dispatchDate(dispatch_date)
                    .dispatchOverDate(dispatch_over_date)
                    .distance(distance)
                    .driverAgent(driver_agent)
                    .eventId(event_id)
                    .fireStationId(firestation_id)
                    .weatherEssentialId(weather_essential_id)
                    .isDeleted(__deleted)
                    .build();
            Properties properties = new Properties();
            properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
            properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer);
            properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer);
            KafkaProducer<String, DispatchDTO> producer = new KafkaProducer<>(properties);
            ProducerRecord<String, DispatchDTO> message = new ProducerRecord<>("OwenTopicTester", dispatchDTO);
            producer.send(message);
        } catch (Exception e) {
            // Exception handling (e.g., JsonParseException or JsonMappingException)
        }
    }
}

