
TABLE_NAME=TestTable
INDEX_NAME=test-table-index
HASH_KEY=HK
RANGE_KEY=RK
GSI_HASH_KEY=GSIHK
GSI_RANGE_KEY=GSIRK
ENDPOINT=http://localhost:4569

# create table
aws dynamodb create-table \
    --table-name $TABLE_NAME \
    --attribute-definitions \
        AttributeName=$HASH_KEY,AttributeType=S \
        AttributeName=$RANGE_KEY,AttributeType=S \
        AttributeName=$GSI_HASH_KEY,AttributeType=S \
        AttributeName=$GSI_RANGE_KEY,AttributeType=S \
    --key-schema \
        AttributeName=$HASH_KEY,KeyType=HASH \
        AttributeName=$RANGE_KEY,KeyType=RANGE \
    --provisioned-throughput \
        ReadCapacityUnits=5,WriteCapacityUnits=5 \
    --global-secondary-indexes \
        IndexName=$INDEX_NAME,KeySchema=[{"AttributeName=$GSI_HASH_KEY,KeyType=HASH"},{"AttributeName=$GSI_RANGE_KEY,KeyType=RANGE"}],Projection={ProjectionType=ALL},ProvisionedThroughput='{ReadCapacityUnits=3,WriteCapacityUnits=3}' \
    --endpoint-url $ENDPOINT

# post data
post_data () {
    aws dynamodb put-item \
        --table-name $TABLE_NAME \
        --item "${1}" \
        --endpoint-url $ENDPOINT
}
post_data "{\"${HASH_KEY}\": {\"S\": \"tnt|distributor_1\"}, \"$RANGE_KEY\": {\"S\": \"2020-01-01\"}, \"$GSI_HASH_KEY\": {\"S\": \"test1\"},  \"$GSI_RANGE_KEY\": {\"S\": \"2020-05-05\"},   \"customer_id\": {\"S\": \"customer_a\"}}"
# post_data "{\"${HASH_KEY}\": {\"S\": \"tnt|distributor_2\"}, \"$RANGE_KEY\": {\"S\": \"2020-03-03\"},\"customer_id\": {\"S\": \"customer_b\"}}"
# post_data "{\"${HASH_KEY}\": {\"S\": \"dvs|custober_a\"}, \"$RANGE_KEY\": {\"S\": \"2020-05-05\"},\"device_id\": {\"S\": \"device_aa\"}}"
# post_data "{\"${HASH_KEY}\": {\"S\": \"dvs|custober_b\"}, \"$RANGE_KEY\": {\"S\": \"2020-06-06\"},\"device_id\": {\"S\": \"device_bb\"}}"
# post_data "{\"${HASH_KEY}\": {\"S\": \"parts|device_aa\"}, \"$RANGE_KEY\": {\"S\": \"2020-10-10\"},\"parts_id\": {\"S\": \"part_A,part_B\"}}"
# post_data "{\"${HASH_KEY}\": {\"S\": \"parts|device_bb\"}, \"$RANGE_KEY\": {\"S\": \"2020-11-11\"},\"parts_id\": {\"S\": \"part_C\"}}"

# scan data
echo ======== scan ========
aws dynamodb scan \
    --table-name $TABLE_NAME \
    --endpoint-url $ENDPOINT \
    | jq -r '.Items[] | [.HK.S, .RK.S, .GSIHK.S, .GSIRK.S, .customer_id.S, .device_id.S, .parts_id.S] | @csv'

# query data
echo ======== query ========
 aws dynamodb query \
    --table-name $TABLE_NAME \
    --index-name $INDEX_NAME \
    --key-condition-expression '#h = :1 AND #r > :2' \
    --expression-attribute-names '{"#h": "GSIHK", "#r": "GSIRK"}' \
    --expression-attribute-values '{":1": {"S": "test1"}, ":2": {"S": "2018-06-05"}}' \
    --endpoint-url $ENDPOINT
            
# delete table
aws dynamodb delete-table \
    --table-name $TABLE_NAME \
    --endpoint-url $ENDPOINT
