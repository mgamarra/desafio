package br.com.challenge.core.data.repository;

import br.com.challenge.core.data.vo.AddressVO;
import br.com.challenge.core.data.vo.rest.EnvelopeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class ApiAddressRepository {

    private static final String ZIPCODE_API_ADDRESS = "https://viacep.com.br/ws/";

    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity<AddressVO> fetchAddressByZipCode(String zipCode){
        String url = ZIPCODE_API_ADDRESS + zipCode + "/json/";
        return restTemplate.exchange(url, HttpMethod.GET, null, AddressVO.class);
    }
}
