package com.demo;

import com.google.gson.Gson;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class LoginServlet extends HttpServlet {
    // 线程安全的用户存储
    private static final Map<String, String> users = new ConcurrentHashMap<>();
    private static final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // 重定向到登录页面
        resp.sendRedirect("/login.html");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        Map<String, Object> responseData = new HashMap<>();

        if ("register".equals(action)) {
            String confirmPassword = req.getParameter("confirmPassword");

            if (password == null || confirmPassword == null || !password.equals(confirmPassword)) {
                responseData.put("success", false);
                responseData.put("message", "两次密码输入不一致");
            } else if (users.containsKey(username)) {
                responseData.put("success", false);
                responseData.put("message", "用户名已存在");
            } else {
                users.put(username, password);
                responseData.put("success", true);
                responseData.put("message", "注册成功");
            }
        } else if ("login".equals(action)) {
            String storedPassword = users.get(username);

            if (storedPassword != null && storedPassword.equals(password)) {
                // 生成随机 token (兼容 JDK 8)
                String authToken = UUID.randomUUID().toString();
                Cookie authCookie = new Cookie("Auth", authToken);
                authCookie.setPath("/");
                authCookie.setMaxAge(24 * 60 * 60); // 24小时
                resp.addCookie(authCookie);

                responseData.put("success", true);
                responseData.put("message", "登录成功");
            } else {
                responseData.put("success", false);
                responseData.put("message", "用户名或密码错误");
            }
        } else {
            responseData.put("success", false);
            responseData.put("message", "无效操作");
        }

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(gson.toJson(responseData));
    }
}