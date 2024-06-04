package org.example.app.repository.impl;

import org.example.app.domain.customer.Customer;
import org.example.app.domain.customer.CustomerMapper;
import org.example.app.repository.IRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class CustomerRepository implements IRepository<Customer> {

    NamedParameterJdbcTemplate template;

    @Autowired
    public CustomerRepository(DataSource dataSource) {
        template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public boolean create(Customer customer) {
        String sql = "INSERT INTO customers (customer_id,first_name,last_name, email, phone_number) VALUES (:customerId, :firstName, :lastName, :email, :phoneNumber)";
        SqlParameterSource paramSource =
                new MapSqlParameterSource()
                        .addValue("customerId", customer.getCustomerId().toString())
                        .addValue("firstName", customer.getFirstName())
                        .addValue("lastName", customer.getLastName())
                        .addValue("email", customer.getEmail())
                        .addValue("phoneNumber", customer.getPhoneNumber());
        return template.update(sql, paramSource) > 0;
    }

    @Override
    public Optional<List<Customer>> getAll() {
        Optional<List<Customer>> optional;
        String sql = "SELECT id, customer_id, first_name, last_name, email, phone_number FROM  customers";
        try {
            optional = Optional.of(template
                    .query(sql, new CustomerMapper()));
        } catch (Exception ex) {
            optional = Optional.empty();
        }
        return optional;
    }

    @Override
    public Optional<Customer> getById(Long id) {
        String sql = "SELECT id, customer_id, first_name, last_name, email, phone_number FROM customers WHERE id = :id LIMIT 1";
        SqlParameterSource paramSource =
                new MapSqlParameterSource("id", id);
        Optional<Customer> optional;
        try {
            optional = Optional.ofNullable(template
                    .queryForObject(sql, paramSource, new CustomerMapper()));
        } catch (Exception e) {
            optional = Optional.empty();
        }
        return optional;
    }

    @Override
    public boolean update(Long id, Customer customer) {
        String sql = "UPDATE customers " +
                "SET customer_id = :customerID, first_name = :firstName, last_name = :lastName, " +
                "email = :email, phone_number = :phoneNumber " +
                "WHERE id = :id";
        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("customerID", customer.getCustomerId().toString())
                .addValue("firstName", customer.getFirstName())
                .addValue("lastName", customer.getLastName())
                .addValue("email", customer.getEmail())
                .addValue("phoneNumber", customer.getPhoneNumber())
                .addValue("id", id);
        return template.update(sql, paramSource) > 0;
    }

    @Override
    public boolean delete(Long id) {
        String sql = "DELETE FROM customers WHERE id = :id";
        SqlParameterSource paramSource =
                new MapSqlParameterSource("id", id);
        return template.update(sql, paramSource) > 0;
    }

    public Optional<Customer> getLastEntity() {
        String sql = "SELECT id, customer_id, first_name, last_name, email, phone_number FROM customers ORDER BY id DESC LIMIT 1";
        SqlParameterSource paramSource =
                new MapSqlParameterSource();
        Optional<Customer> optional;
        try {
            optional = Optional.ofNullable(template
                    .queryForObject(sql, paramSource, new CustomerMapper()));
        } catch (Exception e) {
            optional = Optional.empty();
        }
        return optional;
    }

    public boolean isEmailExists(String email) {
        String sql = "SELECT COUNT(*) FROM customers WHERE email = :email";
        SqlParameterSource paramSource =
                new MapSqlParameterSource("email", email);
        return template.queryForObject(sql, paramSource, Integer.class) > 0;
    }

    public boolean isCustomerEmail(String email, Long id) {
        String sql = "SELECT COUNT(*) FROM customers WHERE email = :email AND id = :id";
        SqlParameterSource paramSource =
                new MapSqlParameterSource()
                        .addValue("email", email)
                        .addValue("id", id);
        return template.queryForObject(sql, paramSource, Integer.class) > 0;
    }


}
