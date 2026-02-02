package hello.core.singleton;

import hello.core.Appconfig;
import hello.core.MemberInfo.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SingletonTest {

    @Test
    @DisplayName("스프링 없는 순수한 DI 컨테이너")
    void pureContainer(){
        Appconfig appconfig = new Appconfig();
        // 1.조회 : 호출할 떄 마다 객체를 생성
        MemberService memberService1 = appconfig.memberService();

        // 2.조회 : 호출할 떄 마다 객체를 생성
        MemberService memberService2 = appconfig.memberService();

        //참조값이 다른 것을 확인
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);

        /*항상 자동화되게 만들어야한다. 눈으로만 확인하면 안된다.*/
        //memberService != memberService2
        Assertions.assertThat(memberService1).isNotSameAs(memberService2);
    }

    public static void main(String[] args) {
        // SingletonService sc = new SingletonService();
        //'SingletonService()' has private access in 'hello.core.singleton.SingletonService'
    }

    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체 사용")
    void singletonServiceTest(){
        //new SingletonService(); // java: SingletonService() has private access in hello.core.singleton.SingletonService

        SingletonService singletonService1 = SingletonService.getInstance();
        SingletonService singletonService2 = SingletonService.getInstance();

        System.out.println("singletonService1 " + singletonService1);
        System.out.println("singletonService2 " + singletonService2);

        Assertions.assertThat(singletonService1).isSameAs(singletonService2);
    }

    @Test
    @DisplayName("스프링 컨테이너와 싱글톤")
    void singletonContainer(){

        ApplicationContext app = new AnnotationConfigApplicationContext(Appconfig.class);
        // 1.조회 : 호출할 떄 마다 객체를 생성
        MemberService memberService1 = app.getBean("memberService", MemberService.class);
        MemberService memberService2 = app.getBean("memberService", MemberService.class);

        //참조값이 다른 것을 확인
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);

        Assertions.assertThat(memberService1).isSameAs(memberService2);
    }
}
