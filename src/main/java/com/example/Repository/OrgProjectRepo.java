package com.example.Repository;

import com.example.Model.OrgProject;
import com.example.Model.Project;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Map;

public interface OrgProjectRepo extends CrudRepository<OrgProject,Integer> {
    @Query(value="select project_code,project_name,project_description,created_at,due_date from organisation_project as op inner join project as p on op.proj_id=p.id where op.org_id=?",nativeQuery = true)
    List <Map<String,Object>> findProjectByOrganization(Integer orgId);
}
