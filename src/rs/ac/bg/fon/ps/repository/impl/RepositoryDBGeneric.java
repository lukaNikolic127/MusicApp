package rs.ac.bg.fon.ps.repository.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import rs.ac.bg.fon.ps.domain.GenericEntity;
import rs.ac.bg.fon.ps.repository.db.DbConnectionFactory;
import rs.ac.bg.fon.ps.repository.db.DbRepository;

public class RepositoryDBGeneric implements DbRepository<GenericEntity> {

    @Override
    public void add(GenericEntity param) throws Exception {
        Connection connection = DbConnectionFactory.getInstance().getConnection();
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ")
                .append(param.getTableName())
                .append(" (").append(param.getColumnNamesForInsert()).append(")")
                .append(" VALUES (").append(param.getInsertValues())
                .append(")");
        String sql = sb.toString();
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
    }

    @Override
    public void edit(GenericEntity param) throws Exception {
        Connection connection = DbConnectionFactory.getInstance().getConnection();
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ").append(param.getTableName()).append(" SET ");
        sb.append(param.getUpdateValues(param)).append(" WHERE ").append(param.getIdCondition());
        String sql = sb.toString();
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
    }

    @Override
    public void delete(GenericEntity param) throws Exception {
        String sql = "DELETE FROM " + param.getTableName() + " WHERE " + param.getDeleteCondition();
        Connection connection = DbConnectionFactory.getInstance().getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
        statement.close();
    }

    @Override
    public List<GenericEntity> getAll(GenericEntity param, String condition) throws Exception {
        Connection connection = DbConnectionFactory.getInstance().getConnection();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ")
                .append(param.getTableName());
        if (condition != null) {
            sb.append(" WHERE ")
                    .append(condition);
        }

        String sql = sb.toString();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        List<GenericEntity> list = param.createListFromResultSet(rs);
        rs.close();
        statement.close();
        return list;
    }

    @Override
    public GenericEntity get(GenericEntity param, String condition) throws Exception {
        Connection connection = DbConnectionFactory.getInstance().getConnection();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ")
                .append(param.getTableName())
                .append(" WHERE ");
        if (condition == null) {
            sb.append(param.getIdCondition());
        } else {
            sb.append(condition);
        }

        String sql = sb.toString();
        Statement statement = connection.createStatement();

        ResultSet rs = statement.executeQuery(sql);

        GenericEntity result = param.createEntityFromResultSet(rs);
        rs.close();
        statement.close();
        return result;
    }

    @Override
    public long getMaxId(GenericEntity param) throws Exception {
        String sql = "SELECT MAX(id) AS maxID FROM " + param.getTableName();
        long result = -1;
        Connection connection = DbConnectionFactory.getInstance().getConnection();

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        if (rs.next()) {
            result = rs.getLong("maxID");
        }
        rs.close();
        statement.close();
        return result;
    }

}
