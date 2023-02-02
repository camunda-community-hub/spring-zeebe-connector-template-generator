package org.camunda.community.extension.spring.zeebe.connector.template.generator;

import io.camunda.zeebe.spring.client.annotation.customizer.ZeebeWorkerValueCustomizer;
import io.camunda.zeebe.spring.client.annotation.value.ZeebeWorkerValue;
import org.springframework.stereotype.Component;

@Component
public class SpringZeebeConnectorTemplateGenerator implements ZeebeWorkerValueCustomizer {

  @Override
  public void customize(ZeebeWorkerValue zeebeWorker) {
    System.out.println("############## Element Template ######################");
    System.out.println(
        "{\n"
            + "  \"$schema\": \"https://unpkg.com/@camunda/zeebe-element-templates-json-schema/resources/schema.json\",\n"
            + "  \"id\": \""
            + zeebeWorker.getType()
            + "\",\n"
            + "  \"name\": \""
            + zeebeWorker.getType()
            + "\",\n"
            + "  \"appliesTo\": [\n    \"bpmn:Task\"\n  ],\n"
            + "  \"elementType\": {\n    \"value\": \"bpmn:ServiceTask\"\n  },\n"
            + "  \"properties\": [\n"
            + "    {\n      \"type\": \"Hidden\",\n      \"value\": \""
            + zeebeWorker.getType()
            + "\",\n      \"binding\": {\n        \"type\": \"zeebe:taskDefinition:type\"\n      }\n    }");
    for (String variable : zeebeWorker.getFetchVariables()) {
      System.out.println(
          ",\n    {\n      \"label\": \""
              + variable
              + "\",\n      \"description\": \""
              + variable
              + "\",\n      \"value\": \"="
              + variable
              + "\",\n      \"type\": \"String\",\n      \"feel\": \"optional\",\n      \"binding\": {\n        \"type\": \"zeebe:input\",\n        \"name\": \""
              + variable
              + "\"\n      },\n      \"constraints\": {\n        \"notEmpty\": true\n      }\n    }");
    }
    System.out.println("  ]\n" + "}");
  }
}
