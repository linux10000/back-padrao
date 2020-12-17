package br.com.juliano.appclient.structure.validation.model;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import br.com.juliano.appclient.structure.props.MessagesProp;
import br.com.juliano.appclient.structure.validation.model.impl.PessoaTelefoneValidationImpl;

@Documented
@Constraint(validatedBy = PessoaTelefoneValidationImpl.class)
@Target( { ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface PessoaTelefoneValidation {

    String message() default MessagesProp.PESSOA_TELEFONE_NOT_VALID;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    RULE rule() default RULE.NONE;
    
    enum RULE {
    	NONE,
    	INSERT_DEFAULT,
    	UPDATE_DEFAULT;
    }
}
