package br.com.challenge.core.service;

import br.com.challenge.core.data.repository.ApiAddressRepository;
import br.com.challenge.core.data.vo.AddressVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class AddressService extends ServiceSupport{

    private final ApiAddressRepository apiAddressRepository;

    @Autowired
    public AddressService(ApiAddressRepository apiAddressRepository){
        this.apiAddressRepository = apiAddressRepository;
    }

    public AddressVO fetchAddressByZipcode(String zipCode){
        ObjectMapper mapper = new ObjectMapper();
        AddressVO addressVO = mapper.convertValue(apiAddressRepository.fetchAddressByZipCode(zipCode).getBody(), AddressVO.class);

        return addressVO;
    }
}
