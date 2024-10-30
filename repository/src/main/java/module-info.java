module de.code.junction.feldbergermgmt.repository {
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires static lombok;

    exports de.code.junction.feldberger.mgmt.data;
    exports de.code.junction.feldberger.mgmt.data.access;
    exports de.code.junction.feldberger.mgmt.data.access.user;
}