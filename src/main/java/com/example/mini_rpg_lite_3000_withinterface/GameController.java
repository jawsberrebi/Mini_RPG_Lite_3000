package com.example.mini_rpg_lite_3000_withinterface;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.Random;

public class GameController {

    private int combatNumber;                                                                                                   //Numéro du combat
    @FXML
    private Label combatNumberString;                                                                                           //Affichage du numéro du combat
    @FXML
    private Label currentHero;                                                                                                  //Affichage du type du héros actuel en train de combattre
    @FXML
    private Label heroData;                                                                                                     //Affichage des informations sur le héros actuel train de combattre
    @FXML
    private Label currentEnemy;                                                                                                 //Affichage du type de l'ennemi actuel en train de combattre
    @FXML
    private Label enemyData;                                                                                                    //Affichage des informations sur l'ennemi actuel train de combattre
    @FXML
    private Label whatToDo;                                                                                                     //Texte qui indique la plupart du temps ce que l'utilisateur peut faire
    @FXML
    private Button gameBtn;                                                                                                     //Bouton principal pour avancer dans le déroulement du jeu
    @FXML
    private Button attackBtn;                                                                                                   //Bouton pour faire attaquer le héros
    @FXML
    private Button defendBtn;                                                                                                   //Bouton pour défendre le héros (mettre son armure)
    @FXML
    private Button useConsumableBtn;                                                                                            //Bouton pour utiliser des consommables (afficher la nourriture disponible et donner le choix entre la nourriture et les sorts pour le mage ou le healer)
    @FXML
    private Label actualState;                                                                                                  //Texte pour afficher l'état actuel du jeu, les actions qui se passent la plupart du temps
    @FXML
    private ListView<String> heroesToHeal = new ListView<>();                                                                   //Liste d'affichage pour choisir un héros à guérir (pour l'attaque du healer)
    @FXML
    private Button healBtn;                                                                                                     //Bouton pour guérir un héros (dans le cas du healer)
    @FXML
    private ImageView armorImg;                                                                                                 //Image signalant que l'armure a été mise (l'image s'affiche quand l'amure a été mise par le héros)
    @FXML
    private Button foodBtn;                                                                                                     //Bouton pour que le héros consomme de la nourriture après le choix du consommable
    @FXML
    private Button potionBtn;                                                                                                   //Bouton pour que le mage ou le healer boive une potion après le choix du consommable
    @FXML
    private ListView consumables;                                                                                               //Liste d'affiche pour les consommables
    @FXML
    private Button consumeBtn;                                                                                                  //Bouton pour consommer un consommable
    @FXML
    private Button enhanceArmorBtn;                                                                                             //Bouton pour augmenter les points d'armure après une victoire
    @FXML
    private Button enhanceDamagesBtn;                                                                                           //Bouton pour augmenter les points de dégât après une victoire
    @FXML
    private Button enhanceConsumablesBtn;                                                                                       //Bouton pour augmenter les points que confèrent les consommables après une victoire
    @FXML
    private Button enhanceQuantityConsumablesBtn;                                                                               //Bouton pour augmenter le nombre de consommables (nourriture ou potion) après une victoire
    @FXML
    private Button enhanceQuantityFoodBtn;                                                                                      //Bouton pour augmenter la quantité de nourriture après une victoire
    @FXML
    private Button enhanceQuantityPotionBtn;                                                                                    //Bouton pour augmenter la quantité de potions (dans le cas du mage et du healer) après une victoire
    @FXML
    private Button enhanceHeroBtn;                                                                                              //Bouton pour améliorer le héros (augmenter les flèches pour le hunter, réduire ou augmenter l'efficacité des sorts pour le healer ou le mage)
    @FXML
    private Button enhanceSpellBtn;                                                                                             //Bouton pour améliorer l'efficacité du sort (pour le healer ou le mage)
    @FXML
    private Button reduceManaCostBtn;                                                                                           //Bouton pour réduire le coût du sort en mana (pour le healer ou le mage)

