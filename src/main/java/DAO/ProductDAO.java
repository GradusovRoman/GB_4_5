package DAO;

import domain.Product;

import java.util.List;


public interface ProductDAO {
    Product findById(long id) throws FindInDatabaseException;
    List<Product> findAll();
    void deleteById(long id);
    Product saveOrUpdate(Product product);
}
