package edu.hhu.air.conditioner.online.monitoring.service.impl;

import edu.hhu.air.conditioner.online.monitoring.constant.MailConsts;
import edu.hhu.air.conditioner.online.monitoring.constant.SystemConsts;
import edu.hhu.air.conditioner.online.monitoring.constant.enums.ErrorCodeEnum;
import edu.hhu.air.conditioner.online.monitoring.exception.BusinessException;
import edu.hhu.air.conditioner.online.monitoring.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

/**
 * @author 覃国强
 * @date 2019/5/9 21:45
 */
@Slf4j
@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private MailSender mailSender;

    @Override
    public void sendActivationCode(String email, String activationCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(MailConsts.SENDER_EMAIL);
        message.setTo(email);
        message.setText(MailConsts.EMAIL_TEXT_TEMPLATE + activationCode);
        try {
            this.mailSender.send(message);
        } catch (Exception e) {
            log.error("邮件发送出错！email: {}, message: {}", email, message, e);
            throw new BusinessException(ErrorCodeEnum.INTERNAL_SERVER_ERROR, "email", SystemConsts.SYSTEM_ERROR_PROMPT);
        }
    }

}
