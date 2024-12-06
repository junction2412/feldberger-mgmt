module de.code.junction.feldbergermgmt.repository {

    requires java.logging;
    requires java.naming;
    requires jakarta.cdi;
    requires jakarta.persistence;
    requires jakarta.transaction;
    requires jakarta.xml.bind;
    requires com.fasterxml.classmate;
    requires net.bytebuddy;
    requires org.hibernate.orm.core;
    requires org.hibernate.commons.annotations;
    requires org.jboss.logging;

    exports de.code.junction.feldberger.mgmt.data;
    exports de.code.junction.feldberger.mgmt.data.access;
    exports de.code.junction.feldberger.mgmt.data.access.address;
    exports de.code.junction.feldberger.mgmt.data.access.customer;
    exports de.code.junction.feldberger.mgmt.data.access.document;
    exports de.code.junction.feldberger.mgmt.data.access.transaction;
    exports de.code.junction.feldberger.mgmt.data.access.user;
    exports de.code.junction.feldberger.mgmt.data.converter;
    exports de.code.junction.feldberger.mgmt.data.service;

    opens de.code.junction.feldberger.mgmt.data.access.address to org.hibernate.orm.core;
    opens de.code.junction.feldberger.mgmt.data.access.customer to org.hibernate.orm.core;
    opens de.code.junction.feldberger.mgmt.data.access.document to org.hibernate.orm.core;
    opens de.code.junction.feldberger.mgmt.data.access.transaction to org.hibernate.orm.core;
    opens de.code.junction.feldberger.mgmt.data.access.user to org.hibernate.orm.core;
}