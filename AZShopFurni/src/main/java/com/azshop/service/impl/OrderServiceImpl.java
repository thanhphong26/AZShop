package com.azshop.service.impl;

import java.util.List;

import com.azshop.dao.IDetailDAO;
import com.azshop.dao.IOrderDAO;
import com.azshop.dao.impl.DetailDAOImpl;
import com.azshop.dao.impl.OrderDAOImpl;
import com.azshop.models.OrderModel;
import com.azshop.service.IOrderService;

public class OrderServiceImpl implements IOrderService {

	IOrderDAO orderDAO = new OrderDAOImpl();
	IDetailDAO detailDAO = new DetailDAOImpl();

	@Override
	public List<OrderModel> listOrderByCustomerID(int customerID) {
		List<OrderModel> list = orderDAO.listOrderByCustomerID(customerID);
		list.forEach(order -> order.setDetails(detailDAO.listDetail(order.getOrderID())));
		return list;
	}

	@Override
	public void updateStatusOrder(int orderID, int status) {
		orderDAO.updateStatusOrder(orderID, status);
	}

	@Override
	public void confirmOrder(int orderID, int confirm) {
		orderDAO.confirmOrder(orderID, confirm);
		
	}

	@Override
	public OrderModel getOrderByOrderID(int orderID) {
		OrderModel order = orderDAO.getOrderByOrderID(orderID);
		order.setDetails(detailDAO.listDetail(order.getOrderID()));
		return order;
	}

	@Override
	public List<OrderModel> findOrderBySeller() {
		List<OrderModel> list = orderDAO.findOrderBySeller();
		list.forEach(order -> order.setDetails(detailDAO.listDetail(order.getOrderID())));
		return list;
	}

	@Override
	public void updateStatusOrder(int orderID,int sellerID, int status) {
		orderDAO.updateStatusOrder(orderID,sellerID, status);
	}

	@Override
	public List<OrderModel> findHisOrder(int sellerID) {
		List<OrderModel> list = orderDAO.findHisOrder(sellerID);
		list.forEach(order -> order.setDetails(detailDAO.listDetail(order.getOrderID())));
		return list;
	}
	
	
	@Override
	public List<OrderModel> findAllOrder() {
		return orderDAO.findAllOrder();
	}
	@Override
	public void updateOrder(OrderModel order) {
		orderDAO.updateOrder(order);
		
	}
	@Override
	public void deleteOrder(int orderID) {
		orderDAO.deleteOrder(orderID);
		
	}
	@Override
	public OrderModel findByOrderID(int orderID) {
		return orderDAO.findByOrderID(orderID);
	}
	@Override
	public OrderModel getOrderByID(int orderID) {
		OrderModel order = orderDAO.getOrderByID(orderID);
		order.setDetails(detailDAO.listDetail(order.getOrderID()));
		return order;
	}
	

	@Override
	public OrderModel insertOrder(OrderModel order) {
		return orderDAO.insertOrder(order);
	}
    
	@Override
	public List<OrderModel> findNeedShipByArea(String area) {
		return addDetailToList(orderDAO.findNeedShipByArea(area));
	}
	@Override
	public List<OrderModel> findShipingByShipperID(int ShipperID) {
		return addDetailToList(orderDAO.findShipingByShipperID(ShipperID));
	}
	@Override
	public List<OrderModel> findHisDeliveryByShipperID(int ShipperID) {
		//return addDetailToList(orderDAO.findHisDeliveryByShipperID(ShipperID));
		return orderDAO.findHisDeliveryByShipperID(ShipperID);
	}
	@Override
	public OrderModel findShipByID(int OrderID) {
		OrderModel order = orderDAO.findShipByID(OrderID);
		order.setDetails(detailDAO.listDetail(OrderID));
		return order;
	}
	
	private List<OrderModel> addDetailToList(List<OrderModel> list){
		list.forEach(order -> order.setDetails(detailDAO.listDetail(order.getOrderID())));
		return list;
	}
}
