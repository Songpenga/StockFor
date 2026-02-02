package hello.core;

import hello.core.MemberInfo.Grade;
import hello.core.MemberInfo.MemberService;
import hello.core.order.Order;
import hello.core.order.OrderService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class OrderApp {
    public static void main(String[] args) {

/*        AppconfigBeforeSpring appconfigBeforeSpring = new AppconfigBeforeSpring();
        MemberService memberService = appconfigBeforeSpring.memberService();
        OrderService orderService = appconfigBeforeSpring.orderService();*/

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Appconfig.class);

        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
        OrderService orderService = applicationContext.getBean("orderService", OrderService.class);


/*      MemberService memberService = new MemberServiceImpl(null);
        OrderService orderService = new OrderServiceImpl(null, null);*/

        long memberId = 11L;
        Member member = new Member(memberId, Grade.VIP, "TestMember");
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "testItem", 20000);

        System.out.println("order => " + order);
        System.out.println("order => " + order.getItemName());
        System.out.println("order => " + order.calculatePrice());
    }
}
