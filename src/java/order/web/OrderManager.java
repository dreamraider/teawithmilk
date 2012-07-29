/*
 * Copyright 2012 Oracle and/or its affiliates.
 * All rights reserved.  You may not modify, use,
 * reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * http://developers.sun.com/license/berkeley_license.html
 */


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package order.web;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIParameter;
import javax.faces.event.ActionEvent;
import order.ejb.RequestBean;
import order.entity.LineItem;
import order.entity.Order;
import order.entity.Part;


/**
 *
 * @author ian
 */
@ManagedBean
@SessionScoped
public class OrderManager {
    private static final Logger logger = Logger.getLogger(
                "order.web.OrderManager");
    private Boolean findVendorTableDisabled = true;
    private Boolean partsTableDisabled = true;
    private Integer currentOrder;
    private Integer newOrderId;
    private List<Part> newOrderParts;
    private List<Part> newOrderSelectedParts;
    private List<Order> orders;
    private List<String> vendorSearchResults;
    private Long selectedVendorPartNumber;
    @EJB
    private RequestBean request;
    private String newOrderShippingInfo;
    private String selectedPartNumber;
    private String vendorName;
    private char newOrderStatus;
    private int newOrderDiscount;
    private int selectedPartRevision;

    /**
     * @return the orders
     */
    public List<Order> getOrders() {
        try {
            this.orders = request.getOrders();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return orders;
    }

    public List<LineItem> getLineItems() {
        try {
            return request.getLineItems(this.currentOrder);
        } catch (Exception e) {
            return null;
        }
    }

    public void removeOrder(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent()
                                                   .findComponent(
                        "deleteOrderId");
            Integer id = Integer.parseInt(param.getValue().toString());
            request.removeOrder(id);
            logger.info("Removed order " + id + ".");
        } catch (Exception e) {
        }
    }

    public void findVendor() {
        try {
            this.findVendorTableDisabled = false;
            this.vendorSearchResults = (List<String>) request
                .locateVendorsByPartialName(vendorName);
            logger.info(
                    "Found " + vendorSearchResults.size()
                    + " vendor(s) using the search string " + vendorName + ".");
        } catch (Exception e) {
            logger.warning(
                    "Problem calling RequestBean.locateVendorsByPartialName from findVendor");
        }
    }

    public void submitOrder() {
        try {
            request.createOrder(
                    newOrderId,
                    newOrderStatus,
                    newOrderDiscount,
                    newOrderShippingInfo);

            logger.info(
                    "Created new order with order ID " + newOrderId
                    + ", status " + newOrderStatus + ", discount "
                    + newOrderDiscount + ", and shipping info "
                    + newOrderShippingInfo + ".");
            this.newOrderId = null;
            this.newOrderDiscount = 0;
            this.newOrderParts = null;
            this.newOrderShippingInfo = null;
        } catch (Exception e) {
            logger.warning("Problem creating order in submitOrder.");
        }
    }

    public void addLineItem() {
        try {
            List<LineItem> lineItems = request.getLineItems(currentOrder);
            logger.info(
                    "There are " + lineItems.size() + " line items in "
                    + currentOrder + ".");
            request.addLineItem(
                    this.currentOrder,
                    this.selectedPartNumber,
                    this.selectedPartRevision,
                    1);
            logger.info("Adding line item to order # " + this.currentOrder);

            //this.clearSelected();
        } catch (Exception e) {
            logger.warning(
                    "Problem adding line items to order ID " + newOrderId);
        }
    }

    /**
     * @param orders the orders to set
     */
    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    /**
     * @return the currentOrder
     */
    public int getCurrentOrder() {
        return currentOrder;
    }

    /**
     * @param currentOrder the currentOrder to set
     */
    public void setCurrentOrder(int currentOrder) {
        this.currentOrder = currentOrder;
    }

    /**
     * @return the newOrderId
     */
    public Integer getNewOrderId() {
        return newOrderId;
    }

    /**
     * @param newOrderId the newOrderId to set
     */
    public void setNewOrderId(Integer newOrderId) {
        this.newOrderId = newOrderId;
    }

    /**
     * @return the newOrderShippingInfo
     */
    public String getNewOrderShippingInfo() {
        return newOrderShippingInfo;
    }

