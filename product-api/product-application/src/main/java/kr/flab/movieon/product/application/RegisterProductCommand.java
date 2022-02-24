package kr.flab.movieon.product.application;

import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
    private Integer year;
    private Integer month;
    private Integer days;
    @NotNull(message = "등록할 상품 상영 시간이 없습니다.")
    @Min(value = 15, message = "등록할 상품의 최소 상영 시간은 15분 이상만 가능합니다.")
    private Integer runningTime;
    @NotBlank(message = "등록할 상품의 감독명이 없습니다.")
    private String directors;
    @NotBlank(message = "등록할 상품의 출연자들이 없습니다.")
    private String actors;
    @Size(min = 1, max = 25, message = "등록할 상품의 상세 이미지는 1개 이상 25개 이하만 가능합니다.")
    private List<@NotBlank String> images;
    @NotNull
    private RegisterItemCommand item;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(String thumbnails) {
        this.thumbnails = thumbnails;
    }

    public String getRateDescription() {
        return rateDescription;
    }

    public void setRateDescription(String rateDescription) {
        this.rateDescription = rateDescription;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Integer getRunningTime() {
        return runningTime;
    }

    public void setRunningTime(Integer runningTime) {
        this.runningTime = runningTime;
    }

    public String getDirectors() {
        return directors;
    }

    public void setDirectors(String directors) {
        this.directors = directors;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public RegisterItemCommand getItem() {
        return item;
    }

    public void setItem(RegisterItemCommand item) {
        this.item = item;
    }
}
