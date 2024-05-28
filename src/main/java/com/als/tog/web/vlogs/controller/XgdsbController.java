package com.als.tog.web.vlogs.controller;

import com.als.tog.config.ResultUtil;
import com.als.tog.web.vlogs.entity.Xgdsb;
import com.als.tog.web.vlogs.form.AddTagForm;
import com.als.tog.web.vlogs.form.QueryVlogsForm;
import com.als.tog.web.vlogs.form.VlogForm;
import com.als.tog.web.vlogs.service.TagsService;
import com.als.tog.web.vlogs.service.XgdsbService;
import com.als.tog.web.vlogs.vo.VlogsVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

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

    @Resource
    private TagsService tagsService;

    @ApiOperation("博客列表查询")
    @PostMapping("/QueryList")
    public ResultUtil QueryList(@RequestBody QueryVlogsForm form) {
        IPage<Xgdsb> pageList = xgdsbService.QueryList(form);
        return ResultUtil.success(pageList);
    }

    @ApiOperation("新增接口")
    @PostMapping("/addVlogs")
    public ResultUtil addVlogs(@RequestBody VlogForm form) {
        xgdsbService.AddVlog(form);
        return ResultUtil.success();
    }

    @ApiOperation("删除接口")
    @GetMapping("/deleteVlogs")
    public ResultUtil deleteVlogs(@RequestParam(name = "id") String id) {
        xgdsbService.DeleteVlog(id);
        return ResultUtil.success();
    }

    @ApiOperation("更新接口")
    @PostMapping("/updateVlogs")
    public ResultUtil UpdateVlog(@RequestBody VlogForm form) {
        xgdsbService.UpdateVlog(form);
        return ResultUtil.success();
    }

    @ApiOperation("查询标签接口")
    @GetMapping("/queryTags")
    public ResultUtil queryTags() {
        List list = tagsService.QueryTags();
        return ResultUtil.success(list);
    }

    @ApiOperation("新增标签接口")
    @PostMapping("/addTags")
    public ResultUtil addTags(@RequestBody AddTagForm addTagForm) {
        tagsService.addTags(addTagForm);
        return ResultUtil.success();
    }
}
