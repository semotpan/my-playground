package io.example;

import com.opencsv.CSVReader;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.io.FileReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@SpringBootApplication
@RequiredArgsConstructor
public class SpringOpencsvtodbApplication implements CommandLineRunner {

	private final JdbcTemplate jdbcTemplate;

	public static void main(String[] args) {
		SpringApplication.run(SpringOpencsvtodbApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Set<String> facilityPostalCodes = new HashSet<>();
		facilityPostalCodes.add("ABAB34");
		facilityPostalCodes.add("ABAB53");

		CSVReader reader = new CSVReader(new FileReader("postalcodes.csv"));
		List<String[]> rows = reader.readAll();
		Set<PostalCode> codes = new HashSet<>(rows.size());
		for (int i = 1; i < rows.size(); i++) {
			PostalCode postalCode = new PostalCode(rows.get(i)[0], rows.get(i)[1]);
			if (facilityPostalCodes.contains(postalCode.toString())) {
				continue;
			}

			codes.add(postalCode);
		}

		Iterator<PostalCode> codeIterator = codes.iterator();

		List<Patient> patients = jdbcTemplate.query("select patientNumber, postalCode1, postalCode2 from cris.patient",
				(rs, rowNum) -> new Patient(
                rs.getInt("patientNumber"),
                rs.getString("postalCode1"),
                rs.getString("postalCode2")));

		String SQL = "update cris.patient set postalcode1 = ?, postalCode2=? where patientNumber = ?";
		for (Patient patient: patients) {

			if (!codeIterator.hasNext()) {
				codeIterator = codes.iterator();
			}

			PostalCode postalCode = codeIterator.next();
			// update db
			jdbcTemplate.update(SQL, patient.patientNumber, postalCode.postalCode1, postalCode.postalCode2);
			System.out.println("Updated Record with ID = " + patient.patientNumber + " postalcode " + postalCode);
		}

		System.out.println(codes);
	}

	@Value
	static class PostalCode {

		String postalCode1;
		String postalCode2;

		@Override
		public String toString() {
			return postalCode1 + postalCode2;
		}
	}

	@Value
	static class Patient {

		Integer patientNumber;
		String postalCode1;
		String postalCode2;

	}
}
