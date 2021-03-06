package ru.akirakozov.sd.refactoring.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.akirakozov.sd.refactoring.database.ProductsDatabaseManager;
import ru.akirakozov.sd.refactoring.html.ProductsHtmlBuilder;
import ru.akirakozov.sd.refactoring.html.SimpleProductsHtmlBuilder;
import ru.akirakozov.sd.refactoring.products.ProductsManager;
import ru.akirakozov.sd.refactoring.servlet.AddProductServlet;
import ru.akirakozov.sd.refactoring.servlet.GetProductsServlet;
import ru.akirakozov.sd.refactoring.servlet.QueryServlet;

public class ProductsServer {
    private final String databaseConnectionString;
    private final Server server;

    public ProductsServer(int port, String databaseConnectionString) {
        this.server = new Server(port);
        this.databaseConnectionString = databaseConnectionString;
    }

    public void start() throws Exception {
        try (ProductsManager productsManager = new ProductsDatabaseManager(databaseConnectionString)) {
            productsManager.createProducts();

            ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
            context.setContextPath("/");
            server.setHandler(context);

            ProductsHtmlBuilder htmlBuilder = new SimpleProductsHtmlBuilder();

            context.addServlet(new ServletHolder(new AddProductServlet(productsManager, htmlBuilder)), "/add-product");
            context.addServlet(new ServletHolder(new GetProductsServlet(productsManager, htmlBuilder)), "/get-products");
            context.addServlet(new ServletHolder(new QueryServlet(productsManager, htmlBuilder)), "/query");

            server.start();
            server.join();
        }
    }
    
    public void stop() throws Exception {
        server.stop();
    }
}
