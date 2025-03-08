package com.example.demo.App.product.repository;

import com.example.demo.App.product.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location,Long> {
}
