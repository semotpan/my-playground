package my.samples.billionrows;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "city_weather")
public class CityWeather {

    private String city;
    private Double temperature;
    private Instant createdOn;

}
