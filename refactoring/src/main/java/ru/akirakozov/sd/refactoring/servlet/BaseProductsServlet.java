package ru.akirakozov.sd.refactoring.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import ru.akirakozov.sd.refactoring.html.ProductsHtmlBuilder;

public abstract class BaseProductsServlet extends HttpServlet {
    protected final ProductsHtmlBuilder htmlBuilder;
    
    protected BaseProductsServlet(ProductsHtmlBuilder htmlBuilder) {
        this.htmlBuilder = htmlBuilder;
    }
    
    protected void setOkHtmlResponse(HttpServletResponse response) {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
