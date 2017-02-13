# Description

This project was created to test spring boot with rest and multiple datasources.

# Structure

we have two domain classes Sale and Product, each in their own package, this is necessary to separate datasources in a easy way.

The configuration files for the datasources are in the application.yml where I declared two datasources:

The configuration beans are defined on:

* DatasourceFactoryConfiguration.java
* DatasourceFinancialConfiguration.java


# Development

clone the project and presto!

# Test

The integration tests are int the SpringBootDataMultidatasourceApplicationTests file

Simply run as JUnit test, on STS or with command *gradle test*

## IDE

This project was created with gradle and Spring Source Tools IDE

# Feedback

Any sugestions are welcome.