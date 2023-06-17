package com.samoilov.dev.telegrambotchatgptconnector.config;

import com.samoilov.dev.telegrambotchatgptconnector.config.properties.ChatGptProperties;
import io.netty.handler.logging.LogLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.ClientCodecConfigurer;
import org.springframework.http.codec.xml.Jaxb2XmlDecoder;
import org.springframework.http.codec.xml.Jaxb2XmlEncoder;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.logging.AdvancedByteBufFormat;

import java.util.function.Consumer;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final ChatGptProperties chatGptProperties;

    private static final String REACTOR_NETTY_HTTP_CLIENT_HTTP_CLIENT = "reactor.netty.http.client.HttpClient";

    @Bean
    public WebClient webClient(@Value("${buffer.size}") int bufferSize) {
        HttpClient httpClient = HttpClient
                .create()
                .wiretap(REACTOR_NETTY_HTTP_CLIENT_HTTP_CLIENT, LogLevel.DEBUG, AdvancedByteBufFormat.TEXTUAL)
                .compress(Boolean.TRUE);

        return WebClient
                .builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .codecs(this.getCodecs(bufferSize))
                .filter(this.logRequest())
                .filter(this.logResponseStatus())
                .baseUrl(chatGptProperties.getUrl())
                .build();
    }

    private ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            log.debug("Request: {} {}", clientRequest.method(), clientRequest.url());
            clientRequest
                    .headers()
                    .forEach((name, values) -> values.forEach(value -> log.debug("{}={}", name, value)));
            return Mono.just(clientRequest);
        });
    }

    private ExchangeFilterFunction logResponseStatus() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            log.debug("Response Status {}", clientResponse.statusCode());
            clientResponse
                    .headers()
                    .asHttpHeaders()
                    .forEach((name, values) -> values.forEach(value -> log.debug("{}={}", name, value)));
            return Mono.just(clientResponse);
        });
    }

    private Consumer<ClientCodecConfigurer> getCodecs(int bufferSize) {
        return c -> {
            c.defaultCodecs().maxInMemorySize(bufferSize * 1024 * 1024);
            c.defaultCodecs().jaxb2Encoder(new Jaxb2XmlEncoder());
            c.defaultCodecs().jaxb2Decoder(new Jaxb2XmlDecoder());
        };
    }

}
