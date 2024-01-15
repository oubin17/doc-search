package com.odk.template.util.request;


import com.odk.base.vo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * HelloWorldRequest
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2023/11/10
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class HelloWorldRequest extends BaseRequest {

    private String name;

}
