package net.pladema.establishment.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "care_line_institution_practice")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CareLineInstitutionPractice {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "care_line_institution_id", nullable = false)
	private Integer careLineInstitutionId;

	@Column(name = "snomed_related_group_id", nullable = false)
	private Integer snomedRelatedGroupId;
}
