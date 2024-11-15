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
    requires static lombok;

    opens de.code.junction.feldberger.mgmt.data.access.user to org.hibernate.orm.core;

    exports de.code.junction.feldberger.mgmt.data.access;
    exports de.code.junction.feldberger.mgmt.data.access.user;
    exports de.code.junction.feldberger.mgmt.data.converter;
}