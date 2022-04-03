package kr.flab.movieon.product.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import kr.flab.movieon.product.application.RegisterProductCommand;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Product", description = "상품 API")
public interface ProductSpecification {

    @Operation(summary = "상품 추가", description = "관리자가 상품 추가를 위한 EndPoint를 제공합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "상품 추가 성공"),
        @ApiResponse(responseCode = "400", description = "상품 추가 에러", content = @Content)
    })
    @PostMapping("/api/v1/products")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    void register(@RequestBody @Valid RegisterProductCommand command);
}
