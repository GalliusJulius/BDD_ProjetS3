package modele;

import java.sql.*;
import java.util.Calendar;
import java.util.Observable;
import controleur.ChangementFenetre;
import graphique.AffichageAdmin;
import graphique.AffichageAppli;
import graphique.Fenetre;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import graphique.AffichageConnection;

/**
 * Modèle de l'application.
 * Ici, le modèle gère la connection à la base de données ainsi que les requêtes vers celle-ci.
 * Il gère également le changement des différents affichages (AffchageAppli, AffichageConnection et AffichageAdmin). 
 */
public class Modele extends Observable{
	
	/**
	 * Fenêtre actuelle de l'application (ou panneau). 
	 */
	private Pane fenetreActu;
	
	/**
	 * Table des résultats des requêtes (tableView). 
	 */
	private TableView<Table> resultat;
	
	/**
	 * Elément sur lequel on y place des scènes.
	 * La scène placée dans cet élément (via la fonction setScene()) représente l'affichage en cours.  
	 */
	private Stage stage;
	
	/**
	 * Hauteur et largeur des affichages en cours. 
	 */
	private int width,heigth;
	
	/**
	 * Mode dans lequel on se trouve dans le cas de l'affichage de l'application (vue AffichageAppli).
	 * Le mode agence change lorsque l'utilisateur coche ou décoche la CheckBox présente dans l'affichage de l'application. 
	 */
	private boolean modeAgence;
	
	/**
	 * Dates représentant la période d'une location d'un véhicule (une date de début et une date de fin). 
	 */
	private Date dateD, dateF;
	
	/**
	 * Catégorie de véhicules choisi par l'utilisateur via la ComboBox de l'affichage de l'application (vue AffichageAppli).
	 * Par défaut, toutes les catégories sont choisies.
	 */
	private String catVehicule ="Toutes catégories";
	
	/**
	 * Connection à la base de données (objet indispensable pour l'execution des requêtes). 
	 */
	private Connection cnt;
	
	
	/**
	 * Constructeur du modèle.
	 * @param w largeur de l'affichage de connection (et des autres affichages que l'on parcourira durant l'execution de l'application).
	 * @param h hauteur de l'affichage de connection (et des autres affichages que l'on parcourira durant l'execution de l'application).
	 */
	public Modele(int w,int h) {
		width=w;
		heigth=h;
		resultat = new TableView<Table>();
		resultat.setEditable(false);
		fenetreActu = new AffichageConnection(w,h,this);
	}
	
	/**
	 * Méthode permettant de se connecter à la base de données (avec l'url, le login et le mot de passe de la BDD).
	 * @throws SQLException
	 */
	public void seConnecter() throws SQLException {
		if(fenetreActu instanceof AffichageConnection) {
			String url = ((AffichageConnection)fenetreActu).getUrl();
			String mdp = ((AffichageConnection)fenetreActu).getMdp();
			String login = ((AffichageConnection)fenetreActu).getLogin();
			System.out.println("Tentative de connection sur "+url+" avec l'id :"+login+" et le mdp : "+mdp);
			
			//CONNECTION :
			// URL : jdbc:oracle:thin:@localhost:1521:XE
			cnt = DriverManager.getConnection(url, login, mdp);
			// Vérifie que la connection est bien effectuée :
			System.out.println("Connecté : " + cnt.isValid(100));
		}
	}
	
	public void connectionEchouee() {
		stage.getScene().getStylesheets().clear();
		stage.getScene().getStylesheets().add(getClass().getResource("../css/echecConnection.css").toExternalForm());
	}
	
	/**
	 * Méthode permettant l'affichage de la vue AffichageAppli (et ses composants) dans l'application.
	 */
	public void afficherAppli() {
		resultat = new TableView();
		fenetreActu = new AffichageAppli(width, heigth, this);
		Scene scene = new Scene(fenetreActu, Fenetre.WIDTH, Fenetre.HEIGHT);
		scene.getStylesheets().add(getClass().getResource("../css/appli.css").toExternalForm());
		stage.setScene(scene);
	}
	
