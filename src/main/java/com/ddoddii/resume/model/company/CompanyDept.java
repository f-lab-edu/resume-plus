package com.ddoddii.resume.model.company;

import com.ddoddii.resume.model.eunm.CompanyDepartment;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "company_dept", uniqueConstraints = {
        @UniqueConstraint(columnNames = "dept")
})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class CompanyDept {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "dept")
    private CompanyDepartment dept;
}
