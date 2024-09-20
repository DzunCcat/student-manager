package main.java.management.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import main.java.management.model.User;

public class CacheManager {
    private static CacheManager instance;
    private Map<Integer, User> userCache;

    private CacheManager() {
        userCache = new HashMap<>();
    }

    public static CacheManager getInstance() {
        if (instance == null) {
            instance = new CacheManager();
        }
        return instance;
    }

    public void addUserToCache(User user) {
        userCache.put(user.getCode(), user);
    }

    public User getUserFromCache(int userId) {
        return userCache.get(userId);
    }

    public void removeUserFromCache(int userId) {
        userCache.remove(userId);
    }
    
    public Collection<User> getAllUsersFromCache() {
        return userCache.values();
    }
}
