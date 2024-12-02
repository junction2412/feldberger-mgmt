package de.code.junction.feldberger.mgmt.data.access.document;

import de.code.junction.feldberger.mgmt.data.access.DataTransferObject;
import de.code.junction.feldberger.mgmt.data.access.transaction.Transaction;
import de.code.junction.feldberger.mgmt.data.converter.ByteArrayColumnEncryptor;
import de.code.junction.feldberger.mgmt.data.converter.DocumentTypeAttributeConverter;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name = "documents")
@Table(uniqueConstraints = {@UniqueConstraint(
        name = "U_TRANSACTION_DOCUMENT",
        columnNames = {"file_name", "transaction_id"}
)})
public class Document implements DataTransferObject<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "file_name", nullable = false, length = 256)
    private String fileName;

    @Convert(converter = DocumentTypeAttributeConverter.class)
    @Column(nullable = false)
    private DocumentType type;

    @Lob
    @Column(name = "binary_data", columnDefinition = "MEDIUMBLOB", nullable = false)
    @Convert(converter = ByteArrayColumnEncryptor.class)
    private byte[] binaryData;

    @ManyToOne
    @JoinColumn(name = "transaction_id", referencedColumnName = "id", nullable = false)
    private Transaction transaction;

    @Column(name = "upload_date", nullable = false)
    private LocalDateTime uploadDate;

    public Document(int id,
                    String fileName,
                    DocumentType type,
                    byte[] binaryData,
                    Transaction transaction,
                    LocalDateTime uploadDate) {

        this.id = id;
        this.fileName = fileName;
        this.type = type;
        this.binaryData = binaryData;
        this.transaction = transaction;
        this.uploadDate = uploadDate;
    }

    @Override
    public Integer getId() {

        return id;
    }

    @Override
    public void setId(Integer id) {

        this.id = id;
    }

    public String getFileName() {

        return fileName;
    }

    public void setFileName(String fileName) {

        this.fileName = fileName;
    }

    public DocumentType getType() {

        return type;
    }

    public void setType(DocumentType type) {

        this.type = type;
    }

    public byte[] getBinaryData() {

        return binaryData;
    }

    public void setBinaryData(byte[] binaryData) {

        this.binaryData = binaryData;
    }

    public Transaction getTransaction() {

        return transaction;
    }

    public void setTransaction(Transaction transaction) {

        this.transaction = transaction;
    }

    public LocalDateTime getUploadDate() {

        return uploadDate;
    }

    public void setUploadDate(LocalDateTime uploadDate) {

        this.uploadDate = uploadDate;
    }
}
