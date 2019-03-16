package com.taotao.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.content.service.ContentService;
import com.taotao.content.util.JedisClient;
import com.taotao.dao.TbContentMapper;
import com.taotao.pojo.EasyUIDataResult;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbContentExample.Criteria;
import com.taotao.utils.JsonUtils;

/**
 * @Title ContentServiceImpl
 * @Description 本类主要功能是内容管理实现类
 * @Company null
 * @author 曾敏
 * @date 2017年9月7日下午3:00:17
 * 
 * 由于Content字段是BOLB类型   所以使用WithBlob方法
 * 
 * 
 */
@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper mapper;
	
	@Value("${INDEX_CONTENT}")
	private String INDEX_CONTENT;
	
	@Autowired
	private JedisClient jedis;
	
	/************       后台内容逻辑        ************/
	
	//新增
	@Override
	public TaotaoResult addContent(TbContent content) {
		// TODO Auto-generated method stub
		content.setCreated(new Date());
		content.setUpdated(new Date());
		mapper.insertSelective(content);
		try {
			//Redis缓存同步
			jedis.hdel(INDEX_CONTENT, content.getCategoryId().toString());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return TaotaoResult.ok();
	}

	//根据id查询
	@Override
	public EasyUIDataResult getContentById(long id, int page, int rows) {
		// TODO Auto-generated method stub

		PageHelper.startPage(page, rows);
		TbContentExample example = new TbContentExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andCategoryIdEqualTo(id);

		List<TbContent> list = mapper.selectByExampleWithBLOBs(example);
		PageInfo<TbContent> info = new PageInfo<>(list);

		EasyUIDataResult result = new EasyUIDataResult();
		result.setTotal(info.getTotal());					//设置记录的条数
		result.setRows(list);								//设置list
		return result;
	}

	//更新
	@Override
	public TaotaoResult updateContent(TbContent content) {
		// TODO Auto-generated method stub
		content.setUpdated(new Date());
		mapper.updateByPrimaryKeyWithBLOBs(content);
		return TaotaoResult.ok();
	}

	//删除逻辑
	@Override
	public TaotaoResult delContent(String ids) {
		// TODO Auto-generated method stub
		//如果是单个id 
		if(ids.contains(",")==false) {
			mapper.deleteByPrimaryKey(Long.valueOf(ids));
		}else {
			
			//批量执行删除
			
			String[] id = ids.split(",");
			List<Long> id2 = new ArrayList<>();
			for (String s : id) {
				id2.add(Long.valueOf(s));
			}

			TbContentExample example = new TbContentExample();
			Criteria criteria = example.createCriteria();
			criteria.andIdIn(id2);
			mapper.deleteByExample(example);

		}
		
		return TaotaoResult.ok();
	}

	
	
	
	/************       前台内容逻辑        ************/	
	
	
	//根据分类id查询广告（首页轮播）
	@Override
	public List<TbContent> getConetentByCId(long id) {
		// TODO Auto-generated method stub
		 
		//先从缓存中查询
		try {
			 String content = jedis.hget(INDEX_CONTENT, id + "");
			 if(StringUtils.isNoneBlank(content)) {		//如果不为空
				 List<TbContent> toList = JsonUtils.jsonToList(content, TbContent.class);
				 return toList;
			 }
			 
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		//缓存中没有再从数据库查询
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(id);
		List<TbContent> list = mapper.selectByExample(example);
		//并且查询完了把这个没有的数据添加到缓存
		try {
			jedis.hset(INDEX_CONTENT, id + "", JsonUtils.objectToJson(list));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return list;
	}

}
