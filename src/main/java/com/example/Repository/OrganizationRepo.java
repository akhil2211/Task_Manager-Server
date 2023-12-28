package com.example.Repository;

import com.example.Model.Organization;
import org.springframework.data.repository.CrudRepository;

public interface OrganizationRepo extends CrudRepository<Organization,Integer> {
}
