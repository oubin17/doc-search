package com.odk.template.domain.impl;

import com.odk.template.domain.domain.User;
import com.odk.template.domain.interfaces.IUser;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * UserRepository
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2023/11/13
 */
@Repository
public class UserRepository implements IUser {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public User findOne(Integer id) {
        return jdbcTemplate.queryForObject("select * from mysql.t_user where id = ?",new BeanPropertyRowMapper<>(User.class), id);
    }
}
