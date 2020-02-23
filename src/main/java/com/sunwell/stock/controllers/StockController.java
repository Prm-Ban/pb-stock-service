package com.sunwell.stock.controllers;

import java.util.Arrays;

import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sunwell.stock.dto.OnHandStockDTO;
import com.sunwell.stock.model.Gudang;
import com.sunwell.stock.model.OnHandStock;
import com.sunwell.stock.services.InventoryService;
import com.sunwell.stock.utils.Filters;
import com.sunwell.stock.utils.ServiceUtil;


@RestController
public class StockController
{
	
	@Autowired
	InventoryService invSvc;
	
	@Autowired
    ServiceUtil svcUtil;
	
	@RequestMapping(value = "resources/stocks", method = RequestMethod.GET,
			produces = "application/json"
	)
    public ResponseEntity<Map<String,Object>> getStocks(
    		@RequestHeader(value="Authorization", required = false) String _auth,
    		@RequestParam(value="itemId", required = false) Integer _itemId,
    		@RequestParam(value="warehouseId", required = false) Integer _warehouseId,
    		Pageable _page
    		) throws Exception 
    {
		Map<String,Object> retData = null;
		
		try {
			Object mainData = null;
			OnHandStock oh = null;
    		Page<OnHandStock> pageOhs = null ;
    		int totalPages = 0;
			long totalItems = 0;
			
			if(_itemId != null)
        		pageOhs = invSvc.findOnHandByIdItem(_itemId, _page);
        	else if (_warehouseId != null)
        		pageOhs = invSvc.findOnHandByWarehouseId(_warehouseId, _page);
        	else
        		pageOhs = invSvc.findAllOnHandStock(_page);
            
            if(pageOhs != null && pageOhs.getNumberOfElements() > 0) {
    			List<OnHandStock> stocks = pageOhs.getContent();
    			List<OnHandStockDTO> stocksData = new LinkedList<>();
    			for(OnHandStock ohs : stocks) {
    				stocksData.add(new OnHandStockDTO(ohs));
    			}
    			mainData = stocksData;
    			totalPages = pageOhs.getTotalPages();
    			totalItems = pageOhs.getTotalElements();
    		}
    		else if (oh != null) {
    			mainData = new OnHandStockDTO(oh);
    			totalPages = 1;
    			totalItems = 1;
    		}
            
            retData = svcUtil.returnSuccessfulData(mainData, totalPages, totalItems);
		}
		catch(Exception e) {
			retData = svcUtil.handleException(e);
		}
        return new ResponseEntity<Map<String,Object>>(retData, null, HttpStatus.OK);
    }
	
	@RequestMapping(value = "resources/stocksbyid", method = RequestMethod.GET,
			produces = "application/json"
	)
    public ResponseEntity<Map<String,Object>> getStocksById(
    		@RequestHeader(value="Authorization", required = false) String _auth,
    		@RequestParam(value="itemId", required = false) List<Integer> _itemId,
    		@RequestParam(value="warehouseId", required = false) Integer _warehouseId
    		) throws Exception 
    {
		Map<String,Object> retData = null;
		
		try {
			Object mainData = null;
			OnHandStock oh = null;
    		Page<OnHandStock> pageOhs = null ;
    		int totalPages = 0;
			long totalItems = 0;
			List<OnHandStock> listOhs = new LinkedList<>();
			List<OnHandStockDTO> listDTO = new LinkedList<>();
			
			for(Integer id : _itemId) {
				System.out.println("INV SVC: " + invSvc);
				OnHandStock ohs = invSvc.findOnHandByIdItemAndWarehouseId(id, _warehouseId);
				if(ohs != null)
					listDTO.add(new OnHandStockDTO(ohs));
			}
			
			if(listDTO.size() > 0)
				mainData = listDTO;
			
			totalPages = 1;
			totalItems = 1;
            
            retData = svcUtil.returnSuccessfulData(mainData, totalPages, totalItems);
		}
		catch(Exception e) {
			retData = svcUtil.handleException(e);
		}
        return new ResponseEntity<Map<String,Object>>(retData, null, HttpStatus.OK);
    }
    
