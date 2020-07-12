# Build
mvn clean package && docker build -t com.alushkja/blogpad .

# RUN

docker rm -f blogpad || true && docker run -d -p 8080:8080 -p 4848:4848 --name blogpad com.alushkja/blogpad 

# System Test

Switch to the "-st" module and perform:

mvn compile failsafe:integration-test