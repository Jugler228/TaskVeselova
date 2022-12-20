package com.example.demo.service;

import com.example.demo.domain.Customer;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repo.CustomerRepo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerServiceTest {

    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private CustomerService customerService;

    @AfterEach
    void tearDown() {
        customerRepo.deleteAll();
    }

    @Test
    public void getCustomerWhichExists() {
        Customer customer = new Customer(1L, "Tom", "Tom", "21.12.2003");
        customerRepo.save(customer);

        Customer returnedCustomer = customerService.getById(1);

        Assert.assertEquals(customer.getName(), returnedCustomer.getName());
        Assert.assertEquals(customer.getSurname(), returnedCustomer.getSurname());
        Assert.assertEquals("18", returnedCustomer.getBirthday());
    }

    @Test
    public void getCustomerWhichDoesNotExists() {
        Exception exception = Assert.assertThrows(NotFoundException.class, () -> {
            customerService.getById(1);
        });

        String expectedMessage = "There isn`t such user";
        String actualMessage = exception.getMessage();

        Assert.assertTrue(actualMessage.contains(expectedMessage));
    }
}