package pl.ur.travel.repository;

import pl.ur.travel.model.dao.Offer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class OfferRepository extends AbstractRepository<Offer> {

    private static final String TABLE_NAME = "offer";

    public OfferRepository() {
        super();
    }

    @Override
    public void insert(final Offer o) {
        String sql = "INSERT INTO " + TABLE_NAME + " (id, name, accepted) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, o.id().toString());
            stmt.setString(2, o.name());
            stmt.setDouble(3, o.accepted() ? 1 : 0);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception as needed
        }
    }

    @Override
    public void updateById(final UUID id, final Offer o) {
        String sql = "UPDATE " + TABLE_NAME + " SET name=?, accepted=? WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, o.name());
            stmt.setDouble(2, o.accepted() ? 1 : 0);
            stmt.setString(3, id.toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception as needed
        }
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected Offer mapResultSet(final ResultSet rs) throws SQLException {
        return new Offer(
                UUID.fromString(rs.getString("id")),
                rs.getString("name"),
                rs.getInt("accepted") == 0 // hack
        );
    }
}
