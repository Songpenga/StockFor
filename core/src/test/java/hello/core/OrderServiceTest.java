package hello.core;

import hello.core.MemberInfo.Grade;
import hello.core.MemberInfo.MemberService;
import hello.core.MemberInfo.MemoryMemberRepository;
import hello.core.discount.FixDiscountPolicy;
import hello.core.order.Order;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OrderServiceTest {

    MemberService memberService;
    OrderService orderService;

    @BeforeEach
    public void beforEach(){
        Appconfig appconfig = new Appconfig();
        memberService = appconfig.memberService();
        orderService = appconfig.orderService();
    }


    @Test
    void createOrder(){
        long memberId = 1L;
        Member member = new Member(1L, Grade.VIP, "test계정");
        memberService.join(member);

        Order order = orderService.createOrder(1L, "testItem", 10000);
        //Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);

        System.out.println("test complete");

    }

}
