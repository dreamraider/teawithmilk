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
package addressbook.ejb;

import addressbook.entity.Contact;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


/**
 *
 * @author ian
 */
@Stateless
public class ContactFacade extends AbstractFacade<Contact> {
    @PersistenceContext(unitName = "address-bookPU")
    private EntityManager em;

    public ContactFacade() {
        super(Contact.class);
    }

    protected EntityManager getEntityManager() {
        return em;
    }
}
