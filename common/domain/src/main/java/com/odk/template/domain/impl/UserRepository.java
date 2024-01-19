package com.odk.template.domain.impl;

import com.odk.base.enums.user.UserStatusEnum;
import com.odk.base.enums.user.UserTypeEnum;
import com.odk.template.domain.domain.UserAccessToken;
import com.odk.template.domain.domain.UserBase;
import com.odk.template.domain.domain.UserIdentification;
import com.odk.template.domain.entity.UserEntity;
import com.odk.template.domain.interfaces.IUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * UserRepository
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2023/11/13
 */
@Repository
public class UserRepository implements IUser {

    private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UserBase findOne(Integer id) {
        String sql = "select * from mysql.t_user where id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(UserBase.class), id);
    }

    @Override
    public boolean checkUserExisted(String tokenType, String tokenValue) {
        String sql = "select count(1) as count from doc_search.t_user_access_token where token_type = ? and token_value = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, tokenType, tokenValue);
        return count > 0;
    }

    @Override
    public UserEntity queryByUserId(String userId) {
        String sql = "select * from doc_search.t_user_base where user_id = ?";
        UserBase userBase = null;
        try {
            userBase = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(UserBase.class), userId);

//             userBase = jdbcTemplate.query(sql, new Object[]{userId}, rs -> {
//                UserBase base = new UserBase();
//                base.setUserId(rs.getString("user_id"));
//                base.setUserName(rs.getString("user_name"));
//                base.setUserType(rs.getString("user_type"));
//                base.setUserStatus(rs.getString("user_status"));
//                base.setCreateTime(rs.getDate("create_date"));
//                base.setUpdateTime(rs.getDate("update_date"));
//                base.setTenantId(rs.getString("tenant_id"));
//                return base;
//            });
        } catch (Exception e) {
            logger.error("找不到用户，用户ID={}", userId);
        }
        if (null == userBase) {
            return null;
        }
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userBase, userEntity);
        userEntity.setUserType(UserTypeEnum.getByCode(userBase.getUserType()));
        userEntity.setUserStatus(UserStatusEnum.getByCode(userBase.getUserStatus()));
        return userEntity;
    }

    @Override
    public UserEntity queryByAccessToken(String tokenType, String tokenValue) {
        String sql = "select * from doc_search.t_user_access_token where token_type = ? and token_value = ?";
        UserAccessToken accessToken = null;
        try {
            accessToken = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(UserAccessToken.class), tokenType, tokenValue);

//             userBase = jdbcTemplate.query(sql, new Object[]{userId}, rs -> {
//                UserBase base = new UserBase();
//                base.setUserId(rs.getString("user_id"));
//                base.setUserName(rs.getString("user_name"));
//                base.setUserType(rs.getString("user_type"));
//                base.setUserStatus(rs.getString("user_status"));
//                base.setCreateTime(rs.getDate("create_date"));
//                base.setUpdateTime(rs.getDate("update_date"));
//                base.setTenantId(rs.getString("tenant_id"));
//                return base;
//            });
        } catch (Exception e) {
            logger.error("找不到用户，tokenType={}, tokenValue={}", tokenType, tokenValue);
        }
        if (null == accessToken) {
            return null;
        }
        return queryByUserId(accessToken.getUserId());
    }

    @Override
    public UserAccessToken queryAccessTokenByTokenValue(String tokenType, String tokenValue) {
        String sql = "select * from doc_search.t_user_access_token where token_type = ? and token_value = ?";
        UserAccessToken accessToken = null;
        try {
            accessToken = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(UserAccessToken.class), tokenType, tokenValue);

        } catch (Exception e) {
            logger.error("找不到token, tokenType={}, tokenValue={}", tokenType, tokenValue);
        }
        return accessToken;
    }

    @Override
    public UserIdentification queryIdentification(String userId, String identifyType) {
        String sql = "select * from doc_search.t_user_identification where user_id = ? and identify_type = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(UserIdentification.class), userId, identifyType);

    }

    @Override
    public void addUserBase(UserBase userBase) {
        String sql = "insert into doc_search.t_user_base(user_id, user_type, user_status, user_name, create_time, update_time, tenant_id) " +
                "values (?, ?, ?, ?, ?, ?, ?)";
        this.jdbcTemplate.update(sql, userBase.getUserId(), userBase.getUserType(), userBase.getUserStatus(), userBase.getUserName(), new Date(), new Date(), "TENANT_DOC_SEARCH");
    }

    @Override
    public void addAccessToken(UserAccessToken accessToken) {
        String sql = "insert into doc_search.t_user_access_token(token_id, user_id, token_type, token_value, create_time, update_time, tenant_id) " +
                "values (?, ?, ?, ?, ?, ?, ?)";
        this.jdbcTemplate.update(sql, accessToken.getTokenId(), accessToken.getUserId(), accessToken.getTokenType(), accessToken.getTokenValue(), new Date(), new Date(), "TENANT_DOC_SEARCH");
    }

    @Override
    public void addIdentification(UserIdentification identification) {
        String sql = "insert into doc_search.t_user_identification(identify_id, user_id, identify_type, identify_value, create_time, update_time, tenant_id) " +
                "values (?, ?, ?, ?, ?, ?, ?)";
        this.jdbcTemplate.update(sql, identification.getIdentifyId(), identification.getUserId(), identification.getIdentifyType(), identification.getIdentifyValue(), new Date(), new Date(), "TENANT_DOC_SEARCH");
    }
}
