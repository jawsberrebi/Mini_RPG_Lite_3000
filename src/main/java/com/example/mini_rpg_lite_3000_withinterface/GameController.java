package com.example.mini_rpg_lite_3000_withinterface;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.Random;

public class GameController {

    @FXML
    private int combatNumber;
    @FXML
    private Label combatNumberString;
    @FXML
    private Label currentHero;
    @FXML
    private Label heroData;
    @FXML
    private Label currentEnemy;
    @FXML
    private Label enemyData;
    @FXML
    private Label whatToDo;
    @FXML
    private Button gameBtn;
    @FXML
    private Button attackBtn;
    @FXML
    private Button defendBtn;
    @FXML
    private Button useConsumableBtn;
    @FXML
    private Label actualState;
    @FXML
    private ListView<String> heroesToHeal = new ListView<>();
    @FXML
    private Button healBtn;
    @FXML
    private ImageView armorImg;
    @FXML
    private Button foodBtn;
    @FXML
    private Button potionBtn;
    @FXML
    private ListView consumables;
    @FXML
    private Button consumeBtn;
    @FXML
    private Button enhanceArmorBtn;
    @FXML
    private Button enhanceDamagesBtn;
    @FXML
    private Button enhanceConsumablesBtn;
    @FXML
    private Button enhanceQuantityConsumablesBtn;
    @FXML
    private Button enhanceQuantityFoodBtn;
    @FXML
    private Button enhanceQuantityPotionBtn;
    @FXML
    private Button enhanceHeroBtn;
    @FXML
    private Button enhanceSpellBtn;
    @FXML
    private Button reduceManaCostBtn;

    private boolean resultAttackHero;

    private boolean attackResultEnemy;

    private int indexHeroToHeal;

    private int indexConsumables;

    private int cursorHeroReward;

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
        System.out.println(HelloApplication.getNumberOfHeroes());
        Game.createHeroGroup(HelloApplication.getNumberOfHeroes(), HelloApplication.getSelectedHeroes());
        Game.playGame();
        this.combatNumber = 0;
        this.combatNumberString.setText(Integer.toString(this.combatNumber));
        refreshListHeroesToHeal();
        this.heroesToHeal.setVisible(false);
        System.out.println(combatNumber);
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

    @FXML
    public void handleBtnAttack(){
        this.armorImg.setVisible(Game.context.isArmorOn());
        if (Game.context.status == Game.Status.HERO_TURN){
            if (Game.context.getHeroes().get(Game.context.getCurrentPositionHero()) instanceof Healer){
                healerAttack();
            }else {
                this.resultAttackHero = Game.context.attack(-1);
            }
            if (!this.resultAttackHero){
                this.enemyData.setText(Game.context.getEnemies().get(Game.context.getCurrentPositionEnemy()).displayData());
            }
            this.heroData.setText(Game.context.getHeroes().get(Game.context.getCurrentPositionHero()).displayData());
            hideActions();

            if (this.resultAttackHero){
                this.gameBtn.setText("Attaque du prochain ennemi");
                this.currentEnemy.setText("L'ennemi est mort !");
                this.enemyData.setVisible(false);
            }else {
                this.gameBtn.setText("Attaque de l'ennemi");
            }
            refreshListHeroesToHeal();
            if (Game.context.getHeroes().get(Game.context.getCurrentPositionHero()) instanceof Healer){
                this.gameBtn.setVisible(false);
            }else{
                this.gameBtn.setVisible(true);
            }
        }
    }

    @FXML
    public void handleBtnDefend(){
        Game.context.defend();
        this.armorImg.setVisible(Game.context.isArmorOn());
        hideActions();
        this.gameBtn.setVisible(true);
    }

    @FXML
    public void handleBtnUseConsumable(){
        hideActions();
        if (Game.context.getHeroes().get(Game.context.getCurrentPositionHero()) instanceof SpellCaster){
            this.foodBtn.setVisible(true);
            this.potionBtn.setVisible(true);
        }else {
            handleBtnFood();
        }
    }

    @FXML
    public void handleBtnFood(){
        this.useConsumableBtn.setVisible(false);
        this.foodBtn.setVisible(false);
        this.potionBtn.setVisible(false);
        this.consumables.getItems().clear();
        for (int i = 0; i < Game.context.getHeroes().get(Game.context.getCurrentPositionHero()).getLembdas().size(); i++){
            this.consumables.getItems().add(Game.context.getHeroes().get(Game.context.getCurrentPositionHero()).getLembdas().get(i).display());
        }
        this.consumables.setVisible(true);
        this.consumeBtn.setText("Manger");
        this.consumeBtn.setVisible(true);
        consumablesSelection();
    }

    @FXML
    public void handleBtnPotion(){
        this.useConsumableBtn.setVisible(false);
        this.foodBtn.setVisible(false);
        this.potionBtn.setVisible(false);
        this.consumables.getItems().clear();
        for (int i = 0; i < Game.context.getHeroes().get(Game.context.getCurrentPositionHero()).getPotions().size(); i++){
            this.consumables.getItems().add(Game.context.getHeroes().get(Game.context.getCurrentPositionHero()).getPotions().get(i).display());
        }
        this.consumables.setVisible(true);
        this.consumeBtn.setText("Boire");
        this.consumeBtn.setVisible(true);
        consumablesSelection();
    }

