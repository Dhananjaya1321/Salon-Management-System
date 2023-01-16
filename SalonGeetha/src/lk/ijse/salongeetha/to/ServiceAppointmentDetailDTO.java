package lk.ijse.salongeetha.to;

public class ServiceAppointmentDetailDTO {
    private String aptId;
    private String sevId;
    private double  price;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    private double discount;

    public ServiceAppointmentDetailDTO() {
    }

    public ServiceAppointmentDetailDTO(String aptId, String sevId) {
        this.aptId = aptId;
        this.sevId = sevId;
    }

    public String getAptId() {
        return aptId;
    }

    public void setAptId(String aptId) {
        this.aptId = aptId;
    }

    public String getSevId() {
        return sevId;
    }

    public void setSevId(String sevId) {
        this.sevId = sevId;
    }

    @Override
    public String toString() {
        return "ServiceAppointmentDetailDTO{" +
                "aptId='" + aptId + '\'' +
                ", sevId='" + sevId + '\'' +
                '}';
    }
}
