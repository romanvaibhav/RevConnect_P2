package com.revconnect.RevConnectWeb.repository;

import com.revconnect.RevConnectWeb.entity.Following;
import com.revconnect.RevConnectWeb.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowingRepository extends JpaRepository<Following,Long> {

    Optional<Following> findByFollowerAndFollowing(User follower, User following);

    List<Following> findAllByFollower(User follower);
    List<Following> findAllByFollowing(User following);
}
