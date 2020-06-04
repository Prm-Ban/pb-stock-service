package com.sunwell.stock.services;

import java.util.List;


import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.sunwell.stock.exception.OperationException;
import com.sunwell.stock.model.Gudang;
import com.sunwell.stock.model.OnHandStock;
import com.sunwell.stock.model.OnHandStockPK;
import com.sunwell.stock.repository.OnHandStockRepo;
import com.sunwell.stock.repository.WarehouseRepo;
import com.sunwell.stock.utils.StandardConstant;

@Service
@Transactional
@Validated
public class InventorySvc implements InventoryService
{
	@Autowired
	OnHandStockRepo ohRepo;
	
	@Autowired
	WarehouseRepo wrRepo;
	
	public OnHandStockRepo getOhRepo() {
		return ohRepo;
	}

	public void setOhRepo(OnHandStockRepo ohRepo) {
		this.ohRepo = ohRepo;
	}	
	
	public OnHandStock findOnHandStock(@NotNull(message="{error_no_id}") OnHandStockPK _pk) {
		return ohRepo.findById(_pk).orElse(null);
	}
	
	public OnHandStock findOnHandByIdItemAndWarehouse(@NotNull(message="{error_no_item}") int _item, 
											  @NotNull(message="{error_no_warehouse}") Gudang _warehouse) {
		return ohRepo.findByIdItemAndWarehouse(_item, _warehouse);
	}
	
	public Page<OnHandStock> findOnHandByIdItemAndWarehouseId(int _item, int _gudang, Pageable _page) {
		return ohRepo.findByIdItemAndWarehouse_SystemId(_item, _gudang, _page);
	}
	
	public OnHandStock findOnHandByIdItemAndWarehouseId(int _item, int _gudang) {
		 return ohRepo.findByIdItemAndWarehouse_SystemId(_item, _gudang);
	}
	
	public Page<OnHandStock> findAllOnHandStock(Pageable _page) {
		return ohRepo.findAll(_page);
	}
	
	public Page<OnHandStock> findOnHandByIdItem(@NotNull(message="{error_no_item}") int _item, Pageable _page) {
		return ohRepo.findByIdItem(_item, _page);
	}
	
	public Page<OnHandStock> findOnHandByWarehouse(@NotNull(message="{error_no_warehouse}") Gudang _gudang, Pageable _page) {
		return ohRepo.findByWarehouse(_gudang, _page);
	}
	
//	public Page<OnHandStock> findOnHandByItemId(int _id, Pageable _page) {
//		return ohRepo.findByItem_SystemId(_id, _page);
//	}
	
	public Page<OnHandStock> findOnHandByWarehouseId(int _gudang, Pageable _page) {
		return ohRepo.findByWarehouse_SystemId(_gudang, _page);
	}
	
//	public double getOnHandQtyByIdItem(int _item) {
//		Gudang g = findDefaultWarehouse();
//		System.out.println("G: " + g.getName());
//		return ohRepo.getQtyByIdItemAndWarehouse(_item, findDefaultWarehouse());
//	}
	
	public double getOnHandQtyByIdItem(int _item) {
		return ohRepo.getQtyByIdItemAndWarehouse_SystemId(_item, findDefaultWarehouse().getSystemId());
	}
	
	public OnHandStock createOnHand (@Valid @NotNull(message = "{error_no_onhand}") OnHandStock _oh)
	{
		prepareOnHand(_oh);
		return ohRepo.save(_oh);
	}
	
	public OnHandStock addOnHand (@Valid @NotNull(message = "{error_no_onhand}") OnHandStock _oh)
	{
		prepareOnHand(_oh); 
		Optional<OnHandStock> ohs = ohRepo.findById(new OnHandStockPK(_oh.getIdItem(), _oh.getWarehouse().getSystemId()));
		OnHandStock oh = ohs.orElse(null);

		if (oh == null) {
			oh = ohRepo.save(_oh);
			
		}
		else 
			oh.setQty(oh.getQty() + _oh.getQty());

		return oh;
	}
	
