package br.edu.uniara.lpi2.rest.controller;

import br.edu.uniara.lpi2.rest.model.Employee;
import br.edu.uniara.lpi2.rest.model.EmployeePaginRepository;
import br.edu.uniara.lpi2.rest.model.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    EmployeeRepository repository;

    @Autowired
    EmployeePaginRepository paginRepository;

    EmployeeController(EmployeeRepository repository) {
        this.repository = repository;
    }


    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/employees")
    List<Employee> all() {
        return repository.findAll();
    }
    // end::get-aggregate-root[]

    @PostMapping("/employees")
    Employee newEmployee(@RequestBody Employee newEmployee) {
        return repository.save(newEmployee);
    }

    // Single item

    @GetMapping("/employees/{id}")
    Employee one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nao encontrado"));
    }

    @GetMapping
    public ResponseEntity<?> all(
            @RequestParam int page,
            @RequestParam int size
    ){
        if(page < 0)
        {
            return ResponseEntity.badRequest().body("page deve ser >= 0");
        }
        if(size < 0 || size > 500)
        {
            return ResponseEntity.badRequest().body("size deve ser entre 0 e 500");
        }
        Pageable pageable = PageRequest.of(page, size);

        final Page<Employee> listEmployee = paginRepository.findAll(pageable);

        return ResponseEntity.ok(listEmployee.stream().toArray());
    }

}
