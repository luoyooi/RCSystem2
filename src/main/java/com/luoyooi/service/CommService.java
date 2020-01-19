package com.luoyooi.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.luoyooi.dao.CommDAO;
import com.luoyooi.pojo.COMMVO;
import com.luoyooi.pojo.CommDO;

import edu.hit.ir.ltp4j.Postagger;
import edu.hit.ir.ltp4j.Segmentor;

public class CommService {
	private CommDAO dao;
	// 定义分词对象
	private Segmentor segmentor;
	// 定义词性标注对象
	private Postagger postagger;
	
	public CommService(){
		//实例化
		dao = new CommDAO();
		segmentor = new Segmentor();
		postagger = new Postagger();
		
		// 载入模板
		if (segmentor.create("D:\\LTP\\ltp_data_v3.4.0\\cws.model") < 0) {
			System.out.println("segmentor load failed");
		}
		if (postagger.create("D:\\LTP\\ltp_data_v3.4.0\\pos.model") < 0) {
			System.out.println("postagger load failed");
		}
	}
	public COMMVO listCOMM(String mvName){
		COMMVO cmvo = new COMMVO();
		// 用于存储分词结果
		ArrayList<String> seg = new ArrayList<String>();
		// 用于存储词性标注结果
		ArrayList<String> tags = new ArrayList<String>();

		// 获取评论
		ArrayList<CommDO> listComm = null;
		listComm = dao.getCommList(mvName);
		
		// 存储评论词汇出现的次数
		Map<String, Integer> map = new TreeMap<>();

		for (CommDO cmDO : listComm) {
			// 调用LTP分词系统处理评论
			segmentor.segment(cmDO.getComment(), seg);
			int size = postagger.postag(seg, tags);

			for (int i = 0; i < size; i++) {
				// 如果是形容词并且长度大于1
				if (tags.get(i).equals("a")&&seg.get(i).length() > 1) {
					String adj = seg.get(i);
					// 如果map中存在此形容词
					if (map.get(adj) != null) {
						// 数量加一
						map.put(adj, map.get(adj) + 1);
					}
					// 否则新建一个
					else {
						map.put(adj, 1);
					}
				}
			}

			// 清除内容以处理下一条评论
			seg.clear();
			tags.clear();
		}
		// 排序
		List<Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			// 降序排序
			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
		});

		// 初始化COMMVO对象中的两个list
		cmvo.setWords(new ArrayList<>());
		cmvo.setTimes(new ArrayList<>());

		// COMMVO对象赋值
		for (Entry<String, Integer> entry : list) {
			cmvo.getWords().add(entry.getKey());
			cmvo.getTimes().add(entry.getValue());
		}

		return cmvo;
	}

}