    private boolean resultAttackHero;                                                                                           //Résultat de l'attaque du héros : si l'ennemi est mort à l'issu du combat, cet attribut vaudra "true", si l'ennemi n'est pas mort, cet attribut vaudra "false"

    private boolean attackResultEnemy;                                                                                          //Résultat de l'attaque de l'ennemi : si le héros est mort à l'issu du combat, cet attribut vaudra "true", si le héros n'est pas mort, cet attribut vaudra "false"

    private int indexHeroToHeal;                                                                                                //Index (curseur) du choix du joueur la liste d'affichage des héros à guérir (dans le cas de l'attaque du healer)

    private int indexConsumables;                                                                                               //Index (curseur) du choix du joueur la liste d'affichage des consommables

    private int cursorHeroReward;                                                                                               //Index (curseur) sur la liste du groupe de héros à récompenser (enchaînement de récompense automatique de héros en héros en fin de combat)

    //Initialisation du jeu, on lance la méthode "play" dans Game pour intialiser le groupe de héros et des ennemis, selon les choix du joueur
    @FXML
    public void initialize(){
        this.cursorHeroReward = 0;
        this.currentHero.setVisible(false);
        this.currentEnemy.setVisible(false);
        this.heroData.setVisible(false);
        this.enemyData.setVisible(false);
        this.healBtn.setVisible(false);
        this.armorImg.setVisible(false);
        this.consumables.setVisible(false);
        this.foodBtn.setVisible(false);
        this.potionBtn.setVisible(false);
        this.consumeBtn.setVisible(false);
        this.enhanceSpellBtn.setVisible(false);
        this.reduceManaCostBtn.setVisible(false);
        this.whatToDo.setText("Que faire ?");
        hideActions();
        hideActionsAfterVictory();
        Game.createHeroGroup(Application.getNumberOfHeroes(), Application.getSelectedHeroes());                                      //Création du groupe de héros à partir du choix fait précédemment
        Game.playGame();                                                                                                             //Initialisation du jeu
        this.combatNumber = 0;
        this.combatNumberString.setText(Integer.toString(this.combatNumber));
        refreshListHeroesToHeal();
        this.heroesToHeal.setVisible(false);
    }

