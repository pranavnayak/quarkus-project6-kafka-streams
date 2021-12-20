package org.popins.dev;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.time.Instant;

import io.smallrye.mutiny.Multi;
import io.smallrye.reactive.messaging.kafka.Record;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.jboss.logging.Logger;

public class ValuesProducer {

	private static final Logger LOG = Logger.getLogger(ValuesProducer.class);
	private Random random = new Random();
	private List<WeatherStation>  stations = List.of(
			new WeatherStation(1, "Bengalurur", 27),
			new WeatherStation(2, "Mumbai", 37),
			new WeatherStation(3, "Delhi", 42),
			new WeatherStation(4, "Kokata", 39),
			new WeatherStation(5, "Chennai", 45),
			new WeatherStation(6, "Pune", 29),
			new WeatherStation(7, "Ahemadabad", 32),
			new WeatherStation(8, "Mysore", 26),
			new WeatherStation(9, "hubli", 35));
	
	 @Outgoing("temperature-values")
	 public Multi<Record<Integer, String>> generate() {
	        return Multi.createFrom()
	        		.ticks()
	        		.every(Duration.ofMillis(500))
	                .onOverflow().drop()
	                .map(tick -> {
	                    WeatherStation station = stations.get(random.nextInt(stations.size()));
	                    double temperature = BigDecimal.valueOf(random.nextGaussian() * 15 + station.getAverageTemprature())
	                            .setScale(1, RoundingMode.HALF_UP)
	                            .doubleValue();

	                    LOG.infov("station: {0}, temperature: {1}", station.name, temperature);
	                    return Record.of(station.id, Instant.now() + ";" + temperature);
	                });
	    }
	
	 @Outgoing("weather-stations")
	    public Multi<Record<Integer, String>> weatherStations() {
	        return Multi.createFrom().items(stations.stream()
	                .map(s -> Record.of(s.id, "{ \"id\" : " + s.id + ", \"name\" : \"" + s.name + "\" }"))
	        );
	    }
	
	private  class WeatherStation{
		 private int id;
		 private String name;
		 private int averageTemprature;
		 
		public WeatherStation(int id, String name, int averageTemprature) {
			super();
			this.id = id;
			this.name = name;
			this.averageTemprature = averageTemprature;
		}

		public int getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		public int getAverageTemprature() {
			return averageTemprature;
		}

		public void setId(int id) {
			this.id = id;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setAverageTemprature(int averageTemprature) {
			this.averageTemprature = averageTemprature;
		}
		 
		 
	}
}
