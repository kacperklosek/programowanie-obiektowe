package pl.ur.travel.util.sql;

public class SQLs {

//    TODO Add constraints for Foreign Key

    public static final String CREATE_TABLE_OFFER = """
            CREATE TABLE IF NOT EXISTS offer (
            id TEXT PRIMARY KEY,
            name TEXT,
            accepted INTEGER);
            """;

//    accepted means selected TODO refactor

    public static final String CREATE_TABLE_OFFER_COSTS = """
            CREATE TABLE IF NOT EXISTS offer_costs (
            id TEXT PRIMARY KEY,
            offer_id TEXT,
            cost_id TEXT);
            """;

    public static final String CREATE_TABLE_COSTS = """
           CREATE TABLE IF NOT EXISTS costs (
            id TEXT PRIMARY KEY,
            type TEXT,
            cost REAL,
            description TEXT);
            """;
}
