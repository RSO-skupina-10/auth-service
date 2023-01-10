## How to run locally
- `docker run -d --name postgres-jdbc4 -e POSTGRES_PASSWORD=postgres -e POSTGRES_USER=postgres -e POSTGRES_DB=authservice -p 5435:5432 postgres:13`
- `mvn clean package`
- `java -jar api/target/api-1.0-SNAPSHOT.jar`