    /**
     * @param newOrderShippingInfo the newOrderShippingInfo to set
     */
    public void setNewOrderShippingInfo(String newOrderShippingInfo) {
        this.newOrderShippingInfo = newOrderShippingInfo;
    }

    /**
     * @return the newOrderStatus
     */
    public char getNewOrderStatus() {
        return newOrderStatus;
    }

    /**
     * @param newOrderStatus the newOrderStatus to set
     */
    public void setNewOrderStatus(char newOrderStatus) {
        this.newOrderStatus = newOrderStatus;
    }

    /**
     * @return the newOrderDiscount
     */
    public int getNewOrderDiscount() {
        return newOrderDiscount;
    }

    /**
     * @param newOrderDiscount the newOrderDiscount to set
     */
    public void setNewOrderDiscount(int newOrderDiscount) {
        this.newOrderDiscount = newOrderDiscount;
    }

    /**
     * @return the newOrderParts
     */
    public List<Part> getNewOrderParts() {
        return request.getAllParts();
    }

    /**
     * @param newOrderParts the newOrderParts to set
     */
    public void setNewOrderParts(List<Part> newOrderParts) {
        this.newOrderParts = newOrderParts;
    }

    /**
     * @return the vendorName
     */
    public String getVendorName() {
        return vendorName;
    }

    /**
     * @param vendorName the vendorName to set
     */
    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    /**
     * @return the vendorSearchResults
     */
    public List<String> getVendorSearchResults() {
        return vendorSearchResults;
    }

    /**
     * @param vendorSearchResults the vendorSearchResults to set
     */
    public void setVendorSearchResults(List<String> vendorSearchResults) {
        this.vendorSearchResults = vendorSearchResults;
    }

    /**
     * @return the newOrderSelectedParts
     */
    public List<Part> getNewOrderSelectedParts() {
        return newOrderSelectedParts;
    }

    /**
     * @param newOrderSelectedParts the newOrderSelectedParts to set
     */
    public void setNewOrderSelectedParts(List<Part> newOrderSelectedParts) {
        Iterator<Part> i = newOrderSelectedParts.iterator();

        while (i.hasNext()) {
            Part part = i.next();
            logger.info("Selected part " + part.getPartNumber() + ".");
        }

        this.newOrderSelectedParts = newOrderSelectedParts;
    }

    /**
     * @return the selectedPartNumber
     */
    public String getSelectedPartNumber() {
        return selectedPartNumber;
    }

    /**
     * @param selectedPartNumber the selectedPartNumber to set
     */
    public void setSelectedPartNumber(String selectedPartNumber) {
        this.selectedPartNumber = selectedPartNumber;
    }

    /**
     * @return the selectedPartRevision
     */
    public int getSelectedPartRevision() {
        return selectedPartRevision;
    }

    /**
     * @param selectedPartRevision the selectedPartRevision to set
     */
    public void setSelectedPartRevision(int selectedPartRevision) {
        this.selectedPartRevision = selectedPartRevision;
    }

    /**
     * @return the selectedVendorPartNumber
     */
    public Long getSelectedVendorPartNumber() {
        return selectedVendorPartNumber;
    }

    /**
     * @param selectedVendorPartNumber the selectedVendorPartNumber to set
     */
    public void setSelectedVendorPartNumber(Long selectedVendorPartNumber) {
        this.selectedVendorPartNumber = selectedVendorPartNumber;
    }

    private void clearSelected() {
        this.setSelectedPartNumber(null);
        this.setSelectedPartRevision(0);
        this.setSelectedVendorPartNumber(new Long(0));
    }

    /**
     * @return the findVendorTableDisabled
     */
    public Boolean getFindVendorTableDisabled() {
        return findVendorTableDisabled;
    }

    /**
     * @param findVendorTableDisabled the findVendorTableDisabled to set
     */
    public void setFindVendorTableDisabled(Boolean findVendorTableDisabled) {
        this.findVendorTableDisabled = findVendorTableDisabled;
    }

    /**
     * @return the partsTableDisabled
     */
    public Boolean getPartsTableDisabled() {
        return partsTableDisabled;
    }

    /**
     * @param partsTableDisabled the partsTableDisabled to set
     */
    public void setPartsTableDisabled(Boolean partsTableDisabled) {
        this.partsTableDisabled = partsTableDisabled;
    }
}
