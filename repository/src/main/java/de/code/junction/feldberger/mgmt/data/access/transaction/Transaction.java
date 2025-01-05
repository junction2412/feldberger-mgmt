package de.code.junction.feldberger.mgmt.data.access.transaction;

import de.code.junction.feldberger.mgmt.data.access.DataTransferObject;
import de.code.junction.feldberger.mgmt.data.access.customer.Customer;
import de.code.junction.feldberger.mgmt.data.access.document.Document;
import de.code.junction.feldberger.mgmt.data.access.user.User;
import de.code.junction.feldberger.mgmt.data.converter.StringColumnEncryptor;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "transactions")
public class Transaction implements DataTransferObject<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "clerk_user_id", referencedColumnName = "id", nullable = false)
    private User clerk;

    @Column(name = "idno", unique = true, nullable = false)
    private String idNo;

    @Column(length = 512)
    @Convert(converter = StringColumnEncryptor.class)
    private String description;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private Customer customer;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "modification_date")
    private LocalDateTime modificationDate;

    private boolean completed;

    @Column(name = "completion_date")
    private LocalDateTime completionDate;

    @OneToMany(mappedBy = "transaction")
    private List<Document> documents;

    public Transaction(int id,
                       User clerk,
                       String idNo,
                       String description,
                       Customer customer,
                       LocalDateTime creationDate,
                       LocalDateTime modificationDate,
                       boolean completed,
                       LocalDateTime completionDate) {

        this.id = id;
        this.clerk = clerk;
        this.idNo = idNo;
        this.description = description;
        this.customer = customer;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
        this.completed = completed;
        this.completionDate = completionDate;
    }

    public Transaction(User clerk,
                       String idNo,
                       String description,
                       Customer customer) {

        this(
                0,
                clerk,
                idNo,
                description,
                customer,
                null,
                null,
                false,
                null
        );
    }

    public Transaction() {

        this(
                0,
                null,
                "",
                "",
                null,
                null,
                null,
                false,
                null
        );
    }

    @Override
    public Integer getId() {

        return id;
    }

    @Override
    public void setId(Integer id) {

        this.id = id;
    }

    public User getClerk() {

        return clerk;
    }

    public void setClerk(User clerk) {

        this.clerk = clerk;
    }

    public String getIdNo() {

        return idNo;
    }

    public void setIdNo(String idNo) {

        this.idNo = idNo;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public Customer getCustomer() {

        return customer;
    }

    public void setCustomer(Customer customer) {

        this.customer = customer;
    }

    public LocalDateTime getCreationDate() {

        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {

        this.creationDate = creationDate;
    }

    public LocalDateTime getModificationDate() {

        return modificationDate;
    }

    public void setModificationDate(LocalDateTime modificationDate) {

        this.modificationDate = modificationDate;
    }

    public boolean isCompleted() {

        return completed;
    }

    public void setCompleted(boolean completed) {

        this.completed = completed;
    }

    public LocalDateTime getCompletionDate() {

        return completionDate;
    }

    public void setCompletionDate(LocalDateTime completionDate) {

        this.completionDate = completionDate;
    }

    public List<Document> getDocuments() {

        return documents;
    }

    public void setDocuments(List<Document> documents) {

        this.documents = documents;
    }

    @Override
    public String toString() {

        return "Transaction{" +
                "id=" + getId() +
                ", clerk=" + getClerk() +
                ", idNo='" + getIdNo() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", customer=" + getCustomer().getId() +
                ", creationDate=" + getCreationDate() +
                ", modificationDate=" + getModificationDate() +
                ", completed=" + isCompleted() +
                ", completionDate=" + getCompletionDate() +
                '}';
    }
}
