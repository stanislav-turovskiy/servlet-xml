package ru.project.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import ru.project.model.Agent;
import ru.project.service.AccountService;
import ru.project.service.AgentService;
import ru.project.service.BaseService;
import ru.project.transport.Request;
import ru.project.utils.XmlUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "servlet", urlPatterns = "/servlet", asyncSupported = true)
public class Servlet extends HttpServlet {

    private final static Logger logger = LoggerFactory.getLogger(Servlet.class);

    private ApplicationContext applicationContext;

    private AgentService agentService;
    private AccountService accountService;

    @Override
    public void init() throws ServletException {
        super.init();
        //AutowireCapableBeanFactory ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext()).getAutowireCapableBeanFactory();
        //ctx.autowireBean(this);
        if (applicationContext == null) {
            applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        }
        if (applicationContext != null &&
                applicationContext.containsBean("agentService") &&
                applicationContext.containsBean("accountService")) {

            agentService = (AgentService) applicationContext.getBean("agentService");
            accountService = (AccountService) applicationContext.getBean("accountService");
        }

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

            BaseService action = new BaseService(agentService, accountService);

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