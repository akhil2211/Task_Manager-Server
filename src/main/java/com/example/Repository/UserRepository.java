package com.example.Repository;

import com.example.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
  @Query(value = "select u.*,r.roles from user as u inner join role as r on r.id = u.role_id where r.id != 1 order by role_id", nativeQuery = true)
  List<Map<String, Object>> findAllUsers();

  Optional<User> findByUsername(String username);

  @Query(value = "select* from user where id=?", nativeQuery = true)
  User getUserById(Integer userId);

  @Query(value = "select* from user where org_id=?", nativeQuery = true)
  List<User> findByOrganization(Integer orgId);

  boolean existsByEmail(String email);

  boolean existsByUsername(String username);

  @Query(value = "select roles from role r inner join user u on r.id=u.role_id where u.id=?", nativeQuery = true)
  String getUserRole(Integer userId);

  @Query(value = "select u.*,r.roles from user as u inner join role as r on u.role_id=r.id where reporting_officer_id=?  order by role_id", nativeQuery = true)
  List<Map<String, Object>> findByReportingOfficer(Integer reportingOfficerId);

  @Query(value = "select u.*,r.roles from user as u inner join role as r on r.id = u.role_id where r.id != 1 and r.id != 2 " +
          "and (u.firstname LIKE CONCAT('%',?1,'%') or u.lastname LIKE CONCAT('%',?1,'%'))", nativeQuery = true)
  List<Map<String, Object>> searchUserByName(String value);

  @Query(value = "SELECT u1.*,concat(u2.firstname,' ',u2.lastname) as Reporting_Officer,r.roles FROM\n" +
          "user u1 LEFT JOIN user u2 ON u1.reporting_officer_id = u2.id  inner join role as r on u1.role_id=r.id where u1.id=?", nativeQuery = true)
  Map<String, Object> findUserProfile(Integer id);

  //@Query(value="select * from user as u1 where case when ?=4 then u1.role_id in (2,3) when ?=3 then u1.role_id in (2) end;",nativeQuery = true)
  //List<User> findReportingOfficerList(Integer roleId);
  @Query(value = "select p.*,concat(u.firstname,(' '),u.lastname) as User from user u " +
          "inner join project_user pu on pu.user_id=u.id inner join project p on " +
          "p.id=pu.project_id  where u.id=? ; ", nativeQuery = true)
  List<Map<String, Object>> findUserProjects(Integer currentUserId);

  @Query(value = "select * from user  where username = ?1 or email = ?2", nativeQuery = true)
  List<User> existByUsernameOrEmail(String username, String email);
  @Query(value = "SELECT * FROM user AS u WHERE (:roleId = 4 AND u.role_id IN (2,3)) OR (:roleId = 3 AND u.role_id = 2)", nativeQuery = true)
  List<User> findReportingOfficerList(@Param("roleId") Integer roleId);

}