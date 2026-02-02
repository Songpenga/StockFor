package hello.core.singleton;

import hello.core.Appconfig;
import hello.core.MemberInfo.MemberRepository;
import hello.core.MemberInfo.MemberServiceImpl;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigurationSingletonTest {

    @Test
    void conigurationTest(){
        ApplicationContext app = new AnnotationConfigApplicationContext(Appconfig.class);

        MemberServiceImpl memberService = app.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = app.getBean("orderService", OrderServiceImpl.class);
        MemberRepository memberRepository = app.getBean("memberRepository", MemberRepository.class);

        MemberRepository memberRepository1 = memberService.getMemberRepository();
        MemberRepository memberRepository2 = orderService.getMemberRepository();

        System.out.println("memberService -> memberRepository = " + memberRepository1);
        System.out.println("orderService -> memberRepository = " + memberRepository2);
        System.out.println("memberRepository = " + memberRepository);

/*        Assertions.assertThat(memberService.getMemberRepository()).isSameAs(memberRepository);
        Assertions.assertThat(orderService.getMemberRepository()).isSameAs(memberRepository);*/

    }

    @Test
    void configurationDeep(){
        ApplicationContext app = new AnnotationConfigApplicationContext(Appconfig.class);
        Appconfig bean = app.getBean(Appconfig.class);

        System.out.println("app = " + app.getClass());
        System.out.println("bean = " + bean.getClass());
    }
}
