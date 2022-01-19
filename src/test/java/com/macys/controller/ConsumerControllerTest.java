package com.macys.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.macys.dto.json.OrderMessageJson;
import com.macys.dto.xml.FulfillmentOrder;
import com.macys.service.ConsumerService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ConsumerControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ConsumerService consumerService;

	@Test
	public void getXmlMessageTest() throws Exception {

		FulfillmentOrder fulfillmentOrder1 = mock(FulfillmentOrder.class);
		FulfillmentOrder fulfillmentOrder2 = mock(FulfillmentOrder.class);
		List<FulfillmentOrder> fulfillmentOrderList = new ArrayList<>();
		fulfillmentOrderList.add(fulfillmentOrder2);
		fulfillmentOrderList.add(fulfillmentOrder1);

		when(consumerService.getXmlMessage()).thenReturn(new ResponseEntity<>(fulfillmentOrderList, HttpStatus.OK));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/macys/consumer/xml")
				.accept(MediaType.APPLICATION_XML).contentType(MediaType.APPLICATION_XML);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		Assertions.assertNotNull(result.getResponse());

	}
	
	
	
	@Test
	public void getGcpXmlMessageTest() throws Exception {

		FulfillmentOrder fulfillmentOrder1 = mock(FulfillmentOrder.class);
		FulfillmentOrder fulfillmentOrder2 = mock(FulfillmentOrder.class);
		List<FulfillmentOrder> fulfillmentOrderList = new ArrayList<>();
		fulfillmentOrderList.add(fulfillmentOrder2);
		fulfillmentOrderList.add(fulfillmentOrder1);

		when(consumerService.getXmlMessageFromGcp()).thenReturn(new ResponseEntity<>(fulfillmentOrderList, HttpStatus.OK));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/macys/consumer/gcp/xml")
				.accept(MediaType.APPLICATION_XML).contentType(MediaType.APPLICATION_XML);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		Assertions.assertNotNull(result.getResponse());

	}

	@Test
	public void getJsonMessageTest() throws Exception {

		OrderMessageJson orederMsg1 = mock(OrderMessageJson.class);
		OrderMessageJson orederMsg2 = mock(OrderMessageJson.class);
		List<OrderMessageJson> ordersList = new ArrayList<>();
		ordersList.add(orederMsg1);
		ordersList.add(orederMsg2);

		when(consumerService.getJsonMessage()).thenReturn(new ResponseEntity<>(ordersList, HttpStatus.OK));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/macys/consumer/json")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		Assertions.assertNotNull(result.getResponse());

	}
	
	
	@Test
	public void getGcpJsonMessageTest() throws Exception {

		OrderMessageJson orederMsg1 = mock(OrderMessageJson.class);
		OrderMessageJson orederMsg2 = mock(OrderMessageJson.class);
		List<OrderMessageJson> ordersList = new ArrayList<>();
		ordersList.add(orederMsg1);
		ordersList.add(orederMsg2);

		when(consumerService.getJsonMessageFromGcp()).thenReturn(new ResponseEntity<>(ordersList, HttpStatus.OK));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/macys/consumer/gcp/json")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		Assertions.assertNotNull(result.getResponse());

	}

}
