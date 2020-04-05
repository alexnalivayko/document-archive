package io.github.alexnalivayko.archive.document.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

@MappedSuperclass
@EqualsAndHashCode
public class AbstractEntity {

	@Id
	@Getter
	@Setter
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "general_seq")
	@SequenceGenerator(name = "general_seq", sequenceName = "generalSequenceGenerator")
	private Long id;
}