    @RequestMapping(value = "resources/stocks", method = RequestMethod.GET,
			produces = "application/json", params="criteria"
	)
    public ResponseEntity<Map<String,Object>> getStocks(
    		@RequestHeader(value="Authorization", required = false) String _auth,
    		@RequestParam(value="criteria") List<String> _filters,
    		Pageable _page
    		) throws Exception 
    {
		Map<String,Object> retData = null;
	
		try {
			Object mainData = null;
    		Page<OnHandStock> pageStocks = null ;
			int totalPages = 0;
			long totalItems = 0;
			Filters filters =  svcUtil.convertToFilters(_filters, OnHandStock.class);			
			pageStocks = invSvc.findOnHandStocks(filters, _page);
			if(pageStocks != null && pageStocks.getNumberOfElements() > 0) {
            	totalPages = pageStocks.getTotalPages();
            	totalItems = pageStocks.getTotalElements();
            	List<OnHandStock> stocks = pageStocks.getContent();            	
            	List<OnHandStockDTO> listOnHandDTO = new LinkedList<>();
            	for(OnHandStock oh : stocks) {
            		listOnHandDTO.add(new OnHandStockDTO(oh));
            	}
            	mainData = listOnHandDTO;
        	}
            
            retData = svcUtil.returnSuccessfulData(mainData, totalPages, totalItems);
		}
		catch(Exception e) {
			retData = svcUtil.handleException(e);
		}
		
        return new ResponseEntity<Map<String,Object>>(retData, null, HttpStatus.OK);
    }
        
	
	@RequestMapping(value = "resources/stocks", method = RequestMethod.POST,
			consumes = "application/json", produces = "application/json"
	)
    public ResponseEntity<Map<String,Object>> addStock(
    		@RequestHeader(value="Authorization", required = false) String _auth,
    		@RequestBody OnHandStockDTO _dto) throws Exception 
    {
		Map<String,Object> retData = null;
		try {
			OnHandStock data = _dto.getData();
			data = invSvc.addOnHand(data);
			retData = svcUtil.returnSuccessfulData(new OnHandStockDTO(data), 1, 1);
		}
		catch(Exception e) {
			retData = svcUtil.handleException(e);
		}
        return new ResponseEntity<Map<String,Object>>(retData, null, HttpStatus.CREATED);
    }
	
	@RequestMapping(value = "resources/stocks", method = RequestMethod.PUT,
			consumes = "application/json", produces = "application/json"
	)
    public ResponseEntity<Map<String,Object>> editStock(
    		@RequestHeader(value="Authorization", required = false) String _auth,
    		@RequestBody OnHandStockDTO _dto) throws Exception 
    {
		Map<String,Object> retData = null;
		try {
			OnHandStock data = _dto.getData();
			System.out.println("WR: " + data.getWarehouse().getName() + " id: " + data.getWarehouse().getSystemId());
			data = invSvc.editOnHand(data);
			retData = svcUtil.returnSuccessfulData(new OnHandStockDTO(data), 1, 1);
		}
		catch(Exception e) {
			retData = svcUtil.handleException(e);
		}
        return new ResponseEntity<Map<String,Object>>(retData, null, HttpStatus.CREATED);
    }
	
    @RequestMapping(value = "resources/stocks", method = RequestMethod.DELETE,
			produces = "application/json"
	)
    public ResponseEntity<Map<String,Object>> deleteStock(
    		@RequestHeader(value="Authorization", required = false) String _auth,
    		@RequestParam("itemId") Integer _itemId,
    		@RequestParam("warehouseId") Integer _warehouseId) throws Exception 
    {
		Map<String,Object> retData = null;
		try {
			OnHandStock oh = invSvc.deleteOnHand(new OnHandStock(_itemId, new Gudang(_warehouseId), -1));
			retData = svcUtil.returnSuccessfulData(new OnHandStockDTO(oh), 1, 1);
		}
		catch(Exception e) {
			retData = svcUtil.handleException(e);
		}
        return new ResponseEntity<Map<String,Object>>(retData, null, HttpStatus.OK);
    }
    
    @RequestMapping(value = "resources/removestocks", method = RequestMethod.POST,
			consumes = "application/json", produces = "application/json"
	)
    public ResponseEntity<Map<String,Object>> removeStocks(
    		@RequestHeader(value="Authorization", required = false) String _auth,
    		@RequestBody List<OnHandStockDTO> _dto) throws Exception 
    {
		Map<String,Object> retData = null;
		try {
			for(OnHandStockDTO ohDTO : _dto) {
				System.out.println("ID: " + ohDTO.getIdItem() + " qty: " + ohDTO.getItemName() + " wid: " + ohDTO.getWarehouse());
				invSvc.removeOnHand(ohDTO.getData());
			}
//			retData = svcUtil.returnSuccessfulData(new OnHandStockDTO(data), 1, 1);
		}
		catch(Exception e) {
			retData = svcUtil.handleException(e);
		}
        return new ResponseEntity<Map<String,Object>>(retData, null, HttpStatus.CREATED);
    }
    
    @RequestMapping(value = "resources/removestock", method = RequestMethod.POST,
			consumes = "application/json", produces = "application/json"
	)
    public ResponseEntity<Map<String,Object>> removeStock(
    		@RequestHeader(value="Authorization", required = false) String _auth,
    		@RequestBody OnHandStockDTO _dto) throws Exception 
    {
		Map<String,Object> retData = null;
		try {
			OnHandStock data = _dto.getData();
			data = invSvc.removeOnHand(data);
			retData = svcUtil.returnSuccessfulData(new OnHandStockDTO(data), 1, 1);
		}
		catch(Exception e) {
			retData = svcUtil.handleException(e);
		}
        return new ResponseEntity<Map<String,Object>>(retData, null, HttpStatus.CREATED);
    }
    
}
