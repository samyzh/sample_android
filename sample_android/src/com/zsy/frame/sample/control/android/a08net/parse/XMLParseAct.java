package com.zsy.frame.sample.control.android.a08net.parse;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.res.Resources.NotFoundException;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zsy.frame.lib.extend.ui.base.BaseAct;
import com.zsy.frame.sample.R;

/**
 * @description：
XML在各种开发中都广泛应用，Android也不例外。作为承载数据的一个重要角色，如何读写XML成为Android开发中一项重要的技能。
今天就由我向大家介绍一下在Android平台下几种常见的XML解析和创建的方法。
在Android中，常见的XML解析器分别为SAX解析器、DOM解析器和PULL解析器，下面，我将一一向大家详细介绍。
SAX解析器：
  SAX(Simple API for XML)解析器是一种基于事件的解析器，它的核心是事件处理模式，主要是围绕着事件源以及事件处理器来工作的。
    当事件源产生事件后，调用事件处理器相应的处理方法，一个事件就可以得到处理。
    在事件源调用事件处理器中特定方法的时候，还要传递给事件处理器相应事件的状态信息，这样事件处理器才能够根据提供的事件信息来决定自己的行为。
  SAX解析器的优点是解析速度快，占用内存少。非常适合在Android移动设备中使用。
DOM解析器：
    DOM是基于树形结构的的节点或信息片段的集合，允许开发人员使用DOM API遍历XML树、检索所需数据。
    分析该结构通常需要加载整个文档和构造树形结构，然后才可以检索和更新节点信息。
    由于DOM在内存中以树形结构存放，因此检索和更新效率会更高。但是对于特别大的文档，解析和加载整个文档将会很耗资源。
PULL解析器：(Android inflate 默认是这种加载方式)
  PULL解析器的运行方式和SAX类似，都是基于事件的模式。不同的是，在PULL解析过程中，我们需要自己获取产生的事件然后做相应的操作，
    而不像SAX那样由处理器触发一种事件的方法，执行我们的代码。
  PULL解析器小巧轻便，解析速度快，简单易用，非常适合在Android移动设备中使用，Android系统内部在解析各种XML时也是用PULL解析器。(重点)
======================================================================================================  
  SAX是一个用于处理XML事件驱动的“推”模型，
优点是一种解析速度快并且占用内存少的xml解析器，它需要哪些数据再加载和解析哪些内容。
缺点是它不会记录标签的关系，而要让你的应用程序自己处理，这样就增加了你程序的负担。

DOM是一种文档对象模型，DOM可以以一种独立于平台和语言的方式访问和修改一个文档的内容和结构。
Dom技术使得用户页面可以动态地变化，如可以动态地显示或隐藏一个元素，改变它们的属性，增加一个元素等，Dom技术使得页面的交互性大大地增强。
缺点是DOM解析XML文件时，会将XML文件的所有内容以文档树方式存放在内存中。

Pull解析和Sax解析很相似,
Pull解析和Sax解析不一样的地方是pull读取xml文件后触发相应的事件调用方法返回的是数字
还有pull可以在程序中控制想解析到哪里就可以停止解析。
----------------------------------------应用范围-----------------------------------------------------
DOM方式最直观和容易理解，但是只适合XML文档较小的时候使用，
而SAX方式更适合在OPhone/Android系统中使用，因为相比DOM占用内存少，适合处理比较大的XML文档，
最后的Pull方式使用场合和SAX类似，但是更适合需要提前结束XML文档解析的场合。
 * 
 * @author samy
 * @date 2015-3-29 下午4:36:28
 */
public class XMLParseAct extends BaseAct {
	public static final String TAG = "XMLParseAct";

	Button button1;
	Button button2;
	Button button3;
	Button button4;

	TextView textView1;
	TextView textView2;
	TextView textView3;
	TextView textView4;

	public XMLParseAct() {
		super();
		setHiddenActionBar(false);
	}

	@Override
	protected void initWidget(Bundle savedInstanceState) {
		super.initWidget(savedInstanceState);

		textView1 = (TextView) this.findViewById(R.id.textView1);
		textView2 = (TextView) this.findViewById(R.id.textView2);
		textView3 = (TextView) this.findViewById(R.id.textView3);
		textView4 = (TextView) this.findViewById(R.id.textView4);

		button1 = (Button) this.findViewById(R.id.button1);
		button2 = (Button) this.findViewById(R.id.button2);
		button3 = (Button) this.findViewById(R.id.button3);
		button4 = (Button) this.findViewById(R.id.button4);
		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
		button3.setOnClickListener(this);
		button4.setOnClickListener(this);

	}

	@Override
	public void widgetClick(View v) {
		super.widgetClick(v);
		switch (v.getId()) {
			case R.id.button1:
				btn1DomParserClick();
				break;
			case R.id.button2:
				btn2SaxParserClick();
				break;
			case R.id.button3:
				btn3PullParser1Click();
				break;
			case R.id.button4:
				btn4PullParser2Click();
				break;
			default:
				break;
		}
	}

	/**
	 * ***********************************事件方法*************************************************************
	 */

