package ru.project.action;

import org.apache.commons.lang3.StringUtils;
import ru.project.model.Account;
import ru.project.service.AccountService;
import ru.project.service.AgentService;
import ru.project.model.Agent;
import ru.project.transport.ResultEnum;
import ru.project.utils.HashGen;
import ru.project.utils.XmlUtils;

import javax.inject.Inject;
import javax.servlet.AsyncContext;
import java.math.BigDecimal;


public class NewAgent extends BaseAction {

    public NewAgent(AsyncContext context, Agent agent, AgentService agentService, AccountService accountService) {
        this.context = context;
        this.agent = agent;
        this.agentService = agentService;
        this.accountService = accountService;
    }

    @Override
    public void run() {
        String validateResult = validate(agent);
        if (StringUtils.isNotEmpty(validateResult)) {
            sendResult(validateResult);
            return;
        }

        agent.setPassword(HashGen.hash(agent.getPassword()));
        if (agentService.createAgent(agent) == 0) {
            sendResult(XmlUtils.responseAgentToXml(ResultEnum.OTHER));
            return;
        }

        Integer agentId = agentService.findIdByLoginAndPassword(agent);
        if (agentId == null) {
            sendResult(XmlUtils.responseAccountToXml(ResultEnum.AGENT_NOT_EXITS));
            return;
        }
        if (accountService.createAccount(new Account(agentId, new BigDecimal(0))) == 0) {
            sendResult(XmlUtils.responseAgentToXml(ResultEnum.OTHER));
            return;
        }

        sendResult(XmlUtils.responseAgentToXml(ResultEnum.OK));
    }

}

