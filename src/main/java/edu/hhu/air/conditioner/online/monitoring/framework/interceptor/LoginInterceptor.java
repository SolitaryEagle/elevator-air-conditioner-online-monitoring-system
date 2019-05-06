package edu.hhu.air.conditioner.online.monitoring.framework.interceptor;

import edu.hhu.air.conditioner.online.monitoring.constant.SessionConsts;
import edu.hhu.air.conditioner.online.monitoring.model.entity.User;

import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author 覃国强
 * @date 2019-03-04
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute(SessionConsts.LOGIN_USER_KEY);
        if (Objects.isNull(loginUser)) {
            request.getRequestDispatcher("/v1/monitoring-system/users/test").forward(request, response);
            return false;
        }
        return true;
    }

}