    @FXML
    public void updateBtn() throws IOException {
        switch (Game.context.status){
            case START_COMBAT:                                                          //État avant le commencement d'un nouveau combat
                this.combatNumber++;                                                    //Incrémentation du numéro de combat
                this.actualState.setVisible(true);                                      //Texte d'état, qui donne des informations sur l'évolution du jeu
                if (this.combatNumber >= 2){
                    this.actualState.setText("Tous les ennemis du combat sont morts");
                }
                this.combatNumberString.setText(Integer.toString(this.combatNumber));
                Game.context.generateCombat();                                          //Génération d'un combat dans Game
                //À chaque nouveau combat, on définit l'ordre du premier attaquant de manière aléatoire, à chaque
                Random random = new Random();
                Game.context.currentPositionHero = random.nextInt(Game.context.getHeroes().size());
                Game.context.currentPositionEnemy = random.nextInt(Game.context.getEnemies().size());
                //Bouton d'état du jeu
                this.gameBtn.setText("Jouer");
                this.gameBtn.setVisible(false);
                hideActionsAfterVictory();
                Game.context.undefendAll();                                                                                         //On désactive l'armure des héros avant chaque nouveau combat
                this.armorImg.setVisible(Game.context.isArmorOn());                                                                 //Image sur l'interface indiquant si l'amure est activée ou non
                this.consumables.setVisible(false);
                this.consumeBtn.setVisible(false);
                this.currentHero.setVisible(true);
                this.currentEnemy.setVisible(true);
                this.heroData.setVisible(true);
                this.enemyData.setVisible(true);
                this.currentHero.setText(Game.context.getHeroes().get(Game.context.getCurrentPositionHero()).displayType());        //Texte d'affichage du type de héro
                this.currentEnemy.setText(Game.context.getEnemies().get(Game.context.getCurrentPositionEnemy()).displayType());     //Texte d'affichage du type d'ennemi
                this.heroData.setText(Game.context.getHeroes().get(Game.context.getCurrentPositionHero()).displayData());           //Texte d'affichage des informations sur le héro
                this.enemyData.setText(Game.context.getEnemies().get(Game.context.getCurrentPositionEnemy()).displayData());        //Texte d'affichage des informations sur l'ennemi
                refreshListHeroesToHeal();                                                                                          //Actualisation de la liste des héros que le healer peut guérir, les héros morts n'y figurent pas
                displayActions();                                                                                                   //Affichage des actions que le joueur peut faire
                Game.context.status = Game.Status.HERO_TURN;                                                                        //Changement du statut de jeu : au tour du joueur
                break;
            case HERO_TURN:                                                                                                         //Tour du joueur
                this.actualState.setVisible(false);
                this.actualState.setVisible(false);
                this.armorImg.setVisible(Game.context.isArmorOn());                                                                 //L'image d'état d'armure est visible seulement si le joueur a activé l'armure sur un héro
                this.currentEnemy.setText(Game.context.getEnemies().get(Game.context.getCurrentPositionEnemy()).displayType());     //Actualisation du type d'ennemi à chaque tour (si un ennemi est vaincu dans un groupe un autre apparaît automatiquement)
                this.enemyData.setText(Game.context.getEnemies().get(Game.context.getCurrentPositionEnemy()).displayData());        //De même pour les informations sur l'ennemi
                if (this.resultAttackHero){                                                                                         //Si l'attaque du héro a tué l'ennemi
                    this.currentEnemy.setText("L'ennemi est mort !");                                                               //Le titre figurant le type de l'ennemi se change en disant que l'ennemi est mort, et sera remplacé par le type du prochain ennemi au tour de l'ennemi
                    this.enemyData.setVisible(false);                                                                               //On fait disparaître les informations de l'ennemi si l'ennemi est mort
                }
                this.currentHero.setText(Game.context.getHeroes().get(Game.context.getCurrentPositionHero()).displayType());        //Actualisation du type du héros combattant
                this.heroData.setText(Game.context.getHeroes().get(Game.context.getCurrentPositionHero()).displayData());           //De même pour les informations du héros combattant
                this.gameBtn.setVisible(false);
                this.gameBtn.setText("Continuer");
                refreshListHeroesToHeal();                                                                                          //Actualisation de la liste des héros non morts que le healer peut guérir
                displayActions();
                break;
            case ENEMY_TURN:                                                                                                        //Tour de l'ennemi
                this.actualState.setVisible(false);
                this.consumables.setVisible(false);
                this.consumeBtn.setVisible(false);
                this.armorImg.setVisible(Game.context.isArmorOn());                                                                 //L'armure reste affichée si c'est le cas
                this.resultAttackHero = false;
                this.enemyData.setVisible(true);                                                                                    //Affichage des informations de l'ennemi
                this.gameBtn.setText("Continuer");
                this.enemyData.setText(Game.context.getEnemies().get(Game.context.getCurrentPositionEnemy()).displayData());        //Actualisation des informations sur l'ennemi
                this.currentEnemy.setText(Game.context.getEnemies().get(Game.context.getCurrentPositionEnemy()).displayType());     //Actualisation du type de l'ennemi combattant
                this.attackResultEnemy = Game.context.attackEnemy();                                                                //Attaque de l'ennemi, et retourne un booléen si l'ennemi a tué le héros ou non
                if (this.attackResultEnemy){                                                                                        //Si l'attaque a tué le héros
                    this.currentHero.setText("Le Héro est mort !");                                                                 //Actualisation du titre de héros disant s'il est mort
                    this.heroData.setText("");                                                                                      //On masque les infos du héros mort
                }else {
                    this.heroData.setText(Game.context.getHeroes().get(Game.context.getCurrentPositionHero()).displayData());       //Sinon, on actualise ses informations
                }
                refreshListHeroesToHeal();                                                                                          //Actualisation de la liste de héros guérissable par le healer (on enlève les héros morts)
                this.whatToDo.setVisible(true);
                this.whatToDo.setText("L'ennemi a attaqué !");
                this.gameBtn.setText("Continuer le combat");
                break;
            case REWARDS_TIME:                                                                                                      //État du jeu où un combat a été gagné : on propose des récompenses pour les héros
                hideActions();
                this.currentHero.setVisible(false);
                this.currentEnemy.setVisible(false);
                this.gameBtn.setVisible(false);
                this.heroData.setVisible(false);
                this.enemyData.setVisible(false);
                this.armorImg.setVisible(false);
                this.whatToDo.setVisible(true);
                //Cette condition permet de balayer la liste de héros vivants pour que chacun aie une récompense choisie par le joueur
                if(this.cursorHeroReward >= Game.context.getHeroes().size()){                                                       //cursorHeroReward balaie le groupe de héros pour savoir à qui faut attribuer la récompense
                    this.cursorHeroReward = 0;                                                                                      //Dans le cas où il atteint la fin du groupe, on n'attribue plus de récompenses
                    hideActionsAfterVictory();
                    Game.context.status = Game.Status.START_COMBAT;                                                                 //Changement de statut au niveau combat
                    this.gameBtn.setVisible(true);
                }else{
                    this.whatToDo.setText("Le combat a été gagné ! Que souhaitez-vous faire pour " + Game.context.getHeroes().get(this.cursorHeroReward).displayType());        //Texte indiquant quel héro va recevoir la récompense
                    displayActionsAfterVictory();
                    if (Game.context.getHeroes().get(this.cursorHeroReward) instanceof Hunter){                                     //Amélioration du héros seulement dans le cas du Hunter, Mage ou Healer (augmente le nombre de flèche ou diminue le coût en mana)
                        this.enhanceHeroBtn.setVisible(true);
                        this.enhanceHeroBtn.setText("Augmenter le nombre de flèches");
                    }else if(Game.context.getHeroes().get(this.cursorHeroReward) instanceof SpellCaster){
                        this.enhanceHeroBtn.setVisible(true);
                        this.enhanceHeroBtn.setText("Améliorer le sort");
                    }else{
                        this.enhanceHeroBtn.setVisible(false);
                    }
                }
                break;
            case END_GAME:                                                                                                            //État de fin du jeu : tous les héros sont morts
                hideActions();
                this.gameBtn.setVisible(false);
                this.currentHero.setVisible(false);
                this.currentEnemy.setVisible(false);
                this.heroData.setVisible(false);
                this.enemyData.setVisible(false);
                this.armorImg.setVisible(false);
                this.consumables.setVisible(false);
                this.consumeBtn.setVisible(false);
                this.actualState.setVisible(true);
                this.actualState.setText("Tous les héros sont morts, au combat " + this.combatNumber);                                   //On affiche un message fin avec le dernier combat (là où tous les héros sont morts)
                break;
        }
    }

