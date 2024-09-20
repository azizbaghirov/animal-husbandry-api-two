package az.eagro.animalhusbandry.model;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "FieldDocument")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FieldDocumentEntity {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FieldDocumentId", nullable = false, updatable = false, unique = true)
    private Integer id;

    @EqualsAndHashCode.Include
    @Column(name = "FieldId")
    private Integer fieldId;

    @Column(name = "FieldName")
    private String fieldName;

    @Column(name = "FieldUUID")
    private String fieldUuid;

    @Column(name = "SpaceHa")
    private BigDecimal spaceHa;

    @Column(name = "RegistryNumber")
    private String registryNumber;

    @Column(name = "RegistrationNumber")
    private String registrationNumber;

    @Column(name = "DocumentClassificationName")
    private String documentClassificationName;

    @Column(name = "DocumentClassificationLabel")
    private String documentClassificationLabel;

    @Column(name = "ReferencedDocumentTypeLabel")
    private String referencedDocumentTypeLabel;

    @Column(name = "ReferencedDocumentTypeName")
    private String referencedDocumentTypeName;

    @Column(name = "DocumentTypeName", nullable = false)
    private String documentTypeName;

    @Column(name = "RegionCode")
    private String regionCode;

    @Column(name = "JournalNumber")
    private String journalNumber;

    @Column(name = "DocumentId")
    private Integer documentId;

    @Column(name = "DocumentTypeLabel", nullable = false)
    private String documentTypeLabel;

    @Column(name = "ContourId")
    private Integer contourId;

    @Column(name = "ContourName")
    private String contourName;

    @Column(name = "ContourSpaceHa")
    private BigDecimal contourSpaceHa;

    @Column(name = "ReferencedDocumentId")
    private Integer referencedDocumentId;

    @Column(name = "ReferencedSpaceHa")
    private BigDecimal referencedSpaceHa;

    @Column(name = "ContractNumber")
    private String contractNumber;

    @Column(name = "ReferencedRegistryNumber")
    private String referencedRegistryNumber;

    @Column(name = "ReferencedRegistrationNumber")
    private String referencedRegistrationNumber;

    @Column(name = "ReferencedJournalNumber")
    private String referencedJournalNumber;

    @Column(name = "ReferencedRegionCode")
    private String referencedRegionCode;

}

