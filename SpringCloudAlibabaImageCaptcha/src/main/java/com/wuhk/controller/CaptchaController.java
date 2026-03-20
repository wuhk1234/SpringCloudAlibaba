package com.wuhk.controller;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import com.wuhk.entity.ImageVO;
import com.wuhk.util.VerifyImageUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;


/**
 * @className: CaptchaController
 * @description: TODO
 * @author: wuhk
 * @date: 2023/4/3 21:04
 * @version: 1.0
 * @company 航天信息
 **/
@Controller
@RequestMapping("/test")
public class CaptchaController{

    public static Cache < String, Integer > cacheg = CacheBuilder.newBuilder().expireAfterWrite(70, TimeUnit.SECONDS)
            .maximumSize(10000).build();

    @RequestMapping("/index")
    public String test() {
        return "index";
    }

    @RequestMapping("/getPic")
    @ResponseBody
    public Map<String, Object> getPic() throws IOException {
        // 读取图库目录
        File imgCatalog = new File(ResourceUtils.getURL("classpath:").getPath() + "sliderimage\\targets\\");
        File[] files = imgCatalog.listFiles();
        // 随机选择需要切的图
        int randNum = new Random().nextInt(files.length);
        File targetFile = files[randNum];
        // 随机选择剪切模版
        Random r = new Random();
        int num = r.nextInt(6) + 1;
        File tempImgFile = new File(ResourceUtils.getURL("classpath:").getPath() + "sliderimage\\templates\\" + num
                + "-w.png");
        // 根据模板裁剪图片
        try {
            Map <String, Object> resultMap = VerifyImageUtil.pictureTemplatesCut(tempImgFile, targetFile);
            // 生成流水号，这里就使用时间戳代替
            String lno = Calendar.getInstance().getTimeInMillis() + "";
            cacheg.put(lno, Integer.valueOf(resultMap.get("xWidth") + ""));
            resultMap.put("capcode", lno);
            // 移除横坐标送前端
            resultMap.remove("xWidth");
            return resultMap;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping("/checkcapcode")
    @ResponseBody
    public Map<String, Object> checkcapcode(ImageVO imageVO) {
        Map<String, Object> result = new HashMap<>();
        Integer x = cacheg.getIfPresent(imageVO.getCapcode());
        if (x == null) {
            // 超期
            result.put("code", 3);
        }
        else if (imageVO.getXpos() - x > 5 || imageVO.getXpos() - x < -5) {
            // 验证失败
            result.put("code", 2);
        }
        else {
            // 验证成功
            result.put("code", 1);
            // .....做自己的操作，发送验证码
        }

        return result;
    }
}
