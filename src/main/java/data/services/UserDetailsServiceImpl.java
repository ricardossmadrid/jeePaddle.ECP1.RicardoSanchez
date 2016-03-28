package data.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import data.daos.AuthorizationDao;
import data.daos.TokenDao;
import data.daos.UserDao;
import data.entities.Role;
import data.entities.User;
import data.entities.Token;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private AuthorizationDao authorizationDao;

	@Autowired
	private TokenDao tokenDao;
	
	private boolean isTokenValid(User user, String tokenValue) {
		boolean isValid = false;
		List<Token> tokens = tokenDao.findByUser(user);
		if (tokens != null) {
			for (int i = 0; i < tokens.size(); i++) {
				if (tokens.get(i).getValue().equals(tokenValue)) {
					isValid = !tokens.get(i).isExpired();
				}
			}
		}
		return isValid;
	}

	@Override
	public UserDetails loadUserByUsername(final String usernameOrEmailOrTokenValue) throws UsernameNotFoundException {
		User user = userDao.findByTokenValue(usernameOrEmailOrTokenValue);
		if (user != null) {
			if (isTokenValid(user, usernameOrEmailOrTokenValue)) {
				List<Role> roleList = authorizationDao.findRoleByUser(user);
				return this.userBuilder(user.getUsername(), new BCryptPasswordEncoder().encode(""), roleList);
			} else {
				throw new UsernameNotFoundException("Token caducado");
			}
		} else {
			user = userDao.findByUsernameOrEmail(usernameOrEmailOrTokenValue);
			if (user != null) {
				return this.userBuilder(user.getUsername(), user.getPassword(), Arrays.asList(Role.AUTHENTICATED));
			} else {
				throw new UsernameNotFoundException("Usuario no encontrado");
			}
		}
	}

	private org.springframework.security.core.userdetails.User userBuilder(String username, String password,
			List<Role> roles) {
		boolean enabled = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (Role role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.roleName()));
		}
		return new org.springframework.security.core.userdetails.User(username, password, enabled, accountNonExpired,
				credentialsNonExpired, accountNonLocked, authorities);
	}
}
