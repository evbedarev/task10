package homework.bedarev.task_03;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)

public @interface Cache {
    String cacheType() default "MEMORY";
    String fileNamePrefix() default "";
    boolean zip() default false;
    Class[] identityBy() default {};
    int maxCountList() default 0;
}
