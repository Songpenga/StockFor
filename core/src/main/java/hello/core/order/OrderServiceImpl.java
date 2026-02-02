package hello.core.order;

import hello.core.Member;
import hello.core.MemberInfo.MemberRepository;
import hello.core.MemberInfo.MemoryMemberRepository;
import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService{

    /* 인터페이스 의존으로 수정*/
    private final MemberRepository memberRepository; // 필드 주입
    private final DiscountPolicy discountPolicy;

    //@RequiredArgsConstructor => 생성자를 만들어줌
    @Autowired //생성자 주입
    public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy  DiscountPolicy discountPolicy) {
        System.out.println("memberRepository " + memberRepository);
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findbyId(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    //테스트용으로 추가
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }

/*
    추상(인터페이스)의존 : DiscountPolicy
    구현클래스 의존 : RateDiscountPolicy, FixDiscountPolicy
    => DIP를 위반하지 않도록 인터페이스에만 의존하도록 의존관계를 변경하면 된다.
       어떻게? -> 인터페이스에만 의존하도록 설계를 변경하면 된다.
    */

    /* ====== 관심사의 분리 ======
    * OrderServiceImpl에는 MemoryMemberRepository, FixDiscountPolicy 객체의 의존관계가 주입된다.
    */
    //private final MemberRepository memberRepository = new MemoryMemberRepository();


/*    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }*/

    /*
    * @Autowired 의 기본동작은 주입할 대상이 없으면 오류가 발생한다. 주입할 대상이 없어도 동작하게 하려면
    * return = false로 지정하면 된다.
    */


    /* DIP 위반 */
    //private final DiscountPolicy discountPolicy = new FixDiscountPolicy(); // <==  고정할인
    //private final DiscountPolicy discountPolicy = new RateDiscountPolicy();



/*    @Autowired // 일반적으로 잘 사용되지 않음
    public void init(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }*/
}
