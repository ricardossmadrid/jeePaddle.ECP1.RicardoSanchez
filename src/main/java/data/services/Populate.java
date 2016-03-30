package data.services;

import java.util.GregorianCalendar;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import config.ResourceNames;
import data.daos.AuthorizationDao;
import data.daos.UserDao;
import data.entities.Authorization;
import data.entities.Role;
import data.entities.User;

@Service
@Transactional
@PropertySource(ResourceNames.PROPERTIES)
public class Populate {

    @Autowired
    private Environment environment;

    @Autowired
    private UserDao userDao;

    @Autowired
    private AuthorizationDao authorizationDao;

    @PostConstruct
    public void readAdmin() {
        createDefaultUser("admin", Role.ADMIN);
        createDefaultUser("trainer", Role.TRAINER);
    }

	public void createDefaultUser(String key, Role role) {
        User userSaved = userDao.findByUsernameOrEmail(environment.getProperty(key + ".username"));
        if (userSaved == null) {
            User user = new User(environment.getProperty(key + ".username"), environment.getProperty(key + ".email"), environment.getProperty(key + ".password"), new GregorianCalendar(1979, 07, 22));
            userDao.save(user);
            authorizationDao.save(new Authorization(user, role));
        }
    }

}
