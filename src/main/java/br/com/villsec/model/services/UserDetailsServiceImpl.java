package br.com.villsec.model.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.villsec.model.entities.domain.AutenticacaoSS;
import br.com.villsec.model.repository.IAutenticacaoSSRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private IAutenticacaoSSRepository theRepository;
	
	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		AutenticacaoSS theAutenticacaoSS = theRepository.findByLogin(login);
		if (theAutenticacaoSS == null) {
			throw new UsernameNotFoundException(login);
		}
		return theAutenticacaoSS;
	}
}
