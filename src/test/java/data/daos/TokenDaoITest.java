package data.daos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import config.PersistenceConfig;
import config.TestsPersistenceConfig;
import data.entities.Token;
import data.entities.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class, TestsPersistenceConfig.class})
public class TokenDaoITest {

    @Autowired
    private TokenDao tokenDao;

    @Autowired
    private DaosService daosService;

    @Test
    public void testFindByUser() {
        Token token = (Token) daosService.getMap().get("tu1");
        User user = (User) daosService.getMap().get("u4");
        List<Token> tokens = tokenDao.findByUser(token.getUser());
        boolean found = false;
        int i = 0;
        while (!found && i < tokens.size()) {
        	if (tokens.get(i).getValue().equals(token.getValue())) {
        		found = true;
        	} else {
        		i++;
        	}
        }
        assertEquals(token, tokens.get(i));
        assertEquals(new ArrayList<Token>(), tokenDao.findByUser(user));
    }
    
    @Test
    public void testDeleteExpiredTokens() {
    	User user = (User) daosService.getMap().get("u4");
    	Token token = new Token(user);
    	Calendar time = Calendar.getInstance();
        time.add(Calendar.HOUR_OF_DAY, -1);
        time.add(Calendar.MINUTE, -1);
        token.setExpirationTime(time);
        tokenDao.save(token);
        assertTrue(existToken(token));
        tokenDao.deleteExpiredTokens(Calendar.getInstance());
        assertFalse(existToken(token));
    }
    
    private boolean existToken(Token token) {
    	List<Token> tokens = tokenDao.findByUser(token.getUser());
    	boolean existToken = false;
        if (tokens != null) {
        	int i = 0;
        	while(!existToken && i < tokens.size()) {
        		if (token.equals(tokens.get(i))) {
        			existToken = true;
        		} else {
        			i++;
        		}
        	}
        }
        return existToken;
    }
}
