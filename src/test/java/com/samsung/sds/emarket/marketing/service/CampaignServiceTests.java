package com.samsung.sds.emarket.marketing.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.samsung.sds.emarket.marketing.repository.CampaignRepository;
import com.samsung.sds.emarket.marketing.repository.entity.CampaignEntity;
import com.samsung.sds.emarket.marketing.service.vo.CampaignVO;
import com.samsung.sds.emarket.marketing.service.vo.NewCampaignVO;

@ExtendWith(MockitoExtension.class)
public class CampaignServiceTests {
	@Mock
	private CampaignRepository campaignRepository;

	@Test
	public void test_listCampaigns() {
		
		List<CampaignEntity> result = new ArrayList<>();
		CampaignEntity campaign = new CampaignEntity();
		campaign.setId(3);
		campaign.setName("test name 3");
		result.add(campaign);
		
		campaign = new CampaignEntity();
		campaign.setId(4);
		campaign.setName("test name 4");
		result.add(campaign);
		
		when(campaignRepository.listCampaigns()).thenReturn(result);      
	    
	    CampaignService campaignService =  
	    new CampaignServiceImpl(campaignRepository,new VOMapperImpl());

		
		List<CampaignVO> list = campaignService.listCampaigns();
		assertThat(list).extracting("id","name").contains(
				tuple(3,"test name 3"),
				tuple(4,"test name 4")
				);
	}
     
	@Test
    public void test_createCampaigns() {
    	String name = "test name";
    	OffsetDateTime from = OffsetDateTime.now();
    	OffsetDateTime to = from.plusMinutes(3); 
    	
    	NewCampaignVO newCampaign = new NewCampaignVO();
        newCampaign.setName(name);
        newCampaign.setDescription("desc");
        newCampaign.setDetailsUri("detailUri");
        newCampaign.setFrom(from);
        newCampaign.setTo(to);

        CampaignService campaignService =  
        	    new CampaignServiceImpl(campaignRepository,new VOMapperImpl());

        when(campaignRepository.createCampaign(any(CampaignEntity.class)))
				.thenAnswer((InvocationOnMock invocation) -> {
					((CampaignEntity)invocation.getArguments()[0]).setId(100);
		        	return 1;
				});

        assertThat(campaignService.createCampaign(newCampaign))
            .hasFieldOrPropertyWithValue("id", 100)
            .hasFieldOrPropertyWithValue("name", name);
    }
	
    @Test
    public void test_updateCampaigns() {
		String name = "test name";
		OffsetDateTime from = OffsetDateTime.now();
		OffsetDateTime to = from.plusMinutes(3);
    	
		CampaignVO Campaign = new  CampaignVO();
		Campaign.setId(100);;
		Campaign.setName(name);
		Campaign.setDescription("desc");
		Campaign.setDetailsUri("detailUri");
		Campaign.setFrom(from);
		Campaign.setTo(to);

        CampaignService campaignService =  
        	    new CampaignServiceImpl(campaignRepository,new VOMapperImpl());

        when(campaignRepository.getCampaign(Campaign.getId())).thenReturn(new CampaignEntity());
         
        assertThat(campaignService.updateCampaign(Campaign)) 
            .hasFieldOrPropertyWithValue("id", 100)
            .hasFieldOrPropertyWithValue("name", name);
    }
    
    @Test
	public void test_getCampaign() {
    	Integer id = 100;
    	String name = "test name";
    	
		CampaignEntity campaign = new CampaignEntity();
		campaign.setId(id);
		campaign.setName(name);
		
		when(campaignRepository.getCampaign(any(Integer.class)))
		.thenReturn(campaign);      
	    
	    CampaignService campaignService =  
	    new CampaignServiceImpl(campaignRepository,new VOMapperImpl());

		
		assertThat(campaignService.getCampaign(id))
		.hasFieldOrPropertyWithValue("id", id)
        .hasFieldOrPropertyWithValue("name", name);
	}
    
    @Test
	public void test_deleteCampaign() {
    	Integer id = 100;
		
		when(campaignRepository.deleteCampaign(any(Integer.class)))
		.thenReturn(1);      
	    
	    CampaignService campaignService =  
	    new CampaignServiceImpl(campaignRepository,new VOMapperImpl());

		assertThat(campaignService.deleteCampaign(id)).isTrue();
	}
}
