package com.event.qr.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.event.qr.dao.UserDAO;
import com.event.qr.model.User;



/**
 * Created by stephan on 20.03.16.
 */
@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserDAO userDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		/*As we have not stored the admin credential in DB ,
		 *  we are providing the hard code admin credential for authentication only if the user name matches*/
		if(username.equals("admin")){
			User user = new User();
			user.setMobileNo("admin");
			user.setPassword("$2a$10$3qRAzLFmtjURMlWiJccRQuswOtBy5zzSGrvQ8Uzo5VJq05qXZ26qq");//admin123
			return user;
		}

		User user = userDao.getUserByMobileNo(username);

		if (user == null) {
			throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
		} else {
			//return JwtUserFactory.create(user);
			return user;
		}

	}







}
