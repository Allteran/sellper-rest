package com.allteran.sellper.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
public class RepairOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String deviceName;

    @ManyToOne
    @JoinColumn(name = "device_type_id")
    private RepairDeviceType deviceType;

    private String serialNumber;
    private String defect;
    private String equipSet;
    private String appearance;
    private String warranty;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private RepairStatus status;
    private String performedActions;

    private LocalDateTime creationDate;
    private LocalDateTime issueDate;

    private int preliminaryPrice;
    private int servicePrice;
    private int componentPrice;
    private int marginPrice;

    private String customerName;
    private String customerPhone;

    @ManyToOne
    @JoinColumn(name = "pos_id")
    private PointOfSales pos;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    public PointOfSales getPos() {
        return pos;
    }

    public RepairStatus getStatus() {
        return status;
    }

    public RepairOrder() {
    }

    public User getAuthor() {
        return author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public RepairDeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(RepairDeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getDefect() {
        return defect;
    }

    public void setDefect(String defect) {
        this.defect = defect;
    }

    public String getEquipSet() {
        return equipSet;
    }

    public void setEquipSet(String equipSet) {
        this.equipSet = equipSet;
    }

    public String getAppearance() {
        return appearance;
    }

    public void setAppearance(String appearance) {
        this.appearance = appearance;
    }

    public String getWarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }

    public void setStatus(RepairStatus status) {
        this.status = status;
    }

    public String getPerformedActions() {
        return performedActions;
    }

    public void setPerformedActions(String performedActions) {
        this.performedActions = performedActions;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDateTime issueDate) {
        this.issueDate = issueDate;
    }

    public int getPreliminaryPrice() {
        return preliminaryPrice;
    }

    public void setPreliminaryPrice(int preliminaryPrice) {
        this.preliminaryPrice = preliminaryPrice;
    }

    public int getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(int servicePrice) {
        this.servicePrice = servicePrice;
    }

    public int getComponentPrice() {
        return componentPrice;
    }

    public void setComponentPrice(int componentPrice) {
        this.componentPrice = componentPrice;
    }

    public int getMarginPrice() {
        return marginPrice;
    }

    public void setMarginPrice(int marginPrice) {
        this.marginPrice = marginPrice;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public void setPos(PointOfSales pos) {
        this.pos = pos;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
