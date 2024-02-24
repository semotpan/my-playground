package my.sample.openfeign.osm;

import lombok.Setter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Address decoding uses the openfeign to communicate with OSM Nominatim
 */
@FeignClient(
        name = "nominatim-osm",
        url = "${feign.client.config.nominatim-osm.url}",
        configuration = FeignConfig.class
)
public interface AddressDecoder {

    @RequestMapping(method = GET, path = "/search?format=json", consumes = APPLICATION_JSON_VALUE)
    List<Coordinate> decode(@RequestParam("q") PostalCode postalCode);

    final class PostalCode {

        private final String value;

        public PostalCode(String value) {
            this.value = requireNonNull(value, "value cannot be null");
        }

        @Override
        public String toString() {
            return value;
        }
    }

    @Setter
    class Coordinate {
        String lat;
        String lon;

        @Override
        public String toString() {
            return String.format("%s,%s", lon, lat);
        }
    }
}
