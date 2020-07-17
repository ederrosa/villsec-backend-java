package br.com.villsec.model.services.validations;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.villsec.model.repository.IPessoaRepository;
import br.com.villsec.model.services.dtos.ProprietarioDTO;
import br.com.villsec.model.services.exceptions.FieldMessage;
import br.com.villsec.model.services.validations.annotations.ProprietarioAnnotation;


public class ProprietarioValidator implements ConstraintValidator< ProprietarioAnnotation, ProprietarioDTO> {

	@Autowired
	private IPessoaRepository theIPessoaRepository;

	@Override
	public void initialize(ProprietarioAnnotation ann) {
	}

	@Override
	public boolean isValid(ProprietarioDTO objDto, ConstraintValidatorContext context) {

		List<FieldMessage> list = new ArrayList<>();

		if (this.theIPessoaRepository.findByTheEmailEmail(objDto.getEmail()) != null) {
			if (objDto.getId() == null
					|| !this.theIPessoaRepository.findByTheEmailEmail(objDto.getEmail()).getId().equals(objDto.getId())) {
				list.add(new FieldMessage("Email", "Email já existente e não pertece ao usuário em questão!"));
			}
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}
