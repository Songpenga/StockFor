package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.RateDiscountPolicy;

public class Note {
    /*
    * 25.02.05
    * @Autowired 매칭 정리
    * 1. 타입 매칭
    * 2. 타입 매칭의 결과가 2개 이상이 때 필드명, 파라미터 명으로 빈 이름 매칭
    *
    * @Quilifier 사용
    * @Quilifier는 추가 구분자를 붙여주는 방법이다. 주입시 추가적인 방법을 제공하는 것이지
    * 빈 이름을 변경하는 것은 아니다.
    * => 빈 등록시 @Quilifier를 붙여준다.
    * Quilifier는 Quilifier를 찾는 용도로만 사용하는게 명확하고 좋다.
    *
    * @Primary 사용
    * @Primary 는 우선순위를 정하는 방법이다. @Autowired 시에 여러 빈 매칭되면 @Primary 가 우선권을 가진다.
    */
    /*
    * 25.02.04
    * @Autowired는 타입(type)으로 조회한다.
    * 타임으로 조회하면 선택된 빈이 2개 이상일 떄 문제가 발생한다.
    * Caused by: org.springframework.beans.factory.NoUniqueBeanDefinitionException
    * //No qualifying bean of type 'hello.core.discount.DiscountPolicy'
    * //available: expected single matching bean but found 2: fixDiscountPolicy,rateDiscountPolicy
    */
    /*
    * 25.01.23
    * 생성자 주입 방식을 서택하는 이유는 여러가지가 있지만,
    * 프레임 워크에 의존하지 않고 순수한 자바 언어의 특징을 잘 살리는 방법이기도 하다.
    * 기본으로 생성자 주입을 사용하고, 필수 값이 아닌 경우에느 수정자 주입 방식을 옵션으로
    * 부여하면 된다. 생성자 주입을 동시에 사용할 수 있따.
    * 항상 생성자 주입을 선택해라, 그리고 가끔 옵션이 필요하면 수정자 주입을 선택해라, 필드주입은 사용하지 않는게 좋다.
    */
    /*
    * 25.01.06
    *
    * 싱글톤 컨테이너
    *   스프링 컨테이너는 싱글턴 패턴을 적용하지 않아도, 객체 인스턴스르 싱글톤으로 관리한다.
        스프링 컨테이너는 싱글톤 컨테이너 역할을 한다. 이렇게 싱그론 객체를 생성하고 관리하는 기능을 싱글톤 레지스트리라 한다.
        * 스프링 컨테이너의 이런 기능 덕분에 싱글턴 패턴의 모든 단점을 해결하면서 객체를 싱글톤으로 유지할 수 있다.
        * DIP, OCP, 테스트, private 생성자로부터 자유롭게 싱글톤을 사용할 수 있다.
    * 싱글톤 패턴
    *   이미 만들어진 객체를 공유해서 효율적으로 사용할 수 있다.
    *
    * 싱글톤 패턴 문제점
    *   싱글톤 패턴을 구현하는 코드 자체가 많이 들어간다.
    * 의존관계상 클라이언트가 구체 클레스에 의존한다. -> dip를 위반한다
    * 클라이언트가 구체 클레스에 의존해서 ocp원칙을 위반할 가능성이 높다.
    * 테스트하기 어렵다.
    * 내부 속성을 변경하거나 초기화 하기 어렵다.
    * private 생성자로 자식 클래스를 만들기 어렵다.
    * 유연성이 떨어진다
    * 안티 패턴으로 불리기도 한다.
    */
    /*
    * 25.01.03 스프링 빈 조회 - 동일한 타입이 둘 이상
    * 타입으로 조회시 같은 타입의 스프링 빈이 들 이상이면 오류가 발생한다. 이떄는 빈 이름을 지정해야된다.
    * ac.getBeansOfType() 을 사용하면 해당 타입의 모든 빈을 조회할 수 있다.
    */
    
    /*
    * 25.01.02
    * AppConging의 등장으로 애플리케이션이 사용영역과 구성영역으로 분리되었다.
    * 사용영역 : OrderServiceImpl, DiscountPolicy, FixDiscountPolicy, RateDiscountPolicy
    * 구성영역 : Appconfig
    *
    * 스프링 빈 조회 : getBean
    * 조회 대상 스프링 빈이 없으면 예외 발생 => NoSuchBeanDefinitionExceptopm : no bean name 'xxxxxxx' available;
    */
}
