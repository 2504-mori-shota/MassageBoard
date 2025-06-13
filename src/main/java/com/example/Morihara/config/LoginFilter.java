package com.example.Morihara.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.FilterConfig;

public class LoginFilter implements Filter {
    @Autowired
    HttpSession httpSession;
    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        //型変換
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        HttpServletResponse httpResponse = (HttpServletResponse)response;

        httpSession = httpRequest.getSession(false);

        if (httpSession != null && httpSession.getAttribute("user") != null){
            chain.doFilter(httpRequest,httpResponse);
        } else {
            httpSession = httpRequest.getSession(true);
            //ここのセッションに詰めた、エラー文をLoginControllerのGetMappingの部分に渡してる。
            httpSession.setAttribute("errorMessageForm", "ログインしてください");
            //ログインページにリダイレクト
            httpResponse.sendRedirect("/");
        }

    }

    @Override
    public void init(FilterConfig config) {
    }

    @Override
    public void destroy() {
    }
}
