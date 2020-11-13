package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;



@Retention(RetentionPolicy.RUNTIME)
//FIELD indique que l'annotation doit etre placee avant un attribut
@Target(ElementType.FIELD)
public @interface Id{
}