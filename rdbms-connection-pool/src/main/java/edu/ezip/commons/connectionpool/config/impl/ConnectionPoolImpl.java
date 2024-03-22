package edu.ezip.commons.connectionpool.config.impl;

import edu.ezip.commons.connectionpool.config.DatabaseConnectionBasicConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class ConnectionPoolImpl {

    private final static String jdbc = "jdbc";
    private final static String LoggingLabel = "C o n n - p o o l";
    //On créer l'objet qu'on est en train de définir (ConnectionPoolImpl est un attribut (static) de sa propre classe partagé par toutes ses insatnces)
    public static ConnectionPoolImpl inst = null;
    private final Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private static String url;
    private  static String databaseURL;
    // A really appropriate JAVA Object (since JAVA 7) to implement this kind of stuff
    // Remind that BlockingDeque is a synchronized ready object;
    //Collection de connexions
    private static final BlockingDeque<Connection> connections = new LinkedBlockingDeque<Connection>(
                                                                DatabaseConnectionBasicConfiguration.getInstance().
                                                                        getPoolSize());

    //permet de créer une instance de pool de connexion si elle n'existe pas déjà
    public static ConnectionPoolImpl getInstance(final String dbEditor) throws SQLException {
        if(inst == null) {
            inst = new ConnectionPoolImpl(dbEditor);
        }
        return inst;
    }

    //Constructeur privé pour ne pas pouvoir créer plus d'objets que l'instance de getInstance()
    private ConnectionPoolImpl(final String dbEditor) throws SQLException {
        //on recupere un objet contznant les configurations necessaires pour la BD
        final DatabaseConnectionBasicConfiguration config =  DatabaseConnectionBasicConfiguration.getInstance();
        final StringBuffer letsBuildUrl = new StringBuffer();
        letsBuildUrl.append(jdbc).append(":")
                .append(dbEditor).append("://")
                .append(config.getHost()).append("/")
                .append(config.getDatabaseName()).append("?")
                .append("user=").append(config.getUsername()).append("&")
                .append("password=").append(config.getPassword()).append("&")
                .append("ssl=false");
        logger.debug("URL = {}", (this.url = letsBuildUrl.toString()));

        initConnections();
        showConnections();
        // To terminate : terminatePool();
    }

    private void initConnections() throws SQLException {
        int justTobeSureConnection = 0;
        //Tant qu'il reste des connexions disponibles
        while ( 0 < connections.remainingCapacity()) {
            connections.addLast(createConnection());
            justTobeSureConnection++;
        }
        logger.debug("{} created, pool size = {}", justTobeSureConnection, DatabaseConnectionBasicConfiguration.
                getInstance().getPoolSize());
    }

    public void terminatePool() throws SQLException {
        int justTobeSureConnection = 0;
        //tant qu'il reste des connexions
        while ( !connections.isEmpty()) {
            //poolFirst() : Returns the tail of this deque, or null if this deque is empty
            final Connection c = connections.pollFirst();
            //compte le nombre de connexions fermées
            justTobeSureConnection++;
            //si la connexion existe (!= null), on la close.
            if ( null != c) c.close();
        }
        logger.debug("{} released, pool size = {}", justTobeSureConnection, DatabaseConnectionBasicConfiguration.
                getInstance().getPoolSize());
    }

    private final Connection createConnection() throws SQLException {
        //Driver Manager : The basic service for managing a set of JDBC drivers.
        return DriverManager.getConnection(this.url);
    }

    private void showConnections() {
        int howmuch = 0;
        final StringBuffer toShowConnections = new StringBuffer();
        toShowConnections.append("{");
        //Iterator takes the place of Enumeration in the Java Collections Framework.
        final Iterator<Connection> trtr = connections.iterator();
        //tant qu'il y a des connexions dans la collection
        //hasNext() : Returns true if the iteration has more elements.
        //en gros, si faux, on entre pas dans le while
        while (trtr.hasNext()) {
            if (howmuch++ > 0) toShowConnections.append(" ★ ");
            final String toStringConnection = trtr.next().toString();
            toShowConnections.append(toStringConnection.replace("org.postgresql.jdbc.", ""));
        }
        toShowConnections.append("}");
        logger.debug("Connections = {}", toShowConnections.toString());
    }

    public int available() {
        return connections.size();
    }

    public final Connection get() {
        return connections.pollFirst();
    }

    public void release(Connection connection) throws InterruptedException {
        connections.offerLast(connection);
    }

//    private void closeConnection(final Connection c) throws SQLException {
//        c.close();
//    }
}
