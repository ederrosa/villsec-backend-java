package br.com.villsec.model.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.villsec.model.entities.domain.AutenticacaoSS;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;

	private JWTUtil jwtUtil;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
		
		setAuthenticationFailureHandler(new JWTAuthenticationFailureHandler());
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		AutenticacaoSS theAutenticacaoSS = new AutenticacaoSS();
		theAutenticacaoSS.setLogin(request.getParameter("login"));
		theAutenticacaoSS.setSenha(request.getParameter("senha"));					
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				theAutenticacaoSS.getLogin(), theAutenticacaoSS.getSenha(), new ArrayList<>());
		Authentication authentication = authenticationManager.authenticate(authenticationToken);
		return authentication;
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authentication) throws IOException, ServletException {
		
		String username = ((AutenticacaoSS) authentication.getPrincipal()).getUsername();
		String token = jwtUtil.generateToken(username);
		response.addHeader("Authorization", "Bearer " + token);
		response.addHeader("access-control-expose-headers", "Authorization");
		response.addHeader("UserType", String.valueOf(((AutenticacaoSS) authentication.getPrincipal()).getTipoUsuario().getCodigo()));
		response.addHeader("access-control-expose-headers", "UserType");
		response.addHeader("UserMatricula", ((AutenticacaoSS) authentication.getPrincipal()).getMatricula().toString());
		response.addHeader("access-control-expose-headers", "UserMatricula");
		response.addHeader("UserUriImgPerfil", ((AutenticacaoSS) authentication.getPrincipal()).getUriImgPerfil().toString());
		response.addHeader("access-control-expose-headers", "UserUriImgPerfil");
	}

	private class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler {

		@Override
		public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
				AuthenticationException exception) throws IOException, ServletException {
			
			response.setStatus(401);
			response.setContentType("application/json");
			response.getWriter().append(json());
		}

		private String json() {
		
			long date = new Date().getTime();
			return "{\"timestamp\": " + date + ", " + "\"status\": 401, " + "\"error\": \"Não autorizado\", "
					+ "\"message\": \"Login ou senha inválidos\", " + "\"path\": \"/login\"}";
		}
	}
}