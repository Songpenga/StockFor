package hello.core.discount;

import hello.core.Member;
import hello.core.MemberInfo.Grade;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class RateDiscountPolicyTest {

    RateDiscountPolicy rateDiscountPolicy = new RateDiscountPolicy();

    @Test
    @DisplayName("VIP는 10% 할인이 적용되어야 한다.")
    void vip_test(){
        //given
        //임의의 멤버 생성
        Member member = new Member(27L, Grade.VIP, "testName");

        //when
        int discount = rateDiscountPolicy.discount(member, 10000);

        //then
        assertThat(discount).isEqualTo(1000); //alt+enter => import static
    }

    @Test
    @DisplayName("VIP가 아니면 할인이 적용되지 않아야됨")
    void vip_x_test(){
        //given
        //임의의 멤버 생성
        Member member = new Member(27L, Grade.BASIC, "testName");

        //when
        int discount = rateDiscountPolicy.discount(member, 100000);

        //then
        assertThat(discount).isEqualTo(0);
    }

}