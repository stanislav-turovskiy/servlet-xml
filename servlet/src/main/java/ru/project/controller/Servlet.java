package ru.project.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;
import ru.project.service.BaseService;
import ru.project.service.AccountService;
import ru.project.service.AgentService;
import ru.project.model.Agent;
import ru.project.transport.Request;
import ru.project.utils.XmlUtils;

import javax.inject.Inject;
import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(name = "servlet", urlPatterns = "/servlet", asyncSupported = true)
public class Servlet extends HttpServlet {

    private final static Logger logger = LoggerFactory.getLogger(Servlet.class);

    @Inject
    private AgentService agentService;
    @Inject
    private AccountService accountService;

    //@Inject
    //private BaseService action;

    @Override
    public void init() throws ServletException {
        super.init();
        AutowireCapableBeanFactory ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext()).getAutowireCapableBeanFactory();
        ctx.autowireBean(this);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }


    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Request req = XmlUtils.xmlToRequest(request.getInputStream());
            Agent agent = new Agent(req.getLogin(), req.getPassword());

            BaseService action = new BaseService( agentService, accountService);

            switch (req.getRequestType()) {
                case NEW_AGENT: {
                    response.getWriter().write(action.createAgent(agent));
                    break;
                }
                case AGT_BALANCE: {
                    response.getWriter().write(action.getAgentBalance(agent));
                    break;
                }
            }
        } catch (Exception e) {
            logger.error("Error processRequest ", e);
        }
    }

}