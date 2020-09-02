package gradle.test.join;

import java.util.ArrayList;
import java.util.HashMap;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DynamoClientJoinTest {
    private static final String TABLE_NAME = "TestTable";
    private static final String ENDPOINT_URL = "http://localhost:4569";
    private static final String REGION = "ap-northeast-1";

    private AmazonDynamoDB client;

    @BeforeEach
    public void setup() throws Exception {
        createClient();
        createTable();

        // 販売テナント論理テーブルのデータ
        postDistributionTenantData();
    }

    @AfterEach
    public void tearDown() throws Exception {
        deleteTable();
    }

    public void createClient() {
        // エンドポイント設定
        EndpointConfiguration endpointConfiguration = new EndpointConfiguration(ENDPOINT_URL, REGION);
        client = AmazonDynamoDBClientBuilder.standard()
                // .withRegion("ap-northeast-1")
                .withEndpointConfiguration(endpointConfiguration).build();
    }

    public void createTable() throws Exception {
        CreateTableRequest request = new CreateTableRequest()
                .withAttributeDefinitions(new AttributeDefinition("DistTenantId", ScalarAttributeType.S))
                .withKeySchema(new KeySchemaElement("DistTenantId", KeyType.HASH))
                .withProvisionedThroughput(new ProvisionedThroughput(new Long(10), new Long(10)))
                .withTableName(TABLE_NAME);

        try {
            CreateTableResult result = client.createTable(request);
            System.out.println(result.getTableDescription().getTableName());
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
    }

    public void deleteTable() throws Exception {
        try {
            client.deleteTable(TABLE_NAME);
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }

    }

    /**
     * 
     */
    public void postDistributionTenantData() {
        postDistributionTenantDataSub("dist_tenant_1", "2020/01/01", "customer_1");
        postDistributionTenantDataSub("dist_tenant_2", "2020/01/01", "customer_2");
    }

    public void postDistributionTenantDataSub(String distId, String date, String customerId) {
        HashMap<String, AttributeValue> itemValues = new HashMap<String, AttributeValue>();
        itemValues.put("DistTenantId", new AttributeValue(distId));
        itemValues.put("TestDate", new AttributeValue(date));
        itemValues.put("CustmerId", new AttributeValue(customerId));

        postData(itemValues);
    }

    public void postData(HashMap<String, AttributeValue> itemValues) {
        try {
            client.putItem(TABLE_NAME, itemValues);
        } catch (ResourceNotFoundException e) {
            System.err.format("Error: The table \"%s\" can't be found.\n", TABLE_NAME);
            System.err.println("Be sure that it exists and that you've typed its name correctly!");
            System.exit(1);
        } catch (AmazonServiceException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    @Test
    @DisplayName("Dynamo Clientの結合テスト")
    public void test() throws Exception {
        System.out.println("--------- hoge");
    }

}