	public List<OnHandStock> removeOnHand (
			@Valid @NotNull(message = "{error_no_item}") @Size(min = 1, message = "{error_no_item}") List<OnHandStock> _onHands)
	{
		for (OnHandStock _ohs : _onHands) {
			_ohs = removeOnHand(_ohs);
		}

		return _onHands;
	}
	
	public OnHandStock removeOnHand (@Valid @NotNull(message = "{error_no_onhand}") OnHandStock _oh)
	{
		prepareOnHand(_oh);
		Optional<OnHandStock> ohs = ohRepo.findById(new OnHandStockPK(_oh.getIdItem(), _oh.getWarehouse().getSystemId()));
		OnHandStock oh = ohs.orElse(null);
		System.out.println(" ID: " + _oh.getIdItem() +
							"\n WRH: " + _oh.getWarehouse().getSystemId() +
							"\n EXP: " + _oh.getStrExpiryDate() +
							"\n SERIAL NO: " + _oh.getSerialNo() + 
							"\n BATCH NO: " + _oh.getBatchNo());

		if (oh == null) 
			throw new OperationException(StandardConstant.ERROR_NOT_ENOUGH_STOCK, null);
		else {
			if(oh.getQty() > _oh.getQty())
				oh.setQty(oh.getQty() - _oh.getQty());
			else if(oh.getQty() == _oh.getQty())
				ohRepo.delete(oh);
			else
				throw new OperationException(StandardConstant.ERROR_NOT_ENOUGH_STOCK, null);
		}

		return oh;
	}
	
	public OnHandStock editOnHand (@Valid @NotNull(message = "{error_no_onhand}") OnHandStock _oh)
	{
		prepareOnHand(_oh);
		Optional<OnHandStock> ohs = ohRepo.findById(new OnHandStockPK(_oh.getIdItem(), _oh.getWarehouse().getSystemId()));
		OnHandStock oh = ohs.orElse(null);

		if (oh == null) {
			ohRepo.save(_oh);
		}
		else {
			oh.setQty(_oh.getQty());
		}

		return oh;
	}
	
	public OnHandStock deleteOnHand(@NotNull(message="{error_no_onhand}") OnHandStock _oh) {
		prepareOnHand(_oh);
		Optional<OnHandStock> ohs = ohRepo.findById(new OnHandStockPK(_oh.getIdItem(), _oh.getWarehouse().getSystemId()));
		OnHandStock oh = ohs.orElse(null);

		
		if(oh == null) 
			throw new OperationException(StandardConstant.ERROR_CANT_FIND_STOCK, null);
	    ohRepo.delete(oh);
	    return oh;
    }
	
	public OnHandStock deleteOnHand(@NotNull(message="{error_no_id}") Integer _itemId, @NotNull(message="{error_no_id}") Integer _warehouseId) {
		OnHandStock stock = ohRepo.findByIdItemAndWarehouse_SystemId(_itemId, _warehouseId);
		if(stock == null) 
			throw new OperationException(StandardConstant.ERROR_CANT_FIND_STOCK, null);
	    ohRepo.delete(stock);
	    return stock;
    }
	
	public List<OnHandStock> addOnHand (
			@Valid @NotNull(message = "{error_no_item}") @Size(min = 1, message = "{error_no_item}") List<OnHandStock> _onHands)
	{
		for (OnHandStock _ohs : _onHands) {
			_ohs = addOnHand(_ohs);
		}

		return _onHands;
	}
	
	
	
	public Gudang findWarehouse(int _systemId) {
		return wrRepo.findById(_systemId).orElse(null);
	}
	
	public Gudang findWarehouseByName(String _name) {
		return wrRepo.findByName(_name);
	}
	
	public Gudang findDefaultWarehouse() {
		return wrRepo.findDefaultWarehouse();
	}
	
	private void prepareOnHand(OnHandStock _oh) {
		
		Gudang wr = null;
		if(_oh.getWarehouse().getSystemId() > 0)
			wr = findWarehouse(_oh.getWarehouse().getSystemId());
		else if(_oh.getWarehouse().getName() != null)
			wr = findWarehouseByName(_oh.getWarehouse().getName());	
		if(wr == null)
			throw new OperationException(StandardConstant.ERROR_CANT_FIND_WAREHOUSE, null);
		
		_oh.setWarehouse(wr);
	}
	
	
}
