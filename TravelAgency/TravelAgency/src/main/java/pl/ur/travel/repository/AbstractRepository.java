package pl.ur.travel.repository;

import pl.ur.travel.util.sql.ConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public abstract class AbstractRepository<T> implements Repository<T> {

    protected final Connection connection;

    protected AbstractRepository()  {
        try {
            this.connection = ConnectionProvider.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<T> selectAll() {
        try (Statement statement = this.connection.createStatement()) {
            String sql = "SELECT * FROM " + getTableName() + ";";
            ResultSet resultSet = statement.executeQuery(sql);
            List<T> out = new ArrayList<>();

            while (resultSet.next()) {
                out.add(mapResultSet(resultSet));
            }

            return out;

        } catch (SQLException e) {
            throw new RuntimeException(e); // TODO handle it
        }
    }

    @Override
    public Optional<T> selectById(final UUID id) {
        String sql = "SELECT * FROM " + this.getTableName() + " WHERE id=?";
        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setString(1, id.toString());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(mapResultSet(resultSet));
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException(e); // TODO handle it
        }
    }

    @Override
    public void deleteById(final UUID id) {
        String sql = "DELETE FROM " + getTableName() + " WHERE id=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, id.toString());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e); // TODO handle it
        }
    }

    protected void executeStatement(String statement) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(statement)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e); // TODO handle it
        }
    }

    protected abstract String getTableName();

    protected abstract T mapResultSet(ResultSet rs) throws SQLException;
}
