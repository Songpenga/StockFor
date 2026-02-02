package hello.core.discount;

import hello.core.Member;

public interface DiscountPolicy {

    /*
    * @return 할인금액
    */
    int discount(Member member, int price);
}
