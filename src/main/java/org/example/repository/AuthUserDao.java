package org.example.repository;

import org.example.entity.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Component
public class AuthUserDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AuthUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Integer save(AuthUser authUser) {
        String sql = "INSERT INTO authuser(username, password, role) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, authUser.getUsername());
            ps.setString(2, authUser.getPassword());
            ps.setString(3, authUser.getRole());
            return ps;
        }, keyHolder);
        Number key = keyHolder.getKey();
        return key != null ? key.intValue() : null;
    }

    public Optional<AuthUser> findByUsername(String username) {
        String sql = "SELECT * FROM authuser WHERE username = ?";
        try {
            AuthUser user = jdbcTemplate.queryForObject(sql,
                    BeanPropertyRowMapper.newInstance(AuthUser.class),
                    username);
            return Optional.ofNullable(user);
        } catch (Exception e) {
            return Optional.empty(); // agar topilmasa
        }
    }

    public boolean existsByUsername(String username) {
        String sql = "SELECT COUNT(*) FROM authuser WHERE username = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, username);
        return count != null && count > 0;
    }

    public List<AuthUser> findAll() {
        String sql = "SELECT * FROM authuser";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(AuthUser.class));
    }

    public Optional<AuthUser> findById(Integer id) {
        String sql = "SELECT * FROM authuser WHERE id = ?";
        try {
            AuthUser user = jdbcTemplate.queryForObject(sql,
                    BeanPropertyRowMapper.newInstance(AuthUser.class),
                    id);
            return Optional.ofNullable(user);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public void deleteById(Integer id) {
        String sql = "DELETE FROM authuser WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
