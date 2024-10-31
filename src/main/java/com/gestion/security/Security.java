package com.gestion.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gestion.entity.Usuario;
import com.gestion.services.UsuarioService;

@Service
public class Security implements UserDetailsService{
	@Autowired
	private UsuarioService servicio;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	    UserDetails obj = null;
	    try {
	        Usuario bean = servicio.loginUsuario(username);
	        
	        // Rol del usuario
	        Set<GrantedAuthority> rol = new HashSet<GrantedAuthority>();
	        // Adicionar dentro del objeto "rol" el rol del usuario actual
	        rol.add(new SimpleGrantedAuthority(bean.getRol().getDescripcion()));
	        // Creamos objeto
	        obj = new User(username, bean.getClave(), rol);
	    } catch (UsernameNotFoundException e) {
	        // Manejamos el error
	        throw new UsernameNotFoundException("Usuario no encontrado: " + e.getMessage(), e);
	    }
	    return obj;
	}

}
