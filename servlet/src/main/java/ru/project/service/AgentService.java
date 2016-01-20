package ru.project.service;

import ru.project.model.Agent;

public interface AgentService {

    int createAgent(Agent agent) ;

    Integer findIdByLogin(Agent agent);

    Integer findIdByLoginAndPassword(Agent agent) ;

}
