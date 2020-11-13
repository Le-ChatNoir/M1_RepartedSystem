package annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
// Annotation accessible a  l'execution

@Target(ElementType.TYPE)
// Annotation associee a une classe



public @interface Table {

	String name();

}