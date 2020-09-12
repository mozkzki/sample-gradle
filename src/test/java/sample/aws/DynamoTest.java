package sample.aws;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * AWS SDK for Java V1を使用してDynamo へのクエリを行うサンプル。
 * 
 * 前提： localstackが動作していること。
 */
public class DynamoTest {
    private static final String TABLE_NAME = "TestTable";
    private static final String ENDPOINT_URL = "http://localhost:4569";
    private static final String REGION = "ap-northeast-1";

    private AmazonDynamoDB client;

    @BeforeEach
    public void setup() throws Exception {
        createClient();
        createTable();
        postPrecipitationData();
    }

    @AfterEach
    public void tearDown() throws Exception {
        deleteTable();
    }

    @Test
    @DisplayName("Dynamo Clientの結合テスト")
    public void test() throws Exception {
        query();
        System.out.println("completed.");
    }

    /**
     * AmazonDynamoDB クライアントを作成。
     */
    public void createClient() {
        // エンドポイント設定
        EndpointConfiguration endpointConfiguration = new EndpointConfiguration(ENDPOINT_URL, REGION);
        client = AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(endpointConfiguration).build();

    }

    /**
     * テーブルを作成。
     */
    public void createTable() throws Exception {
        // Attribute definitions
        ArrayList<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>();

        attributeDefinitions.add(new AttributeDefinition().withAttributeName("MyLocation").withAttributeType("S"));
        attributeDefinitions.add(new AttributeDefinition().withAttributeName("Date").withAttributeType("S"));
        // attributeDefinitions.add(new
        // AttributeDefinition().withAttributeName("Precipitation").withAttributeType("S"));

        // Table key schema
        ArrayList<KeySchemaElement> tableKeySchema = new ArrayList<KeySchemaElement>();
        // Partition key
        tableKeySchema.add(new KeySchemaElement().withAttributeName("MyLocation").withKeyType(KeyType.HASH));
        // Sort key
        tableKeySchema.add(new KeySchemaElement().withAttributeName("Date").withKeyType(KeyType.RANGE));

        CreateTableRequest request = new CreateTableRequest().withTableName(TABLE_NAME)
                .withProvisionedThroughput(
                        new ProvisionedThroughput().withReadCapacityUnits((long) 5).withWriteCapacityUnits((long) 1))
                .withAttributeDefinitions(attributeDefinitions).withKeySchema(tableKeySchema);

        try {
            CreateTableResult result = client.createTable(request);
            System.out.println(result.getTableDescription().getTableName());
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
    }

    /**
     * テーブルを削除。
     */
    public void deleteTable() throws Exception {
        try {
            client.deleteTable(TABLE_NAME);
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }

    }

    /**
     * テーブルにデータを投入。
     */
    public void postPrecipitationData() {
        postPrecipitationDataSub("tokyo", "2020-01-01", "10mm");
        postPrecipitationDataSub("osaka", "2020-03-03", "50mm");
    }

    private void postPrecipitationDataSub(String location, String date, String precipitation) {
        HashMap<String, AttributeValue> itemValues = new HashMap<String, AttributeValue>();
        itemValues.put("MyLocation", new AttributeValue(location));
        itemValues.put("Date", new AttributeValue(date));
        itemValues.put("Precipitation", new AttributeValue(precipitation));

        postData(itemValues);
    }

    private void postData(HashMap<String, AttributeValue> itemValues) {
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

    /**
     * データを取得する。
     */
    public void query() {
        DynamoDB dynamoDB = new DynamoDB(client);
        Table table = dynamoDB.getTable(TABLE_NAME);

        QuerySpec spec = new QuerySpec().withKeyConditionExpression("#d >= :v_date and MyLocation = :v_loc")
                .withNameMap(new NameMap().with("#d", "Date"))
                .withValueMap(new ValueMap().withString(":v_date", "2013-08-10").withString(":v_loc", "tokyo"));

        ItemCollection<QueryOutcome> items = table.query(spec);
        Iterator<Item> iter = items.iterator();
        assertNotNull(iter, "items is null.");
        assertTrue(iter.hasNext());
        while (iter.hasNext()) {
            Item item = iter.next();
            String result = item.toJSONPretty();
            assertNotNull(result, result);
            assertEquals("10mm", item.getString("Precipitation"));
            assertEquals("tokyo", item.getString("MyLocation"));
            assertEquals("2020-01-01", item.getString("Date"));
        }
    }

}