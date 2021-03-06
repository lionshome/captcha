package com.anji.captcha.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.anji.captcha.model.common.ResponseModel;
import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaService;
import com.anji.captcha.util.AESUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by raodeming on 2020/4/30.
 */
@RunWith(SpringRunner.class)
@ComponentScan(basePackages = {"com.anji.captcha"})
@SpringBootTest
public class CaptchaServiceImplTest {

    @Autowired
    private CaptchaService captchaService;

    @Test
    public void get() {
        CaptchaVO captchaVO = new CaptchaVO();
        captchaVO.setCaptchaType("blockPuzzle");
        ResponseModel responseModel = captchaService.get(captchaVO);
        Object repData = responseModel.getRepData();
        CaptchaVO captchaVO1 = JSONObject.parseObject(repData.toString(), CaptchaVO.class);
        String token = captchaVO1.getToken();
        System.out.println("token:" + token);
    }

    @Test
    public void check() {
        String token = "";
        String pointJson = "{}";
        pointJson = AESUtil.aesDecrypt(pointJson);
        CaptchaVO captchaVO = new CaptchaVO();
        captchaVO.setCaptchaType("blockPuzzle");
        captchaVO.setPointJson(pointJson);
        captchaVO.setToken(token);
        ResponseModel check = captchaService.check(captchaVO);
        System.out.println(check.toJsonString());
    }

    @Test
    public void verification() {
        CaptchaVO captchaVO = new CaptchaVO();
        String token = "";
        String pointJson = "{}";
        captchaVO.setCaptchaVerification(token.concat("---").concat(pointJson));
        ResponseModel verification = captchaService.verification(captchaVO);
        System.out.println(verification.toJsonString());
    }
}