    //Lorsque l'on clique le bouton "Attaquer", cette méthode s'exécute :
    @FXML
    public void handleBtnAttack(){
        this.armorImg.setVisible(Game.context.isArmorOn());                                                                             //Si l'armure a été activée, l'image reste affichée
        if (Game.context.status == Game.Status.HERO_TURN){                                                                              //On s'assure qu'il s'agit bien du tour du joueur avec le Status
            if (Game.context.getHeroes().get(Game.context.getCurrentPositionHero()) instanceof Healer){                                 //S'il s'agit d'attaquer avec le healer, l'attaque sera différente
                healerAttack();                                                                                                         //Si c'est le cas, exécution de la fonction concernée
            }else {
                this.resultAttackHero = Game.context.attack(-1);                                                          //Sinon le héro attaque
            }
            if (!this.resultAttackHero){                                                                                                //Si l'attaque n'a pas tué l'ennemi, on actualise ses informations
                this.enemyData.setText(Game.context.getEnemies().get(Game.context.getCurrentPositionEnemy()).displayData());
            }
            this.heroData.setText(Game.context.getHeroes().get(Game.context.getCurrentPositionHero()).displayData());                   //Actualisation des informations du héros après son attaque
            hideActions();

            if (this.resultAttackHero){                                                                                                 //Si l'attaque a marché, on affiche des textes différents à l'écran et on signal que l'ennemi est mort
                this.gameBtn.setText("Attaque du prochain ennemi");
                this.currentEnemy.setText("L'ennemi est mort !");
                this.enemyData.setVisible(false);
            }else {
                this.gameBtn.setText("Attaque de l'ennemi");
            }
            refreshListHeroesToHeal();                                                                                                  //Actualisation de la liste des héros à guérir, utile après l'attaque d'un healer
            if (Game.context.getHeroes().get(Game.context.getCurrentPositionHero()) instanceof Healer){                                 //Dans le cas d'un healer qui attaque (guéris), on invoque le bouton de jeu pour continuer la partie
                this.gameBtn.setVisible(false);
            }else{
                this.gameBtn.setVisible(true);
            }
        }
    }

