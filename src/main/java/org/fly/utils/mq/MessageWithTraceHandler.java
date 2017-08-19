package org.fly.utils.mq;

import java.util.Map;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.fastjson.JSON;
import com.netease.backend.trace.filters.utils.TraceWebUtils;
import com.netease.backend.trace.meta.model.Endpoint;
import com.netease.backend.trace.meta.model.Span;
import com.netease.cloud.nqs.client.Message;
import com.netease.cloud.nqs.client.consumer.MessageHandler;

public abstract class MessageWithTraceHandler extends MQTraceUtil implements MessageHandler{

	@Override
	public boolean handle(Message message) {
		//获取trace内容 
		MQTransInfo info=JSON.parseObject(message.getBody(), MQTransInfo.class);
		Map<String,String> attachments=info.getAttachments();
		boolean consumerSide = false;

		RpcContext context = RpcContext.getContext();
		String spanName = attachments.get(MQTraceUtil.spanName);
		Span span = null;// 本次调用的span
		Endpoint endpoint = new Endpoint(TraceWebUtils.getIPAddress(), TraceWebUtils.getHostName(),
				context.getLocalPort());

		span = buildSpan(consumerSide,spanName,attachments);
		span.setHost(endpoint);
		span.setAppName(trace.getAppName());
		getConcurrent(spanName).incrementAndGet(); // 并发计数
		startInvoke(span, consumerSide);
		boolean finish=false;
		try{
			finish= this.handle(info.getMessage());
		}finally{
			endInvoke(span, consumerSide);
			// 记录并发数据
			int concurrent = getConcurrent(spanName).get(); // 当前并发数
			trace.logConcurrent(span, concurrent);
			// Log span
			trace.logSpan(span);
			getConcurrent(spanName).decrementAndGet(); // 并发计数
		}
		return finish;
	}

	public abstract boolean handle(String message);
}
