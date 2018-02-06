package java.com.timbuchalka.top10downloader.models;

import java.util.Date;
import com.timbuchalka.top10downloader.models.ConsultantGroupUser;
import com.timbuchalka.top10downloader.models.CustomerInformation;

public class Conversation implements ModelInterface {
    private Long id;
    private Byte active;
    private ConsultantGroupUser consultantGroupUser;
    private CustomerInformation customerInformation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Byte getActive() {
        return active;
    }

    public void setActive(Byte active) {
        this.active = active;
    }

    public ConsultantGroupUser getConsultantGroupUser() {
        return consultantGroupUser;
    }

    public void setConsultantGroupUser(ConsultantGroupUser consultantGroupUser) {
        this.consultantGroupUser = consultantGroupUser;
    }

    public CustomerInformation getCustomerInformation() {
        return customerInformation;
    }

    public void setCustomerInformation(CustomerInformation customerInformation) {
        this.customerInformation = customerInformation;
    }
}