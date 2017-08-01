package com.yuyu.a;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/** 
 *
 * <p>Copyright (c) 2017  OSFF	</p>  
 * <p>类描述:		[这个类是webmagic的入门示例程序]	</p>
 * <p>创建人:		[xry]   </p>
 * <p>创建时间:	[2017-8-1 上午11:07:23]   </p>
 * <p>修改人:		[xry]   </p> 
 * <p>修改记录：    </p> 
 **/
public class DownloadTitle implements PageProcessor{
	
	// 第一步：设置抓取的重试次数，间隔时间
	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

	@Override
	public Site getSite() {
		// TODO Auto-generated method stub
		return site;
	}

	/**
	 * 第二步：处理待抓取的页面，在此写业务逻辑
	 */
	@Override
	public void process(Page page) {
		// TODO Auto-generated method stub
		//要抓取的信息，用XPath表达式获取，不了解XPath可以先了解一下
		page.putField("title", page.getHtml().xpath("//*[@id='contents']/div/div[1]/div/div[1]/h1/text()"));
		
		//第三步：发现新的URL，并放入待抓取队列，用正则表达式获取，不了解正则可以先了解一下
		page.addTargetRequests(page.getHtml().links().regex("/article/[^\\s]*").all());
	}
	
	/**
	 * Test
	 * @param args
	 */
	public static void main(String[] args) {
		//蜘蛛创建爬取实例
		Spider.create(new DownloadTitle())
		//添加起始URL
		.addUrl("http://www.jb51.net/article/14397.htm")
		//开启5个线程
		.thread(5)
		//开始抓取
		.run();
	}

}
