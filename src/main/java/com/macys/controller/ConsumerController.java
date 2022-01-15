package com.macys.controller;

import com.macys.dto.json.OrderMessageJson;
import com.macys.dto.xml.FulfillmentOrder;
import com.macys.service.ConsumerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/macys/consumer")
public class ConsumerController {

    @Autowired
    ConsumerService messageService;

    @GetMapping(value = "/xml",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    ResponseEntity<List<FulfillmentOrder>> getXmlMessage() {
        return messageService.getXmlMessage();
    }
    
    

    @GetMapping(value = "/json",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    ResponseEntity<List<OrderMessageJson>> getJsonMessage() {
        return messageService.getJsonMessage();
    }
    
    
    @GetMapping(value = "gcp/xml",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    ResponseEntity<List<FulfillmentOrder>> getXmlMessageFromGcp() {
        return messageService.getXmlMessageFromGcp();
    }
    
    
    @GetMapping(value = "gcp/json",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    ResponseEntity<List<OrderMessageJson>> getJsonMessageFromGCP() {
        return messageService.getJsonMessageFromGcp();
    }
}
