import com.mysql.cj.jdbc.MysqlDataSource;

public class DBService {
    private final MysqlDataSource dataSource;

    public DBService() {
        dataSource = new MysqlDataSource();
        dataSource.setServerName(PropertiesProvider.PROPERTIES.getProperty("host"));
        dataSource.setPortNumber(Integer.parseInt(PropertiesProvider.PROPERTIES.getProperty("port")));
        dataSource.setDatabaseName(PropertiesProvider.PROPERTIES.getProperty("database_name"));
        dataSource.setUser(PropertiesProvider.PROPERTIES.getProperty("uname"));
        dataSource.setPassword(PropertiesProvider.PROPERTIES.getProperty("pwd"));
    }

    public MysqlDataSource getDataSource() {
        return dataSource;
    }
}
