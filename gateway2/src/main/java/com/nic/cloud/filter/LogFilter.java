package com.nic.cloud.filter;

import com.nic.cloud.config.RecorderServerHttpRequestDecorator;
import com.nic.cloud.util.GatewayUtil;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Description:
 *
 * @author james
 * @date 2021/2/18 14:13
 */
@Slf4j
@Component
public class LogFilter implements GlobalFilter, Ordered {

	private static final String REQUEST_PREFIX = "\n--------------------------------- Request  Info -----------------------------";

	private static final String REQUEST_TAIL = "\n-----------------------------------------------------------------------------";

	private static final String RESPONSE_PREFIX = "\n--------------------------------- Response Info -----------------------------";

	private static final String RESPONSE_TAIL = "\n-------------------------------------------------------------------------->>>";

//	private static final String REQUEST_PREFIX = "Request Info [ ";
//
//	private static final String REQUEST_TAIL = " ]";
//
//	private static final String RESPONSE_PREFIX = "Response Info [ ";
//
//	private static final String RESPONSE_TAIL = " ]";

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		StringBuilder normalMsg = new StringBuilder();
		ServerHttpRequest request = exchange.getRequest();
		RecorderServerHttpRequestDecorator requestDecorator = new RecorderServerHttpRequestDecorator(request);
		InetSocketAddress address = requestDecorator.getRemoteAddress();
		String ipAddress = GatewayUtil.getIpAddress(request);
		HttpMethod method = requestDecorator.getMethod();
		URI url = requestDecorator.getURI();
		HttpHeaders headers = requestDecorator.getHeaders();
		Flux<DataBuffer> body = requestDecorator.getBody();
		//读取requestBody传参
		AtomicReference<String> requestBody = new AtomicReference<>("");
		body.subscribe(buffer -> {
			CharBuffer charBuffer = StandardCharsets.UTF_8.decode(buffer.asByteBuffer());
			requestBody.set(charBuffer.toString());
		});
		String requestParams = requestBody.get();
		normalMsg.append(REQUEST_PREFIX);
		normalMsg.append("\nheader=").append(headers);
		normalMsg.append("\nparams=").append(requestParams);
		normalMsg.append("\naddress=").append(address.getHostName() + address.getPort());
		normalMsg.append("\nremote=").append(ipAddress);
		normalMsg.append("\nmethod=").append(method.name());
		normalMsg.append("\nurl=").append(url.getPath());
		normalMsg.append(REQUEST_TAIL);

		ServerHttpResponse response = exchange.getResponse();

		DataBufferFactory bufferFactory = response.bufferFactory();
		normalMsg.append(RESPONSE_PREFIX);
		ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(response) {
			@Override
			public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
				if (body instanceof Flux) {
					Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;
					return super.writeWith(fluxBody.map(dataBuffer -> {
						// probably should reuse buffers
						byte[] content = new byte[dataBuffer.readableByteCount()];
						dataBuffer.read(content);
						String responseResult = new String(content, Charset.forName("UTF-8"));
						normalMsg.append("\nstatus=").append(this.getStatusCode());
						normalMsg.append("\nheader=").append(this.getHeaders());
						normalMsg.append("\nresponseResult=").append(responseResult);
						normalMsg.append(RESPONSE_TAIL);
						log.info(normalMsg.toString());
						return bufferFactory.wrap(content);
					}))
							.doFinally(s -> {
								log.info("------------------------------------------------");
								log.info(Objects.requireNonNull(RequestContextHolder.currentRequestAttributes().getAttribute("username", 0)).toString());
								log.info("------------------------------------------------");
//								UserInfoContext.remove();
							});
				}
				return super.writeWith(body); // if body is not a flux. never got there.
			}
		};
		return chain.filter(exchange.mutate().request(requestDecorator).response(decoratedResponse).build());
	}

	@Override
	public int getOrder() {
		return Ordered.HIGHEST_PRECEDENCE + 1;
	}
}
