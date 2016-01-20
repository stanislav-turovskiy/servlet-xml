package ru.project.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.project.DAO.AgentDAO;
import ru.project.model.Agent;

import java.sql.SQLException;

public class AgentServiceImpl implements AgentService {
    private AgentDAO dao;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = SQLException.class)
    public int createAgent(Agent agent) {
        try {
            return dao.createAgent(agent);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Integer findIdByLogin(Agent agent) {
        try {
            return dao.getByLogin(agent);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer findIdByLoginAndPassword(Agent agent) {
        try {
            return dao.getByLoginAndPassword(agent);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setDao(AgentDAO dao) {
        this.dao = dao;
    }
}
