dir 
echo "hello there"
mvn compile exec:java
curl.exe -X POST -H 'Content-Type:application/json' 'http://localhost:8080/needs' -d '{ \"name\": \"Dog Treats\", \"description\": \"Treats, for the dog\", \"demandRating\": 70.00 }'
curl.exe –i -X PUT -H 'Content-Type:application/json' 'http://localhost:8080/needs' -d '{\"id\": \"7aaa02de-29a1-4970-ab72-9a0421063d3f\",\"name\": \"need for sneed\"}'