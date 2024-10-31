package com.gestion.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestion.entity.TipoPrestamo;


public interface TipoPrestamoRepository extends JpaRepository<TipoPrestamo, Integer> {

}
