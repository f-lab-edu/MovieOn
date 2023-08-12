package kr.flab.movieon.product.application;

import java.util.List;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public final class RegisterItemCommand {

    @NotBlank
    private String name;
    @Min(value = 1)
    private Integer availableDays;
    private Integer basePrice;
    @NotBlank
    private String type;
    private List<RegisterItemOptionCommand> options;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAvailableDays() {
        return availableDays;
    }

    public void setAvailableDays(Integer availableDays) {
        this.availableDays = availableDays;
    }

    public Integer getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Integer basePrice) {
        this.basePrice = basePrice;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<RegisterItemOptionCommand> getOptions() {
        return options;
    }

    public void setOptions(
        List<RegisterItemOptionCommand> options) {
        this.options = options;
    }

    public static final class RegisterItemOptionCommand {
        @NotBlank
        private String optionName;
        @Min(value = 1)
        private Integer salesPrice;

        public String getOptionName() {
            return optionName;
        }

        public void setOptionName(String optionName) {
            this.optionName = optionName;
        }

        public Integer getSalesPrice() {
            return salesPrice;
        }

        public void setSalesPrice(Integer salesPrice) {
            this.salesPrice = salesPrice;
        }
    }
}
