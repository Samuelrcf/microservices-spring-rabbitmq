package com.ms.user.producers;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ms.user.dtos.EmailDto;
import com.ms.user.models.UserModel;

@Component
public class UserProducer {

	@Autowired
	RabbitTemplate rabbitTemplate;
	
	@Value(value = "${broker.queue.email.name}")
	private String routingKey;
	
	public void publishMessageEmail(UserModel userModel) {
		var emailDto = new EmailDto();
		emailDto.setUserId(userModel.getId());
		emailDto.setEmailTo(userModel.getEmail());
		emailDto.setSubject("Cadastro realizado com sucesso.");
		emailDto.setText("Olá, " + userModel.getName() + ", seja bem vindo(a)!");
		
		rabbitTemplate.convertAndSend("", routingKey, emailDto);
	}
}
