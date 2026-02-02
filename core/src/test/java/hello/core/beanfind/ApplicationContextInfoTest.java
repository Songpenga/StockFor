package hello.core.beanfind;

import hello.core.Appconfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationContextInfoTest {
    AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext(Appconfig.class);

    @Test
    @DisplayName("모든 빈 출력") //Junit5, Junit6 => 여긴 @ 안해도됨
    void fildAllBean(){
        String[] beanDefinitionNames = app.getBeanDefinitionNames();

        //app.getBean() : 빈 이름으로 빈 객체(인스턴스)를 조회한다.
        //app.getBeanDefinition() : 스프링에 등록된 모든 빈 이름을 조회한다.

        //iter
        for (String beanDefinitionName : beanDefinitionNames) {
            Object bean = app.getBean(beanDefinitionName);
            System.out.println("bean name : " + beanDefinitionName + " object = " + bean);
        }
    }

    @Test
    @DisplayName("애플리케이션 빈 출력")
    void findApplicationBean(){
        String[] beanDefinitionNames = app.getBeanDefinitionNames();

        //Role ROLE_APPLICATION : 직접 등록한 애플리케이션 빈
        //Role ROLE_INFRASTRUCTURE : 스프링이 내부에서 사용하는 빈

        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = app.getBeanDefinition(beanDefinitionName);

            if(beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION){
                Object bean = app.getBean(beanDefinitionName);
                System.out.println("bean name : " + beanDefinitionName + " object = " + bean);
            }

            if(beanDefinition.getRole() == BeanDefinition.ROLE_INFRASTRUCTURE){
                Object bean = app.getBean(beanDefinitionName);
                System.out.println("bean name2 : " + beanDefinitionName + " object2 = " + bean);
            }
        }
    }
}
