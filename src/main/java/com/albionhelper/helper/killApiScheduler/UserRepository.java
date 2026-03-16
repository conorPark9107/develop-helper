package com.albionhelper.helper.killApiScheduler;


import com.albionhelper.helper.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserName(String userName);


}
