package nosql.workshop.batch.mongodb;

import nosql.workshop.model.Town;
import org.apache.commons.csv.CSVRecord;

/**
 * Created by quentin on 08/02/16.
 */
public class TownParser extends AbstractCSVParser<Town> {


	public static final String OBJECTID = "OBJECTID";
	public static final String TOWNNAME = "TOWNNAME";
	public static final String TOWNNAME_SUGGEST = "TOWNNAME_SUGGEST";
	public static final String POSTCODE = "POSTCODE";
	public static final String PAYS = "PAYS";
	public static final String REGION = "REGION";
	public static final String X = "X";
	public static final String Y = "Y";

	private String filename;

	public TownParser(String filename) {
		this.filename = filename;
	}

	@Override
	protected String getFileName() {
		return filename;
	}

	@Override
	protected String[] getHeader() {
		return new String[0];
	}

	@Override
	protected Town parseLine(CSVRecord csvRecord) {
		return null;
	}
}
