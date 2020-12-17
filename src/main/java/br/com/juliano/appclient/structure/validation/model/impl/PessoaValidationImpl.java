package br.com.juliano.appclient.structure.validation.model.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.juliano.appclient.model.Pessoa;
import br.com.juliano.appclient.structure.props.MessagesProp;
import br.com.juliano.appclient.structure.utils.Comuns;
import br.com.juliano.appclient.structure.utils.FormatUtil;
import br.com.juliano.appclient.structure.utils.SpringValidationUtil;
import br.com.juliano.appclient.structure.validation.model.PessoaValidation;

public class PessoaValidationImpl implements ConstraintValidator<PessoaValidation, Pessoa>{

	@Autowired
	private SpringValidationUtil springValidationUtil;
	
	private PessoaValidation.RULE rule;
	
	@Override
	public void initialize(PessoaValidation constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
		this.rule = constraintAnnotation.rule();
	}
	
	@Override
	public boolean isValid(Pessoa pes, ConstraintValidatorContext arg1) {
		return switch (rule) {
			case INSERT_DEFAULT -> isInsertDefaultValid(pes, arg1); //validacoes diferentes para metodos diferentes
			case UPDATE_DEFAULT -> isUpdateDefaultValid(pes, arg1);
			default -> false;
		};
	}

	private boolean isInsertDefaultValid(Pessoa pes, ConstraintValidatorContext context) {
		if ( !springValidationUtil.isNotNull(pes, MessagesProp.PESSOA_NOT_NULL, context) )
			return false;
		
		return Comuns.checkAll(
				springValidationUtil.isNotEmpty(pes.getPescnome(), MessagesProp.PESSOA_NOME_NOT_NULL, context),
				springValidationUtil.isNotEmpty(pes.getPescemail(), MessagesProp.PESSOA_EMAIL_NOT_NULL, context),
				springValidationUtil.isNotTrue(FormatUtil.emailValido(pes.getPescemail()), MessagesProp.PESSOA_EMAIL_NOT_VALID, context)
				);
	}
	
	private boolean isUpdateDefaultValid(Pessoa pes, ConstraintValidatorContext context) {
		if ( !springValidationUtil.isNotNull(pes, MessagesProp.PESSOA_NOT_NULL, context) )
			return false;
		
		return Comuns.checkAll(
				springValidationUtil.isNotEmpty(pes.getPescnome(), MessagesProp.PESSOA_NOME_NOT_NULL, context),
				springValidationUtil.isNotEmpty(pes.getPescemail(), MessagesProp.PESSOA_EMAIL_NOT_NULL, context),
				springValidationUtil.isNotTrue(FormatUtil.emailValido(pes.getPescemail()), MessagesProp.PESSOA_EMAIL_NOT_VALID, context)
				);
	}
}