    //Cette méthode est exécutée lors du clic sur le bouton "défendre", elle sert à activer l'armure sur le héros actif
    @FXML
    public void handleBtnDefend(){
        Game.context.defend();
        this.armorImg.setVisible(Game.context.isArmorOn());
        hideActions();
        this.gameBtn.setVisible(true);
    }

    //Cette méthode est exécutée lors du clic sur le bouton "Utiliser un consommable"
    @FXML
    public void handleBtnUseConsumable(){
        hideActions();
        if (Game.context.getHeroes().get(Game.context.getCurrentPositionHero()) instanceof SpellCaster){                                //Dans le cas d'un healer ou d'un mage, on rend disponible les potions et la nourriture, le healer et le mage sont les seuls à pouvoir consommer des potions
            this.foodBtn.setVisible(true);
            this.potionBtn.setVisible(true);
        }else {
            handleBtnFood();                                                                                                            //Dans le cas inverse, on passe directement à la méthode qui affiche la nourriture à consommer, les autres héros ne peuvent que consommer de la nourriture
        }
    }

    //Lorsqu'on clique sur le bouton "Manger de la nourriture" dans le cas d'un healer ou d'un mage, cette méthode s'exécute (elle est automatique pour les autres types de héros comme dit précédemment)
    @FXML
    public void handleBtnFood(){
        this.useConsumableBtn.setVisible(false);
        this.foodBtn.setVisible(false);
        this.potionBtn.setVisible(false);
        //Actualisation de la liste de consommables du héros disponible
        this.consumables.getItems().clear();
        for (int i = 0; i < Game.context.getHeroes().get(Game.context.getCurrentPositionHero()).getLembdas().size(); i++){
            this.consumables.getItems().add(Game.context.getHeroes().get(Game.context.getCurrentPositionHero()).getLembdas().get(i).display());
        }
        this.consumables.setVisible(true);                                                                                           //Affichage de la liste de nourriture
        this.consumeBtn.setText("Manger");
        this.consumeBtn.setVisible(true);
        consumablesSelection();                                                                                                     //Méthode gérant la sélection de la nourriture dans la liste
    }

    //Dans le cas du healer ou du mage, cette méthode s'exécute au clic sur le bouton "boire une potion"
    @FXML
    public void handleBtnPotion(){
        this.useConsumableBtn.setVisible(false);
        this.foodBtn.setVisible(false);
        this.potionBtn.setVisible(false);
        //De la même manière que pour la nourriture, on actualise la liste des potions du héros
        this.consumables.getItems().clear();
        for (int i = 0; i < Game.context.getHeroes().get(Game.context.getCurrentPositionHero()).getPotions().size(); i++){
            this.consumables.getItems().add(Game.context.getHeroes().get(Game.context.getCurrentPositionHero()).getPotions().get(i).display());
        }
        this.consumables.setVisible(true);
        this.consumeBtn.setText("Boire");
        this.consumeBtn.setVisible(true);
        consumablesSelection();                                                                                                    //De la même manière que pour la liste de nourriture, cette méthode gère la sélection de la potion
    }

