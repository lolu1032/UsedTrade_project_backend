package com.example.demo.Member.product.repository;

import com.example.demo.Member.product.domain.Category;
import com.example.demo.Member.product.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location,Long> {
}
