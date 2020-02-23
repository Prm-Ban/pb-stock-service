package com.sunwell.stock.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sunwell.stock.model.Gudang;

public interface WarehouseRepo extends JpaRepository<Gudang, Integer> {
	Gudang findDefaultWarehouse(); 
	Gudang findByName(String _name);
}
