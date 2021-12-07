## Movie Theater Seating Challenge

### Language - JAVA

### Steps to compile the solution
* Run the following command to compile all the .java files
```java
javac src/com/company/Main.java src/com/company/interfaces/Theater.java src/com/company/service/MovieTheater.java
```
* go inside src folder and run the following command
```java
java com.company.Main
```
* The program will ask you to input the path to your input file
```java
Enter file path

```
* After parsing input it will display the path of output.txt
```java
/Users/<dir-name>/src/output.txt
```

### Assumptions
* I have assumed that row A is farthest away from screen.
* The seats are allocated in consecutive way.
* I am not allowing user to reserve seats if the number of seats requested exceeds the count of seats in rows.
* The metric of satisfaction for this program is getting a seat far away from screen and not breaking the group apart. 
