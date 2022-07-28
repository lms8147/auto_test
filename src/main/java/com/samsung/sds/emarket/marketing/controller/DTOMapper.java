package com.samsung.sds.emarket.marketing.controller;

import org.mapstruct.Mapper;

import com.samsung.sds.emarket.marketing.api.model.CampaignDTO;
import com.samsung.sds.emarket.marketing.api.model.NewCampaignDTO;
import com.samsung.sds.emarket.marketing.service.vo.CampaignVO;
import com.samsung.sds.emarket.marketing.service.vo.NewCampaignVO;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DTOMapper {

	CampaignDTO toCampaignDTO(CampaignVO campaign);
	
	NewCampaignVO toNewCampaignVO(NewCampaignDTO newCampaignDTO);
	
	@Mapping(target = "id", ignore = true)
	CampaignVO  toCampaignVO(NewCampaignDTO newCampaignDTO);
}
