package io.github.alexnalivayko.archive.document.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@MappedSuperclass
public class AbstractEntity {

	@Id
	@Getter
	@Setter
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "general_seq")
	@SequenceGenerator(name = "general_seq", sequenceName = "generalSequenceGenerator")
	private Long id;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		AbstractEntity that = (AbstractEntity) o;

		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}
}