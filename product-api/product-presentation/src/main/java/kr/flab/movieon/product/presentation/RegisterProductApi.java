package kr.flab.movieon.product.presentation;

import kr.flab.movieon.product.application.ProductManager;
import kr.flab.movieon.product.application.RegisterProductCommand;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterProductApi implements ProductSpecification {

    private final ProductManager productManager;

    public RegisterProductApi(ProductManager productManager) {
        this.productManager = productManager;
    }

    @Override
    public void register(RegisterProductCommand command) {
        productManager.register(command);
    }
}
