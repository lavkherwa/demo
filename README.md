# demo
Everything - Spring, JPA, Resilience, [Unit, Integration, MockMvc, WireMock] tests

## Resilience
* Configuration
  * Dependencies: [dependencies](https://github.com/lavkherwa/demo/blob/main/pom.xml#L50)
  * Properties: [application.yaml](https://github.com/lavkherwa/demo/blob/main/src/main/resources/application.yaml#L4)
* Usage
  * Client Impl: [BookDetailServiceClientImpl](https://github.com/lavkherwa/demo/blob/main/src/main/java/com/example/demo/client/impl/BookDetailServiceClientImpl.java#L34)

## Mock Server for testing external services with mock data
* Configuration
  * Dependencies: [dependencies](https://github.com/lavkherwa/demo/blob/main/pom.xml#L83)
  * Mock Server Config: [WireMockInitializer](https://github.com/lavkherwa/demo/blob/main/src/test/java/com/example/demo/client/config/WireMockInitializer.java)
* Usage
  * Client Impl Test: [BookDetailServiceClientImplTest](https://github.com/lavkherwa/demo/blob/main/src/test/java/com/example/demo/client/impl/BookDetailServiceClientImplTest.java#L21)
