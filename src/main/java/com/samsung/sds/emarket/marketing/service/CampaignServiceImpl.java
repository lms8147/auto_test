package com.samsung.sds.emarket.marketing.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.samsung.sds.emarket.marketing.repository.CampaignRepository;
import com.samsung.sds.emarket.marketing.repository.entity.CampaignEntity;
import com.samsung.sds.emarket.marketing.service.vo.CampaignVO;
import com.samsung.sds.emarket.marketing.service.vo.NewCampaignVO;

@Service
@Transactional
public class CampaignServiceImpl implements CampaignService {

	private final CampaignRepository campaignRepository;
	private final VOMapper voMapper;

	public CampaignServiceImpl(CampaignRepository campaignRepository, VOMapper voMapper) {

		this.campaignRepository = campaignRepository;
		this.voMapper = voMapper;
	}


	@Override
	public List<CampaignVO> listCampaigns() {
		List<CampaignVO> result = new ArrayList<>(); 

		for( CampaignEntity campaignEntity:campaignRepository.listCampaigns()) {

			result.add( voMapper.toCampaignVO(campaignEntity));  
		}

		return result;
	}


	@Override
	public CampaignVO createCampaign(NewCampaignVO newCampaign) {

		//CampaignVO campaignVO = new CampaignVO(); 
		CampaignEntity entity = voMapper.toCampaignEntity(newCampaign);
		campaignRepository.createCampaign(entity);

		return voMapper.toCampaignVO(entity);
	}


	@Override
	public CampaignVO updateCampaign(CampaignVO campaignVO) {
		CampaignEntity campaignEntity = campaignRepository.getCampaign(campaignVO.getId());
		if (campaignEntity == null)
			return null;

		CampaignEntity entity = voMapper.toCampaignEntity(campaignVO);
		campaignRepository.updateCampaign(entity);

		return voMapper.toCampaignVO(entity);
	}


	@Override
	public boolean deleteCampaign(Integer id) {
		boolean result = false;
		int deleteCount = campaignRepository.deleteCampaign(id);
		if (deleteCount > 0) {
			result = true;
		}
		return result;
	}


	@Override
	public CampaignVO getCampaign(Integer id) {
		CampaignEntity campaignEntity = campaignRepository.getCampaign(id);
		return voMapper.toCampaignVO(campaignEntity);
	}

}