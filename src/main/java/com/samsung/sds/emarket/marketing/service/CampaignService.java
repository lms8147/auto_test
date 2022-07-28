package com.samsung.sds.emarket.marketing.service;

import java.util.List;

import com.samsung.sds.emarket.marketing.service.vo.CampaignVO;
import com.samsung.sds.emarket.marketing.service.vo.NewCampaignVO;

public interface CampaignService {

	List<CampaignVO> listCampaigns();
	CampaignVO createCampaign(NewCampaignVO newCampaign);  
	/**
	 * @param CampaignVO
	 * @return CampaignVO or null if id is not exists
	 */
	CampaignVO updateCampaign(CampaignVO CampaignVO);
	boolean deleteCampaign(Integer id);
	/**
	 * @param id
	 * @return CampaignVO or null if id is not exists
	 */
	CampaignVO getCampaign(Integer id);

}
