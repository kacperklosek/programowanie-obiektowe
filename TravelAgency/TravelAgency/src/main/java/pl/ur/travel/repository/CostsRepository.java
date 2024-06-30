package pl.ur.travel.repository;

import pl.ur.travel.model.dao.Cost;
import pl.ur.travel.model.dao.CostType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class CostsRepository extends AbstractRepository<Cost> {

    private static final String TABLE_NAME = "costs";

    public CostsRepository() {
        super();
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public void insert(final Cost o) {
        String sql = "INSERT INTO costs (id, type, cost, description) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, o.id().toString());
            stmt.setString(2, o.type().toString());
            stmt.setDouble(3, o.cost());
            stmt.setString(4, o.description());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception as needed
        }
    }

    @Override
    public void updateById(final UUID id, final Cost o) {
        String sql = "UPDATE costs SET type=?, cost=?, description=? WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, o.type().toString());
            stmt.setDouble(2, o.cost());
            stmt.setString(3, o.description());
            stmt.setString(4, id.toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception as needed
        }
    }

    protected Cost mapResultSet(final ResultSet resultSet) throws SQLException {
        return new Cost(
                UUID.fromString(resultSet.getString("id")),
                CostType.valueOf(resultSet.getString("type")),
                resultSet.getDouble("cost"),
                resultSet.getString("description")
        );
    }

}
