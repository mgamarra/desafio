package br.com.challenge.rest.controller;


import br.com.challenge.core.data.vo.rest.EnvelopeVO;
import br.com.challenge.core.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static br.com.challenge.rest.util.ResponseUtils.ok;
import static br.com.challenge.rest.util.RestConstants.ADDRESS_PATH;
import static br.com.challenge.rest.util.RestConstants.DEFAULT_MEDIA_TYPE;

@RestController
@RequestMapping(value = ADDRESS_PATH, produces = DEFAULT_MEDIA_TYPE)
public class AddressController extends BaseController{

    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        super(ADDRESS_PATH);
        this.addressService = addressService;
    }

    @GetMapping
    public ResponseEntity<EnvelopeVO> fetchAddressByZipcode(@RequestParam String zipcode){
        return ok(addressService.fetchAddressByZipcode(zipcode));
    }
}
