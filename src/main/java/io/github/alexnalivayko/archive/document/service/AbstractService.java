package io.github.alexnalivayko.archive.document.service;

import java.util.Collection;

public interface AbstractService<E> {

	E create();

	void deleteById(Long id);

	E getById(Long id);

	E getByName(String name);

	Collection<E> getAll();
}