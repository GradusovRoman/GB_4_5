import DAO.impl.ProductDAOImpl;
import domain.Product;

import java.util.List;

public class Main {

    public static void main(String[] args) {
//        Flyway flyway = Flyway.configure().dataSource("jdbc:postgresql://localhost:5432/simple-app", "postgres", "postgrespass").load();
//        flyway.migrate();
        ProductDAOImpl productDAOImpl = new ProductDAOImpl();

        saveOrUpdateProduct(productDAOImpl);
//        findById(productDAOImpl);
//        deleteById(productDAOImpl);
        findAll(productDAOImpl);
    }
    public static void saveOrUpdateProduct(ProductDAOImpl productDAOImpl){
        Product product = new Product("product-5",12);
        productDAOImpl.saveOrUpdate(product);

        }
public static void  findById (ProductDAOImpl productDAOImpl) {
        long id= 8;
    Product product = productDAOImpl.findById(id);
    System.out.println(product.toString());
    }

    public static void findAll(ProductDAOImpl productDAOImpl){
        List<Product> products = productDAOImpl.findAll();
        for (Product product : products) {
            System.out.println(product.toString());
        }
    }
public static void deleteById (ProductDAOImpl productDAOImpl) {

    productDAOImpl.deleteById(8L);
}


}
