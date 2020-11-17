package dao.mysql;

import dao.CompanyDao;
import db.DatabaseUtil;
import model.Company;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MySqlCompany implements CompanyDao<Company, ResultSet> {

    private static final String SQL_GET_ALL = "SELECT * FROM company";
    private static final String SQL_GET_BY_TAXNUMBER = "SELECT * FROM company WHERE tax_number = ?";
    private static final String SQL_GET_BY_REGISTRATION_NUMBER = "SELECT * FROM company WHERE registration_number = ?";
    private static final String SQL_GET_BY_NAME = "SELECT * FROM company WHERE name = ?";
    private static final String SQL_GET_BY_ID = "SELECT * FROM company WHERE id = ?";
    private static final String SQL_INSERT = "INSERT INTO company (idCompany,created,modified,name,address,registration_number,tax_number,taxpayer) VALUES(?,?,?,?,?,?,?,? )";
    private static final String SQL_DELETE = "DELETE FROM  company  WHERE idCompany = ?";
    private static final String SQL_UPDATE = "UPDATE company SET name = ?, address= ?, registration_number = ?, tax_number = ?  WHERE idCompany  = ?";

    @Override
    public Company getByName(String name) {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement select = conn.prepareStatement(SQL_GET_BY_NAME)) {
            select.setString(1, name);
            ResultSet rs = select.executeQuery();
            if (rs.first()) {
                return mapDataToObject(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public Company getByTaxNumber(int taxNumber) {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement select = conn.prepareStatement(SQL_GET_BY_TAXNUMBER)) {
            select.setInt(1, taxNumber);
            ResultSet rs = select.executeQuery();
            if (rs.first()) {
                return mapDataToObject(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public Company getByTaxRegistrationNumber(String registrationNumber) {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement select = conn.prepareStatement(SQL_GET_BY_REGISTRATION_NUMBER)) {
            select.setString(1, registrationNumber);
            ResultSet rs = select.executeQuery();
            if (rs.first()) {
                return mapDataToObject(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }


    @Override
    public Company getById(UUID id) {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement select = conn.prepareStatement(SQL_GET_BY_ID)) {
            byte[] ID = new byte[16];

            ByteBuffer.wrap(ID).order(ByteOrder.BIG_ENDIAN).putLong(id.getMostSignificantBits()).putLong(id.getLeastSignificantBits());

            select.setBytes(1, ID);
            ResultSet rs = select.executeQuery();

            if (rs.first())
                return mapDataToObject(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public List<Company> getAll() {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement selected = conn.prepareStatement(SQL_GET_ALL)) {

            List<Company> companies = new ArrayList<>();
            ResultSet rs = selected.executeQuery();

            while (rs.next())
                companies.add(mapDataToObject(rs));

            return companies;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public boolean insert(Company object) {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement select = conn.prepareStatement(SQL_INSERT)) {

            byte[] ID = new byte[16];

            ByteBuffer.wrap(ID).order(ByteOrder.BIG_ENDIAN).putLong(object.getId().getMostSignificantBits()).putLong(object.getId().getLeastSignificantBits());

            select.setBytes(1, ID);
            select.setTimestamp(2, new java.sql.Timestamp(System.currentTimeMillis()));
            select.setTimestamp(3, new java.sql.Timestamp(System.currentTimeMillis()));
            select.setString(4, object.getName());
            select.setInt(5, object.getTaxNumber());
            select.setString(6, object.getAddress());
            select.setString(7, object.getRegistrationNumber());
            select.setBoolean(8, object.getTaxpayer());

            return select.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public boolean update(Company object) {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement select = conn.prepareStatement(SQL_UPDATE)) {

            select.setString(1, object.getName());
            select.setString(2, object.getRegistrationNumber());
            select.setInt(3, object.getTaxNumber());
            select.setString(4, object.getAddress());


            return select.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public boolean delete(Company object) {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement select = conn.prepareStatement(SQL_DELETE)) {
            byte[] ID = new byte[16];

            ByteBuffer.wrap(ID).order(ByteOrder.BIG_ENDIAN).putLong(object.getId().getMostSignificantBits()).putLong(object.getId().getLeastSignificantBits());
            select.setBytes(1, ID);

            select.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public Company mapDataToObject(ResultSet data) throws SQLException {

        Company a = new Company(data.getString("name"), data.getInt("tax_number"), data.getString("registration_number"), data.getString("address"));
        return a;

    }

}