	/**
	 * DOM解析
	 * 1、构造DocumentBuilder解析器
	 * 2、传入xml文件输入流，解析xml文件，返回文档对象
	 * 3、根据文档对象，从内存中检索数据。
	 */
	private void btn1DomParserClick() {
		Log.i(TAG, "btn1Click");
		try {
			// 1:拿到字节流；
			InputStream inputStream = this.getResources().openRawResource(R.raw.raw_data_activity_a08net_parse_xmlparse);
			// 2:得到工厂;
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			// 3:把工厂转化成documentBuilder;
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			// 4:传入xml文件输入流，解析xml文件,返回文档对象
			Document document = documentBuilder.parse(inputStream);
			NodeList nodeList = document.getElementsByTagName("RelativeLayout");
			// 5:根据文档对象，从内存中检索数据。
			Node node = nodeList.item(0);
			NamedNodeMap namedNodeMap = node.getAttributes();

			Node node2 = namedNodeMap.getNamedItem("tools:context");
			textView1.setText(node2.getNodeValue());// .MainActivity

		}
		catch (NotFoundException e) {
			e.printStackTrace();
		}
		catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		catch (SAXException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * SAX解析:
	 * 1. 在继承DefaultHandler的类里面重写需要的回调函数
	 * 2. 创建SAXParser实例
	 * 3. SAXParser实例调用parse方法启动解析
	 * raw中item.xml;
	 * <TextView
	 * android:id="@+id/textView3"
	 * </TextView>
	 */
	private void btn2SaxParserClick() {
		Log.i(TAG, "btn2Click");
		try {
			InputStream inputStream = this.getResources().openRawResource(R.raw.raw_data_activity_a08net_parse_xmlparse);
			// 1:拿到工厂；
			SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
			// 2: 创建SAXParser实例
			SAXParser saxParser = saxParserFactory.newSAXParser();
			// 3:SAXParser实例调用parse方法启动解析
			// 4:在继承DefaultHandler的类里面重写需要的回调函数
			saxParser.parse(inputStream, defaultHandler);
		}
		catch (NotFoundException e) {
			e.printStackTrace();
		}
		catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		catch (SAXException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * SAX用到的接口；
	 */
	DefaultHandler defaultHandler = new DefaultHandler() {
		// 通过计数找到想要的第几个属性;
		int tag = 0;

		@Override
		public void startDocument() throws SAXException {

		}

		@Override
		public void endDocument() throws SAXException {
		}

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			if (localName.equals("TextView")) {
				tag++;
				if (tag == 3) {
					// 写全名：
					textView2.setText(attributes.getValue("android:id"));// @+id/textView3
				}
			}
		}

		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
		}

		@Override
		public void characters(char[] ch, int start, int length) throws SAXException {
		}

	};

	/**
	 * PULL解析1:这种解析不好;
	 * 这种只用在解析xml文件中:数据；
	 * android 默认的加载布局就是用的这个解析加载方式；XmlResourceParser
	 */
	private void btn3PullParser1Click() {
		Log.i(TAG, "btn3Click");
		try {
			// sax【一步一步的解析，随时可以停止，用于数量巨大的xml，操作复杂，采取回调方案，本例是android修改的sax，采取的是标记手法，俗称pull解析】
			// 直接获得xml文件中的资源： this.getResources().getXml(R.xml.pull);
			// 获得TextView节点下的详细信息；
//			this.getResources().getLayout(id)
			XmlResourceParser xmlResourceParser = this.getResources().getXml(R.xml.xml_data_pull_activity_a08net_parse_xmlparse);
			// final XmlResourceParser parser = getResources().getLayout(resource);
			int eventType = xmlResourceParser.getEventType();
			while (true) {
				if (eventType == XmlPullParser.START_DOCUMENT) {

				}
				else if (eventType == XmlPullParser.END_DOCUMENT) {
					break;
				}
				else if (eventType == XmlPullParser.START_TAG) {
					// <TextView
					// android:id="@+id/textView4_1"
					// </TextView>
					if (xmlResourceParser.getName().equals("TextView")) {
						if (xmlResourceParser.getAttributeValue("http://schemas.android.com/apk/res/android", "id").equals("@" + R.id.textView4_1)) {
							textView3.setText(xmlResourceParser.getAttributeValue("http://schemas.android.com/apk/res/android", "text"));// 在xml中的数据pull解析4_1
							break;
						}
					}
				}
				else if (eventType == XmlPullParser.END_TAG) {

				}

				// 让光标走xmlResourceParser.next(),然后获得光标当前位置
				eventType = xmlResourceParser.next();
			}
		}
		catch (NotFoundException e) {
			e.printStackTrace();
		}
		catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * PULL解析2
	 */
	private void btn4PullParser2Click() {
		Log.i(TAG, "btn4Click");
		try {
			InputStream inputStream = this.getResources().openRawResource(R.raw.raw_data_activity_a08net_parse_xmlparse);
			// 1:通过工厂把实例化对象转换成工厂对象；
			XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
			// 2:XmlPullParserFactory实例调用newPullParser方法创建 XmlPullParser解析器实例，
			XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
			// 3:XmlPullParser实例就可以调用getEventType()和next()等方法依次主动提取事件
			// xmlPullParser.setInput(new StringReader(xmlString));
			xmlPullParser.setInput(inputStream, "utf-8");
			// 控制循环；
			boolean b = true;
			// 计数;
			int tag = 0;
			while (b) {
				int eventType = xmlPullParser.next();
				switch (eventType) {
				// 有四个判断就够了;
					case XmlPullParser.START_DOCUMENT:

						break;
					case XmlPullParser.END_DOCUMENT:
						// 到尾了，结束循环；
						b = false;
						break;
					case XmlPullParser.START_TAG:
						if (xmlPullParser.getName().equals("TextView")) {
							tag++;
							if (tag == 4) {
								textView4.setText(xmlPullParser.getAttributeValue("", "android:id"));// @+id/textView4_2
								// 找到后退出；
								b = false;
							}
						}
						break;
					case XmlPullParser.END_TAG:

						break;
					case XmlPullParser.TEXT:

						break;

					default:
						break;
				}
			}
		}
		catch (NotFoundException e) {
			e.printStackTrace();
		}
		catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void setRootView() {
		setContentView(R.layout.activity_a08net_parse_xmlparse);
	}

}
