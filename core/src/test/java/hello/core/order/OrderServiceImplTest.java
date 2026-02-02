package hello.core.order;

import hello.core.Member;
import hello.core.MemberInfo.Grade;
import hello.core.MemberInfo.MemoryMemberRepository;
import hello.core.discount.FixDiscountPolicy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceImplTest {
    @Test
    void createOrder(){

        MemoryMemberRepository memoryMemberRepository = new MemoryMemberRepository();
        memoryMemberRepository.save(new Member(1L, Grade.VIP, "TEST"));

        OrderServiceImpl orderService = new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy());
        Order order =  orderService.createOrder(1L, "testAAA", 100000);
        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);

        /* 1.
        java: constructor OrderServiceImpl in class hello.core.order.OrderServiceImpl cannot be applied to given types;
        required: no arguments
        found:    hello.core.MemberInfo.MemberRepository,hello.core.discount.DiscountPolicy
        reason: actual and formal argument lists differ in length
        * */

        /* 2.
        java.lang.NullPointerException: Cannot invoke "hello.core.MemberInfo.MemberRepository.findbyId(long)" because "this.memberRepository" is null
        * */

        /* 3.
        java: constructor OrderServiceImpl in class hello.core.order.OrderServiceImpl cannot be applied to given types;
        required: hello.core.MemberInfo.MemberRepository,hello.core.discount.DiscountPolicy
        found:    no arguments
        reason: actual and formal argument lists differ in length
        */

        /* java.lang.NullPointerException: Cannot invoke "hello.core.Member.getGrade()" because "member" is null
         => 회원가입이 되어있지 않아서.
         */
    }

}