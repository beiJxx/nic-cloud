package com.nic.cloud.config;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 缓存请求参数，解决body只能读一次问题
 *
 * @author james
 * @date 2021/2/18 14:18
 */
public class RecorderServerHttpRequestDecorator extends ServerHttpRequestDecorator {

	private final List<DataBuffer> dataBuffers = new ArrayList<>();

	public RecorderServerHttpRequestDecorator(ServerHttpRequest delegate) {
		super(delegate);
		super.getBody().map(dataBuffer -> {
			dataBuffers.add(dataBuffer);
			return dataBuffer;
		}).subscribe();
	}

	@Override
	public Flux<DataBuffer> getBody() {
		return copy();
	}

	private Flux<DataBuffer> copy() {
		return Flux.fromIterable(dataBuffers)
				.map(buf -> buf.factory().wrap(buf.asByteBuffer()));
	}

}