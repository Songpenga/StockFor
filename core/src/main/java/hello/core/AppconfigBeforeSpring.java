package hello.core;

import hello.core.MemberInfo.MemberService;
import hello.core.MemberInfo.MemberServiceImpl;
import hello.core.MemberInfo.MemoryMemberRepository;
import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;

/*
* AppConfig는 애플리케이션의 실제 동작에 필요한 구현객체를 생성한다.
* AppConfig는 생성한 객체 인스턴스의 참조를 생성자를 통해서 주입(연결)해준다.
*/
public class AppconfigBeforeSpring {

    public MemberService memberService(){
        /*
        * MemberServiceImpl 입장에서 생성자를 통해 어떤 구현 객체가 들어올지는 알 수 없다.
        * MemberServiceImpl의 생성자를 통해서 어떤 구현 객체를 주입할지는 오직 외부(Appconfig)에서 결정된다
        * MemberServiceImpl은 의존관계에 대한 고민은 외부에 맡기고 실행에만 집중하면 된다.
        */
        
        //ctrl + alt + m
        return new MemberServiceImpl(MemberRepository()); // 생성자 주입


    }

    private static MemoryMemberRepository MemberRepository() {
        return new MemoryMemberRepository();
    }

/*    public OrderService orderService(){
        return new OrderServiceImpl(memberRepository, discountPolicy);
    }*/

    //할인정책
    public DiscountPolicy discountPolicy(){
        return new FixDiscountPolicy();
//        return new RateDiscountPolicy();
    }
    // ==> 리팩터링 장점 : 각각의 메서드 역할이 잘 드러남,
    //                   변경규모가 작아짐 >> 다른 구현체로 변경할 떄 한 부분만 변경하면 된다.
    // appConfing를 보면 역할과 구현클래스가 한눈에 들어온다. 애플리케이션 전체 구성을 빠르게 파악 할 수 있다.

/*    public OrderService orderService(){
        return new OrderServiceImpl(
                new MemoryMemberRepository(),
                new FixDiscountPolicy());
    }*/
}
