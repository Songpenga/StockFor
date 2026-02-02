package hello.core;

import hello.core.MemberInfo.Grade;
import hello.core.MemberInfo.MemberService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {
    public static void main(String[] args) {
/*        AppconfigBeforeSpring appconfigBeforeSpring = new AppconfigBeforeSpring();
        MemberService memberService = appconfigBeforeSpring.memberService();*/

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Appconfig.class);
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);

        Member member = new Member(11L, Grade.VIP, "TESTMember"); //단축키 : ctrl + alt + v
        memberService.join(member); // 멤버 정보로 회원가입

        Member findMember = memberService.findMember(11L);

        System.out.println("new member = " + member.getName());
        System.out.println("find Member = " + findMember.getName());


    }
}
