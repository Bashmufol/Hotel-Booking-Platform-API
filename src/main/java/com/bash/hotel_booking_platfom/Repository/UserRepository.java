package com.bash.hotel_booking_platfom.Repository;

import com.bash.hotel_booking_platfom.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
}
