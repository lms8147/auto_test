package com.samsung.sds.emarket.marketing.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.web.servlet.MockMvc;

import com.samsung.sds.emarket.marketing.service.CampaignService;
import com.samsung.sds.emarket.marketing.service.vo.CampaignVO;
import com.samsung.sds.emarket.marketing.service.vo.NewCampaignVO;

import net.minidev.json.JSONObject;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(CampaignController.class)
@ComponentScan( basePackageClasses = {DTOMapper.class})
@AutoConfigureMockMvc(addFilters = false)
public class CampaignControllerTests {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private CampaignService campaignService;


	@Test
	public void test_getCampaignList() throws Exception {

		List<CampaignVO> result = new ArrayList<>();
		CampaignVO campaignVo = new CampaignVO();
		campaignVo.setId(1);
		campaignVo.setName("test campaign 1");
		result.add(campaignVo);

		campaignVo = new CampaignVO();
		campaignVo.setId(2);
		campaignVo.setName("test campaign 2");
		result.add(campaignVo);

		when(campaignService.listCampaigns()).thenReturn(result);

		this.mvc.perform(get("/api/v1/campaigns"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.[0].id", is(1)))
		.andExpect(jsonPath("$.[0].name", is("test campaign 1")))
		.andExpect(jsonPath("$.[1].id", is(2)))
		.andExpect(jsonPath("$.[1].name", is("test campaign 2")))
		;

	}

	@Test
	public void test_postCampaign() throws Exception {
		String name = "test campaign 1";

		JSONObject json = new JSONObject();

		json.put("name", name);
		json.put("description","campaign description 1");
		json.put("From", "2021-05-27T05:01:43+09:00");
		json.put("to", "2021-06-03T05:01:43+09:00");
		json.put("pictureUri", "/images/banner1.png");
		json.put("detailsUri", "/images/detail1.png");

		CampaignVO campaignVO = new CampaignVO();
		campaignVO.setId(100);
		campaignVO.setName(name);

		when(campaignService.createCampaign(any(NewCampaignVO.class))).thenReturn(campaignVO);

		this.mvc.perform(post("/api/v1/campaigns").contentType("application/json").content(json.toString()))
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.id",is(100)))
		.andExpect(jsonPath("$.name",is(name)));

	}

	@Test
	public void test_postCampaign_only_required() throws Exception{

		String name = "test campaign 1";

		JSONObject json = new JSONObject();

		json.put("name", name);
		//		json.put("description","campaign description 1");

		this.mvc.perform(post("/api/v1/campaigns").contentType("application/json").content(json.toString()))
		.andExpect(status().is(400)) ;
	}

	@Test
	public void test_postCampaign_without_required() throws Exception {
		JSONObject json = new JSONObject();
		// json.put("name", "campaign 1");
		json.put("description", "campaign description 1");
		this.mvc.perform(post("/api/v1/campaigns")
				.contentType("application/json").content(json.toString()))
		.andExpect(status().is(400));
	}

	@Test
	public void test_putCampaign() throws Exception {
		int id = 300;
		String name = "campaign 1";

		JSONObject json = new JSONObject();
		json.put("name", name);
		json.put("description", "campaign description 1");
		json.put("From", "2021-05-27T05:01:43+09:00");
		json.put("to", "2021-06-03T05:01:43+09:00");
		json.put("pictureUri", "/images/banner1.png");
		json.put("detailsUri", "/images/detail1.png");

		when(campaignService.updateCampaign(any(CampaignVO.class)))
		.thenAnswer((InvocationOnMock invocation) -> invocation.getArguments()[0]);

		this.mvc.perform(put("/api/v1/campaigns/" + id).contentType("application/json").content(json.toString()))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id", is(id)))
		.andExpect(jsonPath("$.name", is(name)))
		;

	}
	@Test
	public void test_getCampaign() throws Exception {
		int id = 300;
		String name = "campaign 1";

		CampaignVO campaignVO = new CampaignVO();
		campaignVO.setId(id);
		campaignVO.setName(name);

		when(campaignService.getCampaign(any(Integer.class)))
		.thenReturn(campaignVO);

		this.mvc.perform(get("/api/v1/campaigns/" + id))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id", is(id)))
		.andExpect(jsonPath("$.name", is(name)))
		;
	}

	@Test
	public void test_deleteCampaign() throws Exception {
		int id = 300;

		when(campaignService.deleteCampaign(any(Integer.class)))
		.thenReturn(true);

		this.mvc.perform(delete("/api/v1/campaigns/" + id))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", is(true)))
		;
	}

	@Test
	public void test_deleteCampaign_notExists() throws Exception {
		int id = 300;

		when(campaignService.deleteCampaign(any(Integer.class)))
		.thenReturn(false);

		this.mvc.perform(delete("/api/v1/campaigns/" + id))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", is(false)))
		;
	}
}
