package com.odk.template.domain.impl;

import com.odk.template.domain.domain.Doc;
import com.odk.template.domain.interfaces.IDoc;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public List<Doc> queryByDocIds(List<String> docIds) {
        String sql = "select * from doc_search.t_doc where doc_id in(:ids)";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("ids", docIds);
        NamedParameterJdbcTemplate paramJdbcTemp = new NamedParameterJdbcTemplate(jdbcTemplate);
        return paramJdbcTemp.query(sql, paramMap, (rs, rowNum) -> {
            Doc doc = new Doc();
            doc.setDocId(rs.getString("doc_id"));
            doc.setDocName(rs.getString("doc_name"));
            doc.setDocPath(rs.getString("doc_path"));
            doc.setCreateTime(rs.getDate("create_time"));
            doc.setUpdateTime(rs.getDate("update_time"));
            return doc;
        });

    }
}
