/**
 * 
 */
package org.fly.utils.mq;

/**
 * MQ消费者相关的配置
 *  
 */
public class MQConsumerConfig {

	private int prefetchCount;

	/**
	 * @return the prefetchCount
	 */
	public int getPrefetchCount() {
		return prefetchCount;
	}

	/**
	 * @param prefetchCount
	 *            the prefetchCount to set
	 */
	public void setPrefetchCount(int prefetchCount) {
		this.prefetchCount = prefetchCount;
	}
}
