package com.example.Repository;

import com.example.Model.Token;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TokenRepo extends CrudRepository<Token,Integer> {

    @Query(value="select t.* from token t inner join user u on t.user_id=u.id where u.id= :userId and(t.expired=false or t.revoked=false)",nativeQuery = true)
    List<Token> findAllValidTokensByUser(Integer userId);
    Optional<Token> findByToken(String token);

}
