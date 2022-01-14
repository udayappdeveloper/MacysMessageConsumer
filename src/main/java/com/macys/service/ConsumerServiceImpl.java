package com.macys.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.macys.dto.json.OrderMessageJson;
import com.macys.dto.xml.FulfillmentOrder;
import com.macys.entity.json.OrderMsgJsonEntity;
import com.macys.entity.xml.FulfillmentOrderEntity;
import com.macys.exception.SavingDataException;
import com.macys.repository.JsonMsgRepository;
import com.macys.repository.XmlMsgRepository;
import com.macys.utils.ConsumerConstants;
import com.macys.utils.EntityToPojoUtil;

@Service
public class ConsumerServiceImpl implements ConsumerService {

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	RabbitAdmin rabbitAdmin;

	@Autowired
	@Qualifier("xmlTemplate")
	RabbitTemplate xmlTemplate;

	@Autowired
	@Qualifier("jsonTemplate")
	AmqpTemplate jsonTemplate;

	@Autowired
	XmlMsgRepository xmlMsgRepository;

	@Autowired
	JsonMsgRepository jsonMsgRepository;

	@Override
	public ResponseEntity<List<FulfillmentOrder>> getXmlMessage() {
		List<FulfillmentOrder> fulfilOrderList = new ArrayList<>();
		Properties properties = rabbitAdmin.getQueueProperties(ConsumerConstants.XML_QUEUE);
		int ordersCount = (Integer) (properties != null ? properties.get(RabbitAdmin.QUEUE_MESSAGE_COUNT) : 0);
		for (int i = 0; i < ordersCount; i++) {
			try {
				Message msg = xmlTemplate.receive();
				byte b[] = msg.getBody();
				FulfillmentOrder fulfillmentOrder = new XmlMapper().readValue(b, FulfillmentOrder.class);
				FulfillmentOrderEntity entity = EntityToPojoUtil.xmlPojoToEntity(modelMapper, fulfillmentOrder);
				FulfillmentOrderEntity entity1 = null;
				try {
					entity1 = xmlMsgRepository.save(entity);
					fulfilOrderList.add(fulfillmentOrder);
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} finally {
					if (entity1 == null) {
						throw new SavingDataException();
					}
				}
			} catch (Exception e) {
				break;
			}
		}
		return new ResponseEntity<>(fulfilOrderList, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<OrderMessageJson>> getJsonMessage() {
		List<OrderMessageJson> orderMessageJsonList = new ArrayList<>();
		Properties properties = rabbitAdmin.getQueueProperties(ConsumerConstants.JSON_QUEUE);
		int ordersCount = (Integer) (properties != null ? properties.get(RabbitAdmin.QUEUE_MESSAGE_COUNT) : 0);
		for (int i = 0; i < ordersCount; i++) {
			try {
				OrderMessageJson orderMessageJson = new ObjectMapper()
						.readValue(new String((byte[]) jsonTemplate.receiveAndConvert()), OrderMessageJson.class);
				OrderMsgJsonEntity entity = EntityToPojoUtil.jsonPojoToEntity(modelMapper, orderMessageJson);
				OrderMsgJsonEntity entity1 = null;
				try {
					entity1 = jsonMsgRepository.save(entity);
					orderMessageJsonList.add(orderMessageJson);
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} finally {
					if (entity1 == null) {
						throw new SavingDataException();
					}
				}
			} catch (Exception e) {
				break;
			}
		}
		return new ResponseEntity<>(orderMessageJsonList, HttpStatus.OK);
	}

}
