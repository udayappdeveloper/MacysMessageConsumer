package com.macys.service;

import com.macys.dto.json.OrderMessageJson;
import com.macys.dto.xml.FulfillmentOrder;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ConsumerService {
	ResponseEntity<List<FulfillmentOrder>> getXmlMessage();

	ResponseEntity<List<OrderMessageJson>> getJsonMessage();
}
