package br.edu.uniara.lpi2.rest.model;


import org.springframework.data.repository.PagingAndSortingRepository;

public interface EmployeePaginRepository extends PagingAndSortingRepository<Employee, Long> {
}
