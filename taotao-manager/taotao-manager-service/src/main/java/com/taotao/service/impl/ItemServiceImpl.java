package com.taotao.service.impl;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.dao.TbItemDescMapper;
import com.taotao.dao.TbItemMapper;
import com.taotao.pojo.EasyUIDataResult;
import com.taotao.pojo.IDUtils;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.service.ItemService;
import com.taotao.service.util.JedisClient;
import com.taotao.utils.JsonUtils;

/**
 * @Title ItemServiceImpl
 * @Description 本类主要功能是商品管理Service
 * @Company null
 * @author 曾敏
 * @date 2017年8月29日下午7:01:04
 */
@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper mapper;

	@Autowired
	private TbItemDescMapper itemDescMapper;

	// 注入一个JMStemplate发送消息
	@Autowired
	private JmsTemplate jmsTemplete;
	// 根据id注入一个Destination 由于这里有两个一样的类型的Destination 所以使用名称注入
	@Resource(name = "itemadd-topic")
	private Destination topic;

	@Autowired
	private JedisClient jedis;

	@Value("${REDIS_ITEM}")
	private String REDIS_ITEM; // key的前缀
	@Value("${REDIS_KEY_TIME}")
	private Integer REDIS_KEY_TIME; // 超时时间

	// 根据id查询商品
	@Override
	public TbItem getItemById(long id) {
		// 查询数据库之前先查询缓存 如果缓存有这个数据 直接返回
		try {
			// 中间加冒号 在RedisDesktopManager下显示是按文件夹分开的
			String s = jedis.get(REDIS_ITEM + ":" + id + ":INFO");
			if (StringUtils.isNotBlank(s)) {
				TbItem item = JsonUtils.jsonToPojo(s, TbItem.class);
				return item;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// 如果缓存中没有 就查询数据库
		TbItem tbItem = mapper.selectByPrimaryKey(id);
		// 查询完成添加进Redis
		try {
			jedis.set(REDIS_ITEM + ":" + id + ":INFO", JsonUtils.objectToJson(tbItem));
			// 设置缓存时间
			jedis.expire(REDIS_ITEM + ":" + id + ":INFO", REDIS_KEY_TIME);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return tbItem;
	}

	// 根据id查详细信息
	@Override
	public TbItemDesc getItemDescById(long id) {
		// 查询数据库之前先查询缓存 如果缓存有这个数据 直接返回
		try {
			// 中间加冒号 在RedisDesktopManager下显示是按文件夹分开的
			String s = jedis.get(REDIS_ITEM + ":" + id + ":DESC");
			if (StringUtils.isNotBlank(s)) {
				TbItemDesc item = JsonUtils.jsonToPojo(s, TbItemDesc.class);
				return item;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		TbItemDesc desc = itemDescMapper.selectByPrimaryKey(id);
		// 查询完成添加进Redis
		try {
			jedis.set(REDIS_ITEM + ":" + id + ":DESC", JsonUtils.objectToJson(desc));
			// 设置缓存时间
			jedis.expire(REDIS_ITEM + ":" + id + ":DESC", REDIS_KEY_TIME);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return desc;
	}

	// 查询所有商品(页码，分页大小)
	@Override
	public EasyUIDataResult getItemList(int page, int rows) {
		// TODO Auto-generated method stub
		PageHelper.startPage(page, rows);
		List<TbItem> list = mapper.selectByExample(null);
		PageInfo<TbItem> info = new PageInfo<>(list);

		EasyUIDataResult result = new EasyUIDataResult();
		result.setTotal(info.getTotal()); // 设置记录的条数
		result.setRows(list); // 设置list
		return result;
	}

	// 添加商品
	@Override
	public TaotaoResult addItem(TbItem item, String desc) {
		// TODO Auto-generated method stub
		// 1 生成商品id
		final long itemId = IDUtils.genItemId();
		item.setId(itemId);
		// 商品状态 1正常 2 下架 3删除
		item.setStatus((byte) 1);
		// 2 补全item属性 set
		item.setCreated(new Date());
		item.setUpdated(new Date());
		// 商品表插入
		mapper.insertSelective(item);

		// 3创建一个商品描述表的pojo
		TbItemDesc itemDesc = new TbItemDesc();
		// 4 插入数据
		itemDesc.setItemDesc(desc);
		itemDesc.setItemId(itemId);
		itemDesc.setCreated(new Date());
		itemDesc.setUpdated(new Date());
		itemDescMapper.insertSelective(itemDesc);

		// 添加成功 使用ActiveMQ发送消息到服务器
		jmsTemplete.send(topic, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				// TODO Auto-generated method stub
				TextMessage message = session.createTextMessage(itemId + "");
				return message;
			}
		});

		// 返回
		return TaotaoResult.ok();
	}

}
