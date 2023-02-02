package org.camunda.community.extension.spring.zeebe.connector.template.generator.test;

import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.Variable;
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
