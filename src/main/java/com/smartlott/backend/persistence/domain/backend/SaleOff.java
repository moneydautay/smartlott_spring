package com.smartlott.backend.persistence.domain.backend;

import com.smartlott.backend.persistence.converters.LocalDateTimeAttributeConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Mrs Hoang on 07/02/2017.
 */
@Entity
@Table(name = "sale_off")
public class SaleOff implements Serializable {

    /** The Serial Version UID for Serializable classes */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime from;

    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime to;

    private boolean enabled = true;

    @ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER, targetEntity = InvestmentPackage.class)
    @JoinTable(name = "investment_package_sale_off",
            joinColumns = @JoinColumn(name = "sale_off_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "investment_package_id", referencedColumnName = "id")
    )
    private List<InvestmentPackage> investmentPackages;

    public SaleOff() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public void setFrom(LocalDateTime from) {
        this.from = from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public void setTo(LocalDateTime to) {
        this.to = to;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<InvestmentPackage> getInvestmentPackages() {
        return investmentPackages;
    }

    public void setInvestmentPackages(List<InvestmentPackage> investmentPackages) {
        this.investmentPackages = investmentPackages;
    }

    @Override
    public String toString() {
        return "SaleOff{"
                + "id=" + id
                + ", from=" + from
                + ", to=" + to
                + ", enabled=" + enabled
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SaleOff saleOff = (SaleOff) o;

        return id == saleOff.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
