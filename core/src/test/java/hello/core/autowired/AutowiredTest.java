package hello.core.autowired;

import hello.core.Member;
import jakarta.annotation.Nullable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AutowiredTest {

/*    //호출안됨
    @Autowired(required = false)
    public void setNoBean1(@Nullable Member member){
        System.out.println("setNoBean1 = " + member);
    }

    //null 호출
    @Autowired(required = false)
    public void setNoBean2(@Nullable Member member){
        System.out.println("setNoBean2 = " + member);
    }

    //Option.empty 호출
    @Autowired(required = false)
    public void setNoBean3(Optional<Member> member){
        System.out.println("setNoBean3 = " + member);
    }*/

    @Test
    void AutowiredOption(){
        ApplicationContext app = new AnnotationConfigApplicationContext(TestBean.class);
    }

    /*
    * * 옵션처리
    * 주입할 스프링 빈이 없어도 동작해야 할 떄가 있따.
    * 그런데 @Autowired만 사용하면 required 옵션의 기본값이 true로 되어 있어서 자동 주입 대상이 없으면 오류가 발생한다.
    *
    * 자동 주입 대상을 옵션으로 처리하는 방법은 다음과 같다.
    *  - @Autowired(required = false) : 자동 주입할 대상이 없으면 수정자 메서드 자체가 호출 안됨
    *  - org.springframework.lang.@Nullable : 자동 주입할 대상이 없으면 null이 입력된다.
    *  - Option<> : 자동 주입할 대상이 없으면 Option.empty가 입력된다.
    */
    static class TestBean{

        @Autowired(required = false)
        public void setNoBean1(Member noBean1){
            System.out.println("setNoBean1 = " + noBean1);
        }

        //null 호출
        @Autowired
        public void setNoBean2(@Nullable Member noBean2){
            System.out.println("setNoBean2 = " + noBean2);
        }

        //Option.empty 호출
        @Autowired
        public void setNoBean3(Optional<Member> noBean3){
            System.out.println("setNoBean3 = " + noBean3);
        }
    }
}

