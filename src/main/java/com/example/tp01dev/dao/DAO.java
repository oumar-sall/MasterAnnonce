package com.example.tp01dev.dao;

import java.sql.Connection;
import java.util.List;

public abstract class DAO<T> {
    protected Connection connect = null;

    public DAO() {
        try {
            this.connect = ConnectionDB.getInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Méthodes CRUD de base
    public abstract boolean create(T obj);
    public abstract T find(int id);
    public abstract List<T> findAll();
    public abstract boolean update(T obj);
    public abstract boolean delete(T obj);
}