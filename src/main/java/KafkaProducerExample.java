import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.ProducerConfig;
import java.util.Properties;

public class KafkaProducerExample {
    public static void main(String[] args) {
        // Set up producer properties
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092"); // Kafka broker address
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put("enable.metrics.push", "true");

        // Create KafkaProducer instance
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        // Topic to which messages will be sent
        String topic = "quickstart-events";

        try {
            // Send some messages
            for (int i = 0; i < 10; i++) {
                String message = "KR Message " + i;
                ProducerRecord<String, String> record = new ProducerRecord<>(topic, message);
                producer.send(record);
                System.out.println("Sent message: " + message);
                Thread.sleep(1000); // Wait for 1 second between each message
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the producer when done
            producer.close();
        }
    }
}