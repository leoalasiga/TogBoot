package com.als.tog.web.vlogs.controller;

import com.als.tog.web.vlogs.form.TestForm;
import com.als.tog.web.vlogs.form.VlogForm;
import com.als.tog.web.vlogs.service.XgdsbService;
import com.als.tog.web.vlogs.service.impl.XgdsbServiceImpl;
import com.als.tog.web.vlogs.vo.VlogsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dkw
 * @since 2024年04月08日
 */
@RestController
@RequestMapping("/getVlogs")
public class XgdsbController {

    @Resource
    private XgdsbService xgdsbService;

    @PostMapping("/QueryList")
    public List<VlogsVo> QueryList(@RequestBody  VlogForm form) {
        List<VlogsVo> list = xgdsbService.QueryList(form);
        return list;
    }

    @PostMapping("/addVlogs")
    public void addVlogs(@RequestBody VlogForm form) {
        xgdsbService.AddVlog(form);
    }
}
