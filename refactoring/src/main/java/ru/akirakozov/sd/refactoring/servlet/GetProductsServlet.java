package ru.akirakozov.sd.refactoring.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ru.akirakozov.sd.refactoring.html.ProductsHtmlBuilder;
import ru.akirakozov.sd.refactoring.products.ProductsManager;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends BaseProductsServlet {

    public GetProductsServlet(ProductsManager productsManager, ProductsHtmlBuilder htmlBuilder) {
        super(productsManager, htmlBuilder);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        htmlBuilder.buildProductsListHtml(productsManager.getProducts(), response.getWriter());
            
        setOkHtmlResponse(response);
    }
}
