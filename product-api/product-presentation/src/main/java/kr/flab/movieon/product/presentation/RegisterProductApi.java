package kr.flab.movieon.product.presentation;

import javax.validation.Valid;
import kr.flab.movieon.product.application.ProductManager;
import kr.flab.movieon.product.application.RegisterProductCommand;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
public final class RegisterProductApi {

    private final ProductManager productManager;

    public RegisterProductApi(ProductManager productManager) {
        this.productManager = productManager;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void register(@RequestBody @Valid RegisterProductCommand command) {
        productManager.register(command);
    }
}
