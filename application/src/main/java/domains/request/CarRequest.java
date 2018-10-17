package domains.request;

import javax.validation.constraints.NotNull;

public class CarRequest {

    private Integer id;

    @NotNull
    private String name;

    public CarRequest() {
    }

    public CarRequest(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

