package nosql.workshop.batch.mongodb;

import nosql.workshop.model.Activite;
import org.apache.commons.csv.CSVRecord;

public class ActiviteParser extends AbstractCSVParser<Activite> {

	public static final String INSEE = "Code INSEE";
	public static final String NOM_COMMUNE = "Nom de la commune";
	public static final String NUM_FICHE_EQUIPEMENT = "Numéro de la fiche équipement";
	public static final String NB_EQUIPEMENT_IDENTIQUE = "Nombre d'équipements identiques";
	public static final String ACTIVITE_CODE = "Activité code";
	public static final String ACTIVITE_LIBELLE = "Activité libellé";
	public static final String ACTIVITE_PRATICABLE = "Activité praticable";
	public static final String ACTIVITE_PRATIQUEE = "Activité pratiquée";
	public static final String DANS_SALLE_SPE = "Dans salle spécialisable";
	public static final String NIVEAU_PRATIQUE = "Niveau effectivement pratiqué";

	private String filename;

	public ActiviteParser(String filename) {
		this.filename = filename;
	}

	@Override
	protected String getFileName() {
		return filename;
	}

	@Override
	protected String[] getHeader() {
		return new String[]{
				INSEE,
				NOM_COMMUNE,
				NUM_FICHE_EQUIPEMENT,
				NB_EQUIPEMENT_IDENTIQUE,
				ACTIVITE_CODE,
				ACTIVITE_LIBELLE,
				ACTIVITE_PRATICABLE,
				ACTIVITE_PRATIQUEE,
				DANS_SALLE_SPE,
				NIVEAU_PRATIQUE,
		};
	}

	@Override
	protected Activite parseLine(CSVRecord csvRecord) {
		if (csvRecord == null) {
			return null;
		}
		return null;
	}
}
