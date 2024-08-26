package com.spring3.Solution.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.spring3.Solution.Service.LoginService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JWTUtil jWTUtil;

	@Autowired
	private LoginService loginService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		/*
		 * Filter the incoming request, and verify the request meets the security
		 * criteria
		 */

		String auth = request.getHeader("Authorization");
		String email = null;
		String jwt = null;

		if (auth != null && auth.startsWith("Bearer ")) {
			jwt = auth.substring(7);
			email = this.jWTUtil.getUsernameFromToken(jwt);

		}

		if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails u = this.loginService.loadUserByUsername(email);
			if (this.jWTUtil.validateToken(jwt, u)) {
				UsernamePasswordAuthenticationToken t = new UsernamePasswordAuthenticationToken(u, null,
						u.getAuthorities());
				t.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(t);
			} else {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
			}
		}
		filterChain.doFilter(request, response);

	}

}
