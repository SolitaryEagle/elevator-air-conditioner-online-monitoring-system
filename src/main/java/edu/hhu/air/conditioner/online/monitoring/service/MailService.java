package edu.hhu.air.conditioner.online.monitoring.service;

/**
 * @author 覃国强
 * @date 2019/5/9 21:43
 */
public interface MailService {

    void sendActivationCode(String email, String activationCode);

}
