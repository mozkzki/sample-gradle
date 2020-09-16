
TABLE_NAME=TestTable
HASH_KEY=HK
RANGE_KEY=RK
ENDPOINT=http://localhost:4569

# create table
aws dynamodb create-table \
    --table-name $TABLE_NAME \
    --attribute-definitions \
        AttributeName=$HASH_KEY,AttributeType=S \
        AttributeName=$RANGE_KEY,AttributeType=S \
    --key-schema AttributeName=$HASH_KEY,KeyType=HASH AttributeName=$RANGE_KEY,KeyType=RANGE \
    --provisioned-throughput ReadCapacityUnits=3,WriteCapacityUnits=3 \
    --endpoint-url $ENDPOINT

# post data
post_data () {
    aws dynamodb put-item \
        --table-name $TABLE_NAME \
        --item "${1}" \
        --endpoint-url $ENDPOINT
}
post_data "{\"${HASH_KEY}\": {\"S\": \"tnt|distributor_1\"}, \"$RANGE_KEY\": {\"S\": \"2020-01-01\"},\"customer_id\": {\"S\": \"customer_a\"}}"
post_data "{\"${HASH_KEY}\": {\"S\": \"tnt|distributor_2\"}, \"$RANGE_KEY\": {\"S\": \"2020-03-03\"},\"customer_id\": {\"S\": \"customer_b\"}}"
post_data "{\"${HASH_KEY}\": {\"S\": \"dvs|custober_a\"}, \"$RANGE_KEY\": {\"S\": \"2020-05-05\"},\"device_id\": {\"S\": \"device_aa\"}}"
post_data "{\"${HASH_KEY}\": {\"S\": \"dvs|custober_b\"}, \"$RANGE_KEY\": {\"S\": \"2020-06-06\"},\"device_id\": {\"S\": \"device_bb\"}}"
post_data "{\"${HASH_KEY}\": {\"S\": \"parts|device_aa\"}, \"$RANGE_KEY\": {\"S\": \"2020-10-10\"},\"parts_id\": {\"S\": \"part_A,part_B\"}}"
post_data "{\"${HASH_KEY}\": {\"S\": \"parts|device_bb\"}, \"$RANGE_KEY\": {\"S\": \"2020-11-11\"},\"parts_id\": {\"S\": \"part_C\"}}"

# scan data
aws dynamodb scan \
    --table-name $TABLE_NAME \
    --endpoint-url $ENDPOINT \
    | jq -r '.Items[] | [.HK.S, .RK.S, .customer_id.S, .device_id.S, .parts_id.S] | @csv'

# delete table
aws dynamodb delete-table \
    --table-name $TABLE_NAME \
    --endpoint-url $ENDPOINT \
