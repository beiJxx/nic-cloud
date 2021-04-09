package com.nic.cloud.config;

/**
 * Description:
 *
 * @author james
 * @date 2021/3/11 16:01
 */
public class MqConstant {

	/**
	 * top
	 */
	public static class Topic {

		/**
		 * 稿件录入
		 */
		public static final String ES_USER_IMPORT = "TESTABC_TOPIC";

	}


	/**
	 * TAG
	 */
	public static class Tag {

		public static final String ES_USER_IMPORT_TAG_INSERT = "TESTABC_TAG1";
		public static final String ES_USER_IMPORT_TAG_UPDATE = "TESTABC_TAG2";
		public static final String ES_USER_IMPORT_TAG_DELETE = "TESTABC_TAG3";
	}


	/**
	 * consumeGroup 消费者
	 */
	public static class ConsumeGroup {

		public static final String ES_USER_IMPORT = "TESTABC_GROUP";


	}
}
