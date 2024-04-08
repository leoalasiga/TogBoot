package com.als.tog.web.vlogs.controller;

import com.als.tog.web.vlogs.form.TestForm;
import com.als.tog.web.vlogs.service.impl.XgdsbServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author dkw001
 * @since 2024年04月08日
 */
@RestController
@RequestMapping("/getVlogs")
public class XgdsbController {

    @Autowired
    private XgdsbServiceImpl xgdsbService;

    @PostMapping("/test")
    public Map test(@RequestBody  TestForm form) {
        xgdsbService.sbljj(form);
        Map<String,String> hashMap = new HashMap();
        hashMap.put("111","111");
        return hashMap;
    }
}
