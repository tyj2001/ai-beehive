package cn.beehive.web.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.beehive.base.enums.FrontUserRegisterTypeEnum;
import cn.beehive.base.util.SimpleCaptchaUtil;
import cn.beehive.web.domain.bo.JwtUserInfoBO;
import cn.beehive.web.domain.request.RegisterFrontUserForEmailRequest;
import cn.beehive.web.domain.vo.LoginInfoVO;
import cn.beehive.web.domain.vo.RegisterCaptchaVO;
import cn.beehive.web.domain.vo.UserInfoVO;
import cn.beehive.web.service.FrontUserService;
import cn.beehive.web.service.strategy.user.AbstractRegisterTypeStrategy;
import cn.beehive.web.util.FrontUserUtil;
import org.springframework.stereotype.Service;

/**
 * 前端用户服务
 * 用于处理注册/登录/获取用户信息等功能
 *
 * @author CoDeleven
 */
@Service
public class FrontUserServiceImpl implements FrontUserService {

    @Override
    public void register(RegisterFrontUserForEmailRequest request) {
        AbstractRegisterTypeStrategy registerStrategy = AbstractRegisterTypeStrategy.findStrategyByRegisterType(request.getRegisterType());
        registerStrategy.register(request);
    }

    @Override
    public void verifyCode(FrontUserRegisterTypeEnum registerType, String code) {
        AbstractRegisterTypeStrategy registerStrategy = AbstractRegisterTypeStrategy.findStrategyByRegisterType(registerType);
        registerStrategy.checkVerifyCode(null, code);
    }

    @Override
    public LoginInfoVO login(FrontUserRegisterTypeEnum registerType, String username, String password) {
        AbstractRegisterTypeStrategy strategy = AbstractRegisterTypeStrategy.findStrategyByRegisterType(registerType);
        return strategy.login(username, password);
    }

    @Override
    public UserInfoVO getUserInfo(FrontUserRegisterTypeEnum registerType, Integer extraInfoId) {
        AbstractRegisterTypeStrategy strategy = AbstractRegisterTypeStrategy.findStrategyByRegisterType(registerType);
        return strategy.getLoginUserInfo(extraInfoId);
    }

    @Override
    public RegisterCaptchaVO generateCaptcha() {
        // 创建一个 图形验证码会话ID
        String picCodeSessionId = IdUtil.fastUUID();
        // 根据图形验证码会话ID获取一个图形验证码。该方法会建立起 验证码会话ID 和 图形验证码的关系
        String captchaBase64Image = SimpleCaptchaUtil.getBase64Captcha(picCodeSessionId);
        // 将验证码会话ID加入到Cookie中
        return RegisterCaptchaVO.builder()
                .picCodeSessionId(picCodeSessionId)
                .picCodeBase64(captchaBase64Image).build();
    }

    @Override
    public UserInfoVO getLoginUserInfo() {
        JwtUserInfoBO jwtUserInfo = FrontUserUtil.getJwtUserInfo();
        return getUserInfo(jwtUserInfo.getRegisterType(), jwtUserInfo.getExtraUserId());
    }
}