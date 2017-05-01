# Static Class-level Regression Test Selection
Our RTS Maven plugin integrate with Maven Surefire plugin, perform a static regression test selection on class level.

## Configuration
Download our project to your local storage. Then install it locally.
```
$ mvn clean install
```

Add our plugin in your target project pom.xml.
```
<project>
  ...
  <build>
    ...
    <plugins>
      ...
      <plugin>
          <groupId>TestSelection</groupId>
          <artifactId>TestSelection</artifactId>
          <version>0.0.1-SNAPSHOT</version>
          <configuration>
              <args>
                  <param>ABSOLUTE PATH TO YOUR PROJECT</param>
                  <param>CLASS PACKAGE NAME OF YOUR PROJECT</param>
                  <param>TEST PACKAGE NAME OF YOUR PROJECT</param>
              </args>
              ...
              <!-- YOU ALSO NEED TO ADD any other configuration your surefire plugin has -->
              ...
          </configuration>
      </plugin>
    </plugins>
  </build>
</project>
```
Run our plugin on your target project after making any change.
```
mvn clean
mvn test-compile
mvn TestSelection:TestSelection
```

## Example
#### Target project URL and SHA

https://github.com/apache/commons-dbutils

1e4b780

#### Result of our toll
First run on our tool: 
>Tests run: 300, Failures: 0, Errors: 0, Skipped: 0, Total time: 5.046 s

Make some change in AsyncQueryRunner.Java

Second run:
>Tests run: 32, Failures: 0, Errors: 0, Skipped: 0, Total time: 4.003 s

#### Comparing with Ekstazi
Ekastazi is a Dynamic Regression Test Selection Tool

First run on Ekastazi:
>Tests run: 300, Failures: 0, Errors: 0, Skipped: 0, Total time: 10.521 s

Make the same change in AsyncQueryRunner.Java

Second run:
>Tests run: 32, Failures: 0, Errors: 0, Skipped: 0, Total time: 8.714 s


