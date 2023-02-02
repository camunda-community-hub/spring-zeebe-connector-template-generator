[![Community badge: Incubating](https://img.shields.io/badge/Lifecycle-Incubating-blue)](https://github.com/Camunda-Community-Hub/community/blob/main/extension-lifecycle.md#incubating-)
[![Community extension badge](https://img.shields.io/badge/Community%20Extension-An%20open%20source%20community%20maintained%20project-FF4700)](https://github.com/camunda-community-hub/community)

# Spring Zeebe Connector Template Generator

Generator for [Element Templates](https://docs.camunda.io/docs/next/components/modeler/desktop-modeler/element-templates/about-templates/) for [Camunda Modeler](https://docs.camunda.io/docs/next/components/modeler/about-modeler/) from Job Workers using [Spring Zeebe annotations](https://github.com/camunda-community-hub/spring-zeebe#job-worker-configuration-options).


## Usage
Build using `mvn clean install`.
Add the following dependency to your Spring Zeebe Maven project:
```xml
    <dependency>
      <groupId>org.camunda.community.extension.spring.zeebe.connector.template.generator</groupId>
      <artifactId>spring-zeebe-connector-template-generator</artifactId>
      <version>0.0.1-SNAPSHOT</version>
    </dependency>
```
Add the generator to the component scan of your `ProcessApplication` class:
```java
@SpringBootApplication(
    scanBasePackageClasses = {
      ProcessApplication.class,
      SpringZeebeConnectorTemplateGenerator.class
    })
```

## Example
### Job Worker
```java
package org.example.camunda.process.solution.worker;

import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.Variable;
import org.example.camunda.process.solution.ProcessVariables;
import org.example.camunda.process.solution.service.MyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MyWorker {

  private static final Logger LOG = LoggerFactory.getLogger(MyWorker.class);

  private final MyService myService;

  public MyWorker(MyService myService) {
    this.myService = myService;
  }

  @JobWorker
  public ProcessVariables invokeMyService(@Variable String businessKey) {
    LOG.info("Invoking myService with variables: " + businessKey);

    boolean result = myService.myOperation(businessKey);

    return new ProcessVariables()
        .setResult(result); // new object to avoid sending unchanged variables
  }
}
```
### Resulting template
```json
{
  "$schema": "https://unpkg.com/@camunda/zeebe-element-templates-json-schema/resources/schema.json",
  "id": "invokeMyService",
  "name": "invokeMyService",
  "appliesTo": [
    "bpmn:Task"
  ],
  "elementType": {
    "value": "bpmn:ServiceTask"
  },
  "properties": [
    {
      "type": "Hidden",
      "value": "invokeMyService",
      "binding": {
        "type": "zeebe:taskDefinition:type"
      }
    }
,
    {
      "label": "businessKey",
      "description": "businessKey",
      "value": "=businessKey",
      "type": "String",
      "feel": "optional",
      "binding": {
        "type": "zeebe:input",
        "name": "businessKey"
      },
      "constraints": {
        "notEmpty": true
      }
    }
  ]
}
```
## TODOs

* [ ] Add contribution guide to the repo (
  e.g. [Contributing to this project](https://gist.github.com/jwulf/2c7f772570bfc8654b0a0a783a3f165e) )