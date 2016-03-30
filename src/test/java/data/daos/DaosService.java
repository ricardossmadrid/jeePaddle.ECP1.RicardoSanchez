package data.daos;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import data.entities.Authorization;
import data.entities.Court;
import data.entities.Reserve;
import data.entities.Role;
import data.entities.Token;
import data.entities.Training;
import data.entities.User;
import data.services.DataService;

@Service
public class DaosService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private TokenDao tokenDao;

    @Autowired
    private AuthorizationDao authorizationDao;

    @Autowired
    private CourtDao courtDao;

    @Autowired
    private ReserveDao reserveDao;
    
    @Autowired
    private TrainingDao trainingDao;

    @Autowired
    private DataService genericService;

    private Map<String, Object> map;

    @PostConstruct
    public void populate() {
    	map = new HashMap<>();
        User[] players = this.createUser(0, 4, Role.PLAYER);
        putOnMapWithToken(players);
        for (User user : this.createUser(4, 4, Role.PLAYER)) {
            map.put(user.getUsername(), user);
        }
        User[] trainers = this.createUser(8, 1, Role.TRAINER);
        putOnMapWithToken(trainers);
        this.createCourts(1, 5);
        Calendar date = Calendar.getInstance();
        date.add(Calendar.DAY_OF_YEAR, 1);
        date.set(Calendar.HOUR_OF_DAY, 9);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
        for (int i = 0; i < 4; i++) {
            date.add(Calendar.HOUR_OF_DAY, 1);
            reserveDao.save(new Reserve(courtDao.findOne(i+1), players[i], date));
        }
        this.createTraining(trainers[0]);
    }

	public void createTraining(User trainer) {
		Calendar startDate = Calendar.getInstance(),
				endingDate;
		
		startDate.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
		startDate.set(Calendar.HOUR_OF_DAY, 11);
		startDate.set(Calendar.MINUTE, 0);
		startDate.set(Calendar.SECOND, 0);
		
		endingDate = startDate;
		endingDate.set(Calendar.WEEK_OF_YEAR, startDate.getWeekYear() + 4);
		endingDate.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
		endingDate.set(Calendar.HOUR_OF_DAY, 12);
		endingDate.set(Calendar.MINUTE, 0);
		endingDate.set(Calendar.SECOND, 0);
		
		trainingDao.save(new Training(courtDao.findOne(5), trainer, startDate, endingDate));
		
		while (startDate.before(endingDate)) {
			reserveDao.save(new Reserve(courtDao.findOne(5), trainer, startDate));
			startDate.set(Calendar.WEEK_OF_YEAR, startDate.getWeekYear() + 1);
			startDate.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
			startDate.set(Calendar.HOUR_OF_DAY, 11);
			startDate.set(Calendar.MINUTE, 0);
			startDate.set(Calendar.SECOND, 0);
		}
	}

	public void putOnMapWithToken(User[] users) {
    	
        for (User user : users) {
            map.put(user.getUsername(), user);
        }
        for (Token token : this.createTokens(users)) {
            map.put("t" + token.getUser().getUsername(), token);
        }
    }

    public User[] createUser(int initial, int size, Role role) {
        User[] users = new User[size];
        for (int i = 0; i < size; i++) {
            users[i] = new User("u" + (i + initial), "u" + (i + initial) + "@gmail.com", "p", Calendar.getInstance());
            userDao.save(users[i]);
            authorizationDao.save(new Authorization(users[i], role));
        }
        return users;
    }

    public List<Token> createTokens(User[] users) {
        List<Token> tokenList = new ArrayList<>();
        Token token;
        for (User user : users) {
            token = new Token(user);
            tokenDao.save(token);
            tokenList.add(token);
        }
        return tokenList;
    }

    public void createCourts(int initial, int size) {
        for (int id = 0; id < size; id++) {
            courtDao.save(new Court(id + initial));
        }
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void deleteAll() {
        genericService.deleteAllExceptAdmin();
    }
}
