package com.example.function_module.service;

import com.example.function_module.dto.ApartmentFullInfoDto;
import com.example.function_module.entity.AddressInfo;
import com.example.function_module.entity.ApartmentInfo;
import com.example.function_module.entity.Role;
import com.example.function_module.entity.User;
import com.example.function_module.repository.AddressRepository;
import com.example.function_module.repository.ApartmentInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class RentApartmentServiceImpl implements RentApartmentService{

    private final AddressRepository addressRepository;
    private final ApartmentInfoRepository apartmentInfoRepository;

    @Override
    public void saveNewApart(ApartmentFullInfoDto apartmentFullInfoDto) {

        AddressInfo addressInfo = new AddressInfo();

        addressInfo.setBuildingNumber(apartmentFullInfoDto.getBuildingNumber());
        addressInfo.setCity(apartmentFullInfoDto.getCity());
        addressInfo.setStreet(apartmentFullInfoDto.getStreet());

        ApartmentInfo apartmentInfo = new ApartmentInfo();

        apartmentInfo.setPrice(apartmentFullInfoDto.getPrice());
        apartmentInfo.setRoomsCount(apartmentFullInfoDto.getRoomsCount());
        apartmentInfo.setAddressInfo(addressInfo);

        addressRepository.save(addressInfo);
        apartmentInfoRepository.save(apartmentInfo);
    }
}
