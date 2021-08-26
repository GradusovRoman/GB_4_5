package DAO.impl;

import DAO.FindInDatabaseException;
import DAO.ProductDAO;
import domain.Product;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {

    private final EntityManager entityManager;

    public ProductDAOImpl() {
        EntityManagerFactory factory = new Configuration()
                .configure("/config/hibernate.cfg.xml")
                .buildSessionFactory();
        entityManager = factory.createEntityManager();
    }

    @Override
    public Product findById(long id) {
        Query query = entityManager.createQuery("select p from Product p where p.id = :id");
        query.setParameter("id", id);
        Product product;
        try {
            Object obj = query.getSingleResult();
            product = (Product) obj;
            return product;
        } catch (NoResultException exception) {
            throw new FindInDatabaseException("No data for product with id: " + id, exception);
        }
    }

    @Override
    public List<Product> findAll() {
        Query query = entityManager.createQuery("select p from Product p", Product.class);
        List<Product> products;
        try {
            products = (List<Product>) query.getResultList();
            return products;
        } catch (NoResultException exception) {
            throw new FindInDatabaseException("No data for products in the database", exception);
        }
    }

    @Override
    public void deleteById(long id) {
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("delete from Product p where p.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
        entityManager.getTransaction().commit();
    }

    @Override
    public Product saveOrUpdate(Product product) {
        entityManager.getTransaction().begin();
        long id = -1;
        try {
            if (product.getId() != null)
                findById(product.getId());
            Product merge = entityManager.merge(product);
            id = merge.getId();
        } catch (FindInDatabaseException exception) {
            entityManager.persist(product);
            entityManager.flush();
        } finally {
            entityManager.getTransaction().commit();
        }
        return findById(id);
    }

}
