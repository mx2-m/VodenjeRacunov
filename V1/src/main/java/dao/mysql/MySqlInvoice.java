package dao.mysql;

import dao.InvoiceDao;
import db.DatabaseUtil;
import model.Invoice;
import model.Item;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MySqlInvoice implements InvoiceDao<Invoice, ResultSet> {

    private static final String SQL_GET_ALL = "SELECT * FROM invoice";
    private static final String SQL_GET_BY_INVOICE_NUMBER = "SELECT * FROM invoice WHERE invoice_number = ?";
    private static final String SQL_GET_BY_ID = "SELECT * FROM invoice WHERE idInvoice = ?";
    private static final String SQL_INSERT = "INSERT INTO invoice ( ) VALUES(?,?,?,?,?,?,? )";
    private static final String SQL_DELETE = "DELETE FROM  invoice  WHERE idInvoice = ?";
    private static final String SQL_UPDATE = "UPDATE invoice SET barcode = ?, name = ?, price = ?, vat = ?  WHERE idItems  = ?";

    @Override
    public Invoice getByInvoiceNumber(UUID invoiceNumber) {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement select = conn.prepareStatement(SQL_GET_BY_INVOICE_NUMBER )) {
            byte[] ID = new byte[16];

            ByteBuffer.wrap(ID).order(ByteOrder.BIG_ENDIAN).putLong(invoiceNumber.getMostSignificantBits()).putLong(invoiceNumber.getLeastSignificantBits());

            select.setBytes(1, ID);
            ResultSet rs = select.executeQuery();

            if(rs.first())
                return mapDataToObject(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Invoice getById(UUID id) {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement select = conn.prepareStatement(SQL_GET_BY_ID)) {
            byte[] ID = new byte[16];

            ByteBuffer.wrap(ID).order(ByteOrder.BIG_ENDIAN).putLong(id.getMostSignificantBits()).putLong(id.getLeastSignificantBits());

            select.setBytes(1, ID);
            ResultSet rs = select.executeQuery();

            if(rs.first())
                return mapDataToObject(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }
    @Override
    public List<Invoice> getAll() {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement selected = conn.prepareStatement(SQL_GET_ALL)) {

            List<Invoice> invoice = new ArrayList<>();
            ResultSet rs = selected.executeQuery();

            while (rs.next())
                invoice.add(mapDataToObject(rs));

            return invoice;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }
    @Override
    public boolean insert(Item object) {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement select = conn.prepareStatement(SQL_INSERT)) {

            byte[] ID = new byte[16];

            ByteBuffer.wrap(ID).order(ByteOrder.BIG_ENDIAN).putLong(object.getId().getMostSignificantBits()).putLong(object.getId().getLeastSignificantBits());

            //provjeriti sta sa onim atributima ki jih imamo v bazi a v javi ne!!!

            select.setBytes(1, ID);
            select.setTimestamp(2, new java.sql.Timestamp(System.currentTimeMillis()));
            select.setTimestamp(3, new java.sql.Timestamp(System.currentTimeMillis()));
            select.setString(4, object.getName());
            select.setString(5, object.getEan());
            select.setBigDecimal(6, object.getPrice());
            select.setBigDecimal(7, object.getQuantity());

            return select.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public boolean update(Item object) {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement select = conn.prepareStatement(SQL_UPDATE)) {

            select.setString(1, object.getEan());
            select.setString(2, object.getName());
            select.setBigDecimal(3, object.getPrice());
            select.setBigDecimal(4, object.getQuantity());
            select.setTimestamp(5, new java.sql.Timestamp(System.currentTimeMillis()));//nema bas smisla ampka ne dela
            // ce nimas vse kaj je not null

            return select.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }



    @Override
    public boolean delete(Item object) {
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
    public Invoice mapDataToObject(ResultSet data) throws SQLException {

        Invoice a= new Invoice(data.getBigDecimal("total_discount"),data.getBigDecimal("total"),data.getIssuer("issuer_id"),data.getBytes("customer_id"),data.getString("cashier"),data.getBytes("item_id"));
        return a;

    }


}
