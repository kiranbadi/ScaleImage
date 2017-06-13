/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.vasanti.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Kiran
 */
public class CreateConnection {

    private static final Logger logger = LogManager.getLogger(CreateConnection.class.getName());
    private final Connection conn = null;
    private final Statement statement = null;
    private final PreparedStatement ps = null;
    private final ResultSet rs = null;

    public static Connection getDSConnection() {
        Connection conn = null;
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/TestDB");
            conn = ds.getConnection();
            logger.info("got the db connection using datasource");
        } catch (NamingException | SQLException ex) {

            logger.error("Logging SQL Exception", ex);
        }
        return conn;
    }

    public void close() {
        try {
            if (rs != null) {
                rs.close();
            }

            if (statement != null) {
                statement.close();
            }
            if (ps != null) {
                ps.close();
            }

            if (conn != null) {
                conn.close();
            }
        } catch (Exception ex) {
            logger.error("Logging Exception ", ex);

        }
    }
}
