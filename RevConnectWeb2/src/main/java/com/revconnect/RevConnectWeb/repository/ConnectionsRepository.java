package com.revconnect.RevConnectWeb.repository;

import com.revconnect.RevConnectWeb.entity.Connections;
import com.revconnect.RevConnectWeb.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConnectionsRepository extends JpaRepository<Connections,Long> {

    List<Connections> findByUser1OrUser2(User user1, User user2);

    boolean existsByUser1AndUser2(User user1, User user2);
}
