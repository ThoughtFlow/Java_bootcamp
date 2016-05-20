package module11_util;
import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({METHOD, TYPE, FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnotation {

	public String value() default "none";
}
