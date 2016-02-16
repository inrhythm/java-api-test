# Overview
-----------------------------------------------------------------------------------------------------------------------------------
Demonstrate your knowledge of Web API usage, basic I/O operations using Java, and connecting to and consuming API endpoints.

[Using the the following JSON feed:](http://jsonplaceholder.typicode.com/posts)

Create a command line application to read on the above JSON feed using HTTP. Please perform the following tasks:

1. Clone [this repo](https://github.com/inrhythm/java-api-test) to your local machine.
2. [Install Maven](https://maven.apache.org/index.html) if it is not all ready installed on your local. 
3. Tally the number of unique user Ids in the JSON.
4. Modify the 4th JSON array item and change the title and body of the object to "InRhythm". 
5. Return an InRhythmResponse object to the main Java class which includes the user count and the modified JSON.
6. Write a file to the file system, with the modified JSON, called inrhythm.json.
7. The program must pass the pre-written JUnit test which can be run using "mvn test".

## Requirements
- Use Java 1.6/1.7/1.8+
- All dependencies should be publicly available or properly included with the project and referenced within the POM
- Be creative, have fun and may the force be with you

