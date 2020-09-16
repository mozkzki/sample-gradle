package sample.aws;

import java.net.URI;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.CreateQueueRequest;
import software.amazon.awssdk.services.sqs.model.DeleteQueueRequest;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlRequest;

/**
 * https://docs.aws.amazon.com/ja_jp/sdk-for-java/v2/developer-guide/examples-sqs-message-queues.html
 */
@ExtendWith(MockitoExtension.class)
public class SqsTest {

    private static final String queueName = "foo";
    private SqsClient sqsClient;

    @BeforeEach
    public void createQueue() throws Exception {
        sqsClient = SqsClient.builder().region(Region.US_EAST_1).endpointOverride(new URI("http://localhost:4576"))
                .build();
        CreateQueueRequest createQueueRequest = CreateQueueRequest.builder().queueName(queueName).build();
        sqsClient.createQueue(createQueueRequest);
    }

    @AfterEach
    public void deleteQueue() throws Exception {
        // get queue url
        GetQueueUrlRequest getQueueRequest = GetQueueUrlRequest.builder().queueName(queueName).build();
        String queueUrl = sqsClient.getQueueUrl(getQueueRequest).queueUrl();
        // delete queue
        DeleteQueueRequest deleteQueueRequest = DeleteQueueRequest.builder().queueUrl(queueUrl).build();
        sqsClient.deleteQueue(deleteQueueRequest);
    }

    @Test
    @DisplayName("SQS Clientの結合テスト")
    public void test() throws Exception {
        System.out.println("--------- hoge");
    }

}