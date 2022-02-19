package kr.flab.movieon.product.application;

import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class RegisterProductCommand {

    @NotNull(message = "카테고리 ID가 없습니다.")
    private Long categoryId;
    @NotBlank(message = "등록할 상품명이 없습니다.")
    private String name;
    @NotBlank(message = "등록할 상품 설명이 없습니다.")
    private String description;
    @NotBlank(message = "등록할 상품 섬네일 이미지가 없습니다.")
    private String thumbnails;
    @NotBlank(message = "등록할 상품 연령 정보가 없습니다.")
    private String rateDescription;
    @NotNull(message = "등록할 상품 릴리즈 년도가 없습니다.")
    @Min(value = 1970, message = "등록할 상품 릴리즈 년도는 1970년 이후만 가능합니다.")
    private Integer release;
    @NotNull(message = "등록할 상품 상영 시간이 없습니다.")
    @Min(value = 15, message = "등록할 상품의 최소 상영 시간은 15분 이상만 가능합니다.")
    private Integer runningTime;
    @NotBlank(message = "등록할 상품의 감독명이 없습니다.")
    private String directors;
    @NotBlank(message = "등록할 상품의 출연자들이 없습니다.")
    private String actors;
    @Size(min = 1, max = 25, message = "등록할 상품의 상세 이미지는 1개 이상 25개 이하만 가능합니다.")
    private List<@NotBlank String> images;
}
