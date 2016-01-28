package com.zsy.frame.sample.control.android.a08net.protocol.soap.helps;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.os.Handler;
import android.os.Message;

/**
 * @description：访问WebService的工具类
 * @author samy
 * @date 2014年8月3日 下午1:04:29
 */
public class WebServiceUtils {
	// 含有3个线程的线程池
	private static final ExecutorService executorService = Executors.newFixedThreadPool(3);
	// 命名空间
	private static final String serviceNameSpace = "http://WebXml.com.cn/";
	// 请求URL
	public static final String serviceURL = "http://www.webxml.com.cn/WebServices/WeatherWebService.asmx";

	// 调用省或者直辖市的方法(获得支持的省份或直辖市)
	public static final String getSupportProvince = "getSupportProvince";
	// 调用方法(获得支持的城市)
	public static final String getSupportCity = "getSupportCity";
	// 调用城市的方法(需要带参数)
	public static final String getWeatherbyCityName = "getWeatherbyCityName";
	/**
	 * ps:还得添加个网络请求超时处理；
	 */

	/**
	 * @description：服务器地址,回调方法获取天气信息；
	 * @author samy
	 * @date 2014年8月3日 下午1:44:30
	 */
	public static void callWebService(final String url, final String methodName, HashMap<String, String> properties, final WebServiceCallBack webServiceCallBack) {
		// 用于子线程与主线程通信的Handler
		final Handler mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				// 第七步：解析返回数据;通过回调去解析数据；将返回值回调到callBack的参数中;
				webServiceCallBack.callBack((SoapObject) msg.obj);
			}

		};

		// 第一：实例化SoapObject 对象，指定webService的命名空间（从相关WSDL文档中可以查看命名空间），以及调用方法名称
		SoapObject requestSoapObject = new SoapObject(serviceNameSpace, methodName);
		// 第二步：假设方法有参数的话,设置调用方法参数
		if (properties != null) {
			for (Iterator<Map.Entry<String, String>> it = properties.entrySet().iterator(); it.hasNext();) {
				Map.Entry<String, String> entry = it.next();
				// soapObject.addProperty("参数", "参数值");调用的方法参数与参数值（根据具体需要可选可不选）
				requestSoapObject.addProperty(entry.getKey(), entry.getValue());
			}
		}

		// 第三步：设置SOAP请求信息(参数部分为SOAP协议版本号，与你要调用的webService中版本号一致)；实例化SoapSerializationEnvelope，传入WebService的SOAP协议的版本号
//		final SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		final SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
		// 设置是否调用的是.Net开发的WebService
		soapEnvelope.setOutputSoapObject(requestSoapObject);
		soapEnvelope.dotNet = true;
		// 等有返回时才去设置返回请求格式；
		// soapEnvelope.bodyOut=requestSoapObject;
		// 第四步：注册Envelope；加密处理；
		// (new MarshalBase64()).register(soapEnvelope);

		// 开启线程池去访问WebService
		executorService.submit(new Runnable() {
			@Override
			public void run() {
				SoapObject resultSoapObject = null;
				try {
					// 第五步：构建传输对象，并指明WSDL文档URL
					// 创建HttpTransportSE对象，传递WebService服务器地址; Android传输对象
					HttpTransportSE httpTransportSE = new HttpTransportSE(url);
					httpTransportSE.debug = true;
					// 第六步：调用WebService(其中参数为1：命名空间+方法名称，2：Envelope对象):
					httpTransportSE.call(serviceNameSpace + methodName, soapEnvelope);

					if (soapEnvelope.getResponse() != null) {
						// 获取服务器响应返回的SoapObject
						resultSoapObject = (SoapObject) soapEnvelope.bodyIn;
						// String xml = transport.responseDump.toString();
						// SoapMessage msg = SoapMessage.getRemoteAuthMessage(xml);
					}
				}
				catch (IOException e) {
					e.printStackTrace();
				}
				catch (XmlPullParserException e) {
					e.printStackTrace();
				}
				finally {
					// 将获取的消息利用Handler发送到主线程;下面对结果进行解析，结构类似json对象
					mHandler.sendMessage(mHandler.obtainMessage(0, resultSoapObject));
				}
			}
		});
	}

	public interface WebServiceCallBack {
		public void callBack(SoapObject result);
	}

}
