package hello.core;

import hello.core.MemberInfo.MemberRepository;
import hello.core.MemberInfo.MemoryMemberRepository;
import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan(
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION,
                                            classes = Configuration.class) //빈에 올리지 않을 타입 지정
)

public class AutoAppConfig {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    @MainDiscountPolicy
    DiscountPolicy discountPolicy;

/*    @Bean
    OrderService orderService(){
        //return new OrderServiceImpl(memberRepository, discountPolicy);
        return null;
    }*/
/*    OrderService orderService(MemberRepository memberRepository, DiscountPolicy discountPolicy){
        return new OrderServiceImpl(memberRepository, discountPolicy);
    }*/

    @Bean(name = "memoryMemberRepository")
    @Primary
    MemoryMemberRepository memberRepository(){
        return new MemoryMemberRepository();
    } // 이 경우 수동 빈 등록이 우선권을 가져간다.
}
