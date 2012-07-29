/*
 * Copyright 2012 Oracle and/or its affiliates.
 * All rights reserved.  You may not modify, use,
 * reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * http://developers.sun.com/license/berkeley_license.html
 */


package order.entity;

import java.io.Serializable;


public class LineItemKey implements Serializable {
    private Integer order;
    private int itemId;

    public LineItemKey() {
    }

    public LineItemKey(
        Integer order,
        int itemId) {
        this.setOrder(order);
        this.setItemId(itemId);
    }

    @Override
    public int hashCode() {
        return (((this.getOrder() == null) ? 0 : this.getOrder()
                                                     .hashCode())
        ^ ((int) this.getItemId()));
    }

    @Override
    public boolean equals(Object otherOb) {
        if (this == otherOb) {
            return true;
        }

        if (!(otherOb instanceof LineItemKey)) {
            return false;
        }

        LineItemKey other = (LineItemKey) otherOb;

        return (((this.getOrder() == null) ? (other.getOrder() == null)
                                           : this.getOrder()
                                                 .equals(other.getOrder()))
        && (this.getItemId() == other.getItemId()));
    }

    @Override
    public String toString() {
        return "" + getOrder() + "-" + getItemId();
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
}
