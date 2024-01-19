package com.odk.template.util.vo;

import lombok.Data;

import java.util.Date;

/**
 * DocDto
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/17
 */
@Data
public class DocVO {

    private String docId;

    private String docName;

    private String docPath;

    private Date createTime;

    private Date updateTime;
}
