package com.sunwell.stock.repository;

import java.util.List;




import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

import com.sunwell.stock.model.Gudang;
import com.sunwell.stock.model.OnHandStock;
import com.sunwell.stock.model.OnHandStockPK;


public interface OnHandStockRepo extends JpaRepository<OnHandStock, OnHandStockPK>, JpaSpecificationExecutor<OnHandStock> {
	public OnHandStock findByIdItemAndWarehouse(int _item, Gudang _warehouse) ;
	public OnHandStock findByIdItemAndWarehouse_SystemId(int _pId, int _wId) ;
	public Page<OnHandStock> findByIdItem(int _item, Pageable _page) ;
	public Page<OnHandStock> findByWarehouse(Gudang _w, Pageable _page) ;
	public Page<OnHandStock> findByWarehouse_SystemId(int _id, Pageable _page) ;
	public Page<OnHandStock> findByIdItemAndWarehouse_SystemId(int _pId, int _wId, Pageable _page) ;
    public double getQtyByIdItem(@Param("item") int _item) ;
    public double getQtyByIdItemAndWarehouse(@Param("item") int _item, @Param("warehouse") Gudang _warehouse);
    public double getQtyByIdItemAndWarehouse_SystemId(@Param("itemId") int _item, @Param("warehouseId") int _warehouse);
}