	/**
	 * Méthode permettant l'affichage de la vue AffichageAdmin (et ses composants) dans l'application.
	 */
	public void modeAdmin() {
		if(fenetreActu instanceof AffichageAppli) {
			resultat = new TableView();
			fenetreActu = new AffichageAdmin(width,heigth,this);
			Scene scene = new Scene(fenetreActu, Fenetre.WIDTH, Fenetre.HEIGHT);
			scene.getStylesheets().add(getClass().getResource("../css/admin.css").toExternalForm());
			stage.setScene(scene);
		}
	}
	
	/**
	 * Méthode permettant de rechercher la liste des véhicule disponible à une période données.
	 * On affiche le résultat dans une table (TableView) avec la possibilité de réserver des véhicules.
	 * @throws SQLException
	 */
	public void rechercher() throws SQLException {
		
		PreparedStatement stt = cnt.prepareStatement("SELECT DISTINCT VEHICULE.NO_IMM, VEHICULE.MODELE from VEHICULE"
                            							 +" INNER JOIN CATEGORIE ON VEHICULE.CODE_CATEG = CATEGORIE.CODE_CATEG"
														 +" INNER JOIN CALENDRIER ON VEHICULE.NO_IMM = CALENDRIER.NO_IMM"
														 +" where LIBELLE like ? and VEHICULE.NO_IMM not in"
														 +" (select NO_IMM from CALENDRIER"
														 +" where ? <= DATEJOUR and ? >= DATEJOUR and PASLIBRE is not null)");
		if(catVehicule.equals("Toutes catégories")){
			stt.setString(1, "%");
		}
		else {
			stt.setString(1, catVehicule.toLowerCase());
		}
		
		stt.setDate(2, dateD);
		stt.setDate(3, dateF);
		
		ResultSet res = stt.executeQuery();
		
		TableColumn<Table, String> t1 = new TableColumn<Table, String>(res.getMetaData().getColumnName(1));
		TableColumn<Table, String> t2 = new TableColumn<Table, String>(res.getMetaData().getColumnName(2));
		TableColumn<Table, String> t3 = new TableColumn<Table, String>("Reserver maintenant");
		t1.setCellValueFactory(new PropertyValueFactory<>("no_im"));
		t2.setCellValueFactory(new PropertyValueFactory<>("modele"));
		t3.setCellValueFactory(new PropertyValueFactory<>("reservation"));
		
		ObservableList<Table> data = FXCollections.observableArrayList();
		
		while(res.next()) {
			Button b = new Button("Réserver");
			b.setOnAction(new ChangementFenetre(this, res.getString(1)));
			
			data.add(new Contenu(res.getString(1), res.getString(2), b));
		}
		
		stt.close();
		res.close();
		
		resultat.getItems().clear();
		resultat.getColumns().clear();
		
		resultat.setItems(data);
		resultat.getColumns().setAll(t1, t2, t3);
		
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Méthode permettant de rechercher des agences qui possèdes toutes les catégories de véhicules (de la base de données).
	 * Le résultat est stocké et affcihé dans la table de résultats (TableView).
	 * @throws SQLException
	 */
	public void rechercherAgence() throws SQLException{
		PreparedStatement stt = cnt.prepareStatement("SELECT AGENCE.CODE_AG from AGENCE INNER JOIN VEHICULE on AGENCE.CODE_AG = VEHICULE.CODE_AG"
													 +" INNER JOIN CATEGORIE on VEHICULE.CODE_CATEG = CATEGORIE.CODE_CATEG"
													 +" group by AGENCE.CODE_AG"
													 +" having count(distinct VEHICULE.CODE_CATEG) = (select count(*) from CATEGORIE)");
		ResultSet res = stt.executeQuery();
		
		TableColumn<Table, String> t1 = new TableColumn<Table, String>(res.getMetaData().getColumnName(1));
		t1.setCellValueFactory(new PropertyValueFactory<>("code_ag"));
		
		ObservableList<Table> data = FXCollections.observableArrayList();
		
		while(res.next()) {
			data.add(new Contenu(res.getString(1)));
		}
		
		stt.close();
		res.close();
		
		resultat.getItems().clear();
		resultat.getColumns().clear();
		
		resultat.setItems(data);
		resultat.getColumns().setAll(t1);
		
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Méthode permettant de rechercher les clients ayant loués un certain nombre de modèles de véhicules différents.
	 * Le résultat est stocké et affcihé dans la table de résultats (TableView).
	 * @param nbChoixClient
	 * @throws SQLException
	 */
	public void afficherClient(int nbChoixClient) throws SQLException {
		PreparedStatement stt = cnt.prepareStatement(" select NOM, VILLE, CODPOSTAL from CLIENT"
													+" inner join DOSSIER D on CLIENT.CODE_CLI = D.CODE_CLI"
													+" inner join VEHICULE V on D.NO_IMM = V.NO_IMM"
													+" group by NOM, VILLE, CODPOSTAL"
													+" having count(*) >= ?");
		
		stt.setInt(1, nbChoixClient);
		ResultSet res = stt.executeQuery();
		
		
		TableColumn<Table, String> t1 = new TableColumn<Table, String>(res.getMetaData().getColumnName(1));
		TableColumn<Table, String> t2 = new TableColumn<Table, String>(res.getMetaData().getColumnName(2));
		TableColumn<Table, String> t3 = new TableColumn<Table, String>(res.getMetaData().getColumnName(3));
		t1.setCellValueFactory(new PropertyValueFactory<>("nom"));
		t2.setCellValueFactory(new PropertyValueFactory<>("ville"));
		t3.setCellValueFactory(new PropertyValueFactory<>("cp"));
		
		ObservableList<Table> data = FXCollections.observableArrayList();
		
		while(res.next()) {
			data.add(new Contenu(res.getString(1), res.getString(2), res.getString(3)));
		}
		
		stt.close();
		res.close();
		
		resultat.getItems().clear();
		resultat.getColumns().clear();
		
		resultat.setItems(data);
		resultat.getColumns().setAll(t1, t2, t3);
		
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Métode permettant de mettre à jour (ou d'insérer) la réservation d'un véhicule à une période choisie.
	 * Cette méthode met également l'affichage de la table résultat à jour.
	 * @param immat
	 * @throws SQLException
	 */
	public void mettreAjour(String immat) throws SQLException {
		//on prend la date debut
		PreparedStatement stt = null;
		Date temp = (Date) dateD.clone();
		//tant que la date incremete est plus petit ou egale a la date max
		while(temp.before(dateF) || temp.equals(dateF)) {
			//on regarde si la reservation existe pour cette date
			stt = cnt.prepareStatement("select NO_IMM from CALENDRIER "
														+ "where DATEJOUR = ? "
														+ "and NO_IMM = ?");
			stt.setDate(1, temp);
			stt.setString(2, immat);
			ResultSet res = stt.executeQuery();
			//si il n'y pas de lignes
			if(res.getRow() ==0) {
				stt = cnt.prepareStatement("insert into calendrier values (?,?,'x')");
			}
			else {
				stt = cnt.prepareStatement("update CALENDRIER set PASLIBRE = 'x'"
											+ " where NO_IMM=?"
											+ " and DATEJOUR=?");
			}
			stt.setString(1, immat);
			stt.setDate(2, temp);
			stt.executeUpdate();
			//incrementation d'un jour (j'ai pas trouvé plus facile)
			Calendar c = Calendar.getInstance();
			c.setTime(temp);
			c.add(Calendar.DAY_OF_MONTH, 1);
			temp = new Date(c.getTime().getTime());
			res.close();
		}
		double prix = 0.0;
		int nbJour = 0;
		if(stt != null) {
			stt.close();
			
			PreparedStatement stt2 = cnt.prepareStatement("select TARIF_JOUR, TARIF_HEBDO from TARIF"
														 +" inner join CATEGORIE on TARIF.CODE_TARIF = CATEGORIE.CODE_TARIF"
														 +" inner join VEHICULE on CATEGORIE.CODE_CATEG = VEHICULE.CODE_CATEG" 
														 +" where MODELE like (select MODELE from VEHICULE where NO_IMM like ?)");
			stt2.setString(1, immat);
			
			ResultSet res = stt2.executeQuery();			
			double prixJour = 0.0, prixHebdo = 0.0;
			if(res.next()) {
				prixJour = res.getDouble(1);
				prixHebdo = res.getDouble(2);
			}
			nbJour =(int)((dateF.getTime() - dateD.getTime() )/(1000*60*60*24)); 
			prix = (nbJour%7*prixJour + ((int)(nbJour/7))*prixHebdo);
			stt2.close();
			
		}
		rechercher();
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Votre Réservation");
		alert.setHeaderText(null);
		alert.setContentText("Votre réservation de " + nbJour + " jours est confirmée, elle coutera : " + prix + "€");

		alert.showAndWait();
	}
	
	public void afficherTableAudit() throws SQLException {
		PreparedStatement stt = null;
		try {
			stt = cnt.prepareStatement("select * from Audit_");
		} catch (SQLException e) {}
		
		if(stt != null) {
			
			ResultSet res = stt.executeQuery();
			
			TableColumn<Table, String> t1 = new TableColumn<Table, String>(res.getMetaData().getColumnName(1));
			TableColumn<Table, String> t2 = new TableColumn<Table, String>(res.getMetaData().getColumnName(2));
			TableColumn<Table, String> t3 = new TableColumn<Table, String>(res.getMetaData().getColumnName(3));
			TableColumn<Table, String> t4 = new TableColumn<Table, String>(res.getMetaData().getColumnName(4));
			TableColumn<Table, String> t5 = new TableColumn<Table, String>(res.getMetaData().getColumnName(5));
			t1.setCellValueFactory(new PropertyValueFactory<>("num_doss"));
			t2.setCellValueFactory(new PropertyValueFactory<>("date"));
			t3.setCellValueFactory(new PropertyValueFactory<>("nom"));
			t4.setCellValueFactory(new PropertyValueFactory<>("marque"));
			t5.setCellValueFactory(new PropertyValueFactory<>("modele"));
			
			ObservableList<Table> data = FXCollections.observableArrayList();
			
			while(res.next()) {
				data.add(new Audit(res.getInt(1), res.getDate(2), res.getString(3), res.getString(4), res.getString(5)));
			}
			
			stt.close();
			res.close();
			
			resultat.getItems().clear();
			resultat.getColumns().clear();
			
			resultat.setItems(data);
			resultat.getColumns().setAll(t1, t2, t3, t4, t5);
			
			setChanged();
			notifyObservers();
			
			
		}
	}

	/**
	 * Getter de la fenêtre actuelle.
	 * @return fenêtre actuelle
	 */
	public Pane getFenetreActu() {
		return fenetreActu;
	}

	/**
	 * Setter de la fenêtre actuelle.
	 * @param fenetreActu fenêtre actuelle.
	 */
	public void setFenetreActu(Pane fenetreActu) {
		this.fenetreActu = fenetreActu;
	}
	
	
	/**
	 * Setter de la catégorie de véhicule.
	 * @param s catégorie de véhicule
	 */
	public void setCatVehicule(String s) {
		catVehicule = s;
	}
	
	/**
	 * Setter de la date de début (de la période à choisir).
	 * @param d date de début
	 */
	public void setDateD(Date d) {
		dateD = d;
	}
	
	/**
	 * Setter de la date de fin.
	 * @param d date de fin
	 */
	public void setDateF(Date d) {
		dateF = d;
	}
	
	/**
	 * Setter du mode agence.
	 * @param b mode agence (boolean)
	 */
	public void setModeAgence(boolean b) {
		modeAgence = b;
	}
	
	/**
	 * Getter du mode agence.
	 * @return mode agence (boolean)
	 */
	public boolean getModeAgence() {
		return modeAgence;
	}
	
	/**
	 * Getter de la table de résultats (TableView).
	 * @return table de résultats
	 */
	public TableView getResultat() {
		return resultat;
	}

	/**
	 * Setter de l'élément permettant le changement des scènes (ou des affichages).
	 * @param st élément en question
	 */
	public void setStage(Stage st) {
		stage = st;
	}
}

