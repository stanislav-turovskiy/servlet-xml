package ru.project.action;

import ru.project.service.AccountService;
import ru.project.model.Account;
import ru.project.model.Agent;
import ru.project.service.AgentService;
import ru.project.transport.ResultEnum;
import ru.project.utils.XmlUtils;

import javax.servlet.AsyncContext;

public class AgentBalance extends BaseAction {


    public AgentBalance(AsyncContext context, Agent agent, AgentService agentService, AccountService accountService) {
        this.context = context;
        this.agent = agent;
        this.agentService = agentService;
        this.accountService = accountService;
    }


    @Override
    public void run() {
        Integer id = agentService.findIdByLoginAndPassword(agent);
        if (id == null) {
            sendResult(XmlUtils.responseAccountToXml(ResultEnum.AGENT_NOT_EXITS));
            return;
        }

        Account account = accountService.findByAgentId(id);
        if (account == null) {
            sendResult(XmlUtils.responseAccountToXml(ResultEnum.ACCOUNT_NOT_EXITS));
            return;
        }

        sendResult(XmlUtils.responseAccountToXml(ResultEnum.OK, account.getBalance()));
    }
}