    @FXML
    public void handleBtnConsume(){
        consumablesSelection();
        if(this.consumeBtn.getText().equals("Manger")){
            Game.context.getHeroes().get(Game.context.getCurrentPositionHero()).useConsumable(this.indexConsumables, true);
        }else {
            Game.context.getHeroes().get(Game.context.getCurrentPositionHero()).useConsumable(this.indexConsumables, false);
        }
        Game.context.status = Game.Status.ENEMY_TURN;
        this.consumables.setVisible(false);
        this.consumeBtn.setVisible(false);
        this.heroData.setText(Game.context.getHeroes().get(Game.context.getCurrentPositionHero()).displayData());
        this.gameBtn.setVisible(true);
    }

    private void healerAttack(){
        this.heroesToHeal.setVisible(true);
        this.healBtn.setVisible(true);
        this.heroesToHeal.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                indexHeroToHeal = heroesToHeal.getSelectionModel().getSelectedIndex();
            }
        });
    }

    @FXML
    public void handleBtnHeal(){
        if (this.indexHeroToHeal >= 0){
            Game.context.attack(this.indexHeroToHeal);
            this.heroData.setText(Game.context.getHeroes().get(Game.context.getCurrentPositionHero()).displayData());
            this.healBtn.setVisible(false);
            this.heroesToHeal.setVisible(false);
            this.gameBtn.setVisible(true);
            Game.context.status = Game.Status.ENEMY_TURN;
        }
    }

    private void displayActions(){
        this.whatToDo.setVisible(true);
        this.whatToDo.setText("Que faire ?");
        this.attackBtn.setVisible(true);
        this.defendBtn.setVisible(true);
        this.useConsumableBtn.setVisible(true);
    }

    private void hideActions(){
        this.whatToDo.setVisible(false);
        this.attackBtn.setVisible(false);
        this.defendBtn.setVisible(false);
        this.useConsumableBtn.setVisible(false);
    }

    private void refreshListHeroesToHeal(){
        this.heroesToHeal.getItems().clear();
        for (int i = 0; i < Game.context.getHeroes().size(); i++){
            this.heroesToHeal.getItems().add(Game.context.getHeroes().get(i).displayType());
        }
    }

    private void consumablesSelection(){
        this.consumables.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                indexConsumables = consumables.getSelectionModel().getSelectedIndex();
            }
        });
    }

    private void displayActionsAfterVictory(){
        this.enhanceArmorBtn.setVisible(true);
        this.enhanceDamagesBtn.setVisible(true);
        this.enhanceConsumablesBtn.setVisible(true);
        this.enhanceQuantityConsumablesBtn.setVisible(true);
    }

    @FXML
    public void handleBtnEnhanceArmor(){
        hideActionsAfterVictory();
        Game.context.enhanceArmor(this.cursorHeroReward);
        this.cursorHeroReward++;
        Game.context.status = Game.Status.REWARDS_TIME;
        this.gameBtn.setText("Continuer le jeu");
        this.gameBtn.setVisible(true);
    }

    @FXML
    public void handleBtnEnhanceDamages(){
        hideActionsAfterVictory();
        Game.context.enhanceDamages(this.cursorHeroReward);
        this.cursorHeroReward++;
        Game.context.status = Game.Status.REWARDS_TIME;
        this.gameBtn.setText("Continuer le jeu");
        this.gameBtn.setVisible(true);
    }

    @FXML
    public void handleBtnEnhanceConsumables(){
        hideActionsAfterVictory();
        Game.context.enhanceConsumables(this.cursorHeroReward);
        this.cursorHeroReward++;
        Game.context.status = Game.Status.REWARDS_TIME;
        this.gameBtn.setText("Continuer le jeu");
        this.gameBtn.setVisible(true);
    }

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

    @FXML
    public void handleBtnEnhanceQuantityFood(){
        hideActionsAfterVictory();
        Game.context.enhanceQuantityFood(this.cursorHeroReward);
        this.cursorHeroReward++;
        Game.context.status = Game.Status.REWARDS_TIME;
        this.gameBtn.setText("Continuer le jeu");
        this.gameBtn.setVisible(true);
    }

    @FXML
    public void handleBtnEnhanceQuantityPotions(){
        hideActionsAfterVictory();
        Game.context.enhanceQuantityPotions(this.cursorHeroReward);
        this.cursorHeroReward++;
        Game.context.status = Game.Status.REWARDS_TIME;
        this.gameBtn.setText("Continuer le jeu");
        this.gameBtn.setVisible(true);
    }

    @FXML
    public void handleBtnEnhanceHero(){
        hideActionsAfterVictory();

        if (Game.context.getHeroes().get(this.cursorHeroReward) instanceof SpellCaster){
            this.enhanceSpellBtn.setVisible(true);
            this.reduceManaCostBtn.setVisible(true);
        }else {
            Game.context.enhanceHero(this.cursorHeroReward);
            this.cursorHeroReward++;
            Game.context.status = Game.Status.REWARDS_TIME;
            this.gameBtn.setText("Continuer le jeu");
            this.gameBtn.setVisible(true);
        }
        this.enhanceHeroBtn.setVisible(false);
    }

    public void handleBtnEnhanceSpell(){
        this.enhanceSpellBtn.setVisible(false);
        this.reduceManaCostBtn.setVisible(false);
        Game.context.enhanceHero(this.cursorHeroReward);
        this.cursorHeroReward++;
        Game.context.status = Game.Status.REWARDS_TIME;
        this.gameBtn.setText("Continuer le jeu");
        this.gameBtn.setVisible(true);
    }

    public void handleBtnReduceManaCost(){
        this.enhanceSpellBtn.setVisible(false);
        this.reduceManaCostBtn.setVisible(false);
        Game.context.reduceManaCost(this.cursorHeroReward);
        this.cursorHeroReward++;
        Game.context.status = Game.Status.REWARDS_TIME;
        this.gameBtn.setText("Continuer le jeu");
        this.gameBtn.setVisible(true);
    }

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
