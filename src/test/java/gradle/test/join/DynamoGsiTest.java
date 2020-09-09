package gradle.test.join;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Index;
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
import com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndex;
import com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndexDescription;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.Projection;
import com.amazonaws.services.dynamodbv2.model.ProjectionType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.amazonaws.services.dynamodbv2.model.TableDescription;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DynamoGsiTest {
    private static final String TABLE_NAME = "WeatherDataGsi";
    private static final String ENDPOINT_URL = "http://localhost:4569";
    private static final String REGION = "ap-northeast-1";

    private AmazonDynamoDB client;

    @BeforeEach
    void setup() {
        createClient();
        createTable();
        postPrecipitationData();
    }

    @AfterEach
    void tearDown() {
        deleteTable();
    }

    @Test
    void test() {
        desc();
        query();
        System.out.println("hoge");
    }

    public void createClient() {
        // エンドポイント設定
        EndpointConfiguration endpointConfiguration = new EndpointConfiguration(ENDPOINT_URL, REGION);
        client = AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(endpointConfiguration).build();
    }

    void createTable() {
        DynamoDB dynamoDB = new DynamoDB(client);

        // Attribute definitions
        ArrayList<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>();

        attributeDefinitions.add(new AttributeDefinition().withAttributeName("MyLocation").withAttributeType("S"));
        attributeDefinitions.add(new AttributeDefinition().withAttributeName("Date").withAttributeType("S"));
        attributeDefinitions.add(new AttributeDefinition().withAttributeName("Precipitation").withAttributeType("S"));

        // Table key schema
        ArrayList<KeySchemaElement> tableKeySchema = new ArrayList<KeySchemaElement>();
        // Partition key
        tableKeySchema.add(new KeySchemaElement().withAttributeName("MyLocation").withKeyType(KeyType.HASH));
        // Sort key
        tableKeySchema.add(new KeySchemaElement().withAttributeName("Date").withKeyType(KeyType.RANGE));

        // PrecipIndex
        GlobalSecondaryIndex precipIndex = new GlobalSecondaryIndex().withIndexName("PrecipIndex")
                .withProvisionedThroughput(
                        new ProvisionedThroughput().withReadCapacityUnits((long) 10).withWriteCapacityUnits((long) 1))
                .withProjection(new Projection().withProjectionType(ProjectionType.ALL));

        ArrayList<KeySchemaElement> indexKeySchema = new ArrayList<KeySchemaElement>();

        // Partition key
        indexKeySchema.add(new KeySchemaElement().withAttributeName("Date").withKeyType(KeyType.HASH));
        // Sort key
        indexKeySchema.add(new KeySchemaElement().withAttributeName("Precipitation").withKeyType(KeyType.RANGE));

        precipIndex.setKeySchema(indexKeySchema);

        CreateTableRequest createTableRequest = new CreateTableRequest().withTableName(TABLE_NAME)
                .withProvisionedThroughput(
                        new ProvisionedThroughput().withReadCapacityUnits((long) 5).withWriteCapacityUnits((long) 1))
                .withAttributeDefinitions(attributeDefinitions).withKeySchema(tableKeySchema)
                .withGlobalSecondaryIndexes(precipIndex);

        Table table = dynamoDB.createTable(createTableRequest);
        System.out.println(table.getDescription());
    }

    void deleteTable() {
        DynamoDB dynamoDB = new DynamoDB(client);
        Table table = dynamoDB.getTable(TABLE_NAME);
        try {
            table.delete();
            table.waitForDelete();
        } catch (Exception e) {
            System.err.println("DeleteTable request failed for " + TABLE_NAME);
            System.err.println(e.getMessage());
        }
    }

    void query() {
        DynamoDB dynamoDB = new DynamoDB(client);

        Table table = dynamoDB.getTable(TABLE_NAME);
        Index index = table.getIndex("PrecipIndex");

        QuerySpec spec = new QuerySpec().withKeyConditionExpression("#d = :v_date and Precipitation >= :v_precip")
                .withNameMap(new NameMap().with("#d", "Date"))
                .withValueMap(new ValueMap().withString(":v_date", "2020-01-01").withString(":v_precip", "10mm"));

        ItemCollection<QueryOutcome> items = index.query(spec);
        Iterator<Item> iter = items.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next().toJSONPretty());
        }
    }

    void desc() {
        DynamoDB dynamoDB = new DynamoDB(client);

        Table table = dynamoDB.getTable(TABLE_NAME);
        TableDescription tableDesc = table.describe();

        Iterator<GlobalSecondaryIndexDescription> gsiIter = tableDesc.getGlobalSecondaryIndexes().iterator();
        while (gsiIter.hasNext()) {
            GlobalSecondaryIndexDescription gsiDesc = gsiIter.next();
            System.out.println("Info for index " + gsiDesc.getIndexName() + ":");

            Iterator<KeySchemaElement> kseIter = gsiDesc.getKeySchema().iterator();
            while (kseIter.hasNext()) {
                KeySchemaElement kse = kseIter.next();
                System.out.printf("\t%s: %s\n", kse.getAttributeName(), kse.getKeyType());
            }
            Projection projection = gsiDesc.getProjection();
            System.out.println("\tThe projection type is: " + projection.getProjectionType());
            if (projection.getProjectionType().toString().equals("INCLUDE")) {
                System.out.println("\t\tThe non-key projected attributes are: " + projection.getNonKeyAttributes());
            }
        }
    }

    /**
     * 
     */
    public void postPrecipitationData() {
        postPrecipitationDataSub("tokyo", "2020-01-01", "10mm");
        postPrecipitationDataSub("osaka", "2020-03-03", "50mm");
    }

    public void postPrecipitationDataSub(String location, String date, String precipitation) {
        HashMap<String, AttributeValue> itemValues = new HashMap<String, AttributeValue>();
        itemValues.put("MyLocation", new AttributeValue(location));
        itemValues.put("Date", new AttributeValue(date));
        itemValues.put("Precipitation", new AttributeValue(precipitation));

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

}
