package org.camunda.community.extension.spring.zeebe.connector.template.generator;

import io.camunda.zeebe.spring.client.annotation.customizer.ZeebeWorkerValueCustomizer;
import io.camunda.zeebe.spring.client.annotation.processor.ZeebeWorkerAnnotationProcessor;
import io.camunda.zeebe.spring.client.annotation.value.ZeebeWorkerValue;
import io.camunda.zeebe.spring.client.bean.ClassInfo;
import io.camunda.zeebe.spring.client.bean.MethodInfo;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import org.camunda.community.extension.spring.zeebe.connector.template.generator.test.MyWorker;
import org.junit.jupiter.api.Test;

public class SpringZeebeConnectorTemplateGeneratorTest {
  @Test
  void testCustomize() {
    ArrayList<ZeebeWorkerValueCustomizer> zeebeWorkerValueCustomizers = new ArrayList<>();
    SpringZeebeConnectorTemplateGenerator generator = new SpringZeebeConnectorTemplateGenerator();
    zeebeWorkerValueCustomizers.add(generator);
    final ZeebeWorkerAnnotationProcessor annotationProcessor =
        new ZeebeWorkerAnnotationProcessor(null, zeebeWorkerValueCustomizers, null, null);
    final MethodInfo methodInfo = extract(MyWorker.class, "invokeMyService");
    final Optional<ZeebeWorkerValue> zeebeWorkerValue =
        annotationProcessor.readJobWorkerAnnotationForMethod(methodInfo);
    generator.customize(zeebeWorkerValue.get());
  }

  private MethodInfo extract(Class<?> clazz, String methodName) {
    final Method method =
        Arrays.stream(clazz.getMethods())
            .filter(m -> m.getName().equals(methodName))
            .findFirst()
            .get();
    final ClassInfo classInfo = ClassInfo.builder().build();
    return MethodInfo.builder().classInfo(classInfo).method(method).build();
  }
}
