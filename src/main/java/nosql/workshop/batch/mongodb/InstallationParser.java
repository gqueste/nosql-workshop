package nosql.workshop.batch.mongodb;

import nosql.workshop.model.Installation;
import org.apache.commons.csv.CSVRecord;

public class InstallationParser extends AbstractCSVParser<Installation> {

	public static final String NOM_USUEL_INSTALLATION = "Nom usuel de l'installation";
	public static final String NUM_INSTALLATION = "Numéro de l'installation";
	public static final String NOM_COMMUNE = "Nom de la commune";
	public static final String INSEE = "Code INSEE";
	public static final String POSTAL = "Code postal";
	public static final String LIEU_DIT = "Nom du lieu dit";
	public static final String NUM_VOIE = "Numero de la voie";
	public static final String NOM_VOIE = "Nom de la voie";
	public static final String LOCATION = "location";
	public static final String LONGITUDE = "Longitude";
	public static final String LATITUDE = "Latitude";
	public static final String ACCES_MOBILITE = "Aucun aménagement d'accessibilité";
	public static final String ACCES_MOBILITE_REDUITE = "Accessibilité handicapés à mobilité réduite";
	public static final String ACCES_HANDICAP_SENSORIEL = "Accessibilité handicapés sensoriels";
	public static final String EMPRISE_FONCIERE = "Emprise foncière en m2";
	public static final String GARDIEN = "Gardiennée avec ou sans logement de gardien";
	public static final String MULTI_COMMUNE = "Multi commune";
	public static final String NB_PLACE_TOTALE_PARKING = "Nombre total de place de parking";
	public static final String NB_PLACE_TOTALE_HANDICAP_PARKING = "Nombre total de place de parking handicapés";
	public static final String INSTALLATION_PARTICULIERE = "Installation particulière";
	public static final String DESSERTE_METRO = "Desserte métro";
	public static final String DESSERTE_BUS = "Desserte bus";
	public static final String DESSERTE_TRAM = "Desserte Tram";
	public static final String DESSERTE_TRAIN = "Desserte train";
	public static final String DESSERTE_BATEAU = "Desserte bateau";
	public static final String DESSERTE_AUTRE = "Desserte autre";
	public static final String NB_TOTAL_EQUI_SPORT = "Nombre total d'équipements sportifs";
	public static final String NB_TOTAL_FICHE_EQUIPEMENT = "Nombre total de fiches équipements";
	public static final String DATE_MAJ_FICHE_INSTALLATION = "Date de mise à jour de la fiche installation";

	private String filename;

	public InstallationParser(String filename) {
		this.filename = filename;
	}

	@Override
	protected String getFileName() {
		return filename;
	}

	@Override
	protected String[] getHeader() {
		return new String[]{
				NOM_USUEL_INSTALLATION,
				NUM_INSTALLATION,
				NOM_COMMUNE,
				INSEE,
				POSTAL,
				LIEU_DIT,
				NUM_VOIE,
				NOM_VOIE,
				LOCATION,
				LONGITUDE,
				LATITUDE,
				ACCES_MOBILITE,
				ACCES_MOBILITE_REDUITE,
				ACCES_HANDICAP_SENSORIEL,
				EMPRISE_FONCIERE,
				GARDIEN,
				MULTI_COMMUNE,
				NB_PLACE_TOTALE_PARKING,
				NB_PLACE_TOTALE_HANDICAP_PARKING,
				INSTALLATION_PARTICULIERE,
				DESSERTE_METRO,
				DESSERTE_BUS,
				DESSERTE_TRAM,
				DESSERTE_TRAIN,
				DESSERTE_BATEAU,
				DESSERTE_AUTRE,
				NB_TOTAL_EQUI_SPORT,
				NB_TOTAL_FICHE_EQUIPEMENT,
				DATE_MAJ_FICHE_INSTALLATION,
		};
	}

	@Override
	protected Installation parseLine(CSVRecord csvRecord) {
		if(csvRecord==null){
			return null;
		}
		return null;
	}
}
