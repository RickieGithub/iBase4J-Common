package top.ibase4j.core.interceptor;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.i18n.LocaleContextHolder;

import cz.mallat.uasparser.OnlineUpdater;
import cz.mallat.uasparser.UASparser;

/**
 * 国际化信息设置(基于SESSION)
 * 
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:16:45
 */
public class LocaleInterceptor extends BaseInterceptor {
    static UASparser uasParser;

    static {
        try {
            uasParser = new UASparser(OnlineUpdater.getVendoredInputStream());
        } catch (IOException e) {
            logger.error("", e);
        }
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {
        HttpSession session = request.getSession();
        // 设置客户端语言
        Locale locale = (Locale)session.getAttribute("LOCALE");
        if (locale == null) {
            String language = request.getParameter("locale");
            if (StringUtils.isNotBlank(language)) {
                locale = new Locale(language);
                session.setAttribute("LOCALE", locale);
            } else {
                locale = request.getLocale();
            }
        }
        LocaleContextHolder.setLocale(locale);
        return super.preHandle(request, response, handler);
    }
}