package com.example.tp01dev.dao;

import com.example.tp01dev.model.Annonce;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnnonceDAO extends DAO<Annonce> {

    @Override
    public boolean create(Annonce obj) {
        try {
            String sql = "INSERT INTO annonce (title, description, adress, mail) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connect.prepareStatement(sql);
            statement.setString(1, obj.getTitle());
            statement.setString(2, obj.getDescription());
            statement.setString(3, obj.getAdress());
            statement.setString(4, obj.getMail());

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Annonce> findAll() {
        List<Annonce> list = new ArrayList<>();
        try {
            ResultSet result = this.connect.createStatement().executeQuery("SELECT * FROM annonce");
            while (result.next()) {
                Annonce a = new Annonce();
                a.setId(result.getInt("id"));
                a.setTitle(result.getString("title"));
                a.setDescription(result.getString("description"));
                a.setAdress(result.getString("adress"));
                a.setMail(result.getString("mail"));
                a.setDate(result.getTimestamp("date"));
                list.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Annonce find(int id) {
        Annonce a = null;
        try {
            String sql = "SELECT * FROM annonce WHERE id = ?";
            PreparedStatement statement = connect.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                a = new Annonce();
                a.setId(result.getInt("id"));
                a.setTitle(result.getString("title"));
                a.setDescription(result.getString("description"));
                a.setAdress(result.getString("adress"));
                a.setMail(result.getString("mail"));
                a.setDate(result.getTimestamp("date"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return a;
    }

    @Override
    public boolean update(Annonce obj) {
        try {
            String sql = "UPDATE annonce SET title = ?, description = ?, adress = ?, mail = ? WHERE id = ?";
            PreparedStatement statement = connect.prepareStatement(sql);
            statement.setString(1, obj.getTitle());
            statement.setString(2, obj.getDescription());
            statement.setString(3, obj.getAdress());
            statement.setString(4, obj.getMail());
            statement.setInt(5, obj.getId());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Annonce obj) {
        try {
            String sql = "DELETE FROM annonce WHERE id = ?";
            PreparedStatement statement = connect.prepareStatement(sql);
            statement.setInt(1, obj.getId());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}