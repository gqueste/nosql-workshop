package nosql.workshop.batch.mongodb;

import nosql.workshop.model.Equipement;
import org.apache.commons.csv.CSVRecord;

/**
 * Created by quentin on 08/02/16.
 */
public class EquipementParser extends AbstractCSVParser<Equipement> {

	public static final String INSEE = "ComInsee";
	public static final String LIB = "ComLib";
	public static final String NUM_INSTALL = "InsNumeroInstall";
	public static final String INS_NOM = "InsNom";
	public static final String EQUI_ID = "EquipementId";
	public static final String EQUI_NOM = "EquNom";
	public static final String EQUI_NOM_BATI = "EquNomBatiment";
	public static final String EQUI_TYPE_LIB = "EquipementTypeLib";
	public static final String EQUI_FICHE = "EquipementFiche";
	public static final String FAMILLE_FICHE_LIB = "FamilleFicheLib";
	public static final String GESTION_TYPE_PROPRIO_PRINC = "GestionTypeProprietairePrincLib";
	public static final String GESTION_TYPE_GESTIO_PRINC = "GestionTypeGestionnairePrincLib";
	public static final String GESTION_TYPE_PROPRIO_SEC = "GestionTypeProprietaireSecLib";
	public static final String GESTION_TYPE_GESTIO_SEC = "GestionTypeGestionnaireSecLib";
	public static final String EQU_GESTION_DSP = "EquGestionDSP";
	public static final String EQU_DOUCHE = "EquDouche";
	public static final String EQU_ECLAIRAGE = "EquEclairage";
	public static final String EQU_ERP_CTS = "EquErpCTS";
	public static final String EQU_ERP_REF = "EquErpREF";
	public static final String EQU_ERP_L = "EquErpL";
	public static final String EQU_ERP_N = "EquErpN";
	public static final String EQU_ERP_O = "EquErpO";
	public static final String EQU_ERP_OA = "EquErpOA";
	public static final String EQU_ERP_P = "EquErpP";
	public static final String EQU_ERP_PA = "EquErpPA";
	public static final String EQU_ERP_R = "EquErpR";
	public static final String EQU_ERP_RPE = "EquErpRPE";
	public static final String EQU_ERP_SG = "EquErpSG";
	public static final String EQU_ERP_X = "EquErpX";
	public static final String EQU_ERP_CATEGORIE = "EquErpCategorie";
	public static final String EQU_NB_IDENTIQUE = "EquNbEquIdentique";
	public static final String EQU_ANNEE_SERVICE = "EquAnneeService";
	public static final String ANNEE_SERVICE_LIB = "AnneeServiceLib";
	public static final String EQU_NB_PLACE_TRIBUNE = "EquNbPlaceTribune";
	public static final String NATURE_SOL_LIB = "NatureSolLib";
	public static final String NATURE_LIBELLE = "NatureLibelle";
	public static final String EQU_HAUTEUR_EVOLUTION = "EquHauteurEvolution";
	public static final String EQU_LONGUEUR_EVOLUTION = "EquLongueurEvolution";
	public static final String EQU_LARGEUR_EVOLUTION = "EquLargeurEvolution";
	public static final String EQU_SURFACE_EVOLUTION = "EquSurfaceEvolution";
	public static final String EQU_HAUTEUR_SURFACE_EVO = "EquHauteurSurfaceEvo";
	public static final String EQU_NB_COULOIR_PISTE = "EquNbCouloirPiste";
	public static final String EQU_NB_VESTIAIRE_SPO = "EquNbVestiaireSpo";
	public static final String EQU_VESTIAIRE_SPO_CHAUFFE = "EquVestiaireSpoChauffe";
	public static final String EQU_NB_VESTIAIRE_ARBITRE = "EquNbVestiaireArbitre";
	public static final String EQU_SANITAIRE_SPORTIF = "EquSanitaireSportif";
	public static final String EQU_SANITAIRE_PUBLIC = "EquSanitairePublic";
	public static final String EQU_OUVERT_SAISON = "EquOuvertSaison";
	public static final String EQU_PROXIMITE = "EquProximite";
	public static final String EQU_SONO = "EquSono";
	public static final String EQU_TABLEAU_FIXE = "EquTableauFixe";
	public static final String EQU_CHRONO = "EquChrono";
	public static final String EQU_AMENAGEMENT_AUCUN = "EquAmenagementAucun";
	public static final String EQU_UTIL_SCOLAIRE = "EquUtilScolaire";
	public static final String EQU_UTIL_CLUB = "EquUtilClub";
	public static final String EQU_UTIL_AUTRE = "EquUtilAutre";
	public static final String EQU_UTIL_INDIVIDUEL = "EquUtilIndividuel";
	public static final String EQU_UTIL_PERFORMANCE = "EquUtilPerformance";
	public static final String EQU_UTIL_FORMATION = "EquUtilFormation";
	public static final String EQU_UTIL_RECREATION = "EquUtilRecreation";
	public static final String EQU_DATE_DERNIER_TRAVAUX_REALISE = "EquDateDernierTravauxReal";
	public static final String ANNEE_TRAVAUX_REAL_LIBELLE = "AnneeTravauxRealLibelle";
	public static final String EQU_DATE_DERNIER_TRAVAUX_AUCUN = "EquDateDernierTravauxAucun";
	public static final String EQU_TRAVAUX_REAL_CONFORMITE = "EquTravauxRealConformite";
	public static final String EQU_TRAVAUX_REAL_NORME = "EquTravauxRealNorme";
	public static final String EQU_TRAVAUX_REAL_USAGER = "EquTravauxRealUsager";
	public static final String EQU_TRAVAUX_REAL_DEGRADATION = "EquTravauxRealDegradation";
	public static final String EQU_TRAVAUX_REAL_VETUSTE = "EquTravauxRealVetuste";
	public static final String EQU_ACCES_HANDIM_AIRE = "EquAccesHandimAire";
	public static final String EQU_ACCES_HANDIM_TRIBUNE = "EquAccesHandimTribune";
	public static final String EQU_ACCES_HANDIM_VESTIAIRE = "EquAccesHandimVestiaire";
	public static final String EQU_ACCES_HANDIM_SANI_SPO = "EquAccesHandimSaniSpo";
	public static final String EQU_ACCES_HANDIM_SANI_PUB = "EquAccesHandimSaniPub";
	public static final String EQU_ACCES_HANDIM_AUCUN = "EquAccesHandimAucun";
	public static final String EQU_ACCES_HANDIS_AUCUN = "EquAccesHandisAucun";
	public static final String EQU_ACCES_HANDIS_AIRE = "EquAccesHandisAire";
	public static final String EQU_ACCES_HANDIS_TRIBUNE = "EquAccesHandisTribune";
	public static final String EQU_ACCES_HANDIS_VESTIAIRE = "EquAccesHandisVestiaire";
	public static final String EQU_ACCES_HANDIS_SANI_SPO = "EquAccesHandisSaniSpo";
	public static final String EQU_ACCES_HANDIS_SANI_PUB = "EquAccesHandisSaniPub";
	public static final String EQU_ACCUEIL_CLUB = "EquAccueilClub";
	public static final String EQU_ACCUEIL_SALLE = "EquAccueilSalle";
	public static final String EQU_ACCUEIL_BUVETTE = "EquAccueilBuvette";
	public static final String EQU_ACCUEIL_DOPAGE = "EquAccueilDopage";
	public static final String EQU_ACCUEIL_MEDIC = "EquAccueilMedic";
	public static final String EQU_ACCUEIL_INFIRMERIE = "EquAccueilInfirmerie";
	public static final String EQU_ACCUEIL_BUREAU = "EquAccueilBureau";
	public static final String EQU_ACCUEIL_RECEPTION = "EquAccueilReception";
	public static final String EQU_ACCUEIL_LOCAL_RANGEMENT = "EquAccueilLocalRangement";
	public static final String EQU_ACCUEIL_AUTRE = "EquAccueilAutre";
	public static final String EQU_ACCUEIL_AUCUN = "EquAccueilAucun";
	public static final String EQU_CHAUFFAGE_NON = "EquChauffageNon";
	public static final String EQU_CHAUFFAGE_FUEL = "EquChauffageFuel";
	public static final String EQU_CHAUFFAGE_GAZ = "EquChauffageGaz";
	public static final String EQU_CHAUFFAGE_ELEC = "EquChauffageElectricite";
	public static final String EQU_CHAUFFAGE_SOLAIRE = "EquChauffageSolaire";
	public static final String EQU_CHAUFFAGE_AUTRE = "EquChauffageAutre";
	public static final String EQU_CONFORT_SAUNA = "EquConfortSauna";
	public static final String EQU_CONFORT_BAIN_BOUILLONANT = "EquConfortBainBouillonant";
	public static final String EQU_CONFORT_BAIN_VAPEUR = "EquConfortBainVapeur";
	public static final String EQU_CONFORT_SOLARIUM = "EquConfortSolarium";
	public static final String EQU_CONFORT_AUTRE = "EquConfortAutre";
	public static final String EQU_CONFORT_AUCUN = "EquConfortAucun";
	public static final String EQU_DEMARCHE_HQE = "EquDemarcheHQE";
	public static final String EQU_SAE_NB_COULOIR = "EquSaeNbCouloir";
	public static final String EQU_SAE_HAUTEUR = "EquSaeHauteur";
	public static final String EQU_SAE_SURFACE = "EquSaeSurface";
	public static final String EQU_NATURE_SIGNAL = "EquNatureSignal";
	public static final String EQU_NATURE_ALERT = "EquNatureAlert";
	public static final String EQU_NATURE_AC_PUB_PED = "EquNatureAcPubPed";
	public static final String EQU_NATURE_AC_PUB_ROUT = "EquNatureAcPubRout";
	public static final String EQU_NATURE_AC_PUB_MEC = "EquNatureAcPubMec";
	public static final String EQU_NATURE_AC_PUB_NAU = "EquNatureAcPubNau";
	public static final String EQU_NATURE_AC_SEC_PED = "EquNatureAcSecPed";
	public static final String EQU_NATURE_AC_SEC_ROUT = "EquNatureAcSecRout";
	public static final String EQU_NATURE_AC_SEC_MEC = "EquNatureAcSecMec";
	public static final String EQU_NATURE_AC_SEC_NAU = "EquNatureAcSecNau";
	public static final String EQU_NATURE_LOC_TEC = "EquNatureLocTec";
	public static final String EQU_NATURE_LOC_PED = "EquNatureLocPed";
	public static final String EQU_NATURE_AUTORISE = "EquNatureAutorise";
	public static final String EQU_NATURE_PDESI = "EquNaturePDESI";
	public static final String EQU_IP_NATURE_SITUATIONLIB = "EquipNatureSituationLib";
	public static final String EQU_NATURE_SE_VOIES = "EquNatureSEVoies";
	public static final String EQU_NATURE_CLASS_FEDE_MINI = "EquNatureClassFedeMini";
	public static final String EQU_NATURE_CLASS_FEDE_MAXI = "EquNatureClassFedeMaxi";
	public static final String EQU_NATURE_ES_TOUR = "EquNatureESTour";
	public static final String EQU_NATURE_AE_TREUIL = "EquNatureAETreuil";
	public static final String EQU_NATURE_SK_TOTAL_REMONTEE = "EquNatureSKTotalRemontee";
	public static final String EQUIPEMENT_TIR_10 = "EquipementTir10";
	public static final String EQUIPEMENT_TIR_25 = "EquipementTir25";
	public static final String EQUIPEMENT_TIR_50 = "EquipementTir50";
	public static final String EQUIPEMENT_TIR_100 = "EquipementTir100";
	public static final String EQUIPEMENT_TIR_200 = "EquipementTir200";
	public static final String EQUIPEMENT_TIR_300 = "EquipementTir300";
	public static final String EQUIPEMENT_TIR_PLATEAU = "EquipementTirPlateau";
	public static final String EQUIPEMENT_TIR_AUTRE = "EquipementTirAutre";
	public static final String EQU_ATH_DEV = "EquAthDev";
	public static final String EQU_ATH_LONG_LIGNE_DROITE = "EquAthLongLigneDroite";
	public static final String EQU_ATH_NB_COULOIR_LIGNE = "EquAthNbCouloirLigne";
	public static final String EQU_ATH_NB_COULOIR_HORS_LIGNE = "EquAthNbCouloirHorsLigne";
	public static final String EQU_ATH_RIVIERE = "EquAthRiviere";
	public static final String EQU_NAT_SURV = "EquNatSurv";
	public static final String EQU_ATH_NB_SAUT_TOTAL = "EquAthNbSautTotal";
	public static final String EQU_ATH_NB_SAUT_HAUTEUR = "EquAthNbSautHauteur";
	public static final String EQU_ATH_NB_SAUT_LONGUEUR = "EquAthNbSautLongueur";
	public static final String EQU_ATH_NB_SAUT_TRIPLE = "EquAthNbSautTriple";
	public static final String EQU_ATH_NB_SAUT_PERCHE = "EquAthNbSautPerche";
	public static final String EQU_ATH_NB_LANCER_TOTAL = "EquAthNbLancerTotal";
	public static final String EQU_ATH_NB_POIDS = "EquAthNbPoids";
	public static final String EQU_ATH_NB_DISQUE = "EquAthNbDisque";
	public static final String EQU_ATH_NB_JAVELOT = "EquAthNbJavelot";
	public static final String EQU_ATH_NB_MARTEAU = "EquAthNbMarteau";
	public static final String EQU_ATH_NB_MARTEAU_MIXTE = "EquAthNBMarteauMixte";
	public static final String EQU_NAT_FORME_LIB = "EquNatFormeLib";
	public static final String EQU_NAT_LONGUEUR_BASSIN = "EquNatLongueurBassin";
	public static final String EQU_NAT_LARGEUR_BASSIN = "EquNatLargeurBassin";
	public static final String EQU_NAT_SURFACE_BASSIN = "EquNatSurfaceBassin";
	public static final String EQU_NAT_PROF_MINI = "EquNatProfMini";
	public static final String EQU_NAT_PROF_MAX = "EquNatProfMax";
	public static final String EQU_NAT_COULOIR = "EquNatCouloir";
	public static final String EQU_NAT_SURFACE_PLAGE_BASSIN = "EquNatSurfacePlageBassin";
	public static final String EQU_NAT_NB_T_TOTAL = "EquNatNbTTotal";
	public static final String EQU_NAT_NB_T1 = "EquNatNbT1";
	public static final String EQU_NAT_NB_T3 = "EquNatNbT3";
	public static final String EQU_NAT_NB_P_TOTAL = "EquNatNbPTotal";
	public static final String EQU_NAT_NB_P3 = "EquNatNbP3";
	public static final String EQU_NAT_NB_P5 = "EquNatNbP5";
	public static final String EQU_NAT_NB_P7 = "EquNatNbP7";
	public static final String EQU_NAT_NB_P10 = "EquNatNbP10";
	public static final String EQU_NAT_MA_V = "EquNatMaV";
	public static final String EQU_NAT_TOBOG = "EquNatTobog";
	public static final String EQU_NAT_PENTAGLISSE = "EquNatPentaglisse";
	public static final String EQU_NAT_RIVIERE = "EquNatRiviere";
	public static final String EQU_NAT_IM_HANDI = "EquNatImHandi";
	public static final String EQU_NAT_FM = "EquNatFM";
	public static final String EQU_NAT_MM = "EquNatMM";
	public static final String EQU_NAT_ECL_SUB = "EquNatEclSub";
	public static final String EQU_NAT_SONORISATION_SUB = "EquNatSonorisationSub";
	public static final String EQU_NAT_AUTRE = "EquNatAutre";
	public static final String EQU_PRESENCE_PATAUGEOIR = "EquPresencePataugeoir";
	public static final String EQU_GPS_X = "EquGpsX";
	public static final String EQU_GPS_Y = "EquGpsY";
	public static final String EQU_DATE_MAJ = "EquDateMaj";


	private String filename;

	public EquipementParser(String filename) {
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
	protected Equipement parseLine(CSVRecord csvRecord) {
		return null;
	}
}
