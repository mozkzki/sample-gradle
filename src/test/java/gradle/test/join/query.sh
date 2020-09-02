aws dynamodb query \
    --table-name TestTable \
    --profile localstack \
    --endpoint-url http://localhost:4569 \
    --key-condition-expression "DistTenantId = :name" \
    --expression-attribute-values  '{":name":{"S":"dist_tenant_1"}}'
