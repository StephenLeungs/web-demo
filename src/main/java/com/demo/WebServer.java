package com.demo;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class WebServer {
    private final int port;

    public WebServer(int port) {
        this.port = port;
    }

    public void start() throws Exception {
        Server server = new Server(port);
        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/");

        // 注册Servlet
        context.addServlet(new ServletHolder(new LoginServlet()), "/login");
        context.addServlet(new ServletHolder(new CookieCheckServlet()), "/cookie_check");

        // 关键修改：使用类加载器加载静态资源
        context.setResourceBase(getClass().getClassLoader().getResource("webapp").toExternalForm());

        // 添加默认Servlet处理静态文件
        context.addServlet(new ServletHolder("default", new org.eclipse.jetty.servlet.DefaultServlet()), "/");
        context.setWelcomeFiles(new String[]{"login.html"});

        server.setHandler(context);
        server.start();
        server.join();
    }
}