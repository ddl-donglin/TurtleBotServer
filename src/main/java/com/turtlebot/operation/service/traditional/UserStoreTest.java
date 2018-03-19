package com.turtlebot.operation.service.traditional;

import com.turtlebot.operation.dataobject.User;
import org.apache.ignite.cache.store.CacheStore;
import org.apache.ignite.lang.IgniteBiInClosure;
import org.apache.ignite.resources.SpringResource;
import org.jetbrains.annotations.Nullable;

import javax.cache.Cache;
import javax.cache.integration.CacheLoaderException;
import javax.cache.integration.CacheWriterException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

/**
 * 描述:
 *
 * @outhor didonglin
 * @create 2018-03-19 15:44
 */
public class UserStoreTest implements CacheStore<Integer, User> {

    @SpringResource(resourceName = "dataSource")

    private DataSource dataSource;
    public UserStoreTest(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // This method is called whenever IgniteCache.loadCache() method is called.
    @Override
    public void loadCache(IgniteBiInClosure<Integer, User> clo, @Nullable Object... objects) throws CacheLoaderException {
        System.out.println(">> Loading cache from store...");
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("select * from user")) {
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        User user = new User(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getString(5));
                        clo.apply(user.getId(),user);
                        /*User user = new User(rs.getInteger(1), rs.getInteger(2), rs.getString(3), rs.getInt(4));
                        clo.apply(user.getId(), user);*/
                    }
                }
            }
        } catch (SQLException e) {
            throw new CacheLoaderException("Failed to load values from cache store.", e);
        }
    }
    @Override
    public void sessionEnd(boolean b) throws CacheWriterException {

    }

    // This method is called whenever IgniteCache.get() method is called.
    @Override
    public User load(Integer key) throws CacheLoaderException {
        System.out.println(">> Loading user from store...");
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("select * from user where id = ?")) {
                st.setString(1, key.toString());
                ResultSet rs = st.executeQuery();
                return rs.next() ? new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5)) : null;
            }
        } catch (SQLException e) {
            throw new CacheLoaderException("Failed to load values from cache store.", e);
        }
    }

    @Override
    public Map<Integer, User> loadAll(Iterable<? extends Integer> iterable) throws CacheLoaderException {
        return null;
    }

    @Override
    public void write(Cache.Entry<? extends Integer, ? extends User> entry) throws CacheWriterException {

    }

    @Override
    public void writeAll(Collection<Cache.Entry<? extends Integer, ? extends User>> collection) throws CacheWriterException {

    }

    @Override
    public void delete(Object o) throws CacheWriterException {

    }

    @Override
    public void deleteAll(Collection<?> collection) throws CacheWriterException {

    }

    // Other CacheStore method implementations.
}