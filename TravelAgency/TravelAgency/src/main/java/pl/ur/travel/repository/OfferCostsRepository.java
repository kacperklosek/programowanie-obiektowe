package pl.ur.travel.repository;

import pl.ur.travel.model.dao.OfferCost;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class OfferCostsRepository extends AbstractRepository<OfferCost> {

    private static final String TABLE_NAME = "offer_costs";

    public OfferCostsRepository() {
        super();
    }

    @Override
    public void insert(final OfferCost o) {
        String sql = "INSERT INTO offer_costs (id, offer_id, cost_id) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, o.id().toString());
            stmt.setString(2, o.offerId().toString());
            stmt.setString(3, o.costId().toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            // TODO handle it
        }
    }

    @Override
    public void updateById(final UUID id, final OfferCost o) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected OfferCost mapResultSet(final ResultSet rs) throws SQLException {
        return new OfferCost(
                UUID.fromString(rs.getString("id")),
                UUID.fromString(rs.getString("offer_id")),
                UUID.fromString(rs.getString("cost_id"))
        );
    }
}
