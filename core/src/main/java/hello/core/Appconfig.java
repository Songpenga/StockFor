package hello.core;

/*
* @Bean memberService -> new MemoryMemberRepository
* @Beam orderService  -> new MemoryMemberRepository
*
* call AppConfing.memberService
* callm Appconfig.memberRepository
* callm Appconfig.memberRepository
* callO Appconfig.orderService
* callm Appconfig.memberRepository
*/
import hello.core.MemberInfo.MemberRepository;
import hello.core.MemberInfo.MemberService;
import hello.core.MemberInfo.MemberServiceImpl;
import hello.core.MemberInfo.MemoryMemberRepository;
import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/*
    @Bean이 붙은 메서드마다 이미 스프링 빈이 존재하면 존재하는 빈을 반환하고, 스프링 빈이 없으면 생성해서
    스프링 빈으로 등록하고 반환하는 코드가 동적으로 반들어진다.
    덕분에 싱글톤이 보장되는 것이다.

    @Bean만 사용해도 스프링 빈으로 등록되지만, 싱글톤을 보장하지 않는다

* */
@Configuration
public class Appconfig {

    @Bean
    public MemberService memberService(){
        // soutm : System.out.println("Appconfig.memberService");
        System.out.println("call AppConfing.memberService");
        return new MemberServiceImpl(MemberRepository()); // 생성자 주입
    }

    @Bean
    public MemberRepository memberRepository(){
        System.out.println("callm Appconfig.memberRepository");
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService(){
        System.out.println("callO Appconfig.orderService");
        return new OrderServiceImpl(
                MemberRepository(),
                discountPolicy());
    }

    @Primary
    public MemoryMemberRepository MemberRepository() {
        return new MemoryMemberRepository();
    }

    //할인정책
    @Bean
    public DiscountPolicy discountPolicy(){
        return new FixDiscountPolicy();
//        return new RateDiscountPolicy();
    }

}
