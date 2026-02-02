package hello.core.scan.filter;

import java.lang.annotation.*;

@Target(ElementType.TYPE) //이건 공부가 필요함
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyExcludeComponent {
}
