package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.dao.TbItemCatMapper;
import com.taotao.pojo.EasyUITreeNode;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemCatExample.Criteria;
import com.taotao.service.ItemCatService;

@Service
public class ItemCatServiceImpl implements ItemCatService{

	@Autowired
	private TbItemCatMapper mapper;
	
	//根据父节点查询子节点列表
	@Override
	public List<EasyUITreeNode> getItemCatList(long parentId) {
		// TODO Auto-generated method stub
		
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		
		
		List<TbItemCat> list = mapper.selectByExample(example);
		
		List<EasyUITreeNode> nodes  = new ArrayList<>();

		for (TbItemCat tbItemCat : list) {
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(tbItemCat.getId());
			node.setText(tbItemCat.getName());
			
			//判断是不是父节点
			node.setState(tbItemCat.getIsParent()?"closed":"open");
			nodes.add(node);
		}
		
		return nodes;
	}

	
}
