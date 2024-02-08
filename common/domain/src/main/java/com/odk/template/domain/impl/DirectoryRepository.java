package com.odk.template.domain.impl;

import com.odk.base.util.LocalDateTimeUtil;
import com.odk.template.domain.domain.Directory;
import com.odk.template.domain.interfaces.IDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DirectoryRepository
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/24
 */
@Repository
public class DirectoryRepository implements IDirectory {

    private static final Logger logger = LoggerFactory.getLogger(DirectoryRepository.class);

    private final JdbcTemplate jdbcTemplate;

    public DirectoryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public boolean checkExistence(String dicId, String userId) {
        String sql = "select count(1) from doc_search.t_directory where dir_id = ? and user_id = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, dicId, userId);
        return count > 0;
    }

    @Override
    public void createDirectory(Directory directory) {
        String sql = "insert into doc_search.t_directory(dir_id, parent_id, dir_name, user_id, create_time, update_time) values (?, ?, ?, ?, ?, ?)";
        this.jdbcTemplate.update(sql, directory.getDirId(), directory.getParentId(), directory.getDirName(), directory.getUserId(), LocalDateTimeUtil.getCurrentDateTime(), LocalDateTimeUtil.getCurrentDateTime());

    }

    @Override
    public int updateDirectory(String name, String dirId, String userId) {
        String sql = "update doc_search.t_directory set dir_name = ? where dir_id = ? and user_id = ?";
        return this.jdbcTemplate.update(sql, name, dirId, userId);
    }

    @Override
    public boolean deleteDirectory(String dicId, String userId) {
        String sql = "delete from doc_search.t_directory where dir_id = ? and user_id = ?";
        int update = this.jdbcTemplate.update(sql, dicId, userId);
        logger.info("update={}", update);
        return true;
    }

    /**
     *  (rs, rowNum) -> {
     *             Directory directory = new Directory();
     *             directory.setDirId(rs.getString("dir_id"));
     *             directory.setParentId(rs.getString("parent_id"));
     *             directory.setDirName(rs.getString("dir_name"));
     *             directory.setUserId(rs.getString("user_id"));
     *             directory.setCreateTime(LocalDateTimeUtil.convertTimestampToLocalDateTime(rs.getTimestamp("create_time").getTime()));
     *             directory.setUpdateTime(LocalDateTimeUtil.convertTimestampToLocalDateTime(rs.getTimestamp("update_time").getTime()));
     *             return directory;
     *         }
     *
     * @param curDicId
     * @param userId
     * @return
     */
    @Override
    public List<Directory> queryChildDir(String curDicId, String userId) {

        StringBuilder builder = new StringBuilder();
        builder.append("select * from doc_search.t_directory where parent_id");
        if (curDicId == null) {
            builder.append(" is null and user_id = '").append(userId).append("'");
        } else {
            builder.append(" = '").append(curDicId).append("' and user_id = '").append(userId).append("'");
        }

        return jdbcTemplate.query(builder.toString(), (rs, rowNum) -> {
            Directory directory = new Directory();
            directory.setDirId(rs.getString("dir_id"));
            directory.setParentId(rs.getString("parent_id"));
            directory.setDirName(rs.getString("dir_name"));
            directory.setUserId(rs.getString("user_id"));
            directory.setCreateTime(LocalDateTimeUtil.convertTimestampToLocalDateTime(rs.getTimestamp("create_time").getTime()));
            directory.setUpdateTime(LocalDateTimeUtil.convertTimestampToLocalDateTime(rs.getTimestamp("update_time").getTime()));
            return directory;
        });


    }
}

