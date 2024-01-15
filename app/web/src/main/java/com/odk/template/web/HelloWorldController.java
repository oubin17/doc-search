package com.odk.template.web;

import com.odk.base.vo.response.ServiceResponse;
import com.odk.template.api.HelloWorldApi;
import com.odk.template.util.request.HelloWorldRequest;
import com.odk.template.util.response.HelloWorldResponse;
import org.springframework.web.bind.annotation.*;

/**
 * HelloWorldController
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2023/11/10
 */
@RestController
@RequestMapping("/hello")
public class HelloWorldController {


    private final HelloWorldApi helloWorldApi;

    public HelloWorldController(HelloWorldApi helloWorldApi) {
        this.helloWorldApi = helloWorldApi;
    }

    @GetMapping
    public ServiceResponse<HelloWorldResponse> helloWorld() {
        HelloWorldResponse response = new HelloWorldResponse();
        response.setResult("调用到服务端啦");

        return ServiceResponse.valueOfSuccess(response);
    }

    @PostMapping
    public ServiceResponse<HelloWorldResponse> helloWorldPost(@RequestBody HelloWorldRequest helloWorldRequest) {
        return helloWorldApi.helloWorld(helloWorldRequest);
    }
}
