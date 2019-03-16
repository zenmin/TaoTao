package com.taotao.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.content.service.ContentCategroyService;
import com.taotao.dao.TbContentCategoryMapper;
import com.taotao.pojo.EasyUITreeNode;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbContentCategoryExample.Criteria;

/**
 * @Title ContentCategroyServiceImpl
 * @Description 本类主要功能是内容分类管理
 * @Company null
 * @author 曾敏
 * @date 2017年9月5日下午5:08:51
 */
@Service
public class ContentCategroyServiceImpl implements ContentCategroyService{

	@Autowired
	private TbContentCategoryMapper mapper;



	//获取全部分类
	@Override
	public List<EasyUITreeNode> getCategroyList(long id) {
	
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(id);
		// TODO Auto-generated method stub
		List<TbContentCategory> list = mapper.selectByExample(example);

		//创建一个eastUI树节点
		List<EasyUITreeNode> list2 = new ArrayList<>();

		for (TbContentCategory t : list) {
			EasyUITreeNode node = new EasyUITreeNode();
			//设置参数
			node.setId(t.getId());
			//判断 如果是父类  就关闭状态  如果是子分类  就展开
			node.setState(t.getIsParent()?"closed":"open");
			node.setText(t.getName());
			list2.add(node);
		}

		return list2;
	}


	//新增类目
	@Override
	public TaotaoResult addContentCategory(long parentId, String name) {
		// TODO Auto-generated method stub
		TbContentCategory category = new TbContentCategory();
		category.setParentId(parentId);	//设置他的父节点id

		category.setName(name);
		category.setStatus(1);		//1 正常  2 删除
		category.setSortOrder(1);	//排序  默认为1
		category.setIsParent(false);
		category.setCreated(new Date());;
		category.setUpdated(new Date());

		mapper.insertSelective(category);

		//判断父节点状态   如果是子节点  把它变为父节点	这里需要在mapper.xml中添加一个主键返回 
		TbContentCategory parent = mapper.selectByPrimaryKey(parentId);
		if(parent.getIsParent()==false) {
			parent.setIsParent(true);
			mapper.updateByPrimaryKeySelective(parent);
		}

		return TaotaoResult.ok(category);			//返回结果 包装对象  
	}


	//删除节点
	@Override
	public TaotaoResult delContentCategory(long id) {
		// TODO Auto-generated method stub
		//先查询该节点的信息
		TbContentCategory category = mapper.selectByPrimaryKey(id);

		//1删除节点
		mapper.deleteByPrimaryKey(id);

		//2判断是否是父节点
		Boolean isParent = category.getIsParent();

		//如果是父节点
		if(isParent) {
			//1、  删除下面所有子节点  
			TbContentCategoryExample delExample = new TbContentCategoryExample();
			Criteria criteria = delExample.createCriteria();
			criteria.andParentIdEqualTo(id);
			mapper.deleteByExample(delExample);
		}else {
			//如果是子节点   判断该   子节点的父节点下  还有没有节点
			Long parentId = category.getParentId();
			TbContentCategoryExample hasExample = new TbContentCategoryExample();
			Criteria criteria = hasExample.createCriteria();
			criteria.andParentIdEqualTo(parentId);

			List<TbContentCategory> list = mapper.selectByExample(hasExample);
			//如果没有子节点  就设置他为子节点
			if(list.size()==0) {
				TbContentCategory c = new TbContentCategory();
				c.setId(parentId);
				c.setIsParent(false);
				mapper.updateByPrimaryKeySelective(c);
			}
		}
		return TaotaoResult.ok();
	}


	//更新节点名称
	@Override
	public TaotaoResult updateContentCategory(long id, String name) {
		// TODO Auto-generated method stub

		TbContentCategory record = new TbContentCategory();
		record.setId(id);
		record.setName(name);
		mapper.updateByPrimaryKeySelective(record );

		return TaotaoResult.ok();
	}

}