    //Lorsque l'on clique sur le bouton "consommer", cette méthode s'exécute
    @FXML
    public void handleBtnConsume(){
        consumablesSelection();
        if(this.consumeBtn.getText().equals("Manger")){                                                                           //Dans les méthodes précédentes, on modifie le texte du bouton de consommation que l'on affiche (selon si l'on a sélectionné "manger de la nourriture" ou "boire une potion")
            Game.context.getHeroes().get(Game.context.getCurrentPositionHero()).useConsumable(this.indexConsumables, true);//Si nous somme dans le cas où "Manger" est inscrit sur le bouton, le héros consomme sa nourriture
        }else {
            Game.context.getHeroes().get(Game.context.getCurrentPositionHero()).useConsumable(this.indexConsumables, false);//Sinon, dans le cas où "Boire" est inscrit sur le bouton, le healer ou mage boit sa potion
        }
        Game.context.status = Game.Status.ENEMY_TURN;                                                                             //Après consommation, le statut change pour le tour de l'ennemi
        this.consumables.setVisible(false);
        this.consumeBtn.setVisible(false);
        this.heroData.setText(Game.context.getHeroes().get(Game.context.getCurrentPositionHero()).displayData());                 //Actualisation des informations du joueur
        this.gameBtn.setVisible(true);
    }

