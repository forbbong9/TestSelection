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



