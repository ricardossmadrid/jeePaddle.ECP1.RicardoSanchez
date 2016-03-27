package data.entities;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Token {
	
	public static final int VALIDITY_TIME = 1;

    @Id
    @GeneratedValue
    private int id;

    @Column(unique = true, nullable = false)
    private String value;
    
    @Column(nullable = false)
    private Calendar expirationTime;

    @ManyToOne
    @JoinColumn
    private User user;

    public Token() {
    }

    public Token(User user) {
        assert user != null;
        this.user = user;
        this.expirationTime = Calendar.getInstance();
        this.expirationTime.add(Calendar.HOUR, VALIDITY_TIME);
        this.value = new Encrypt().encryptInBase64UrlSafe("" + user.getId() + user.getUsername() + Long.toString(new Date().getTime())
                + user.getPassword());
    }

    public int getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public Calendar getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(Calendar expirationTime) {
		this.expirationTime = expirationTime;
	}

	public User getUser() {
        return user;
    }
    
    public boolean isExpired() {
    	return expirationTime.before(Calendar.getInstance());
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        return id == ((Token) obj).id;
    }

    @Override
    public String toString() {
        return "Token [id=" + id + ", value=" + value + ", userId=" + user.getId() + ", expirationTime=" + expirationTime + "]";
    }
}
