/*
 * Copyright 2012 Oracle and/or its affiliates.
 * All rights reserved.  You may not modify, use,
 * reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * http://developers.sun.com/license/berkeley_license.html
 */


package order.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@IdClass(order.entity.LineItemKey.class)
@Entity
@Table(name = "PERSISTENCE_ORDER_LINEITEM")
@NamedQueries({
    @NamedQuery(name = "findAllLineItems",query = "SELECT l "
    + "FROM LineItem l")
    , @NamedQuery(name = "findLineItemsByOrderId", query = "SELECT l FROM LineItem l "
    + "WHERE l.order.orderId = :orderId " + "ORDER BY l.itemId")
    , @NamedQuery(name = "findLineItemById", query = "SELECT DISTINCT l FROM LineItem l "
    + "WHERE l.itemId = :itemId AND l.order.orderId = :orderId")
})
public class LineItem implements java.io.Serializable {
    private Order order;
    private VendorPart vendorPart;
    private int itemId;
    private int quantity;

    public LineItem() {
    }

    public LineItem(
        Order order,
        int quantity,
        VendorPart vendorPart) {
        this.order = order;
        this.itemId = order.getNextId();
        this.quantity = quantity;
        this.vendorPart = vendorPart;
    }

    @Id
    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @JoinColumn(name = "VENDORPARTNUMBER")
    @ManyToOne
    public VendorPart getVendorPart() {
        return vendorPart;
    }

    public void setVendorPart(VendorPart vendorPart) {
        this.vendorPart = vendorPart;
    }

    @Id
    @ManyToOne
    @JoinColumn(name = "ORDERID")
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
