package com.wuhk.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageInfo;
import com.wuhk.entity.ComType;
import com.wuhk.entity.DevType;
import com.wuhk.entity.SchoolStudent;
import com.wuhk.service.SchoolStudentService;
import com.wuhk.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @className: SchoolStudentController
 * @description: TODO
 * @author: wuhk
 * @date: 2023/7/21 10:15
 * @version: 1.0
 * @company 航天信息
 **/
@RestController
@RequestMapping("/page")
public class SchoolStudentController {

    @Autowired
    SchoolStudentService schoolStudentService;
    /**
     * <p>分页方式一</p>
     * @method SchoolStudentController.gateSchoolStudentPageOne()
     * @param page：pageSize
     * @return Page<SchoolStudent>
     * @author wuhk
     * @date 2023/7/21 11:27
     **/
    @RequestMapping("/gateSchoolStudentPageOne")
    public R<Page<SchoolStudent>> gateSchoolStudentPageOne(int page, int pageSize){
        Page<SchoolStudent> schoolStudentPage = schoolStudentService.gateSchoolStudentPageOne(page, pageSize);
        return R.data(schoolStudentPage);
    }
    /**
     * <p>分页方式二</p>
     * @method SchoolStudentController.gateSchoolStudentPageTwo()
     * @param page：pageSize
     * @return Page<SchoolStudent>
     * @author wuhk
     * @date 2023/7/21 11:27
     **/
    @RequestMapping("/gateSchoolStudentPageTwo")
    public R<IPage<SchoolStudent>> gateSchoolStudentPageTwo(int page, int pageSize){
        IPage<SchoolStudent> schoolStudentPage = schoolStudentService.getPageStudentTwo(page, pageSize);
        return R.data(schoolStudentPage);
    }

    /**
     * <p>分页方式三</p>
     * @method SchoolStudentController.gateSchoolStudentPageThree()
     * @param page：pageSize
     * @return Page<SchoolStudent>
     * @author wuhk
     * @date 2023/7/21 11:27
     **/
    @RequestMapping("/gateSchoolStudentPageThree")
    public R<IPage<SchoolStudent>> gateSchoolStudentPageThree(int page, int pageSize){
        IPage<SchoolStudent> schoolStudentPage = schoolStudentService.getPageStudentThree(page, pageSize);
        return R.data(schoolStudentPage);
    }

    /**
     * <p>分页方式四</p>
     * @method SchoolStudentController.gateSchoolStudentPageFour()
     * @param page：pageSize
     * @return Page<SchoolStudent>
     * @author wuhk
     * @date 2023/7/21 11:27
     **/
    @RequestMapping("/gateSchoolStudentPageFour")
    public R<PageInfo<SchoolStudent>> gateSchoolStudentPageFour(int page, int pageSize){
        PageInfo<SchoolStudent> schoolStudentPage = schoolStudentService.getPageStudentFour(page, pageSize);
        return R.data(schoolStudentPage);
    }
    /**
     * <p>树形方式</p>
     * @method SchoolStudentController.gateSchoolStudentPageFour()
     * @param devType：
     * @return R<List<ComType>>
     * @author wuhk
     * @date 2023/7/21 11:27
     **/
    @RequestMapping("/newTree/list")
    //@ApiOperation(value = "树形结构列表", notes = "传入devType")
    public R<List<DevType>> newTreeList(@RequestBody DevType devType) {
        List<DevType> pages = schoolStudentService.newTreeList(devType);
        return R.data(pages);
    }

    /**
     * <p>树形方式一</p>
     * @method SchoolStudentController.gateSchoolStudentPageFour()
     * @param comType：
     * @return R<List<ComType>>
     * @author wuhk
     * @date 2023/7/21 11:27
     **/
    @RequestMapping("/selectComTypeTrees")
    public R<List<ComType>> selectComTypeTrees(@RequestBody ComType comType){
        List<ComType>  comTypeList = schoolStudentService.selectComTypeTrees(comType);
        return R.data(comTypeList);
    }
}
