package ru.project.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.project.model.Account;
import ru.project.model.Agent;
import ru.project.transport.ResultEnum;
import ru.project.utils.HashGen;
import ru.project.utils.XmlUtils;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Named
@Singleton
public class BaseService {

    private final static Logger logger = LoggerFactory.getLogger(BaseService.class);

    protected AgentService agentService;
    protected AccountService accountService;

    @Inject
    public BaseService(AgentService agentService, AccountService accountService) {
        this.agentService = agentService;
        this.accountService = accountService;
    }

    public String createAgent(Agent agent) {
        String validateResult = validate(agent);
        if (StringUtils.isNotEmpty(validateResult))
            return validateResult;

        agent.setPassword(HashGen.hash(agent.getPassword()));
        if (agentService.createAgent(agent) == 0)
            return XmlUtils.responseAgentToXml(ResultEnum.OTHER);

        Integer agentId = agentService.findIdByLoginAndPassword(agent);
        if (agentId == null)
            return XmlUtils.responseAccountToXml(ResultEnum.AGENT_NOT_EXITS);

        if (accountService.createAccount(new Account(agentId, new BigDecimal(0))) == 0)
            return XmlUtils.responseAgentToXml(ResultEnum.OTHER);

        return XmlUtils.responseAgentToXml(ResultEnum.OK);
    }

    public String getAgentBalance(Agent agent) {
        Integer id = agentService.findIdByLoginAndPassword(agent);
        if (id == null)
            return XmlUtils.responseAccountToXml(ResultEnum.AGENT_NOT_EXITS);

        Account account = accountService.findByAgentId(id);
        if (account == null)
            return XmlUtils.responseAccountToXml(ResultEnum.ACCOUNT_NOT_EXITS);


        return XmlUtils.responseAccountToXml(ResultEnum.OK, account.getBalance());
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
