

# Execution and compilation of the front end
cd ..
mvn clean install
cd front-end
mvn compile
mvn exec:java
