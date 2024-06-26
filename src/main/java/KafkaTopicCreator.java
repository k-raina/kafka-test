import java.io.IOException;
import java.lang.System;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.KafkaFuture;
import java.util.Properties;
import java.util.concurrent.ExecutionException;


public class KafkaTopicCreator {

    public static void main(String[] args) throws IOException {
        // Configure the AdminClient properties
        Properties properties = ConfluentCloudProduceConsume.readConfig("~/projects/oss/kraina/kafka-test/client.properties");
        properties.put(AdminClientConfig.CLIENT_ID_CONFIG, "KafkaTopicCreator"); // Client ID for AdminClient

        // Create AdminClient
        try (AdminClient adminClient = AdminClient.create(properties)) {

            // Create a NewTopic object
            String topicName = "quickstart-events";
            int numPartitions = 3;
            short replicationFactor = 3;
            NewTopic newTopic = new NewTopic(topicName, numPartitions, replicationFactor);

            // Create the topic
            KafkaFuture<Void> createTopicFuture = adminClient.createTopics(java.util.Collections.singleton(newTopic)).all();
            createTopicFuture.get(); // Wait for topic creation to complete

            System.out.println("Topic '" + topicName + "' created successfully.");

        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error creating topic: " + e.getMessage());
        }
    }
}
