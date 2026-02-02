package hello.core.scan;

import hello.core.AutoAppConfig;
import hello.core.MemberInfo.MemberRepository;
import hello.core.MemberInfo.MemberService;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;

class AutoAppConfigTest {

    @Test
    void basicScan(){
        AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext(AutoAppConfig.class);

        MemberService memberService = app.getBean(MemberService.class);
        assertThat(memberService).isInstanceOf(MemberService.class);

        OrderServiceImpl bean = app.getBean(OrderServiceImpl.class);
        MemberRepository memberRepository =  bean.getMemberRepository();
        System.out.println("memberRepository = " + memberRepository);
    }
}