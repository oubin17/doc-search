package com.odk.template.domain.impl;

import com.odk.base.util.LocalDateTimeUtil;
import com.odk.template.domain.domain.Directory;
import com.odk.template.domain.interfaces.IDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
    public boolean checkExistence(String dicId) {
        String sql = "select count(1) from doc_search.t_directory where dir_id = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, dicId);
        return count > 0;
    }

    @Override
    public void createDirectory(Directory directory) {
        String sql = "insert into doc_search.t_directory(dir_id, parent_id, dir_name, user_id, create_time, update_time) values (?, ?, ?, ?, ?, ?)";
        this.jdbcTemplate.update(sql, directory.getDirId(), directory.getParentId(), directory.getDirName(), directory.getUserId(), LocalDateTimeUtil.getCurrentDateTime(), LocalDateTimeUtil.getCurrentDateTime());

    }
}
