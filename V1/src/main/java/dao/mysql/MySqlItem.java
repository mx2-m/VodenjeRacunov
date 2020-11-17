package dao.mysql;

import dao.ItemDao;
import db.DatabaseUtil;
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

public class MySqlItem implements ItemDao<Item, ResultSet> {

    private static final String SQL_GET_ALL = "SELECT * FROM item";
    private static final String SQL_GET_BY_BARCODE = "SELECT * FROM item WHERE barcode = ?";
    private static final String SQL_GET_BY_NAME = "SELECT * FROM item WHERE name = ?";
    private static final String SQL_GET_BY_ID = "SELECT * FROM item WHERE idItem = ?";
    private static final String SQL_INSERT = "INSERT INTO item (idItems,modified,created,name,barcode,price,vat) VALUES(?,?,?,?,?,?,? )";
    private static final String SQL_DELETE = "DELETE FROM  item  WHERE idItems = ?";
    private static final String SQL_UPDATE = "UPDATE item SET barcode = ?, name = ?, price = ?, vat = ?  WHERE idItems  = ?";

    @Override
    public Item getByName(String name) {
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
    public Item getByBarcode(String barcode) {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement select = conn.prepareStatement(SQL_GET_BY_BARCODE)) {
            select.setString(1, barcode);
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
    public Item getById(UUID id) {
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
    public List<Item> getAll() {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement selected = conn.prepareStatement(SQL_GET_ALL)) {

            List<Item> items = new ArrayList<>();
            ResultSet rs = selected.executeQuery();

            while (rs.next())
                items.add(mapDataToObject(rs));

            return items;

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

        UUID A= getById(object.getId()).getId();

            /*byte[] ID = new byte[16];

            ByteBuffer.wrap(ID).order(ByteOrder.BIG_ENDIAN).putLong(object.getId().getMostSignificantBits()).putLong(object.getId().getLeastSignificantBits());
*/

            select.setBytes(1, ID);

            select.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public Item mapDataToObject(ResultSet data) throws SQLException {
       // data.next();
        //ne znam ovdje najbolje kako da rijesim id
        Item a= new Item(data.getString("name"),data.getString("barcode"),data.getBigDecimal("price"),data.getBigDecimal("vat"),data.getDouble("davek"));
        return a;

    }

}
