package ru.akirakozov.sd.refactoring.html;

import java.io.PrintWriter;
import java.util.List;

import ru.akirakozov.sd.refactoring.products.Product;

public interface ProductsHtmlBuilder {
    void buildProductsListHtml(List<Product> products, PrintWriter writer);

    void buildMinPriceHtml(Product product, PrintWriter writer);

    void buildMaxPriceHtml(Product product, PrintWriter writer);

    void buildSumPriceHtml(Long sum, PrintWriter writer);

    void buildCountProductsHtml(Integer count, PrintWriter writer);
    
    void buildAddProductHtml(Product product, PrintWriter writer);
    
    void buildUnknownCommandHtml(String command, PrintWriter writer);
}
