package data.entities;

import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.Test;

import data.entities.Token;
import data.entities.User;

public class TokenTest {

    @Test
    public void testTokenUser() {
        User user = new User("u", "u@gmail.com", "p", Calendar.getInstance());
        Token token = new Token(user);
        assertTrue(token.getValue().length() > 20);
    }
    
    @Test
    public void testCreationExpirationTime() {
    	User user = new User("u", "u@gmail.com", "p", Calendar.getInstance());
        Token token = new Token(user);
        Calendar auxiliarTime = Calendar.getInstance();
        auxiliarTime.add(Calendar.HOUR, 1);
        auxiliarTime.add(Calendar.SECOND, 1);
        assertTrue(token.getExpirationTime().before(auxiliarTime));
        auxiliarTime.add(Calendar.SECOND, -2);
        assertTrue(token.getExpirationTime().after(auxiliarTime));
    }

}
