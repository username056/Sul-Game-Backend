package org.sejong.sulgamewiki.util;

import io.swagger.v3.oas.models.Operation;
import org.sejong.sulgamewiki.util.annotation.ApiChangeLog;
import org.sejong.sulgamewiki.util.annotation.ApiChangeLogs;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springdoc.core.customizers.OperationCustomizer;

@Component
public class CustomOperationCustomizer implements OperationCustomizer {

  @Override
  public Operation customize(Operation operation, HandlerMethod handlerMethod) {
    ApiChangeLogs apiChangeLogs = handlerMethod.getMethodAnnotation(ApiChangeLogs.class);

    if (apiChangeLogs != null) {
      StringBuilder tableBuilder = new StringBuilder();

      tableBuilder.append("\n\n**변경 이력:**\n\n");
      tableBuilder.append("| 날짜       | 작성자   | 변경 내용 |\n");
      tableBuilder.append("|------------|----------|-----------|\n");

      for (ApiChangeLog log : apiChangeLogs.value()) {
        // 변경 내용에 파이프(|) 문자가 있을 경우 이스케이프 처리
        String description = log.description().replace("|", "\\|");

        tableBuilder.append(String.format("| %s | %s | %s |\n",
            log.date(), log.author(), description));
      }

      String originalDescription = operation.getDescription() != null ? operation.getDescription() : "";
      operation.setDescription(originalDescription + tableBuilder.toString());
    }

    return operation;
  }
}