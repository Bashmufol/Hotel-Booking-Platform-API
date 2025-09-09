package com.bash.hotel_booking_platfom.Repository;

import com.bash.hotel_booking_platfom.model.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserRepository {
    private final Map<UUID, User> users = new HashMap<>();

    public User save(User user) {
        if(user.getId() == null) {
            user.setId(UUID.randomUUID());
        }
        return users.put(user.getId(), user);
    }

    public Optional<User> findById(UUID id) {
        return Optional.ofNullable(users.get(id));
    }

    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    public void deleteById(UUID id) {
        users.remove(id);
    }
    public void deleteAll(){
        users.clear();
    }

}
