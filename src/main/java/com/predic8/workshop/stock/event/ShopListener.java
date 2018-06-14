package com.predic8.workshop.stock.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.predic8.workshop.stock.dto.Stock;
import io.prometheus.client.Counter;
import io.prometheus.client.DoubleAdder;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@Service
public class ShopListener {
	private final ObjectMapper mapper;
	private final Map<String, Stock> articles;
	private final NullAwareBeanUtilsBean beanUtils;


    private Counter stockCounter;
    private io.prometheus.client.Gauge stockGauge;
    private DoubleAdder stockAdder;

	public ShopListener(ObjectMapper mapper, Map<String, Stock> articles, NullAwareBeanUtilsBean beanUtils) {
		this.mapper = mapper;
        this.articles = articles;
        this.beanUtils = beanUtils;
        this.stockCounter = Counter.build().name("Bestand").help("Lagerbestand").register();
        this.stockAdder = new DoubleAdder();

	}

	@KafkaListener(topics = "shop")
	public void listen(Operation op) throws InvocationTargetException, IllegalAccessException {
		if (op.getBo() == null || op.getObject() == null) return;
		op.logReceive();
		if(op.getBo().equals("article")){

            Stock stock = mapper.convertValue(op.getObject(), Stock.class);
			switch(op.getAction()){
                case "patch":

                    break;
                case "update":
                    Stock stockToUpdate = articles.get(stock.getUuid());
                	if(stockToUpdate == null) return;
                    stockCounter.inc(1);
                    stockAdder.add(stockToUpdate.getQuantity() == null ? 0.0 : stockToUpdate.getQuantity()-(stock.getQuantity() == null ? 0.0 : stock.getQuantity()));
                    beanUtils.copyProperties(stockToUpdate, stock);
                	articles.put(stockToUpdate.getUuid(), stock);
                	break;
                case "create":
				    articles.put(stock.getUuid(), stock);
				    stockCounter.inc(1);
                    stockAdder.add(stock.getQuantity() == null ? 0.0 : stock.getQuantity());
				    break;
                case "delete":
                    articles.remove(stock.getUuid());
                    stockCounter.inc(1);
                    stockAdder.add(stock.getQuantity() == null ? 0.0 : -stock.getQuantity());
                    break;
		    }
		}


	}
}