package com.enterpriseapplications.springboot.services.implementations;


import com.enterpriseapplications.springboot.data.dao.MessageDao;
import com.enterpriseapplications.springboot.services.interfaces.MessageService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageServiceImp implements MessageService
{
    private final MessageDao messageDao;
    private final ModelMapper modelMapper;
}
