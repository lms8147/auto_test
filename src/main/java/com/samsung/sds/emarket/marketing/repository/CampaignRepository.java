package com.samsung.sds.emarket.marketing.repository;

import java.util.List;

import com.samsung.sds.emarket.marketing.repository.entity.CampaignEntity;

public interface CampaignRepository {

	List<CampaignEntity> listCampaigns();

	int createCampaign(CampaignEntity entity);

	CampaignEntity getCampaign(int id);

	int updateCampaign(CampaignEntity entity);

	int deleteCampaign(Integer id);

}
