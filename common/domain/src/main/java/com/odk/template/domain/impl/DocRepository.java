package com.odk.template.domain.impl;

import com.odk.template.domain.domain.Doc;
import com.odk.template.domain.interfaces.IDoc;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * DocRepository
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/15
 */
@Repository
public class DocRepository implements IDoc {

    private final JdbcTemplate jdbcTemplate;

    public DocRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveDoc(Doc doc) {
        String sql = "insert into doc_search.t_doc(doc_id, doc_name, doc_path, user_id, create_time, update_time) values (?, ?, ?, ?, ?, ?)";
        this.jdbcTemplate.update(sql, doc.getDocId(), doc.getDocName(), doc.getDocPath(), doc.getUserId(), new Date(), new Date());
    }
}
