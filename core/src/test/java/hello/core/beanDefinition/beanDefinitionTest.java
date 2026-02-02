package hello.core.beanDefinition;

import hello.core.Appconfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class beanDefinitionTest {
    AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext(Appconfig.class);

    @Test
    @DisplayName("빈 설정 메타정보 확인")
    void findApplicationBean(){
        String[] beanDefinitionNames = app.getBeanDefinitionNames();
        for (String beanDef : beanDefinitionNames) {
            BeanDefinition beanDefinition = app.getBeanDefinition(beanDef);

            if(beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION){
                System.out.println("beandef = " + beanDef +
                        " beanDefinition = " + beanDefinition);
            }
        }
    }
}
