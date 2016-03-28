package data.daos;

import java.util.Calendar;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import data.entities.Token;
import data.entities.User;

public interface TokenDao extends JpaRepository<Token, Integer> {

    List<Token> findByUser(User user);
    
    @Transactional
    @Modifying
    @Query(value = "delete from Token token where token.expirationTime < ?1")
    void deleteExpiredTokens(Calendar time);
}
