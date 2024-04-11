package com.als.tog.web.vlogs.controller;

import com.als.tog.config.ResultUtil;
import com.als.tog.web.vlogs.form.QueryVlogsForm;
import com.als.tog.web.vlogs.form.VlogForm;
import com.als.tog.web.vlogs.service.XgdsbService;
import com.als.tog.web.vlogs.vo.VlogsVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

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
    public ResultUtil QueryList(@RequestBody QueryVlogsForm form) {
        List<VlogsVo> list = xgdsbService.QueryList(form);
        return ResultUtil.success(list);
    }

    @PostMapping("/addVlogs")
    public ResultUtil addVlogs(@RequestBody VlogForm form) {
        xgdsbService.AddVlog(form);
        return ResultUtil.success();
    }


    @PostMapping("/deleteVlogs")
    public ResultUtil deleteVlogs(@RequestBody VlogForm form) {
        xgdsbService.DeleteVlog(form);
        return ResultUtil.success();
    }

    @PostMapping("/updateVlogs")
    public ResultUtil UpdateVlog(@RequestBody VlogForm form) {
        xgdsbService.UpdateVlog(form);
        return ResultUtil.success();
    }
}