    //Cette méthode est automatique exécutée dans le cas du healer si l'on clique sur le bouton "Attaquer", comme vu précédemment
    private void healerAttack(){
        this.heroesToHeal.setVisible(true);
        this.healBtn.setVisible(true);
        this.heroesToHeal.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {                 //Évènement pour gérer la sélection du héros à guérir
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                indexHeroToHeal = heroesToHeal.getSelectionModel().getSelectedIndex();
            }
        });
    }

    //Cette méthode est exécutée si l'on clique sur le bouton "guérir" dans le cas de l'attaque d'un healer
    @FXML
    private void handleBtnHeal(){
        if (this.indexHeroToHeal >= 0){
            Game.context.attack(this.indexHeroToHeal);                                                                          //Pour l'attaque du healer, on passe l'index de la liste des héros actuelle pour que le healer guérisse le héros choisi par le joueur
            this.heroData.setText(Game.context.getHeroes().get(Game.context.getCurrentPositionHero()).displayData());           //Actualisation des informations du héros
            this.healBtn.setVisible(false);
            this.heroesToHeal.setVisible(false);
            this.gameBtn.setVisible(true);
            Game.context.status = Game.Status.ENEMY_TURN;                                                                       //Après l'attaque du healer, c'est le tour de l'ennemi, donc mise à jour du statut
        }
    }

    //Méthode permettant d'afficher les boutons que le joueur peut activer lors de son tour : "attaquer", "défendre" et "utiliser un consommable"
    private void displayActions(){
        this.whatToDo.setVisible(true);
        this.whatToDo.setText("Que faire ?");
        this.attackBtn.setVisible(true);
        this.defendBtn.setVisible(true);
        this.useConsumableBtn.setVisible(true);
    }

    //Méthode permettant de masquer les boutons "attaquer", "défendre" et "utiliser un consommable", méthode activée quand ce n'est pas au tour du joueur
    private void hideActions(){
        this.whatToDo.setVisible(false);
        this.attackBtn.setVisible(false);
        this.defendBtn.setVisible(false);
        this.useConsumableBtn.setVisible(false);
    }

    //Méthode actualisant la liste de héros encore vivants que le healer peut guérir
    private void refreshListHeroesToHeal(){
        this.heroesToHeal.getItems().clear();                                                                                  //On supprime tout dans un premier temps
        for (int i = 0; i < Game.context.getHeroes().size(); i++){
            this.heroesToHeal.getItems().add(Game.context.getHeroes().get(i).displayType());                                   //Puis on recollecte les héros à partir de la liste d'origine
        }
    }

    //Méthode avec évènement permettant de gérant la sélection du consommable à consommer
    private void consumablesSelection(){
        this.consumables.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                indexConsumables = consumables.getSelectionModel().getSelectedIndex();
            }
        });
    }

    //Méthode s'exécutant après une victoire de combat, permettant aux boutons de récompense de s'afficher. On peut :
    private void displayActionsAfterVictory(){
        this.enhanceArmorBtn.setVisible(true);                                                                                  //Améliorer l'armure du personnage
        this.enhanceDamagesBtn.setVisible(true);                                                                                //Améliorer ses points de dégât
        this.enhanceConsumablesBtn.setVisible(true);                                                                            //Améliorer les points (de vie et de mana) que procurent les consommables
        this.enhanceQuantityConsumablesBtn.setVisible(true);                                                                    //Augmenter sa quantité de consommables
    }

    //Méthode s'exécutant après un clic sur le bouton "améliorer l'armure"
    @FXML
    private void handleBtnEnhanceArmor(){
        hideActionsAfterVictory();
        Game.context.enhanceArmor(this.cursorHeroReward);                                                                       //Amélioration de l'amure
        this.cursorHeroReward++;                                                                                                //Après chaque récompense attribuée à un personnage, le curseur parcourant la liste des héros se déplace au héros suivant à récompenser
        Game.context.status = Game.Status.REWARDS_TIME;                                                                         //On est toujours dans le moment d'attribution de récompenses
        this.gameBtn.setText("Continuer le jeu");
        this.gameBtn.setVisible(true);
    }

    //Méthode s'exécutant après un clic sur le bouton "améliorer les dégâts "
    @FXML
    private void handleBtnEnhanceDamages(){
        hideActionsAfterVictory();
        Game.context.enhanceDamages(this.cursorHeroReward);                                                                     //On améliore les dégâts de l'arme du héros
        this.cursorHeroReward++;                                                                                                //Après chaque récompense attribuée à un personnage, le curseur parcourant la liste des héros se déplace au héros suivant à récompenser
        Game.context.status = Game.Status.REWARDS_TIME;                                                                         //On est toujours dans le moment d'attribution de récompenses
        this.gameBtn.setText("Continuer le jeu");
        this.gameBtn.setVisible(true);
    }

    //Méthode s'exécutant après un clic sur le bouton "améliorer les consommables"
    @FXML
    private void handleBtnEnhanceConsumables(){
        hideActionsAfterVictory();
        Game.context.enhanceConsumables(this.cursorHeroReward);                                                                 //On augmente les points de vie (et de mana, dans le cas d'un mage ou d'un healer) que confèrent les consommables
        this.cursorHeroReward++;                                                                                                //Après chaque récompense attribuée à un personnage, le curseur parcourant la liste des héros se déplace au héros suivant à récompenser
        Game.context.status = Game.Status.REWARDS_TIME;                                                                         //On est toujours dans le moment d'attribution de récompenses
        this.gameBtn.setText("Continuer le jeu");
        this.gameBtn.setVisible(true);
    }

    //Méthode s'exécutant après un clic sur le bouton "augmenter le nombre de consommables". Ici on affiche le boutton
    //"Augmenter la nourriture" que dans le cas où on est en présence d'un mage ou d'un healer.
    @FXML
    public void handleBtnEnhanceQuantityConsumables(){
        hideActionsAfterVictory();
        this.enhanceQuantityFoodBtn.setVisible(true);
        boolean isThereASpellCaster = false;
        if (Game.context.getHeroes().get(this.cursorHeroReward) instanceof SpellCaster){
            isThereASpellCaster = true;
        }
        if(isThereASpellCaster){
            this.enhanceQuantityPotionBtn.setVisible(true);
        }
    }

    //Méthode servant à améliorer les points de vie que confère la nourriture
    @FXML
    public void handleBtnEnhanceQuantityFood(){
        hideActionsAfterVictory();
        Game.context.enhanceQuantityFood(this.cursorHeroReward);                                                                    // On augmente le nombre de points de vie que donne la nourriture
        this.cursorHeroReward++;                                                                                                    // Après chaque récompense attribuée à un personnage, le curseur parcourant la liste des héros se déplace au héros suivant à récompenser
        Game.context.status = Game.Status.REWARDS_TIME;                                                                             // On est toujours dans le moment d'attribution de récompenses
        this.gameBtn.setText("Continuer le jeu");
        this.gameBtn.setVisible(true);
    }

    //Idem pour le cas des potions : on augmente le nombre de points de mana que confèrent les potions
    @FXML
    public void handleBtnEnhanceQuantityPotions(){
        hideActionsAfterVictory();
        Game.context.enhanceQuantityPotions(this.cursorHeroReward);
        this.cursorHeroReward++;
        Game.context.status = Game.Status.REWARDS_TIME;
        this.gameBtn.setText("Continuer le jeu");
        this.gameBtn.setVisible(true);
    }

    //Méthode améliorant les héros : augmenter des flèches pour le hunter ou améliorer/réduire le coût des sorts pour le mage ou le healer
    @FXML
    public void handleBtnEnhanceHero(){
        hideActionsAfterVictory();

        if (Game.context.getHeroes().get(this.cursorHeroReward) instanceof SpellCaster){                                        // S'il s'agit d'un mage ou d'un healer
            this.enhanceSpellBtn.setVisible(true);                                                                              // On fait apparaître les 2 choix (améliorer les sorts ou réduire le coût en mana)
            this.reduceManaCostBtn.setVisible(true);
        }else {
            Game.context.enhanceHero(this.cursorHeroReward);                                                                    // Sinon (on appliquera cela au hunter) on augmente son nombre de flèches
            this.cursorHeroReward++;                                                                                            // Après chaque récompense attribuée à un personnage, le curseur parcourant la liste des héros se déplace au héros suivant à récompenser
            Game.context.status = Game.Status.REWARDS_TIME;                                                                     // On est toujours dans le moment d'attribution de récompenses
            this.gameBtn.setText("Continuer le jeu");
            this.gameBtn.setVisible(true);
        }
        this.enhanceHeroBtn.setVisible(false);
    }

    //Méthode pour améliorer l'efficacité des sorts du healer ou du mage
    public void handleBtnEnhanceSpell(){
        this.enhanceSpellBtn.setVisible(false);
        this.reduceManaCostBtn.setVisible(false);
        Game.context.enhanceHero(this.cursorHeroReward);                                                                        // Amélioration du sort
        this.cursorHeroReward++;                                                                                                // Après chaque récompense attribuée à un personnage, le curseur parcourant la liste des héros se déplace au héros suivant à récompenser
        Game.context.status = Game.Status.REWARDS_TIME;                                                                         // On est toujours dans le moment d'attribution de récompenses
        this.gameBtn.setText("Continuer le jeu");
        this.gameBtn.setVisible(true);
    }

    //Idem, mais pour réduire les coûts des sorts en mana des sorts du healer ou du mage
    public void handleBtnReduceManaCost(){
        this.enhanceSpellBtn.setVisible(false);
        this.reduceManaCostBtn.setVisible(false);
        Game.context.reduceManaCost(this.cursorHeroReward);
        this.cursorHeroReward++;
        Game.context.status = Game.Status.REWARDS_TIME;
        this.gameBtn.setText("Continuer le jeu");
        this.gameBtn.setVisible(true);
    }

    //Cette méthode cache les boutons de récompenses (dans le cas de la victoire d'un combat)
    private void hideActionsAfterVictory(){
        this.enhanceHeroBtn.setVisible(false);
        this.enhanceArmorBtn.setVisible(false);
        this.enhanceDamagesBtn.setVisible(false);
        this.enhanceConsumablesBtn.setVisible(false);
        this.enhanceQuantityConsumablesBtn.setVisible(false);
        this.enhanceQuantityFoodBtn.setVisible(false);
        this.enhanceQuantityPotionBtn.setVisible(false);
    }
}
