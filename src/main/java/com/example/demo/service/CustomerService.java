package com.example.demo.service;

import com.example.demo.domain.Customer;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repo.CustomerRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    public final CustomerRepo customerRepo;

    public CustomerService(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    public List<Customer> getAll() {
        return customerRepo.findAll();
    }

    public Customer getById(long id) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.MM.yyyy");

        Optional<Customer> optionalCustomer = customerRepo.findById(id);
        Customer customer = optionalCustomer.orElseThrow(NotFoundException::new);

        int customer_age = getAge(LocalDate.parse(customer.getBirthday(), formatter));
        customer.setBirthday(String.valueOf(customer_age));

        return customer;
    }

    private int getAge(LocalDate birthday) {
        return (int) ChronoUnit.YEARS.between(birthday, LocalDate.now());
    }
}
