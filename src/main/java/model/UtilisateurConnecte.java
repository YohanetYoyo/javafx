package model;

public final class UtilisateurConnecte extends Utilisateur {

    private static UtilisateurConnecte INSTANCE;

    private UtilisateurConnecte(Utilisateur utilisateur) {
        super(utilisateur.getIdUtilisateur(), utilisateur.getNom(), utilisateur.getPrenom(), utilisateur.getEmail(), utilisateur.getMdp());
    }


    public static boolean initInstance(Utilisateur utilisateur) {
        if(INSTANCE == null) {
            INSTANCE = new UtilisateurConnecte(utilisateur);
            return true;
        }
        return false;
    }

    public static UtilisateurConnecte getInstance() {
        return INSTANCE;
    }

    public static boolean clearInstance() {
        if(INSTANCE != null) {
            INSTANCE = null;
            return true;
        }
        return false;
    }
}
