package com.bash.hotel_booking_platfom.Repository;

import com.bash.hotel_booking_platfom.model.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
public class UserRepository {
    private final Map<UUID, User> users = new HashMap<>();

}
