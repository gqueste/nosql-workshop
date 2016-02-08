package nosql.workshop.batch.mongodb;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCSVParser<T> {

	protected abstract String getFileName();
	protected abstract String[] getHeader();
	private List<T> datas = new ArrayList<>();

	protected CSVFormat getCSVFormat(){
		return CSVFormat.DEFAULT.withHeader(getHeader());
	}

	public AbstractCSVParser<T> read() {

		CSVFormat csvFileFormat = getCSVFormat();
		try {
			FileReader fileReader = new FileReader(getFileName());
			CSVParser csvFileParser = new CSVParser(fileReader, csvFileFormat);
			List<CSVRecord> records = csvFileParser.getRecords();
			if (shouldWeSkipFirstLine()) {
				records.remove(0);
			}
			for (CSVRecord record : records) {
				T row = parseLine(record);
				datas.add(row);
			}
			return this;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public boolean shouldWeSkipFirstLine() {
		return true;
	}

	protected abstract T parseLine(CSVRecord csvRecord);

	public List<T> getDatas() {
		return datas;
	}
}

