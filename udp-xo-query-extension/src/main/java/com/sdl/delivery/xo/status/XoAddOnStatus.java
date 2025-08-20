package com.sdl.delivery.xo.status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class XoAddOnStatus {

    private static final Logger LOG = LoggerFactory.getLogger(XoAddOnStatus.class);

    @GetMapping("/xo")
    public String sayHello() {
        return "XO Add-On Successfully Deployed...";
    }
}
