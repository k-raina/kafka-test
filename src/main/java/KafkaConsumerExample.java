import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import java.util.Collections;
import java.util.Properties;

public class KafkaConsumerExample {
    public static void main(String[] args) {
        // Set up consumer properties
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092"); // Kafka broker address
        props.put("group.id", "test-group"); // Consumer group id
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", StringDeserializer.class.getName());
        props.put("enable.metrics.push", "true");

        // Create KafkaConsumer instance
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

        // Subscribe to the topic(s)
        consumer.subscribe(Collections.singletonList("quickstart-events")); // Add your topic(s) here

        try {
            // Poll for new messages
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(100);
                for (ConsumerRecord<String, String> record : records) {
                    System.out.printf("Received message: offset = %d, key = %s, value = %s%n",
                            record.offset(), record.key(), record.value());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the consumer when done
            consumer.close();
        }
    }
}
