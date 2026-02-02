package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

class StatefulServiceTest {

    @Test
    void statefulServiceSingle(){
        ApplicationContext app = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulBean1 = app.getBean(StatefulService.class);
        StatefulService statefulBean2 = app.getBean(StatefulService.class);

        //ThreadA : A사용자 주문
        //[1]
        //statefulBean1.order("userA", 170000);

        //[2]
        int userAPrice = statefulBean1.order("userA", 10000);
        int userBPrice = statefulBean2.order("userB", 70000);

        //[1] ThreadB : B사용자 주문
        //statefulBean2.order("userB", 50000);

        //ThreadA : A사용자 주문금액 조회
        // int price = statefulBean1.getPrice(); // [1]
         System.out.println("price = " + userAPrice);

        //Assertions.assertThat(statefulBean1.getPrice()).isEqualTo(50000);
        //ThreadB : B사용자 주문금액 조회
        //int priceB = statefulBean2.getPrice();

    }


    static class TestConfig{

        @Bean
        public StatefulService statefulService()
        {
            return new StatefulService();
        }
    }
}