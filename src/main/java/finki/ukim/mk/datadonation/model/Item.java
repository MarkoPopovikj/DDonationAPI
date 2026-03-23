package finki.ukim.mk.datadonation.model;

import finki.ukim.mk.datadonation.model.common.BaseAuditableEntity;
import finki.ukim.mk.datadonation.model.enums.CountryCode;
import finki.ukim.mk.datadonation.model.enums.ItemCategory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "items")
public class Item extends BaseAuditableEntity {

    @Column(name = "original_file_name")
    private String originalFileName;

    @Column(name = "file_url", nullable = false)
    private String fileUrl;

    @Column(name = "file_size_bytes")
    private Long fileSizeBytes;

    @Column(name = "mime_type", length = 100)
    private String mimeType;

    @Enumerated(EnumType.STRING)
    @Column(name = "language")
    private CountryCode language;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", length = 50, nullable = false)
    private ItemCategory category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "donor_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private User donor;
}