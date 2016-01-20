package ru.project.action;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.project.service.AccountService;
import ru.project.service.AgentService;
import ru.project.model.Agent;
import ru.project.transport.ResultEnum;
import ru.project.utils.XmlUtils;

import javax.servlet.AsyncContext;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class BaseAction implements Runnable {

    private final static Logger logger = LoggerFactory.getLogger(BaseAction.class);

    protected Agent agent;
    protected AgentService agentService;
    protected AccountService accountService;
    protected AsyncContext context;

    protected void sendResult(String res) {
        try {
            PrintWriter out = context.getResponse().getWriter();
            out.write(res);
            context.complete();
        } catch (IOException e) {
            logger.error("Exception", e);
            throw new RuntimeException(e);
        }
    }

    protected String validate(Agent agent) {
        if (isExistAgent(agent))
            return XmlUtils.responseAgentToXml(ResultEnum.DUPLICATE_AGENT);
        else if (!isValidPhone(agent.getPhone()))
            return XmlUtils.responseAgentToXml(ResultEnum.WRONG_LOGIN);
        else if (isBadPassword(agent.getPassword()))
            return XmlUtils.responseAgentToXml(ResultEnum.BAD_PASSWORD);

        return StringUtils.EMPTY;
    }


    private boolean isValidPhone(String number) {
        Pattern pattern = Pattern.compile("^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$");
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }


    private boolean isExistAgent(Agent agent) {
        return agentService.findIdByLogin(agent) != null;
    }

    private boolean isBadPassword(String password) {
        return StringUtils.length(password) < 8;
    }
}
