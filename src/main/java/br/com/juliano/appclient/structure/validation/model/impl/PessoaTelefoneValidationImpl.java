package br.com.juliano.appclient.structure.validation.model.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.juliano.appclient.enums.TipoTelefone;
import br.com.juliano.appclient.model.PessoaTelefone;
import br.com.juliano.appclient.structure.props.MessagesProp;
import br.com.juliano.appclient.structure.utils.Comuns;
import br.com.juliano.appclient.structure.utils.SpringValidationUtil;
import br.com.juliano.appclient.structure.validation.model.PessoaTelefoneValidation;

public class PessoaTelefoneValidationImpl implements ConstraintValidator<PessoaTelefoneValidation, PessoaTelefone>{

	@Autowired
	private SpringValidationUtil springValidationUtil;
	
	private PessoaTelefoneValidation.RULE rule;
	
	@Override
	public void initialize(PessoaTelefoneValidation constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
		this.rule = constraintAnnotation.rule();
	}
	
	@Override
	public boolean isValid(PessoaTelefone ptl, ConstraintValidatorContext arg1) {
		return switch (rule) {
			case INSERT_DEFAULT -> isInsertDefaultValid(ptl, arg1);
			case UPDATE_DEFAULT -> isUpdateDefaultValid(ptl, arg1);
			default -> false;
		};
	}

	private boolean isInsertDefaultValid(PessoaTelefone ptl, ConstraintValidatorContext context) {
		if ( !springValidationUtil.isNotNull(ptl, MessagesProp.PESSOA_TELEFONE_NOT_NULL, context) )
			return false;
		
		return Comuns.checkAll(
				springValidationUtil.isNotEmpty(ptl.getPtlcnumero(), MessagesProp.PESSOA_TELEFONE_NUMERO_NOT_NULL, context),
				springValidationUtil.isNotNull(ptl.getPtlnpesFK(), MessagesProp.PESSOA_NOT_NULL, context),
				springValidationUtil.isNotNull(ptl.getPtlntipoEnum(), MessagesProp.PESSOA_TELEFONE_TIPO_NOT_NULL, context),
				springValidationUtil.isNotTrue(!ptl.getPtlntipoEnum().equals(TipoTelefone.NOT_AVAILABLE), MessagesProp.PESSOA_TELEFONE_TIPO_NOT_VALID, context)
				);
	}
	
	private boolean isUpdateDefaultValid(PessoaTelefone ptl, ConstraintValidatorContext context) {
		if ( !springValidationUtil.isNotNull(ptl, MessagesProp.PESSOA_TELEFONE_NOT_NULL, context) )
			return false;
		
		return Comuns.checkAll(
				springValidationUtil.isNotNull(ptl.getPtlnid(), MessagesProp.PESSOA_TELEFONE_ID_NOT_NULL, context),
				springValidationUtil.isNotEmpty(ptl.getPtlcnumero(), MessagesProp.PESSOA_TELEFONE_NUMERO_NOT_NULL, context),
				springValidationUtil.isNotNull(ptl.getPtlnpesFK(), MessagesProp.PESSOA_NOT_NULL, context),
				springValidationUtil.isNotNull(ptl.getPtlntipoEnum(), MessagesProp.PESSOA_TELEFONE_TIPO_NOT_NULL, context),
				springValidationUtil.isNotTrue(!ptl.getPtlntipoEnum().equals(TipoTelefone.NOT_AVAILABLE), MessagesProp.PESSOA_TELEFONE_TIPO_NOT_VALID, context)
				);
	}
}
