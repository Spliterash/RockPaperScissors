docker run -it -v %cd%/jars/client.jar:/mnt/app/client.jar --network host -w /mnt/app openjdk:17 java -jar client.jar
PAUSE