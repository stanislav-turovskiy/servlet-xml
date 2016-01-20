package ru.project.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import ru.project.action.AgentBalance;
import ru.project.action.BaseAction;
import ru.project.action.NewAgent;
import ru.project.service.AccountService;
import ru.project.service.AgentService;
import ru.project.model.Agent;
import ru.project.transport.Request;
import ru.project.utils.XmlUtils;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ThreadPoolExecutor;


@WebServlet(name = "servlet", urlPatterns = "/servlet", asyncSupported = true)
public class Servlet extends HttpServlet {

    private final static Logger logger = LoggerFactory.getLogger(Servlet.class);

    private ApplicationContext applicationContext;
    private AgentService agentService;
    private AccountService accountService;


    @Override
    public void init() throws ServletException {
        if (applicationContext == null)
            applicationContext = AppContext.getApplicationContext();

        if (applicationContext != null && applicationContext.containsBean("agentService") && applicationContext.containsBean("accountService")) {
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

            AsyncContext asyncContext = request.startAsync();
            ThreadPoolExecutor executor = (ThreadPoolExecutor) request.getServletContext().getAttribute("executor");

            switch (req.getRequestType()) {
                case NEW_AGENT: {
                    BaseAction action = new NewAgent(asyncContext, agent, agentService, accountService);
                    executor.execute(action);
                    break;
                }
                case AGT_BALANCE: {
                    BaseAction action = new AgentBalance(asyncContext, agent, agentService, accountService);
                    executor.execute(action);
                    break;
                }
            }
        } catch (Exception e) {
            logger.error("Error processRequest ", e);
        }
    }

}