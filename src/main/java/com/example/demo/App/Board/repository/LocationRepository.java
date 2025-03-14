package com.example.demo.App.Board.repository;

import com.example.demo.App.Board.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location,Long> {
}
