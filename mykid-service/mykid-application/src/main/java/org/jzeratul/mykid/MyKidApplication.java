package org.jzeratul.mykid;

import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@SpringBootApplication
public class MyKidApplication {

  private static final Logger log = LoggerFactory.getLogger(MyKidApplication.class);
  
  public static void main(String[] args) {
    SpringApplication.run(MyKidApplication.class, args);
  }
  

  @Component
  public class EndpointsListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

      log.info("Listing all registered endpoints:");

      ApplicationContext applicationContext = event.getApplicationContext();

      applicationContext.getBean(RequestMappingHandlerMapping.class)
              .getHandlerMethods()
              .entrySet()
              .stream()
              .sorted(Comparator.comparing(e -> e.getKey().getPatternsCondition().getPatterns().iterator().next()))
              .forEach(
                      entry -> {
                        var iterator = entry.getKey().getMethodsCondition().getMethods().iterator();
                        log.info("{}{} -> {}()", entry.getKey().getPatternsCondition().getPatterns(),
                                iterator.hasNext() ? iterator.next().name() : "",
                                entry.getValue().getMethod().getName());
                      }
              );
    }
  }
}

